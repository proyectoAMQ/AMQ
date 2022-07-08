package com.example.amq.models.paypal;

public class DtRefund {
    Amount amount;
    String invoice_id;

    public DtRefund(Amount amount, String invoice_id) {
        this.amount = amount;
        this.invoice_id = invoice_id;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public String getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(String invoice_id) {
        this.invoice_id = invoice_id;
    }
}
