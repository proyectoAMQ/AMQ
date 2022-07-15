package com.example.amq.models.paypal;

public class DtRefund {
    Amount amount;
    String invoice_id;
    Boolean final_capture;
    String note_to_payer;
    String soft_descriptor;


    public DtRefund(Amount amount, String invoice_id, Boolean final_capture, String note_to_payer, String soft_descriptor) {
        this.amount = amount;
        this.invoice_id = invoice_id;
        this.final_capture = final_capture;
        this.note_to_payer = note_to_payer;
        this.soft_descriptor = soft_descriptor;
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

    public Boolean getFinal_capture() {
        return final_capture;
    }

    public void setFinal_capture(Boolean final_capture) {
        this.final_capture = final_capture;
    }

    public String getNote_to_payer() {
        return note_to_payer;
    }

    public void setNote_to_payer(String note_to_payer) {
        this.note_to_payer = note_to_payer;
    }

    public String getSoft_descriptor() {
        return soft_descriptor;
    }

    public void setSoft_descriptor(String soft_descriptor) {
        this.soft_descriptor = soft_descriptor;
    }
}
