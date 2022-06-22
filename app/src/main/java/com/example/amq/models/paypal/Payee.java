package com.example.amq.models.paypal;

public class Payee {
    private String email_address;
    private String merchant_id;

    public Payee(String email_address, String merchant_id) {
        this.email_address = email_address;
        this.merchant_id = merchant_id;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }
}
