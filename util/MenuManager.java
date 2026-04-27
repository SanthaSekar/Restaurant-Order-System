package util;

import model.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the restaurant menu.
 * Utility class to build, display, and search menu items.
 */
public class MenuManager {

    private List<MenuItem> menu;

    public MenuManager() {
        menu = new ArrayList<>();
        initializeMenu();
    }

    private void initializeMenu() {
        // Appetizers
        menu.add(new Appetizer(1, "Crispy Spring Rolls",   80.0,  false));
        menu.add(new Appetizer(2, "Spicy Chicken Wings",   120.0, true));
        menu.add(new Appetizer(3, "Paneer Tikka",          110.0, true));
        menu.add(new Appetizer(4, "Veg Soup",              60.0,  false));

        // Main Courses
        menu.add(new MainCourse(5, "Butter Chicken",       250.0, "Indian"));
        menu.add(new MainCourse(6, "Paneer Butter Masala", 220.0, "Indian"));
        menu.add(new MainCourse(7, "Veg Fried Rice",       180.0, "Chinese"));
        menu.add(new MainCourse(8, "Spaghetti Bolognese",  300.0, "Italian"));
        menu.add(new MainCourse(9, "Grilled Fish",         320.0, "Continental"));

        // Beverages
        menu.add(new Beverage(10, "Mango Lassi",   60.0,  true));
        menu.add(new Beverage(11, "Masala Chai",   30.0,  false));
        menu.add(new Beverage(12, "Cold Coffee",   80.0,  true));
        menu.add(new Beverage(13, "Fresh Lime Soda",55.0, true));
    }

    public void displayMenu() {
        String line = "─".repeat(60);
        System.out.println("\n" + line);
        System.out.println("                 SPICE GARDEN MENU");
        System.out.println(line);

        System.out.println("\n   APPETIZERS");
        System.out.println("  " + "─".repeat(55));
        for (MenuItem item : menu) {
            if (item instanceof Appetizer) item.display();
        }

        System.out.println("\n   MAIN COURSE");
        System.out.println("  " + "─".repeat(55));
        for (MenuItem item : menu) {
            if (item instanceof MainCourse) item.display();
        }

        System.out.println("\n   BEVERAGES");
        System.out.println("  " + "─".repeat(55));
        for (MenuItem item : menu) {
            if (item instanceof Beverage) item.display();
        }

        System.out.println("\n" + line);
    }

    public MenuItem findById(int id) {
        return menu.stream()
                   .filter(item -> item.getId() == id)
                   .findFirst()
                   .orElse(null);
    }

    public List<MenuItem> getMenu() { return menu; }
}
