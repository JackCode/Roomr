package com.jackcode.Roomr.backend.model;

public enum RoomType {
    K1("Standard 1 King Bed"),
    K1T("Premium 1 King Bed"),
    K1W("Premium 1 King Bed w/ Whirlpool Tub"),
    Q2("Standard 2 Queen Beds"),
    Q2T("Premium 2 Queen Beds"),
    K1RRU1("Standard 1 King Bed w/ Roll-In Shower"),
    K1RRC("UKNOWN TYPE- CONTACT JACKSON TO UPDATE"),
    K1RRD("UKNOWN TYPE- CONTACT JACKSON TO UPDATE"),
    Q2RRC("UKNOWN TYPE- CONTACT JACKSON TO UPDATE"),
    K1ZRU1("1 King Bed - Presidential Suite");

    private String description;

    RoomType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
