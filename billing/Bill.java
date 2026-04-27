package billing;

import order.Order;
import model.MenuItem;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Generates and prints the formatted bill for an order.
 * Demonstrates: POLYMORPHISM via BillingStrategy
 */
public class Bill {

    private static final double TAX_RATE = 0.05; // 5% GST

    private Order order;
    private BillingStrategy billingStrategy;
    private boolean applyTax;

    private double grandTotal; // stored final value

    public Bill(Order order, BillingStrategy billingStrategy, boolean applyTax) {
        this.order = order;
        this.billingStrategy = billingStrategy;
        this.applyTax = applyTax;
    }

    public void printBill() {

        // Step 1: Calculate values
        double subtotal       = order.getSubtotal();
        double surcharge      = order.getSurchargeAmount();
        double afterSurcharge = subtotal + surcharge;

        double afterBilling   = billingStrategy.applyBilling(afterSurcharge);

        double tax            = applyTax ? afterBilling * TAX_RATE : 0;

        // IMPORTANT: store final total ONCE
        this.grandTotal = afterBilling + tax;

        // Formatting
        String line  = "─".repeat(50);
        String dline = "═".repeat(50);

        System.out.println("\n" + dline);
        System.out.println("           SPICE GARDEN RESTAURANT");
        System.out.println("              123 MG Road, Chennai");
        System.out.println("           Tel: +91 98765 43210");
        System.out.println(dline);

        System.out.printf("  Order #%-10d  Date: %s%n",
                order.getOrderId(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));

        System.out.println("  Order Type : " + order.getOrderType());
        System.out.println("  Status     : " + order.getStatus());
        System.out.println("  Billing    : " + billingStrategy.getStrategyName());

        System.out.println(line);
        System.out.printf("  %-26s  %8s%n", "ITEM", "PRICE");
        System.out.println(line);

        for (MenuItem item : order.getItems()) {
            System.out.printf("  %-26s  Rs.%6.2f%n", item.getName(), item.getPrice());
        }

        System.out.println(line);
        System.out.printf("  %-26s  Rs.%6.2f%n", "Subtotal", subtotal);
        System.out.printf("  %-26s  Rs.%6.2f%n", order.getSurchargeLabel(), surcharge);

        // Discount / billing adjustment display
        if (afterSurcharge != afterBilling) {
            System.out.printf("  %-26s  Rs.%6.2f%n",
                    billingStrategy.getDescription(afterSurcharge),
                    afterBilling - afterSurcharge);
        }

        // Tax display
        if (applyTax) {
            System.out.printf("  %-26s  Rs.%6.2f%n", "GST (5%)", tax);
        }

        System.out.println(dline);
        System.out.printf("  %-26s  Rs.%6.2f%n", "GRAND TOTAL", this.grandTotal);
        System.out.println(dline);

        System.out.println("    Thank you for dining with us!");
        System.out.println("    Visit again at www.spicegarden.in");
        System.out.println(dline + "\n");
    }

    public double getGrandTotal() {
        return this.grandTotal;
    }
}