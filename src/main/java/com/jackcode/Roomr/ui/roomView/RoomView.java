package com.jackcode.Roomr.ui.roomView;

import com.jackcode.Roomr.backend.model.Bathroom;
import com.jackcode.Roomr.backend.model.Facing;
import com.jackcode.Roomr.backend.model.Room;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.ArrayList;
import java.util.List;

public class RoomView extends VerticalLayout {
    private Room room;
    private H2 title = new H2();
    private Grid<RoomProperty> propertyGrid = new Grid<>(RoomProperty.class);
    private Grid<Image> imageGrid = new Grid<>();


    public RoomView() {
        addClassName("room-view-form");

        configurePropertyGrid();
        configureImageGrid();
        configureTitle();

        Div content = new Div(propertyGrid, imageGrid);
        content.addClassName("room-content");
        content.setSizeFull();
        add(title, content);
    }

    private void configureImageGrid() {
        imageGrid.addClassName("image-grid");
        imageGrid.addComponentColumn(image -> image);
        imageGrid.setSelectionMode(Grid.SelectionMode.NONE);
        imageGrid.setSizeFull();
    }

    private void updateImageGrid() {
        List<Image> images = new ArrayList<>();
        room.getPhotos().forEach(url -> images.add(new Image(url.toString(), "Photo Not Found")));
        images.forEach(Image::setWidthFull);
//        images.forEach(image -> image.setHeight("-1"));
        images.forEach(image -> image.addClickListener(event -> {
                getUI().get().getPage().open(event.getSource().getSrc(), "_blank");
            }));
        imageGrid.setItems(images);
    }

    private void configureTitle() {
        title.setClassName("room-heading");
    }

    private void configurePropertyGrid() {
        propertyGrid.addClassName("properties");
        propertyGrid.setSizeFull();
        propertyGrid.setSelectionMode(Grid.SelectionMode.NONE);
        propertyGrid.addThemeVariants(
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);
    }

    public void setRoom(Room room) {
        this.room = room;
        if (room != null) {
            title.setText(room.getRoomNumber().toString()
                    + " - "
                    + room.getRoomType()
                    + " (" + room.getRoomType().getDescription() + ")");
            propertyGrid.setItems(bindRoomProperties());
            updateImageGrid();
        } else {
            ; // Clear images when RoomView is closed
        }
    }

    private List<RoomProperty> bindRoomProperties() {
        List<RoomProperty> boundedProperties = new ArrayList<>();

        if (room != null) {
            Bathroom bRoom = room.getBathroom();
            boundedProperties.add(new RoomProperty("Floor", room.getFloor().toString()));
            boundedProperties.add(new RoomProperty("Square Footage", room.getSquareFootage().toString()));
            boundedProperties.add(new RoomProperty("Fireplace", room.getHasFireplace() ? "Yes" : "No"));
            boundedProperties.add(new RoomProperty("Bathroom Style", bRoom.getBathroomType().getDescription()));
            boundedProperties.add(new RoomProperty("Number of Sinks", bRoom.getNumberOfSinks().toString()));
            boundedProperties.add((new RoomProperty("Number of Shower Heads",
                    bRoom.getNumberOfShowerHeads().toString())));
            boundedProperties.add(new RoomProperty("TV in Bathroom", bRoom.getHasTv() ? "Yes" : "No"));
            boundedProperties.add(new RoomProperty("Connecting Rooms", getConnectingRoomsString()));
            boundedProperties.add(new RoomProperty("Facing", getFacingString()));
            boundedProperties.add(new RoomProperty("Balcony", room.getHasBalcony() ? "Yes" : "No"));
            boundedProperties.add(new RoomProperty("Skylight", room.getHasSkylight() ? "Yes" : "No"));
            boundedProperties.add(new RoomProperty("Sofa", room.getHasSofa() ? "Yes" : "No"));
            boundedProperties.add(new RoomProperty("Built-in Drawers",
                    room.getHasBuiltInDrawers() ? "Yes" : "No"));
        } else {
            boundedProperties.add(new RoomProperty("No Properties Found", "-"));
        }
        return boundedProperties;
    }

    private String getFacingString() {
        StringBuffer facingString = new StringBuffer();
        for (Facing facing : room.getFacing()) {
            facingString.append(facing.getDescription() + "; ");
        }
        return facingString.toString();
    }

    private String getConnectingRoomsString() {
        StringBuffer connectingRoomsString = new StringBuffer();
        for (Integer connectingRoom : room.getConnectingRooms()) {
            connectingRoomsString.append(connectingRoom);
            connectingRoomsString.append("; ");
        }
        return connectingRoomsString.toString();
    }
}
