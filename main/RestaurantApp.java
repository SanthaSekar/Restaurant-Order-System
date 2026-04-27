package main;

import model.*;
import order.*;
import billing.*;
import util.*;

import java.util.Scanner;

public class RestaurantApp {

    static Scanner scanner = new Scanner(System.in);
    static MenuManager menuManager = new MenuManager();

    public static void main(String[] args) {

        printBanner();

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1 -> runOrderFlow();
                case 2 -> menuManager.displayMenu();
                case 3 -> runDemoMode();
                case 0 -> {
                    running = false;
                    System.out.println("\n  Goodbye! Visit again.\n");
                }
                default -> System.out.println("  Invalid choice. Try again.");
            }
        }
        scanner.close();
    }

    // ─── Main order flow ────────────────────────────────────────
    private static void runOrderFlow() {
        menuManager.displayMenu();

        // 1. Create order
        Order order = selectOrderType();
        if (order == null) return;

        // 2. Add items
        addItemsToOrder(order);
        if (order.getItems().isEmpty()) {
            System.out.println("  No items added. Order cancelled.");
            return;
        }

        // 3. Status flow
        System.out.println("\n  ── Order Status Tracking ──");
        order.advanceStatus();
        order.advanceStatus();

        // 4. Billing strategy
        BillingStrategy strategy = selectBillingStrategy();

        // 5. Tax
        System.out.print("\n  Apply GST (5%)? (y/n): ");
        boolean applyTax = scanner.next().trim().equalsIgnoreCase("y");

        // 6. Generate bill
        Bill bill = new Bill(order, strategy, applyTax);
        bill.printBill();

        // 7. Mark as paid
        order.advanceStatus();

        double finalAmount = bill.getGrandTotal();

        FileHandler.saveOrder(order, strategy, finalAmount);
        FileHandler.saveOrderToCSV(order, strategy, finalAmount);
    }

    // ─── Order type selection ────────────────────────────────────
    private static Order selectOrderType() {
        System.out.println("\n  Select Order Type:");
        System.out.println("  [1] Dine-In");
        System.out.println("  [2] Takeaway");
        System.out.println("  [3] Delivery");
        int choice = readInt("  Choice: ");

        return switch (choice) {
            case 1 -> {
                int table = readInt("  Enter Table Number: ");
                yield new DineInOrder(table);
            }
            case 2 -> new TakeawayOrder();
            case 3 -> {
                scanner.nextLine();
                System.out.print("  Enter Delivery Address: ");
                String address = scanner.nextLine().trim();
                double dist = readDouble("  Distance in km: ");
                yield new DeliveryOrder(address, dist);
            }
            default -> {
                System.out.println("    Invalid order type.");
                yield null;
            }
        };
    }

    // ─── Item selection ──────────────────────────────────────────
    private static void addItemsToOrder(Order order) {
        System.out.println("\n  ── Add Items to Order ──");
        System.out.println("  (Enter item ID to add; enter 0 when done)\n");

        while (true) {
            int id = readInt("  Item ID: ");
            if (id == 0) break;

            MenuItem item = menuManager.findById(id);
            if (item != null) {
                order.addItem(item);
            } else {
                System.out.println("    Item ID " + id + " not found.");
            }
        }
    }

    // ─── Billing strategy selection ──────────────────────────────
    private static BillingStrategy selectBillingStrategy() {
        System.out.println("\n  Select Billing Strategy:");
        System.out.println("  [1] Normal Billing");
        System.out.println("  [2] Discount Billing (15% off)");
        System.out.println("  [3] Happy Hour Billing (20% off)");
        int choice = readInt("  Choice: ");

        return switch (choice) {
            case 2 -> new DiscountBilling(0.15);
            case 3 -> new HappyHourBilling();
            default -> new NormalBilling();
        };
    }

    // ─── Demo mode ──────────────────────────────────────────────
    // PURPOSE: Shows all 4 OOP concepts automatically for demonstration.
    // NOTE: Demo orders are NOT saved to orders.csv (keeps your data clean).
    //       Only real orders placed via option [1] are saved.
    private static void runDemoMode() {
        System.out.println("\n  ╔══════════════════════════════════════════════╗");
        System.out.println("  ║           OOP CONCEPTS USING JAVA            ║");
        System.out.println("  ║       RESTAURANT AND MENU SIMULATION         ║");
        System.out.println("  ╚══════════════════════════════════════════════╝");

        System.out.println("\n  Abstraction + Inheritance");
        System.out.println("  MenuItem: Appetizer/MainCourse/Beverage extend it.");
        menuManager.displayMenu();

        System.out.println("  Private fields accessed only via getters:");
        var item = menuManager.findById(5);
        System.out.println("  item.getName()  = " + item.getName());
        System.out.println("  item.getPrice() = Rs." + item.getPrice());
        System.out.println("  item.getId()    = " + item.getId());

        System.out.println(" ");

        DineInOrder dineIn = new DineInOrder(3);
        dineIn.addItem(menuManager.findById(5));  // Butter Chicken Rs.250
        dineIn.addItem(menuManager.findById(10)); // Mango Lassi    Rs.60
        System.out.printf("  DineInOrder.calculateTotal()   = Rs.%.2f  (+10%% service)%n",
                dineIn.calculateTotal());

        TakeawayOrder takeaway = new TakeawayOrder();
        takeaway.addItem(menuManager.findById(5));
        takeaway.addItem(menuManager.findById(10));
        System.out.printf("  TakeawayOrder.calculateTotal() = Rs.%.2f  (+Rs.30 packaging)%n",
                takeaway.calculateTotal());

        DeliveryOrder delivery = new DeliveryOrder("Anna Nagar", 4.0);
        delivery.addItem(menuManager.findById(5));
        delivery.addItem(menuManager.findById(10));
        System.out.printf("  DeliveryOrder.calculateTotal() = Rs.%.2f  (+Rs.40 delivery)%n",
                delivery.calculateTotal());

        dineIn.advanceStatus(); // PLACED → PREPARING
        dineIn.advanceStatus(); // PREPARING → SERVED

        BillingStrategy[] strategies = {
            new billing.NormalBilling(),
            new billing.DiscountBilling(0.15),
            new billing.HappyHourBilling()
        };

        for (BillingStrategy s : strategies) {
            Bill b = new Bill(dineIn, s, true);
            b.printBill();
        }

    }

    // ─── Helpers ────────────────────────────────────────────────
    private static void printBanner() {
        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║        RESTAURANT ORDER & MENU SIMULATION            ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
    }

    private static void printMainMenu() {
        System.out.println("\n  ┌─────────────────────────────┐");
        System.out.println("  │       MAIN MENU             │");
        System.out.println("  │  [1] Place an Order         │");
        System.out.println("  │  [2] View Menu Only         │");
        System.out.println("  │  [3] Generate Bill          │");
        System.out.println("  │  [0] Exit                   │");
        System.out.println("  └─────────────────────────────┘");
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.next().trim());
            } catch (NumberFormatException e) {
                System.out.println("    Please enter a valid number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.next().trim());
            } catch (NumberFormatException e) {
                System.out.println("    Please enter a valid number.");
            }
        }
    }
}