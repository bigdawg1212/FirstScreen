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
     *
     * @param newPart the new part to be added
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds a new product to the inventory.
     *
     * @param newProduct the new product to be added
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Updates an existing part in the inventory.
     *
     * @param index        the index of the part to be updated
     * @param updatedPart  the updated part object
     */
    public static void updatePart(int index, Part updatedPart) {
        allParts.set(index, updatedPart);
    }

    /**
     * Updates an existing product in the inventory.
     *
     * @param index           the index of the product to be updated
     * @param updatedProduct  the updated product object
     */
    public static void updateProduct(int index, Product updatedProduct) {
        allProducts.set(index, updatedProduct);
    }

    /**
     * Removes a part from the inventory.
     *
     * @param oldPart the part to be removed
     */
    public static void removePart(Part oldPart) { allParts.remove(oldPart); }

    /**
     * Removes a product from the inventory.
     *
     * @param oldProduct the product to be removed
     */
    public static void removeProduct(Product oldProduct) { allProducts.remove(oldProduct); }

    /**
     * Returns the list of all parts in the inventory.
     *
     * @return the list of all parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Returns the list of all products in the inventory.
     *
     * @return the list of all products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
