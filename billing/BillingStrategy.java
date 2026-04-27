package billing;

/**
 * Interface for billing strategies.
 * Demonstrates: INTERFACE-BASED POLYMORPHISM
 *
 * Any class implementing this can be swapped in at RUNTIME
 * — that is Runtime Polymorphism via interfaces.
 */
public interface BillingStrategy {

    /**
     * Apply the billing rule to the raw order total.
     * @param amount  total before billing adjustment
     * @return        final amount after applying the strategy
     */
    double applyBilling(double amount);

    /** Human-readable label shown on the bill. */
    String getStrategyName();

    /** Description of the discount / surcharge for the bill printout. */
    String getDescription(double amount);
}
