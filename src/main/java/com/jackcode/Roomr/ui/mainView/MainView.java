package com.jackcode.Roomr.ui.mainView;

import com.jackcode.Roomr.backend.model.Bathroom;
import com.jackcode.Roomr.backend.model.Facing;
import com.jackcode.Roomr.backend.model.Room;
import com.jackcode.Roomr.backend.model.RoomType;
import com.jackcode.Roomr.backend.service.RoomService;
import com.jackcode.Roomr.ui.roomView.RoomView;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route("")
@CssImport("./styles/shared-styles.css")
public class MainView extends VerticalLayout {

    // Visual Components
    private final Grid<Room> roomGrid = new Grid<>();
    private final RoomView roomView;
    private final Accordion filterAccordion = new Accordion();
    private final HorizontalLayout filterLayout = new HorizontalLayout();
    private FormLayout filterForm = new FormLayout();

    // Filter Components
    ListDataProvider<Room> dataProvider;
    List<Room> roomList;
    TextField roomNumberFilter = new TextField("Room Number");
    CheckboxGroup<String> tvInBathroomFilter = new CheckboxGroup();
    CheckboxGroup<RoomType> roomTypeFilter = new CheckboxGroup<>();
    CheckboxGroup<String> bathroomTypeFilter = new CheckboxGroup<>();
    CheckboxGroup<String> fireplaceFilter = new CheckboxGroup();
    CheckboxGroup<String> balconyFilter = new CheckboxGroup();
    CheckboxGroup<String> skylightFilter = new CheckboxGroup();
    CheckboxGroup<String> sofaFilter = new CheckboxGroup();
    CheckboxGroup<Integer> showerHeadFilter = new CheckboxGroup();
    CheckboxGroup<String> facingFilter = new CheckboxGroup<>();
    CheckboxGroup<Integer> floorFilter = new CheckboxGroup<>();
    Button clearFiltersButton = new Button("Clear Filters");

    public MainView(RoomService roomService) {
        // Backend Components
        addClassName("list-view");
        setSizeFull();

        roomList = roomService.findAll();
        dataProvider = new ListDataProvider<>(roomList);

        configureFilterComponents();
        configureRoomGrid();
        configureFilterAccordion();
        configureClearFiltersButton();

        roomView = new RoomView();
        roomView.setSizeFull();

        Div content = new Div(roomGrid, roomView);
        content.addClassName("content");
        content.setSizeFull();
        content.setMaxHeight("600px");

        HorizontalLayout topFilters = new HorizontalLayout(roomNumberFilter, roomTypeFilter, clearFiltersButton);

        Div allContent = new Div(topFilters, filterAccordion, content);
        allContent.addClassName("all-content");
        allContent.setSizeFull();

        add(allContent);

        closeRoomView();
    }

    private void configureClearFiltersButton() {
        clearFiltersButton.setClassName("clear-filters-btn");
        clearFiltersButton.addClickListener(e -> clearFilters());
    }

    private void clearFilters() {
        roomNumberFilter.clear();
        fireplaceFilter.clear();
        balconyFilter.clear();
        skylightFilter.clear();
        showerHeadFilter.clear();
        sofaFilter.clear();
        tvInBathroomFilter.clear();
        roomTypeFilter.clear();
        facingFilter.clear();
        bathroomTypeFilter.clear();
        floorFilter.clear();
    }

    private void configureFilterAccordion() {
        filterAccordion.add("Additional Filters", filterForm);
        filterAccordion.close();
    }

    private void configureFilterComponents() {
        // Room Number Filter
        roomNumberFilter.setClearButtonVisible(true);
        roomNumberFilter.setMaxWidth("100px");
        roomNumberFilter.setValueChangeMode(ValueChangeMode.LAZY);
        roomNumberFilter.addValueChangeListener(e -> applyFilter());

        // TV in Bathroom Filter
        tvInBathroomFilter.setLabel("TV in Bathroom");
        tvInBathroomFilter.setItems("Yes", "No");
        tvInBathroomFilter.addValueChangeListener(e -> applyFilter());

        // Room Type Filter
        roomTypeFilter.setLabel("Room Type");
        roomTypeFilter.addClassName("room-type-filter");
        roomTypeFilter.setItems(RoomType.values());
        roomTypeFilter.addValueChangeListener(e -> applyFilter());

        // Bathroom Type Filter
        bathroomTypeFilter.setLabel("Bathroom Style");
        List<String> bTypes = new ArrayList<>();
        Bathroom.BathroomType.stream().forEach(type -> {
            bTypes.add(type.getDescription());
        });
        bathroomTypeFilter.setItems(bTypes);
        bathroomTypeFilter.addValueChangeListener(e -> applyFilter());

        // Fireplace Filter
        fireplaceFilter.setLabel("Fireplace");
        fireplaceFilter.setItems("Yes", "No");
        fireplaceFilter.addValueChangeListener(e -> applyFilter());

        // Balcony Filter
        balconyFilter.setLabel("Balcony");
        balconyFilter.setItems("Yes", "No");
        balconyFilter.addValueChangeListener(e -> applyFilter());

        // Skylight Filter
        skylightFilter.setLabel("Skylight");
        skylightFilter.setItems("Yes", "No");
        skylightFilter.addValueChangeListener(e -> applyFilter());

        // Sofa Filter
        sofaFilter.setLabel("Sofa");
        sofaFilter.setItems("Yes", "No");
        sofaFilter.addValueChangeListener(e -> applyFilter());

        // Showerhead Filter
        showerHeadFilter.setLabel("Shower Heads");
        showerHeadFilter.setItems(1, 2);
        showerHeadFilter.addValueChangeListener(e -> applyFilter());

        // Facing Filter
        facingFilter.setLabel("Facing");
        List<String> facingTypes = new ArrayList<>();
        Facing.stream().forEach(type -> {
            facingTypes.add(type.getDescription());
        });
        facingFilter.setItems(facingTypes);
        facingFilter.addValueChangeListener(e -> applyFilter());

        // Floor Filter
        floorFilter.setLabel("Floor");
        floorFilter.setItems(2, 3, 4, 5, 6, 7, 8);
        floorFilter.addValueChangeListener(e -> applyFilter());

        filterForm.add(
                fireplaceFilter,
                balconyFilter,
                skylightFilter,
                showerHeadFilter,
                sofaFilter,
                tvInBathroomFilter,
                facingFilter,
                bathroomTypeFilter,
                floorFilter);

        filterForm.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0em", 1),
                new FormLayout.ResponsiveStep("40em", 2),
                new FormLayout.ResponsiveStep("40em", 3),
                new FormLayout.ResponsiveStep("40em", 4),
                new FormLayout.ResponsiveStep("40em", 5),
                new FormLayout.ResponsiveStep("40em", 6),
                new FormLayout.ResponsiveStep("40em", 7),
                new FormLayout.ResponsiveStep("40em", 8)
        );

        filterForm.setColspan(facingFilter, 2);
        filterForm.setColspan(bathroomTypeFilter, 4);
        filterForm.setColspan(floorFilter, 3);
    }

    private void configureRoomGrid() {
        roomGrid.setDataProvider(dataProvider);
        roomGrid.addClassName("room-grid");
        roomGrid.setSizeFull();
        roomGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        this.addColumns();
        roomGrid.asSingleSelect().addValueChangeListener(event -> showRoom(event.getValue()));
    }

    private void addColumns() {
        roomGrid.removeAllColumns();
        roomGrid.addColumn(Room::getRoomNumber).setHeader("Room Number");
        roomGrid.addColumn(Room::getRoomType).setHeader("Room Type");
        roomGrid.addColumn(room -> {
            Bathroom bathroom = room.getBathroom();
            return bathroom == null ? "-" : bathroom.getBathroomType().getDescription();
        }).setHeader("Bathroom Style");
        roomGrid.addColumn(Room::getSquareFootage).setHeader("Square Footage");
        roomGrid.addColumn(Room::getHasFireplace).setHeader("Has Fireplace");
        roomGrid.addColumn(Room::getHasBalcony).setHeader("Has Balcony");
        roomGrid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void removeColumns() {
        roomGrid.removeAllColumns();
        roomGrid.addColumn(Room::getRoomNumber).setHeader("Room Number");
        roomGrid.addColumn(Room::getRoomType).setHeader("Room Type");
    }

    private void showRoom(Room room) {
        if (room == null) {
            closeRoomView();
        } else {
            this.removeColumns();  // Use to remove columns when room view form opens
            roomView.setRoom(room);
            roomView.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeRoomView() {
        this.addColumns();
        roomView.setRoom(null);
        roomView.setVisible(false);
        removeClassName("editing");
    }

    private void applyFilter() {
        dataProvider.clearFilters();

        // Room Number Filter
        if (roomNumberFilter != null) {
            dataProvider.addFilter(room -> room
                    .getRoomNumber().toString()
                    .contains(roomNumberFilter.getValue().trim()));
        }

        // TV in Bathroom Filter
        if (!tvInBathroomFilter.getValue().isEmpty() &&
                !(tvInBathroomFilter.getValue().contains("Yes") && tvInBathroomFilter.getValue().contains("No"))) {
            dataProvider.addFilter(room -> {
                if (tvInBathroomFilter.getValue().contains("Yes")) {
                    return room.getBathroom().getHasTv();
                } else {
                    return !room.getBathroom().getHasTv();
                }
            });
        }

        // Room Type Filter
        if (!roomTypeFilter.getValue().isEmpty()) {
            dataProvider.addFilter(room -> roomTypeFilter.getValue().contains(room.getRoomType()));
        }

        // Bathroom Type Filter
        if (!bathroomTypeFilter.getValue().isEmpty()) {
            dataProvider.addFilter(room -> bathroomTypeFilter
                    .getValue()
                    .contains(room.getBathroom().getBathroomType().getDescription()));
        }

        // Fireplace Filter
        if (!fireplaceFilter.getValue().isEmpty() &&
                !(fireplaceFilter.getValue().contains("Yes") && fireplaceFilter.getValue().contains("No"))) {
            dataProvider.addFilter(room -> {
             if (fireplaceFilter.getValue().contains("Yes")) {
                 return room.getHasFireplace();
             } else {
                 return !room.getHasFireplace();
             }
            });
        }

        // Balcony Filter
        if (!balconyFilter.getValue().isEmpty() &&
                !(balconyFilter.getValue().contains("Yes") && balconyFilter.getValue().contains("No"))) {
            dataProvider.addFilter(room -> {
                if (balconyFilter.getValue().contains("Yes")) {
                    return room.getHasBalcony();
                } else {
                    return !room.getHasBalcony();
                }
            });
        }

        // Skylight Filter
        if (!skylightFilter.getValue().isEmpty() &&
                !(skylightFilter.getValue().contains("Yes") && skylightFilter.getValue().contains("No"))) {
            dataProvider.addFilter(room -> {
                if (skylightFilter.getValue().contains("Yes")) {
                    return room.getHasSkylight();
                } else {
                    return !room.getHasSkylight();
                }
            });
        }

        // Sofa Filter
        if (!sofaFilter.getValue().isEmpty() &&
                !(sofaFilter.getValue().contains("Yes") && sofaFilter.getValue().contains("No"))) {
            dataProvider.addFilter(room -> {
                if (sofaFilter.getValue().contains("Yes")) {
                    return room.getHasSofa();
                } else {
                    return !room.getHasSofa();
                }
            });
        }

        // Showerhead Filter
        if (!showerHeadFilter.getValue().isEmpty()) {
            dataProvider.addFilter(room ->
                showerHeadFilter.getValue().contains(room.getBathroom().getNumberOfShowerHeads())
            );
        }

        // Facing Type
        if (!facingFilter.getValue().isEmpty()) {
            dataProvider.addFilter(room -> {
              for (String selectedFacing : facingFilter.getValue()) {
                  if (selectedFacing.equals("6th")) {
                      selectedFacing = "SIXTH";
                  }
                  if (room.getFacing().contains(Facing.valueOf(selectedFacing.toUpperCase()))) {
                      return true;
                  }
              }
              return false;
            });
        }

        // Floor Filter
        if (!floorFilter.getValue().isEmpty()) {
            dataProvider.addFilter(room ->
                    floorFilter.getValue().contains(room.getFloor())
            );
        }
    }
}
