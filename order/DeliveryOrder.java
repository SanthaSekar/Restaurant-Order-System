package order;

/**
 * Delivery order — adds delivery fee based on distance.
 * Demonstrates: INHERITANCE + METHOD OVERRIDING
 */
public class DeliveryOrder extends Order {

    private static final double DELIVERY_FEE_PER_KM = 10.0;
    private double distanceKm;
    private String deliveryAddress;

    public DeliveryOrder(String deliveryAddress, double distanceKm) {
        super();
        this.deliveryAddress = deliveryAddress;
        this.distanceKm      = distanceKm;
        System.out.println("  [Delivery] Order created. " + deliveryAddress
                           + " (" + distanceKm + " km)");
    }

    public String getDeliveryAddress() { return deliveryAddress; }
    public double getDistanceKm()      { return distanceKm; }

    @Override
    public double getSurchargeAmount() {
        return distanceKm * DELIVERY_FEE_PER_KM;
    }

    @Override
    public String getSurchargeLabel() {
        return "Delivery Fee (" + distanceKm + " km Rs.10)";
    }

    @Override
    public double calculateTotal() {
        return getSubtotal() + getSurchargeAmount();
    }

    @Override
    public String getOrderType() {
        return "Delivery " + deliveryAddress;
    }
}
