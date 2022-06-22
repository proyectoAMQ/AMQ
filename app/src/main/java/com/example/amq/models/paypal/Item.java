package com.example.amq.models.paypal;

public class Item {
    private String name;
    private String description;
    private String quantity;
    private UnitAmount unit_amount;

    public Item(String name, String description, String quantity, UnitAmount unit_amount) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.unit_amount = unit_amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public UnitAmount getUnit_amount() {
        return unit_amount;
    }

    public void setUnit_amount(UnitAmount unit_amount) {
        this.unit_amount = unit_amount;
    }
}
