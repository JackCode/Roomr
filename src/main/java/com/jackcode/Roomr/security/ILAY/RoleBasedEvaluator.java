package com.jackcode.Roomr.security.ILAY;

import com.jackcode.Roomr.security.config.SecurityUtils;
import com.jackcode.Roomr.ui.loginView.LoginView;
import com.vaadin.flow.router.Location;
import com.vaadin.flow.router.NotFoundException;
import org.ilay.Access;
import org.ilay.AccessEvaluator;

public class RoleBasedEvaluator implements AccessEvaluator<SecuredByRole> {

    @Override
    public Access evaluate(Location location, Class navigationTarget, SecuredByRole annotation) {
        if(!SecurityUtils.isAccessGranted(navigationTarget, annotation)) {
            if(SecurityUtils.isUserLoggedIn()) {
                return Access.restricted(NotFoundException.class);
            } else {
                return Access.restricted(LoginView.ROUTE);
            }
        }

        return Access.granted();
    }
}
