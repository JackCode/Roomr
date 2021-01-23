package com.jackcode.Roomr.ui.admin.rooms;

import com.jackcode.Roomr.backend.model.Room;
import com.jackcode.Roomr.backend.service.RoomService;
import com.jackcode.Roomr.security.ILAY.SecuredByRole;
import com.jackcode.Roomr.ui.admin.AdminView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Route(value="admin/rooms", layout = AdminView.class)
@PageTitle("Admin - Rooms | Roomr")
@CssImport("./styles/admin-styles.css")
@SecuredByRole("ROLE_Admin")
public class AdminEditRooms extends VerticalLayout {

    private final RoomService roomService;
    private final Grid<Room> roomGrid = new Grid<>(Room.class);
    private final IntegerField roomFilter = new IntegerField();
    private final Checkbox notUpdated = new Checkbox("Needs Photos");
    private RoomForm form;

    @Autowired
    public AdminEditRooms(RoomService roomService) {
        this.roomService = roomService;
        addClassName("entry-view");
        setSizeFull();

        configureRoomGrid();
        configureRoomForm();

        Div content = new Div(roomGrid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolbar(), content);
        updateList();
        closeEditor();
    }

    private void configureRoomForm() {
        form = new RoomForm();
        form.addListener(RoomForm.SaveEvent.class, this::saveContact);
        form.addListener(RoomForm.DeleteEvent.class, this::deleteContact);
        form.addListener(RoomForm.CloseEvent.class, e -> closeEditor());
    }

    private void configureRoomGrid() {
        roomGrid.addClassName("room-grid");
        roomGrid.setSizeFull();
        roomGrid.setColumns("roomNumber");
        roomGrid.asSingleSelect().addValueChangeListener(event -> editRoom(event.getValue()));
    }

    private void updateList() {
        List<Room> roomNumberFilterResults = roomService.findAll(String.valueOf(roomFilter.getValue()));
        List<Room> filterResults = new ArrayList<>();

        if (notUpdated.getValue()) {
            for (Room room : roomNumberFilterResults) {
                if (!room.getIsUpdated()) {
                    filterResults.add(room);
                }
            }
        } else {
            filterResults = roomNumberFilterResults;
        }


        roomGrid.setItems(filterResults);
    }

    public void editRoom(Room room) {
        if (room == null) {
            closeEditor();
        } else {
            form.setRoom(room);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setRoom(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void saveContact(RoomForm.SaveEvent event) {
        roomService.save(event.getRoom());
        updateList();
        closeEditor();
    }

    private void deleteContact(RoomForm.DeleteEvent event) {
        roomService.delete(event.getRoom());
        updateList();
        closeEditor();
    }

    private HorizontalLayout getToolbar() {
        roomFilter.setClearButtonVisible(true);
        roomFilter.setPlaceholder("Room Number...");
        roomFilter.setValueChangeMode(ValueChangeMode.LAZY);
        roomFilter.addValueChangeListener(e -> updateList());

        notUpdated.addValueChangeListener(e -> updateList());

        Button addRoomButton = new Button("Add Room");
        addRoomButton.addClickListener(click -> addRoom());
        addRoomButton.setEnabled(false);

        HorizontalLayout toolbar = new HorizontalLayout(roomFilter, notUpdated, addRoomButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addRoom() {
        roomGrid.asSingleSelect().clear();
        editRoom(new Room());
    }
}
