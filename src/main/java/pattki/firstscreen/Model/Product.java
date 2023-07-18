package pattki.firstscreen.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The `Product` class represents a product in the inventory system.
 * It contains properties and methods to manage the product and its associated parts.
 */
public class Product {

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    private ObservableList<Part> associatedParts;

    /**
     * Constructs a new `Product` with the specified properties.
     *
     * @param id    the ID of the product
     * @param name  the name of the product
     * @param price the price of the product
     * @param stock the current stock level of the product
     * @param min   the minimum stock level of the product
     * @param max   the maximum stock level of the product
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.associatedParts = FXCollections.observableArrayList();
    }

    /**
     * Returns the ID of the product.
     *
     * @return the ID of the product
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the product.
     *
     * @param id the ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of the product.
     *
     * @return the name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the price of the product.
     *
     * @return the price of the product
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the product.
     *
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns the current stock level of the product.
     *
     * @return the stock level of the product
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets the current stock level of the product.
     *
     * @param stock the stock level to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Returns the minimum stock level of the product.
     *
     * @return the minimum stock level of the product
     */
    public int getMin() {
        return min;
    }

    /**
     * Sets the minimum stock level of the product.
     *
     * @param min the minimum stock level to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Returns the maximum stock level of the product.
     *
     * @return the maximum stock level of the product
     */
    public int getMax() {
        return max;
    }

    /**
     * Sets the maximum stock level of the product.
     *
     * @param max the maximum stock level to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Adds an associated part to the product.
     *
     * @param part the part to add as an associated part
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Removes an associated part from the product.
     *
     * @param selectedAssociatedPart the associated part to remove
     * @return `true` if the associated part was removed successfully, `false` otherwise
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * Returns all the associated parts of the product.
     *
     * @return an `ObservableList` containing all the associated parts of the product
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    };
}
