package com.jackcode.Roomr.ui.listView;

public class RoomProperty {
    private String property;
    private String value;

    public RoomProperty(String property, String value) {
        this.property = property;
        this.value = value;
    }

    public RoomProperty() {
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
