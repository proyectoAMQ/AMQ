package com.example.amq.models.paypal;

public class Amount {
    private String currency_code;
    private String value;
    private Breakdown breakdown;

    public Amount(String currency_code, String value, Breakdown breakdown) {
        this.currency_code = currency_code;
        this.value = value;
        this.breakdown = breakdown;
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

    public Breakdown getBreakdown() {
        return breakdown;
    }

    public void setBreakdown(Breakdown breakdown) {
        this.breakdown = breakdown;
    }
}
