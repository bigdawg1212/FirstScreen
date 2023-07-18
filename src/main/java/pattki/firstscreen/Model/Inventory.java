package pattki.firstscreen.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The `Inventory` class represents the inventory system.
 * It manages the collection of parts and products in the inventory.
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds a new part to the inventory.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }
    /**
     * Adds a new product to the inventory.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }
    /**
     * Updates an existing part in the inventory.
     */
    public static void updatePart(int index, Part updatedPart) {
        allParts.set(index, updatedPart);
    }
    /**
     * Updates an existing product in the inventory.
     */
    public static void updateProduct(int index, Product updatedProduct) {
        allProducts.set(index, updatedProduct);
    }
    /**
     * Removes a part from the inventory.
     */
    public static void removePart(Part oldPart) { allParts.remove(oldPart); }
    /**
     * Removes a product from the inventory.
     */
    public static void removeProduct(Product oldProduct) { allProducts.remove(oldProduct); }
    /**
     * Returns the list of all parts in the inventory.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    /**
     * Returns the list of all products in the inventory.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
