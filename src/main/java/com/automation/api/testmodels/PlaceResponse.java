package com.automation.api.testmodels;

public class PlaceResponse {

    private String status;
    private String place_id;
    private String scope;
    private String reference;
    private String id;

    // Default Constructor
    public PlaceResponse() {}

    // Parameterized Constructor
    public PlaceResponse(String status, String place_id, String scope, String reference, String id) {
        this.status = status;
        this.place_id = place_id;
        this.scope = scope;
        this.reference = reference;
        this.id = id;
    }

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
