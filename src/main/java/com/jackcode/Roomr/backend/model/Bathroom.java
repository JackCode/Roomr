package com.jackcode.Roomr.backend.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.stream.Stream;

@Entity
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

        public static Stream<BathroomType> stream() {
            return Stream.of(BathroomType.values());
        }
    }

    private BathroomType bathroomType;
    private Integer numberOfSinks;
    private Integer numberOfShowerHeads;
    private Boolean hasTv;

    public Bathroom(BathroomType bathroomType, Integer numberOfSinks, Integer numberOfShowerHeads, Boolean hasTv) {
        this.bathroomType = bathroomType;
        this.numberOfSinks = numberOfSinks;
        this.numberOfShowerHeads = numberOfShowerHeads;
        this.hasTv = hasTv;
    }

    public Bathroom() {
    }

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

    public Boolean getHasTv() {
        return hasTv;
    }

    public void setHasTv(Boolean hasTv) {
        this.hasTv = hasTv;
    }
}
