package com.example.amq.models.paypal;

public class DtRefundReponse {
    private String id;

    public DtRefundReponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
