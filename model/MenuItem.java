package model;

/**
 * Abstract base class for all menu items.
 * Demonstrates: ABSTRACTION + ENCAPSULATION
 */
public abstract class MenuItem {

    // ENCAPSULATION: private fields with getters
    private int id;
    private String name;
    private double price;
    private String category;

    public MenuItem(int id, String name, double price, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    // Getters (Encapsulation)
    public int getId()          { return id; }
    public String getName()     { return name; }
    public double getPrice()    { return price; }
    public String getCategory() { return category; }

    // ABSTRACTION: subclasses must implement their own display
    public abstract void display();

    // ABSTRACTION: subclasses must describe their category label
    public abstract String getCategoryLabel();

    @Override
    public String toString() {
        return String.format("[%d] %-22s Rs.%-8.2f  (%s)", id, name, price, getCategoryLabel());
    }
}
