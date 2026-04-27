package billing;

/**
 * Happy Hour billing — 20% flat discount, only on beverages conceptually
 * but applied to whole bill for simplicity.
 * Demonstrates: IMPLEMENTING BillingStrategy (Polymorphism)
 */
public class HappyHourBilling implements BillingStrategy {

    private static final double HAPPY_HOUR_DISCOUNT = 0.20;

    @Override
    public double applyBilling(double amount) {
        return amount * (1 - HAPPY_HOUR_DISCOUNT);
    }

    @Override
    public String getStrategyName() {
        return "Happy Hour Billing";
    }

    @Override
    public String getDescription(double amount) {
        double saving = amount * HAPPY_HOUR_DISCOUNT;
        return String.format("Happy Hour Discount (20%%): -Rs.%.2f  ", saving);
    }
}
