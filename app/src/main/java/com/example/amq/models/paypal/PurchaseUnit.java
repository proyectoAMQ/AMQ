package com.example.amq.models.paypal;

import java.util.List;

public class PurchaseUnit {
    private String reference_id = null;
    private Payee payee = null;
    private List<Item> items = null;
    private Amount amount = null;

    public PurchaseUnit(String reference_id, Payee payee, List<Item> items, Amount amount) {
        this.reference_id = reference_id;
        this.payee = payee;
        this.items = items;
        this.amount = amount;
    }

    public String getReference_id() {
        return reference_id;
    }

    public void setReference_id(String reference_id) {
        this.reference_id = reference_id;
    }

    public Payee getPayee() {
        return payee;
    }

    public void setPayee(Payee payee) {
        this.payee = payee;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }
}
