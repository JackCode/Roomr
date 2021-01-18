package com.jackcode.Roomr.security;

import com.jackcode.Roomr.security.ILAY.SecuredByRole;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("unauthorized")
@PageTitle("Unauthorized Access | Roomr")
@SecuredByRole()
public class UnauthorizedAccessView extends VerticalLayout {
    public final static String ROUTE = "@Route";

    public UnauthorizedAccessView() {
    }
}
