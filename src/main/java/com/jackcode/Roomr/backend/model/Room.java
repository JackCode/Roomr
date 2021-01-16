package com.jackcode.Roomr.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Document(collection = "rooms")
public class Room extends AbstractDocument {

    private @NotNull String roomNumber;

    @JsonIgnore
    private Integer floor;

    @NotNull
    private RoomType roomType;

    @NotNull
    private BathroomType bathroomType;

    @NotNull
    private Integer numberOfSinks;
    @NotNull
    private Integer numberOfShowerHeads;
    @NotNull
    private Integer squareFootage;
    @NotNull
    private Boolean hasFireplace;
    @NotNull
    private Boolean hasBuiltInDrawers;
    @NotNull
    private Boolean hasSofa;
    @NotNull
    private Boolean hasSkylight;
    @NotNull
    private Boolean hasBalcony;
    @NotNull
    private Boolean hasTvInBathroom;

    public Room() {
    }

    private String connectingRooms;

    private Set<Facing> facing;

    private List<String> photos;

    private void setFloor() {
        this.floor = (Integer.valueOf(roomNumber) / 10) / 10;
    }

    public @NotNull String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(@NotNull String roomNumber) {
        this.roomNumber = roomNumber;
        this.setFloor();
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Integer getSquareFootage() {
        return squareFootage;
    }

    public void setSquareFootage(Integer squareFootage) {
        this.squareFootage = squareFootage;
    }

    public Boolean getHasFireplace() {
        return hasFireplace;
    }

    public void setHasFireplace(Boolean hasFireplace) {
        this.hasFireplace = hasFireplace;
    }

    public Boolean getHasBuiltInDrawers() {
        return hasBuiltInDrawers;
    }

    public void setHasBuiltInDrawers(Boolean hasBuiltInDrawers) {
        this.hasBuiltInDrawers = hasBuiltInDrawers;
    }

    public Boolean getHasSofa() {
        return hasSofa;
    }

    public void setHasSofa(Boolean hasSofa) {
        this.hasSofa = hasSofa;
    }

    public Boolean getHasSkylight() {
        return hasSkylight;
    }

    public void setHasSkylight(Boolean hasSkylight) {
        this.hasSkylight = hasSkylight;
    }

    public Boolean getHasBalcony() {
        return hasBalcony;
    }

    public void setHasBalcony(Boolean hasBalcony) {
        this.hasBalcony = hasBalcony;
    }

    public String getConnectingRooms() {
        return connectingRooms;
    }

    public void setConnectingRooms(String connectingRooms) {
        this.connectingRooms = connectingRooms;
    }

    public Set<Facing> getFacing() {
        return facing;
    }

    public void setFacing(Set<Facing> facing) {
        this.facing = facing;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public Integer getFloor() {
        return floor;
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

    public Boolean getHasTvInBathroom() {
        return hasTvInBathroom;
    }

    public void setHasTvInBathroom(Boolean hasTvInBathroom) {
        this.hasTvInBathroom = hasTvInBathroom;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                '}';
    }
}
