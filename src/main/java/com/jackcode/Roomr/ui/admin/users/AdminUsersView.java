package com.jackcode.Roomr.ui.admin.users;

import com.jackcode.Roomr.exceptions.ExceptionDialog;
import com.jackcode.Roomr.security.ILAY.SecuredByRole;
import com.jackcode.Roomr.security.model.AppUser;
import com.jackcode.Roomr.security.repository.UserRepository;
import com.jackcode.Roomr.ui.admin.AdminView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Route(value="admin/users", layout = AdminView.class)
@PageTitle("Admin - Users | Roomr")
@CssImport("./styles/admin-styles.css")
@SecuredByRole("ROLE_Admin")
public class AdminUsersView extends VerticalLayout {

    private final UserRepository userRepository;

    private final HorizontalLayout adminRow = new HorizontalLayout();
    private final HorizontalLayout userRow = new HorizontalLayout();

    private final Label adminLabel = new Label("Admin: ");
    private final Label userLabel = new Label("User: ");
    private final TextField adminUsername = new TextField();
    private final TextField userUsername = new TextField();
    private final PasswordField adminPassword = new PasswordField();
    private final PasswordField userPassword =  new PasswordField();
    private final Button updateAdminBtn = new Button("Update");
    private final Button updateUserBtn = new Button("Update");
    private final Label adminUpdateSuccessLabel = new Label("Update Successful!");
    private final Label userUpdateSuccessLabel = new Label("Update Successful!");

    @Autowired
    public AdminUsersView(UserRepository userRepository) {
        this.userRepository = userRepository;
        addClassName("admin-users-view");

        configureAdminRow();
        configureUserRow();

        add(adminRow,
                userRow);

        add(new H1("User Credentials"),
                adminRow,
                userRow);

    }

    private void configureUserRow() {

        userUsername.setValue(userRepository.findByRoles("User").getUsername());
        userUsername.setLabel("Username");
        userUsername.addValueChangeListener(e -> userUpdateSuccessLabel.setVisible(false));
        userUsername.setValueChangeMode(ValueChangeMode.LAZY);
        userPassword.setLabel("Password");
        userPassword.addValueChangeListener(e -> userUpdateSuccessLabel.setVisible(false));
        userPassword.setValueChangeMode(ValueChangeMode.LAZY);
        updateUserBtn.addClickListener(e -> updateUser());
        userUpdateSuccessLabel.addClassName("success-label");
        userUpdateSuccessLabel.setVisible(false);

        userRow.add(
                userLabel,
                userUsername,
                userPassword,
                updateUserBtn,
                userUpdateSuccessLabel
        );
    }

    private void updateUser() {

        boolean successfulUpdate = false;
        userUpdateSuccessLabel.setVisible(false);

        if(userUsername != null && !userUsername.getValue().isEmpty()) {
            try {
                AppUser user = userRepository.findByRoles("User");
                user.setUsername(userUsername.getValue());
                userRepository.save(user);
                successfulUpdate = true;
            } catch (Exception ex) {
                successfulUpdate = false;
                new ExceptionDialog("E1002: mongodb error", "Unable to save username. (Error code: E1002)");
            }

            if (userPassword != null && !userPassword.getValue().isEmpty()) {
                if (userPassword != null && userPassword.getValue().length() < 4) {
                    successfulUpdate = false;
                    new ExceptionDialog("E1002: password too short", "Password must be 4 character or more.");
                } else {
                    try {
                        AppUser user = userRepository.findByRoles("User");
                        user.setPassword(BCrypt.hashpw(userPassword.getValue(), BCrypt.gensalt()));
                        userRepository.save(user);
                        successfulUpdate = true;
                    } catch (Exception ex) {
                        successfulUpdate = false;
                        new ExceptionDialog("E1003: password db error", "Error saving password. (Error Code: E1003)");
                    }
                }
            }

        } else {
            successfulUpdate = false;
            new ExceptionDialog("E1002: username empty", "Must provide a username. (Error code: E1002)");
        }
        userUpdateSuccessLabel.setVisible(successfulUpdate);
    }

    private void configureAdminRow() {

        adminUsername.setValue(userRepository.findByRoles("Admin").getUsername());
        adminUsername.setLabel("Username");
        adminUsername.addValueChangeListener(e -> adminUpdateSuccessLabel.setVisible(false));
        adminUsername.setValueChangeMode(ValueChangeMode.LAZY);
        adminPassword.setLabel("Password");
        adminPassword.addValueChangeListener(e -> adminUpdateSuccessLabel.setVisible(false));
        adminPassword.setValueChangeMode(ValueChangeMode.LAZY);
        updateAdminBtn.addClickListener(e -> updateAdmin());
        adminUpdateSuccessLabel.addClassName("success-label");
        adminUpdateSuccessLabel.setVisible(false);


        adminRow.add(
                adminLabel,
                adminUsername,
                adminPassword,
                updateAdminBtn,
                adminUpdateSuccessLabel
        );
    }

    private void updateAdmin() {
        boolean successfulUpdate = false;
        adminUpdateSuccessLabel.setVisible(false);

        if(adminUsername != null && !adminUsername.getValue().isEmpty()) {
            try {
                AppUser admin = userRepository.findByRoles("Admin");
                admin.setUsername(adminUsername.getValue());
                userRepository.save(admin);
                successfulUpdate = true;
            } catch (Exception ex) {
                successfulUpdate = false;
                new ExceptionDialog("E1002: mongodb error", "Unable to save username. (Error code: E1002)");
            }

            if (adminPassword != null && !adminPassword.getValue().isEmpty()) {
                if (adminPassword != null && adminPassword.getValue().length() < 4) {
                    successfulUpdate = false;
                    new ExceptionDialog("E1002: password too short", "Password must be 4 character or more.");
                } else {
                    try {
                        AppUser admin = userRepository.findByRoles("Admin");
                        admin.setPassword(BCrypt.hashpw(adminPassword.getValue(), BCrypt.gensalt()));
                        userRepository.save(admin);
                        successfulUpdate = true;
                    } catch (Exception ex) {
                        successfulUpdate = false;
                        new ExceptionDialog("E1003: password db error", "Error saving password. (Error Code: E1003)");
                    }
                }
            }

        } else {
            successfulUpdate = false;
            new ExceptionDialog("E1002: username empty", "Must provide a username. (Error code: E1002)");
        }


        adminUpdateSuccessLabel.setVisible(successfulUpdate);
    }


}
