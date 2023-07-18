package pattki.firstscreen.Model;

import pattki.firstscreen.PartIdGenerator;

/**
 * The `InHouse` class represents a part that is produced in-house.
 * It extends the `Part` class and includes additional information about the machine used to produce the part.
 */
public class InHouse extends Part{

    private int machineId;

    /**
     * Constructs an `InHouse` part with the specified attributes.
     *
     * id          the ID of the part
     * name        the name of the part
     * price       the price of the part
     * stock       the current stock level of the part
     * min         the minimum stock level of the part
     * max         the maximum stock level of the part
     * machineId   the ID of the machine used to produce the part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Returns the ID of the machine used to produce the part.
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * Sets the ID of the machine used to produce the part.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
