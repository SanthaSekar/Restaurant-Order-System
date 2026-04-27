package util;

import order.Order;
import model.MenuItem;
import billing.BillingStrategy;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Handles saving order details to:
 * 1. orders.txt  — human-readable receipt log
 * 2. orders.csv  — clean CSV for Excel and Power BI analysis
 *
 * BUG FIXES:
 *  - Discount was always 0.0       → now correctly calculated
 *  - GST was sometimes negative    → now always based on discounted amount
 *  - FinalAmount had float noise   → now rounded to 2 decimal places
 *  - CSV had one row per item      → now one row per ORDER (correct for analysis)
 */
public class FileHandler {

    private static final String TEXT_FILE = "orders.txt";
    private static final String CSV_FILE  = "orders.csv";

    // ================================
    // 1. SAVE TO TEXT FILE
    // ================================
    public static void saveOrder(Order order, BillingStrategy strategy, double grandTotal) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEXT_FILE, true))) {

            writer.write("========================================");
            writer.newLine();
            writer.write("ORDER ID    : " + order.getOrderId());
            writer.newLine();
            writer.write("DATE/TIME   : " +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
            writer.newLine();
            writer.write("ORDER TYPE  : " + order.getOrderType());
            writer.newLine();
            writer.write("STATUS      : " + order.getStatus());
            writer.newLine();
            writer.write("BILLING     : " + strategy.getStrategyName());
            writer.newLine();

            writer.write("----------------------------------------");
            writer.newLine();
            writer.write(String.format("%-24s %s", "ITEM", "PRICE (Rs.)"));
            writer.newLine();
            writer.write("----------------------------------------");
            writer.newLine();

            for (MenuItem item : order.getItems()) {
                writer.write(String.format("%-24s %.2f", item.getName(), item.getPrice()));
                writer.newLine();
            }

            writer.write("----------------------------------------");
            writer.newLine();
            writer.write(String.format("%-24s %.2f", "Subtotal", order.getSubtotal()));
            writer.newLine();
            writer.write(String.format("%-24s %.2f", order.getSurchargeLabel(), order.getSurchargeAmount()));
            writer.newLine();
            writer.write(String.format("%-24s %.2f", "GRAND TOTAL", grandTotal));
            writer.newLine();

            writer.write("========================================");
            writer.newLine();
            writer.newLine();

            System.out.println("\n   Order saved to '" + TEXT_FILE + "' successfully.");

        } catch (IOException e) {
            System.err.println("   Error saving order: " + e.getMessage());
        }
    }

    // ================================================================
    // 2. SAVE TO CSV — ONE ROW PER ORDER (Excel and Power BI ready)
    //
    //  Columns:
    //    OrderID, Date, Time, OrderType, Items, ItemCount,
    //    Subtotal, SurchargeType, SurchargeAmt, BillingStrategy,
    //    DiscountAmt, AmtAfterDiscount, GSTAmt, GrandTotal
    // ================================================================
    public static void saveOrderToCSV(Order order, BillingStrategy strategy,
                                      double grandTotal) {

        File file = new File(CSV_FILE);

        try (BufferedWriter w = new BufferedWriter(new FileWriter(file, true))) {

            // Write header only when file is brand new / empty
            if (file.length() == 0) {
                w.write("OrderID,Date,Time,OrderType,Items,ItemCount," +
                        "Subtotal,SurchargeType,SurchargeAmt," +
                        "BillingStrategy,DiscountAmt," +
                        "AmtAfterDiscount,GSTAmt,GrandTotal");
                w.newLine();
            }

            // ── Recalculate every figure cleanly ──────────────────
            double subtotal       = round2(order.getSubtotal());
            double surcharge      = round2(order.getSurchargeAmount());
            double afterSurcharge = round2(subtotal + surcharge);

            // Amount after billing strategy (discount) applied
            double afterBilling   = round2(strategy.applyBilling(afterSurcharge));

            // Discount = money saved (0 for NormalBilling)
            double discountAmt    = round2(afterSurcharge - afterBilling);

            // GST = GrandTotal - post-discount amount  (0 if no tax chosen)
            double gstAmt         = round2(grandTotal - afterBilling);
            if (gstAmt < 0) gstAmt = 0;   // safety guard

            double cleanTotal     = round2(grandTotal);

            // ── Build items list ──────────────────────────────────
            StringBuilder itemNames = new StringBuilder();
            for (int i = 0; i < order.getItems().size(); i++) {
                if (i > 0) itemNames.append(" | ");
                itemNames.append(order.getItems().get(i).getName());
            }
            int itemCount = order.getItems().size();

            // ── Date and Time ─────────────────────────────────────
            LocalDateTime now = LocalDateTime.now();
            String date = now.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            String time = now.format(DateTimeFormatter.ofPattern("HH:mm"));

            // Sanitise OrderType (remove commas to keep CSV clean)
            String orderType = order.getOrderType().replace(",", " ");

            // ── Write single summary row ──────────────────────────
            w.write(
                order.getOrderId()                    + "," +
                date                                  + "," +
                time                                  + "," +
                csvEscape(orderType)                  + "," +
                csvEscape(itemNames.toString())       + "," +
                itemCount                             + "," +
                subtotal                              + "," +
                csvEscape(order.getSurchargeLabel())  + "," +
                surcharge                             + "," +
                csvEscape(strategy.getStrategyName()) + "," +
                discountAmt                           + "," +
                afterBilling                          + "," +
                gstAmt                                + "," +
                cleanTotal
            );
            w.newLine();

            System.out.println("   Order saved to '" + CSV_FILE + "' successfully.");

        } catch (IOException e) {
            System.err.println("   Error saving CSV: " + e.getMessage());
        }
    }

    /** Round to 2 decimal places — eliminates floating-point noise */
    private static double round2(double val) {
        return Math.round(val * 100.0) / 100.0;
    }

    /** Wrap in quotes if value contains a comma */
    private static String csvEscape(String val) {
        if (val == null) return "";
        if (val.contains(",") || val.contains("\"")) {
            return "\"" + val.replace("\"", "\"\"") + "\"";
        }
        return val;
    }
}