package com.jackcode.Roomr.ui.listView;

import com.jackcode.Roomr.backend.model.BathroomType;
import com.jackcode.Roomr.backend.model.Facing;
import com.jackcode.Roomr.backend.model.Room;
import com.jackcode.Roomr.backend.model.RoomType;
import com.jackcode.Roomr.backend.service.ImageService;
import com.jackcode.Roomr.backend.service.RoomService;
import com.jackcode.Roomr.ui.MainLayout;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Route(value="", layout = MainLayout.class)
@PageTitle("Roomr | List View")
public class ListView extends VerticalLayout {

    private final ImageService imageService = new ImageService();

    // Visual Components
    private final Grid<Room> roomGrid = new Grid<>();
    private final RoomView roomView;
    private final AccordionPanel filterAccordion = new AccordionPanel();
    private final Accordion subFilterAccordion = new Accordion();
    private final HorizontalLayout topFilters;
    private final FormLayout filterForm = new FormLayout();


    // Filter Components
    private final ListDataProvider<Room> dataProvider;
    private final TextField roomNumberFilter = new TextField("Room Number");
    private final CheckboxGroup<String> tvInBathroomFilter = new CheckboxGroup();
    private final CheckboxGroup<RoomType> roomTypeFilter = new CheckboxGroup<>();
    private final CheckboxGroup<String> bathroomTypeFilter = new CheckboxGroup<>();
    private final CheckboxGroup<String> fireplaceFilter = new CheckboxGroup();
    private final CheckboxGroup<String> balconyFilter = new CheckboxGroup();
    private final CheckboxGroup<String> skylightFilter = new CheckboxGroup();
    private final CheckboxGroup<String> sofaFilter = new CheckboxGroup();
    private final CheckboxGroup<String> bodyShowerFilter = new CheckboxGroup();
    private final CheckboxGroup<Integer> showerHeadFilter = new CheckboxGroup();
    private final CheckboxGroup<String> facingFilter = new CheckboxGroup<>();
    private final CheckboxGroup<Integer> floorFilter = new CheckboxGroup<>();
    private final Button clearFiltersButton = new Button("Clear Filters");

    public ListView(RoomService roomService) {
        // Backend Components
        addClassName("list-view");
        setSizeFull();

        dataProvider = new ListDataProvider<>(roomService.findAll());

        topFilters = new HorizontalLayout(roomNumberFilter, roomTypeFilter);

        configureFilterComponents();
        configureRoomGrid();
        configureFilterAccordion();
        configureClearFiltersButton();

        roomView = new RoomView(imageService);
        roomView.addListener(RoomView.CloseEvent.class, e -> closeRoomView());
        roomView.setSizeFull();

        Div content = new Div(roomGrid, roomView);
        content.addClassName("content");
        content.setSizeFull();
        content.setMaxHeight("600px");


        Div allContent = new Div(filterAccordion, content);
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
        bodyShowerFilter.clear();
    }

    private void configureFilterAccordion() {
        filterAccordion.setSummary(new Label("Filters"));
        filterAccordion.addContent(topFilters, filterForm);
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
        BathroomType.stream().forEach(type -> bTypes.add(type.getDescription()));
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

        // Body Shower Filter
        bodyShowerFilter.setLabel("Shower Spa");
        bodyShowerFilter.setItems("Yes", "No");
        bodyShowerFilter.addValueChangeListener(e -> applyFilter());

        // Showerhead Filter
        showerHeadFilter.setLabel("Shower Heads");
        showerHeadFilter.setItems(1, 2, 3);
        showerHeadFilter.addValueChangeListener(e -> applyFilter());

        // Facing Filter
        facingFilter.setLabel("Facing");
        List<String> facingTypes = new ArrayList<>();
        Facing.stream().forEach(type -> facingTypes.add(type.getDescription()));
        facingFilter.setItems(facingTypes);
        facingFilter.addValueChangeListener(e -> applyFilter());

        // Floor Filter
        floorFilter.setLabel("Floor");
        floorFilter.setItems(2, 3, 4, 5, 6, 7, 8);
        floorFilter.addValueChangeListener(e -> applyFilter());

        filterForm.add(
                fireplaceFilter,
                balconyFilter,
                sofaFilter,
                bodyShowerFilter,
                showerHeadFilter,
                facingFilter,
                bathroomTypeFilter,
                clearFiltersButton);

        filterForm.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0em", 1),
                new FormLayout.ResponsiveStep("0em", 2),
                new FormLayout.ResponsiveStep("0em", 3),
                new FormLayout.ResponsiveStep("0em", 4),
                new FormLayout.ResponsiveStep("0em", 5),
                new FormLayout.ResponsiveStep("0em", 6),
                new FormLayout.ResponsiveStep("0em", 7),
                new FormLayout.ResponsiveStep("0em", 8),
                new FormLayout.ResponsiveStep("0em", 9)
        );

        filterForm.setColspan(showerHeadFilter, 2);
        filterForm.setColspan(facingFilter, 3);
        filterForm.setColspan(bathroomTypeFilter, 8);

    }

    private void configureRoomGrid() {
        roomGrid.setDataProvider(dataProvider);
        roomGrid.addClassName("room-grid");
        roomGrid.setSizeFull();
        roomGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        this.addColumns();

        // Sort grid by Room Number
        GridSortOrder<Room> order
                = new GridSortOrder<>(roomGrid.getColumns().get(0), SortDirection.ASCENDING);
        roomGrid.sort(Collections.singletonList(order));

        // Open RoomView when room is clicked (or close if open)
        roomGrid.asSingleSelect().addValueChangeListener(event -> showRoom(event.getValue()));
    }

    private void addColumns() {
        roomGrid.removeAllColumns();
        roomGrid.addColumn(Room::getRoomNumber).setHeader("Room Number");
        roomGrid.addColumn(Room::getRoomType).setHeader("Room Type");
        roomGrid.addColumn(room -> room.getBathroomType().getDescription()).setHeader("Bathroom Type");
        roomGrid.addColumn(Room::getSquareFootage).setHeader("Square Footage");
        roomGrid.addColumn(room -> (room.getHasFireplace() ? "Yes" : "No")).setHeader("Fireplace");
        roomGrid.addColumn(room -> (room.getHasBalcony() ? "Yes" : "No")).setHeader("Balcony");
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
            filterAccordion.setOpened(false);
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
        dataProvider.addFilter(room -> room
                .getRoomNumber()
                .contains(roomNumberFilter.getValue().trim()));

        // TV in Bathroom Filter
        if (!tvInBathroomFilter.getValue().isEmpty() &&
                !(tvInBathroomFilter.getValue().contains("Yes") && tvInBathroomFilter.getValue().contains("No"))) {
            dataProvider.addFilter(room -> {
                if (tvInBathroomFilter.getValue().contains("Yes")) {
                    return room.getHasTvInBathroom();
                } else {
                    return !room.getHasTvInBathroom();
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
                    .contains(room.getBathroomType().getDescription()));
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

        // Sofa Filter
        if (!bodyShowerFilter.getValue().isEmpty() &&
                !(bodyShowerFilter.getValue().contains("Yes") && bodyShowerFilter.getValue().contains("No"))) {
            dataProvider.addFilter(room -> {
                if (bodyShowerFilter.getValue().contains("Yes")) {
                    return room.getHasBodyShower();
                } else {
                    return !room.getHasBodyShower();
                }
            });
        }

        // Showerhead Filter
        if (!showerHeadFilter.getValue().isEmpty()) {
            dataProvider.addFilter(room ->
                showerHeadFilter.getValue().contains(room.getNumberOfShowerHeads())
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
