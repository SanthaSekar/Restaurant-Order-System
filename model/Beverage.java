package model;

/**
 * Represents a Beverage menu item.
 * Demonstrates: INHERITANCE from MenuItem + METHOD OVERRIDING
 */
public class Beverage extends MenuItem {

    private boolean isCold;

    public Beverage(int id, String name, double price, boolean isCold) {
        super(id, name, price, "Beverage");
        this.isCold = isCold;
    }

    public boolean isCold() { return isCold; }

    @Override
    public String getCategoryLabel() {
        return isCold ? "Beverage | Cold" : "Beverage | Hot";
    }

    @Override
    public void display() {
        System.out.println("  " + this);
        System.out.println("     Served " + (isCold ? "chilled." : "hot."));
    }
}
