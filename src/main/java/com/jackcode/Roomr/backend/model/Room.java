package com.jackcode.Roomr.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.net.URL;
import java.util.List;
import java.util.Set;

@Entity
public class Room extends AbstractEntity implements Cloneable, Serializable {

    @NotNull
    private Integer roomNumber;

    @JsonIgnore
    private Integer floor;
    private RoomType roomType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bathroom_id")
    private Bathroom bathroom;

    private Integer squareFootage;
    private Boolean hasFireplace;
    private Boolean hasBuiltInDrawers;
    private Boolean hasSofa;
    private Boolean hasSkylight;
    private Boolean hasBalcony;

    public Room() {
    }

    @ElementCollection(targetClass = Integer.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Integer> connectingRooms;

    @ElementCollection(targetClass = Facing.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Facing> facing;

    @ElementCollection(targetClass = URL.class, fetch = FetchType.EAGER)
    private List<URL> photos;

    private void setFloor() {
        this.floor = (this.roomNumber / 10) / 10;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
        this.setFloor();
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

    public Set<Integer> getConnectingRooms() {
        return connectingRooms;
    }

    public void setConnectingRooms(Set<Integer> connectingRooms) {
        this.connectingRooms = connectingRooms;
    }

    public Set<Facing> getFacing() {
        return facing;
    }

    public void setFacing(Set<Facing> facing) {
        this.facing = facing;
    }

    public List<URL> getPhotos() {
        return photos;
    }

    public void setPhotos(List<URL> photos) {
        this.photos = photos;
    }

    public Integer getFloor() {
        return floor;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                '}';
    }
}
