package com.jackcode.Roomr.backend.model;

public class Bathroom extends AbstractEntity {

    public enum BathroomType {
        WALK_IN_SHOWER("Walk-in Shower Only"),
        BATH_SHOWER_COMBO("Bathtub-Shower Combo"),
        SEPARATE_BATH_AND_SHOWER("Separate Bath and Walk-In Shower");

        private String description;

        BathroomType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return this.description;
        }
    }

    private BathroomType bathroomType;
    private Integer numberOfSinks;
    private Integer numberOfShowerHeads;
    private Boolean hasTv;

    public BathroomType getBathroomType() {
        return bathroomType;
    }

    public void setBathroomType(BathroomType bathroomType) {
        this.bathroomType = bathroomType;
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

    public Boolean hasTv() {
        return hasTv;
    }

    public void setHasTv(Boolean hasTv) {
        this.hasTv = hasTv;
    }
}
