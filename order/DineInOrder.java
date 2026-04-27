package order;

/**
 * Dine-In order — adds 10% service charge.
 * Demonstrates: INHERITANCE + METHOD OVERRIDING
 */
public class DineInOrder extends Order {

    private static final double SERVICE_CHARGE_RATE = 0.10;
    private int tableNumber;

    public DineInOrder(int tableNumber) {
        super();
        this.tableNumber = tableNumber;
        System.out.println("  [Dine-In] Order created for Table #" + tableNumber);
    }

    public int getTableNumber() { return tableNumber; }

    @Override
    public double getSurchargeAmount() {
        return getSubtotal() * SERVICE_CHARGE_RATE;
    }

    @Override
    public String getSurchargeLabel() {
        return "Service Charge (10%)";
    }

    @Override
    public double calculateTotal() {
        return getSubtotal() + getSurchargeAmount();
    }

    @Override
    public String getOrderType() {
        return "Dine-In (Table #" + tableNumber + ")";
    }
}
