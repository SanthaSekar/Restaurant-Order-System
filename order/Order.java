package order;

import model.MenuItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for all order types.
 * Demonstrates: ABSTRACTION + ENCAPSULATION
 */
public abstract class Order {

    // Order status enum for state tracking
    public enum OrderStatus {
        PLACED, PREPARING, SERVED, PAID
    }

    private static int orderCounter = 1000;

    private int orderId;
    private List<MenuItem> items;
    private OrderStatus status;

    public Order() {
        this.orderId  = ++orderCounter;
        this.items    = new ArrayList<>();
        this.status   = OrderStatus.PLACED;
    }

    // Add a menu item to the order
    public void addItem(MenuItem item) {
        items.add(item);
        System.out.println("  Added: " + item.getName() + "  (Rs." + item.getPrice() + ")");
    }

    // Compute raw subtotal
    public double getSubtotal() {
        return items.stream().mapToDouble(MenuItem::getPrice).sum();
    }

    // ABSTRACTION: each order type adds its own fees
    public abstract double calculateTotal();

    // ABSTRACTION: label for the surcharge line on the bill
    public abstract String getSurchargeLabel();

    public abstract double getSurchargeAmount();

    // Status management
    public void advanceStatus() {
        switch (status) {
            case PLACED:     status = OrderStatus.PREPARING; break;
            case PREPARING:  status = OrderStatus.SERVED;    break;
            case SERVED:     status = OrderStatus.PAID;      break;
            default:         System.out.println("Order already PAID.");
        }
        System.out.println("Order #" + orderId + " status → " + status);
    }

    // Getters
    public int getOrderId()         { return orderId; }
    public List<MenuItem> getItems(){ return items; }
    public OrderStatus getStatus()  { return status; }
    public abstract String getOrderType();
}
