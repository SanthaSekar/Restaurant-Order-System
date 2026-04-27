package model;

/**
 * Represents a Main Course menu item.
 * Demonstrates: INHERITANCE from MenuItem + METHOD OVERRIDING
 */
public class MainCourse extends MenuItem {

    private String cuisineType; // e.g., Indian, Chinese, Italian

    public MainCourse(int id, String name, double price, String cuisineType) {
        super(id, name, price, "Main Course");
        this.cuisineType = cuisineType;
    }

    public String getCuisineType() { return cuisineType; }

    @Override
    public String getCategoryLabel() {
        return "Main Course | " + cuisineType;
    }

    @Override
    public void display() {
        System.out.println("  " + this);
        System.out.println("     Cuisine: " + cuisineType);
    }
}
