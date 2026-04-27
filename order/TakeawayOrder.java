package order;

/**
 * Takeaway order — adds flat packaging fee.
 * Demonstrates: INHERITANCE + METHOD OVERRIDING
 */
public class TakeawayOrder extends Order {

    private static final double PACKAGING_FEE = 30.0;

    public TakeawayOrder() {
        super();
        System.out.println("  For Takeaway - Order created.");
    }

    @Override
    public double getSurchargeAmount() {
        return PACKAGING_FEE;
    }

    @Override
    public String getSurchargeLabel() {
        return "Packaging Fee";
    }

    @Override
    public double calculateTotal() {
        return getSubtotal() + PACKAGING_FEE;
    }

    @Override
    public String getOrderType() {
        return "Takeaway";
    }
}
