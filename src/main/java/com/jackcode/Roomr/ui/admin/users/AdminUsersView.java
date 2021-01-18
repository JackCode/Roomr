package com.jackcode.Roomr.ui.admin.users;

import com.jackcode.Roomr.security.ILAY.SecuredByRole;
import com.jackcode.Roomr.ui.admin.AdminView;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value="admin/users", layout = AdminView.class)
@PageTitle("Admin - Users | Roomr")
@CssImport("./styles/admin-styles.css")
@SecuredByRole("ROLE_Admin")
public class AdminUsersView extends VerticalLayout {

    public AdminUsersView() {
        add(new H1("USERS VIEW"));
    }
}
