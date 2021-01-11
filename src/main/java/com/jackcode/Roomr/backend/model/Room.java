package com.jackcode.Roomr.backend.model;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import java.net.URL;
import java.util.List;


public class Room extends AbstractEntity implements Cloneable {

    private Integer roomNumber;
    private Integer floor;
    private RoomType roomType;
    private Bathroom bathroom;
    private Integer squareFootage;

    private Boolean hasFireplace;
    private Boolean hasBuiltInDrawers;
    private Boolean hasSofa;
    private Boolean hasSkylight;
    private Boolean hasBalcony;

    private List<Integer> connectingRooms;
    private List<String> facing;
    private List<URL> photos;

    @PostConstruct
    private void setFloor() {
        this.floor = (this.roomNumber / 10) / 10;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Bathroom getBathroom() {
        return bathroom;
    }

    public void setBathroom(Bathroom bathroom) {
        this.bathroom = bathroom;
    }

    public Integer getSquareFootage() {
        return squareFootage;
    }

    public void setSquareFootage(Integer squareFootage) {
        this.squareFootage = squareFootage;
    }

    public Boolean hasFireplace() {
        return hasFireplace;
    }

    public void setHasFireplace(Boolean hasFireplace) {
        this.hasFireplace = hasFireplace;
    }

    public Boolean hasBuiltInDrawers() {
        return hasBuiltInDrawers;
    }

    public void setHasBuiltInDrawers(Boolean hasBuiltInDrawers) {
        this.hasBuiltInDrawers = hasBuiltInDrawers;
    }

    public Boolean hasSofa() {
        return hasSofa;
    }

    public void setHasSofa(Boolean hasSofa) {
        this.hasSofa = hasSofa;
    }

    public Boolean hasSkylight() {
        return hasSkylight;
    }

    public void setHasSkylight(Boolean hasSkylight) {
        this.hasSkylight = hasSkylight;
    }

    public Boolean hasBalcony() {
        return hasBalcony;
    }

    public void setHasBalcony(Boolean hasBalcony) {
        this.hasBalcony = hasBalcony;
    }

    public List<Integer> getConnectingRooms() {
        return connectingRooms;
    }

    public void setConnectingRooms(List<Integer> connectingRooms) {
        this.connectingRooms = connectingRooms;
    }

    public List<String> getFacing() {
        return facing;
    }

    public void setFacing(List<String> facing) {
        this.facing = facing;
    }

    public List<URL> getPhotos() {
        return photos;
    }

    public void setPhotos(List<URL> photos) {
        this.photos = photos;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                '}';
    }
}
