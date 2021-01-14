package com.jackcode.Roomr.ui.adminView;

import com.jackcode.Roomr.backend.model.BathroomType;
import com.jackcode.Roomr.backend.model.Facing;
import com.jackcode.Roomr.backend.model.Room;
import com.jackcode.Roomr.backend.model.RoomType;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class RoomForm extends FormLayout {
    IntegerField roomNumber = new IntegerField("Room Number");
    ComboBox<RoomType> roomType = new ComboBox<>("Room Type");
    ComboBox<BathroomType> bathroomType = new ComboBox<>("Bathroom Type");
    IntegerField numberOfSinks = new IntegerField("Sinks");
    IntegerField numberOfShowerHeads = new IntegerField("Shower Heads");
    Checkbox hasTvInBathroom = new Checkbox("TV in Bathroom");
    IntegerField squareFootage = new IntegerField("Square Footage");
    Checkbox hasFireplace = new Checkbox("Fireplace");
    Checkbox hasBuiltInDrawers = new Checkbox("Built-In Drawers");
    Checkbox hasSofa = new Checkbox("Sofa");
    Checkbox hasSkylight = new Checkbox("Skylight");
    Checkbox hasBalcony = new Checkbox("Balcony");
    CheckboxGroup<Facing> facing = new CheckboxGroup<>();
    TextField connectingRooms = new TextField("Connecting Rooms");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Close");

    private Binder<Room> binder = new BeanValidationBinder<>(Room.class);
    private Room room;

    public RoomForm() {
        addClassName("room-form");
        binder.bindInstanceFields(this);
        setResponsiveSteps(new ResponsiveStep("40em", 1));
        setResponsiveSteps(new ResponsiveStep("40em", 2));
        setResponsiveSteps(new ResponsiveStep("40em", 3));

        configureBinder();
        configureMultivalueFields();

        add(roomNumber,
                roomType,
                squareFootage,
                hasFireplace,
                hasBuiltInDrawers,
                hasSofa,
                hasSkylight,
                hasBalcony,
                hasTvInBathroom,
                bathroomType,
                numberOfSinks,
                numberOfShowerHeads,
                facing,
                connectingRooms,
                createButtonsLayout());
        getChildren().forEach(child -> this.setColspan(child, 1));
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, room)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        save.setEnabled(false);

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            if (room != null) {
                binder.writeBean(room);
                fireEvent(new SaveEvent(this, room));
            }
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public void setRoom(Room room) {
        this.room = room;
        binder.readBean(room);
    }

    private void configureBinder() {

    }

    private void configureMultivalueFields() {
        bathroomType.setItems(BathroomType.values());
        bathroomType.setItemLabelGenerator(BathroomType::getDescription);
        bathroomType.setClearButtonVisible(true);

        roomType.setItems(RoomType.values());
        roomType.setClearButtonVisible(true);

        facing.setLabel("Facing");
        facing.setItems(Facing.values());
        facing.setItemLabelGenerator(Facing::getDescription);
    }

    public static abstract class RoomFormEvent extends ComponentEvent<RoomForm> {
        private Room room;

        protected RoomFormEvent(RoomForm source, Room room) {
            super(source, false);
            this.room = room;
        }

        public Room getRoom() {
            return room;
        }
    }

    public static class SaveEvent extends RoomFormEvent {
        SaveEvent(RoomForm source, Room room) {
            super(source, room);
        }
    }

    public static class DeleteEvent extends RoomFormEvent {
        DeleteEvent(RoomForm source, Room room) {
            super(source, room);
        }
    }

    public static class CloseEvent extends RoomFormEvent {
        CloseEvent(RoomForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
        ComponentEventListener<T> listener) {
    return getEventBus().addListener(eventType, listener);
    }
}
