package com.example.amq.models.paypal;

public class Breakdown {
    private ItemTotal item_total;

    public Breakdown(ItemTotal item_total) {
        this.item_total = item_total;
    }

    public ItemTotal getItem_total() {
        return item_total;
    }

    public void setItem_total(ItemTotal item_total) {
        this.item_total = item_total;
    }

}
