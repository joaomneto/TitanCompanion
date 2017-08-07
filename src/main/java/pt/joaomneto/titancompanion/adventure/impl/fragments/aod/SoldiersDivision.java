package pt.joaomneto.titancompanion.adventure.impl.fragments.aod;

/**
 * Created by Joao Neto on 26/04/17.
 */

public class SoldiersDivision {
    private String type;
    private int quantity;
    private int initialQuantity;

    public SoldiersDivision(String type, int quantity) {
        this.type = type;
        this.quantity = quantity;
        this.initialQuantity = quantity;
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

    public void resetToInitialValues(){
        quantity = initialQuantity;
    }

    public void incrementAllValues(){
        quantity+=5;
        initialQuantity = quantity;
    }

    public void decrementAllValues(){
        quantity=Math.max(0, quantity-5);
        initialQuantity = quantity;
    }

    public void setInitialQuantity(int initialQuantity) {
        this.initialQuantity = initialQuantity;
    }
}