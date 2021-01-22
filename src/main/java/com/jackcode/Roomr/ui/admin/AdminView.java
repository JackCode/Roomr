package com.jackcode.Roomr.ui.admin;

import com.jackcode.Roomr.security.ILAY.SecuredByRole;
import com.jackcode.Roomr.ui.admin.rooms.AdminEditRooms;
import com.jackcode.Roomr.ui.admin.users.AdminUsersView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;


@CssImport("./styles/admin-styles.css")
@SecuredByRole("ROLE_Admin")
public class AdminView extends AppLayout {

    public AdminView() {
        createHeader();
        createDrawer();

    }

    private void createHeader() {
        H1 logo = new H1("Roomr");
        logo.addClassName("logo");

        Anchor home = new Anchor("/", "Home");
        Anchor logout = new Anchor("logout", "Logout");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, home, logout);
        header.expand(logo);
        header.setDefaultVerticalComponentAlignment(
                FlexComponent.Alignment.CENTER
        );
        header.setWidth("100%");
        header.addClassName("header");

        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink listLink = new RouterLink("Users", AdminUsersView.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(listLink,
                new RouterLink("Rooms", AdminEditRooms.class)));
        setDrawerOpened(true);
    }
}
