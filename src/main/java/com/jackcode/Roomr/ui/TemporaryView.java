package com.jackcode.Roomr.ui;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class TemporaryView extends VerticalLayout {
    public TemporaryView() {
        add(new H1("Hello, World!"));
    }
}
