package com.jackcode.Roomr.backend.model;

public enum RoomType {
    K1("Standard 1 King Bed"),
    K1T("Premium 1 King Bed"),
    K1W("1 King Bed w/ Whirlpool Tub"),
    Q2("Standard 2 Queen Beds"),
    Q2T("Premium 1 Queen Beds");

    private String description;

    RoomType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
