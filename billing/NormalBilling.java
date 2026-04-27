package billing;

/**
 * Normal billing — no discount, no surcharge.
 * Demonstrates: IMPLEMENTING BillingStrategy (Polymorphism)
 */
public class NormalBilling implements BillingStrategy {

    @Override
    public double applyBilling(double amount) {
        return amount; // no change
    }

    @Override
    public String getStrategyName() {
        return "Normal Billing";
    }

    @Override
    public String getDescription(double amount) {
        return "No discount applied.";
    }
}
