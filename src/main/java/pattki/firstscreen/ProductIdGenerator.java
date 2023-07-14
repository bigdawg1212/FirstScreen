package pattki.firstscreen;

public class ProductIdGenerator {

    // RUNTIME/Logical ERROR
    // Prevents the partIDs from starting from 0.
    private static int nextProductId = 1;

    public static int generateNextId() {
        return nextProductId++;
    }

    // FUTURE ENHANCEMENT
    // Also associate a time generated tied to when the product/part was added to the Inventory

}
