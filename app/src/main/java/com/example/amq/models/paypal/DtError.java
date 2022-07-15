package com.example.amq.models.paypal;

import java.util.List;

public class DtError {
    String name;
    String message;
    String debug_id;
    List<Details> details;
    private List<Link> links;

    public DtError(String name, String message, String debug_id, List<Details> details, List<Link> links) {
        this.name = name;
        this.message = message;
        this.debug_id = debug_id;
        this.details = details;
        this.links = links;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDebug_id() {
        return debug_id;
    }

    public void setDebug_id(String debug_id) {
        this.debug_id = debug_id;
    }

    public List<Details> getDetails() {
        return details;
    }

    public void setDetails(List<Details> details) {
        this.details = details;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}