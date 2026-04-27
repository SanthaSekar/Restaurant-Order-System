package billing;

/**
 * Discount billing — applies a configurable % discount.
 * Demonstrates: IMPLEMENTING BillingStrategy (Polymorphism)
 */
public class DiscountBilling implements BillingStrategy {

    private double discountRate; // e.g., 0.15 = 15%

    public DiscountBilling(double discountRate) {
        this.discountRate = discountRate;
    }

    @Override
    public double applyBilling(double amount) {
        return amount * (1 - discountRate);
    }

    @Override
    public String getStrategyName() {
        return "Discount Billing (" + (int)(discountRate * 100) + "% off)";
    }

    @Override
    public String getDescription(double amount) {
        double saving = amount * discountRate;
        return String.format("Discount (%.0f%%): -Rs.%.2f", discountRate * 100, saving);
    }
}
