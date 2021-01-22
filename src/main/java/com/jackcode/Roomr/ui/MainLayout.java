package com.jackcode.Roomr.ui;

import com.jackcode.Roomr.security.ILAY.SecuredByRole;
import com.jackcode.Roomr.security.config.SecurityUtils;
import com.jackcode.Roomr.ui.admin.AdminView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.server.PWA;
import org.springframework.core.annotation.AnnotationUtils;

@CssImport("./styles/shared-styles.css")
@PWA(name="Roomr",
        shortName = "Roomr",
        offlineResources = {
                "./styles/offline.css",
                "./images/offline.png"
        })
public class MainLayout extends AppLayout {
    public MainLayout() {
        createHeader();
    }

    private void createHeader() {
        H1 logo = new H1("Roomr");
        logo.addClassName("logo");

        Anchor admin = new Anchor("admin/users", "Admin");
        admin.setVisible(false);

        if (SecurityUtils.isAccessGranted(AdminView.class,
                AnnotationUtils.findAnnotation(AdminView.class, SecuredByRole.class))) {
            admin.setVisible(true);
        }

        Anchor logout = new Anchor("logout", "Logout");

        HorizontalLayout header = new HorizontalLayout(logo, admin, logout);
        header.expand(logo);
        header.setDefaultVerticalComponentAlignment(
                FlexComponent.Alignment.CENTER
        );
        header.setWidth("100%");
        header.addClassName("header");

        addToNavbar(header);
    }

}
