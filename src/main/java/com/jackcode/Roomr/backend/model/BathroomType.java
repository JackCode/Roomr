package com.jackcode.Roomr.backend.model;

import java.util.stream.Stream;

public enum BathroomType {
    WALK_IN_SHOWER("Walk-in Shower Only"),
    BATH_SHOWER_COMBO("Bathtub-Shower Combo"),
    SEPARATE_BATH_AND_SHOWER("Separate Bath and Walk-In Shower"),
    ROLL_IN_SHOWER("Roll-in Shower"),
    ACCESSIBLE_TUB("Accessible Tub-Shower Combo");

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
