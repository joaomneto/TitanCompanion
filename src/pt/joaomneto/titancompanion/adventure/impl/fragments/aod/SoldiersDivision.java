package pt.joaomneto.titancompanion.adventure.impl.fragments.aod;

/**
 * Created by joao on 26/04/17.
 */

public class SoldiersDivision {
    private String type;
    private int quantity;

    public SoldiersDivision(String type, int quantity) {
        this.type = type;
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}