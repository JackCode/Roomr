package com.jackcode.Roomr.ui.loginView;

import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;

@Route("login")
@PageTitle("Login | Roomr")
@Secured("ROLE_Admin")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private LoginOverlay login = new LoginOverlay();
    public final static String ROUTE = "@Route";

    public LoginView() {
        addClassName("login");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        login.setAction("login");
        login.setOpened(true);
        login.setForgotPasswordButtonVisible(false);
        login.setTitle("Roomr");
        login.setDescription("Hotel Room Details");
        getElement().appendChild(login.getElement());
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (beforeEnterEvent.getLocation()
        .getQueryParameters()
        .getParameters()
        .containsKey("error")) {
            login.setError(true);
        }
    }
}
