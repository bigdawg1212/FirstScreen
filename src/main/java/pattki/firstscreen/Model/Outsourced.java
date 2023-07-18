package pattki.firstscreen.Model;

import pattki.firstscreen.PartIdGenerator;

/**
 * The `Outsourced` class represents an outsourced part in the inventory system.
 * It extends the `Part` class and adds additional properties specific to outsourced parts.
 */
public class Outsourced extends Part{

    private String companyName;

    /**
     * Constructs a new `Outsourced` part with the specified properties.
     *
     * id           the ID of the part
     * name         the name of the part
     * price        the price of the part
     * stock        the current stock level of the part
     * min          the minimum stock level of the part
     * max          the maximum stock level of the part
     * companyName  the name of the company associated with the outsourced part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Returns the name of the company associated with the outsourced part.
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets the name of the company associated with the outsourced part.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
