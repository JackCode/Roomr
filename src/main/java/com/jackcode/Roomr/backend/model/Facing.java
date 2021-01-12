package com.jackcode.Roomr.backend.model;

import java.util.stream.Stream;

public enum Facing {
    VINE("Vine"),
    SIXTH("6th"),
    ALLEY("Alley"),
    INTERIOR("Interior");

    private String description;

    Facing(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Stream<Facing> stream() {
        return Stream.of(Facing.values());
    }
}
