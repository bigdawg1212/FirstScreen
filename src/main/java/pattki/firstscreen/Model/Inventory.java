package pattki.firstscreen.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    public static void removePart(Part oldPart) { allParts.remove(oldPart); }

    public static void removeProduct(Product oldProduct) { allProducts.remove(oldProduct); }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Part> getAllFilteredParts() {
        return filteredParts;
    }

    public static ObservableList<Product> getAllFilteredProducts() {
        return filteredProducts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    // End of TableView Video two
    // Testing adding the remaining methods in Inventory class IAW UML diagram







}
