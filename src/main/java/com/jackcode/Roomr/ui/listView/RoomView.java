package com.jackcode.Roomr.ui.listView;

import com.jackcode.Roomr.backend.model.Facing;
import com.jackcode.Roomr.backend.model.Room;
import com.jackcode.Roomr.exceptions.ExceptionDialog;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.shared.Registration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RoomView extends VerticalLayout {
    private Room room;
    private final H4 title = new H4();
    private final Grid<RoomProperty> propertyGrid = new Grid<>(RoomProperty.class);
    private final VerticalLayout imageLayout = new VerticalLayout();
    private SplitLayout propertyImagesSplitLayout;
    private final Button closeButton = new Button();

    private final Map<String, List<Image>> allRoomImages;
    
    public RoomView(Map<String, List<Image>> allRoomImages) {
        this.allRoomImages = allRoomImages;
        addClassName("room-view-form");

        configurePropertyGrid();
        configureSplitLayout();
        configureTitle();
        configureCloseButton();

        HorizontalLayout hL = new HorizontalLayout(title, closeButton);
        title.setWidthFull();
        hL.setFlexGrow(1, title);
        add(hL, propertyImagesSplitLayout);
    }

    private void configureSplitLayout() {
        propertyImagesSplitLayout = new SplitLayout();
        propertyImagesSplitLayout.addToPrimary(propertyGrid);
        propertyImagesSplitLayout.addToSecondary(imageLayout);
        propertyImagesSplitLayout.setOrientation(SplitLayout.Orientation.HORIZONTAL);
        propertyImagesSplitLayout.addClassName("room-content");
        propertyImagesSplitLayout.setPrimaryStyle("minWidth", "400px");
        propertyImagesSplitLayout.setSecondaryStyle("minWidth", "300px");
        propertyImagesSplitLayout.setSizeFull();
    }

    private void configureCloseButton() {
        closeButton.setIcon(new Icon(VaadinIcon.CLOSE_SMALL));
        closeButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        closeButton.addClickShortcut(Key.ESCAPE);
        closeButton.addClickListener(event -> fireEvent(new CloseEvent(this)));
    }

    private void updateImageGrid() {
        imageLayout.removeAll();
        propertyImagesSplitLayout.setSplitterPosition(75);
        if (allRoomImages.getOrDefault(room.getRoomNumber(), null) != null) {
            for (Image image : allRoomImages.get(room.getRoomNumber())) {
                image.setWidth("100%");
                image.setHeight("-1");
                image.addClickListener(event -> showImageOverlay(image));
                imageLayout.add(image);
            }
        } else {
            imageLayout.add(new Label("No photos for this room."));
        }
    }

    private void showImageOverlay(Image image) {
        if(getUI().isPresent()) {
            getUI().get().getPage().open(image.getSrc(), "_blank");
        }
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
            title.setText(room.getRoomNumber()
                    + " - "
                    + room.getRoomType()
                    + " (" + room.getRoomType().getDescription() + ")");
            propertyGrid.setItems(bindRoomProperties());
            updateImageGrid();
        }
    }

    private List<RoomProperty> bindRoomProperties() {
        List<RoomProperty> boundedProperties = new ArrayList<>();

        if (room != null) {
            try {
                boundedProperties.add(new RoomProperty("Floor", room.getFloor().toString()));
                boundedProperties.add(new RoomProperty("Square Footage", room.getSquareFootage().toString()));
                boundedProperties.add(new RoomProperty("Fireplace", room.getHasFireplace() ? "Yes" : "No"));
                boundedProperties.add(new RoomProperty("Bathroom Style", room.getBathroomType().getDescription()));
                boundedProperties.add(new RoomProperty("Number of Sinks", room.getNumberOfSinks().toString()));
                boundedProperties.add((new RoomProperty("Number of Shower Heads",
                        room.getNumberOfShowerHeads().toString())));
                boundedProperties.add(new RoomProperty("Shower Spa",
                        room.getHasBodyShower() ? "Yes" : "No"));
                boundedProperties.add(new RoomProperty("TV in Bathroom", room.getHasTvInBathroom() ? "Yes" : "No"));
                boundedProperties.add(new RoomProperty("Connecting Rooms", room.getConnectingRooms()));
                boundedProperties.add(new RoomProperty("Facing", getFacingString()));
                boundedProperties.add(new RoomProperty("Balcony", room.getHasBalcony() ? "Yes" : "No"));
                boundedProperties.add(new RoomProperty("Skylight", room.getHasSkylight() ? "Yes" : "No"));
                boundedProperties.add(new RoomProperty("Sofa", room.getHasSofa() ? "Yes" : "No"));
                boundedProperties.add(new RoomProperty("Built-in Drawers",
                        room.getHasBuiltInDrawers() ? "Yes" : "No"));
                boundedProperties.add(new RoomProperty("Notes", room.getNotes()));
            } catch (NullPointerException ex) {
                new ExceptionDialog("E1000", "Something went wrong. Error Code: E1000");
            }
        } else {
            boundedProperties.add(new RoomProperty("No Properties Found", "-"));
        }
        return boundedProperties;
    }

    private String getFacingString() {
        StringBuilder facingString = new StringBuilder();
        for (Facing facing : room.getFacing()) {
            facingString.append(facing.getDescription()).append(" ");
        }
        return facingString.toString();
    }

    public static abstract class RoomViewEvent extends ComponentEvent<RoomView> {

        protected RoomViewEvent(RoomView source) {
            super(source, false);
        }
    }
    

    public static class CloseEvent extends RoomView.RoomViewEvent {
        CloseEvent(RoomView source) {
            super(source);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
