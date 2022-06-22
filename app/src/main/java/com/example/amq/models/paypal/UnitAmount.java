package com.example.amq.models.paypal;

public class UnitAmount {
    private String currency_code;
    private String value;

    public UnitAmount(String currency_code, String value) {
        this.currency_code = currency_code;
        this.value = value;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
