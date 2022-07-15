package com.example.amq.models.paypal;

import java.util.List;

public class DtRefundReponse {
    private String id;
    private String status;
    private List<Link> links;

    public DtRefundReponse(String id, String status, List<Link> links) {
        this.id = id;
        this.status = status;
        this.links = links;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
