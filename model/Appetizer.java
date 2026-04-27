package model;

/**
 * Represents an Appetizer menu item.
 * Demonstrates: INHERITANCE from MenuItem + METHOD OVERRIDING
 */
public class Appetizer extends MenuItem {

    private boolean isSpicy;

    public Appetizer(int id, String name, double price, boolean isSpicy) {
        super(id, name, price, "Appetizer");
        this.isSpicy = isSpicy;
    }

    public boolean isSpicy() { return isSpicy; }

    @Override
    public String getCategoryLabel() {
        return isSpicy ? "Appetizer  Spicy" : "Appetizer";
    }

    @Override
    public void display() {
        System.out.println("  " + this);
        System.out.println("     Served before the main course." + (isSpicy ? " (Contains chilli)" : ""));
    }
}
