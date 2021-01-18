package com.jackcode.Roomr.backend.model;

public enum RoomType {
    K1("Standard 1 King Bed"),
    K1T("Premium 1 King Bed"),
    K1W("1 King Bed w/ Whirlpool Tub"),
    Q2("Standard 2 Queen Beds"),
    Q2T("Premium 2 Queen Beds"),
    K1RRU1("1 King Bed - Hannaford Suite"),
    K1RRC("Standard 1 King Bed w/ Accessible Tub"),
    K1RRD("Standard 1 King Bed w/ Roll-in Shower"),
    K1RRV("Standard 1 King Bed - Hearing Accessible/Accessible Tub"),
    Q2RRC("Standard 2 Queen Beds w/ Accessible Tub"),
    Q2RRD("Standard 2 Queen Beds w/ Roll-in Shower"),
    Q2RRV("Standard 2 Queen Beds - Hearing Accessible/Accessible Tub"),
    K1ZRU1("1 King Bed - Presidential Suite");

    private String description;

    RoomType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
