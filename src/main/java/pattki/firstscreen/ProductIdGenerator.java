package pattki.firstscreen;

/**
 * The `ProductIdGenerator` class generates unique IDs for products.
 * It provides a method to generate the next available product ID.
 */
public class ProductIdGenerator {

    // RUNTIME/Logical ERROR
    // Prevents the partIDs from starting from 0.
    private static int nextProductId = 1;

    /**
     * Generates the next available product ID.
     *
     * @return the next product ID
     */
    public static int generateNextId() {
        return nextProductId++;
    }

    // FUTURE ENHANCEMENT
    // Also associate a time generated tied to when the product/part was added to the Inventory

}
