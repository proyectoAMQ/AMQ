package com.example.amq.models.paypal;

public class Details {
    String field;
    String value;
    String location;
    String issue;
    String description;

    public Details(String field, String value, String location, String issue, String description) {
        this.field = field;
        this.value = value;
        this.location = location;
        this.issue = issue;
        this.description = description;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
