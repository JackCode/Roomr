package com.jackcode.Roomr.ui.listView;

import com.jackcode.Roomr.backend.model.Facing;
import com.jackcode.Roomr.backend.model.Room;
import com.jackcode.Roomr.backend.service.ImageService;
import com.jackcode.Roomr.exceptions.ExceptionDialog;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.shared.Registration;
import de.codecamp.vaadin.components.messagedialog.MessageDialog;

import java.util.ArrayList;
import java.util.List;

public class RoomView extends VerticalLayout {
    private Room room;
    private final H2 title = new H2();
    private final Grid<RoomProperty> propertyGrid = new Grid<>(RoomProperty.class);
    private final Grid<Image> imageGrid = new Grid<>();
    private final SplitLayout propertyImagesSplitLayout;
    private List<Image> images;
    private Boolean firstSplitLayoutResize;

    private final Button closeButton = new Button("Close");

    private final ImageService imageService;


    
    public RoomView(ImageService imageService) {
        this.imageService = imageService;
        addClassName("room-view-form");

        configurePropertyGrid();
        configureImageGrid();
        configureTitle();
        configureCloseButton();

        propertyImagesSplitLayout = new SplitLayout();
        propertyImagesSplitLayout.addToPrimary(propertyGrid);
        propertyImagesSplitLayout.addToSecondary(imageGrid);
        propertyImagesSplitLayout.setOrientation(SplitLayout.Orientation.HORIZONTAL);
        propertyImagesSplitLayout.addClassName("room-content");
        propertyImagesSplitLayout.addSplitterDragendListener(e -> updateImageSize());
        propertyImagesSplitLayout.setPrimaryStyle("minWidth", "400px");
        propertyImagesSplitLayout.setSecondaryStyle("minWidth", "300px");
        propertyImagesSplitLayout.setSizeFull();

        expand(title);
        add(title, closeButton, propertyImagesSplitLayout);
    }

    private void configureCloseButton() {
        closeButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        closeButton.addClickShortcut(Key.ESCAPE);
        closeButton.addClickListener(event -> fireEvent(new CloseEvent(this)));
    }

    private void configureImageGrid() {
        imageGrid.addClassName("image-grid");
        imageGrid.setSelectionMode(Grid.SelectionMode.NONE);
        imageGrid.addColumnResizeListener(e->updateImageSize());
        imageGrid.addComponentColumn(image -> image);
        imageGrid.setSizeFull();
    }

    // You have to use this to set the images first to prevent the grid
    // from having too small of rows
    private void updateImageGrid() {
        images = imageService.getImagesForRoom(room.getRoomNumber());

        if (images == null || images.isEmpty()) {
            Image voidImage = new Image();
            voidImage.setAlt("No photos for this room");
            images.add(voidImage);
        } else {
            images.forEach(image -> image.setHeight("200px"));
            images.forEach(image -> image.setWidth("-1"));
            images.forEach(image -> image.addClickListener(event -> showImageOverlay(image)));
        }
        imageGrid.setItems(images);
        firstSplitLayoutResize = true;
    }

    // You can then update the size and such, but have to create a new list
    // this is mildly expensive and should be fixed one day
    private void updateImageSize() {
        if (firstSplitLayoutResize) {
            List<Image> updatedImages = new ArrayList<>();
            for (Image curImage : this.images) {
                updatedImages.add(new Image(curImage.getSrc(), String.valueOf(curImage.getAlt())));
            }
            String splitWidth = propertyImagesSplitLayout.getWidth();
            updatedImages.forEach(image -> image.setWidth(splitWidth));
            updatedImages.forEach(image -> image.setWidth("100%"));
            updatedImages.forEach(image -> image.addClickListener(event -> showImageOverlay(image)));
            imageGrid.setItems(updatedImages);
        }
        firstSplitLayoutResize = false;
    }

    private void showImageOverlay(Image image) {
        Image overlayImage = new Image();
        overlayImage.addClassName("overlay-image");
        overlayImage.setSrc(image.getSrc());
        overlayImage.setAlt("Error displaying image.");
        overlayImage.setHeight("500px");
        overlayImage.setWidth("-1");

        MessageDialog imageDialog = new MessageDialog();
        imageDialog.setTitle("Room " + room.getRoomNumber());
        overlayImage.addClickListener(e -> imageDialog.close());
        imageDialog.addButton().icon(VaadinIcon.CLOSE_SMALL).closeOnClick();
        imageDialog.add(new VerticalLayout(overlayImage));
        imageDialog.open();
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
            imageGrid.setVisible(false);
            updateImageGrid();
            imageGrid.setVisible(true);
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
                boundedProperties.add(new RoomProperty("Shower - Body Sprayer",
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
