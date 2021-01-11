package com.jackcode.Roomr.backend.model;

public class Bathroom {
    private String description;
    private Integer numberOfSinks;
    private Integer numberOfShowerHeads;
    private Boolean hasTv;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNumberOfSinks() {
        return numberOfSinks;
    }

    public void setNumberOfSinks(Integer numberOfSinks) {
        this.numberOfSinks = numberOfSinks;
    }

    public Integer getNumberOfShowerHeads() {
        return numberOfShowerHeads;
    }

    public void setNumberOfShowerHeads(Integer numberOfShowerHeads) {
        this.numberOfShowerHeads = numberOfShowerHeads;
    }

    public Boolean getHasTv() {
        return hasTv;
    }

    public void setHasTv(Boolean hasTv) {
        this.hasTv = hasTv;
    }
}
