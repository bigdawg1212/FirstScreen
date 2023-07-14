package pattki.firstscreen;

public class PartIdGenerator {

    // RUNTIME/Logical ERROR
    // Prevents the partIDs from starting from 0.
    private static int nextPartId = 1;

    public static int generateNextId() {
        return nextPartId++;
    }

    // FUTURE ENHANCEMENT
    // Also associate a time generated tied to when the product/part was added to the Inventory

}
