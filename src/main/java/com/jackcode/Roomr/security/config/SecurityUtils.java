package com.jackcode.Roomr.security.config;

import com.jackcode.Roomr.security.ILAY.SecuredByRole;
import com.jackcode.Roomr.ui.loginView.LoginView;
import com.vaadin.flow.server.HandlerHelper;
import com.vaadin.flow.shared.ApplicationConstants;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class SecurityUtils {

    public SecurityUtils() {
    }

    public static boolean isFrameworkInternalRequest(HttpServletRequest request) {
        final String parameterValue = request.getParameter(
                ApplicationConstants.REQUEST_TYPE_PARAMETER);

        return parameterValue != null && Stream.of(HandlerHelper.RequestType.values())
                .anyMatch((r -> r.getIdentifier().equals(parameterValue)));
    }

    public static boolean isUserLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication != null
                && !(authentication instanceof AnonymousAuthenticationToken)
                && authentication.isAuthenticated();
    }

    public static boolean isAccessGranted(Class<?> securedClass, SecuredByRole annotation) {
        if (LoginView.class.equals(securedClass)) {
            return true;
        }

        if (!isUserLoggedIn()) {
            return false;
        }

        // Allow if no roles are required
        SecuredByRole securedByRole = AnnotationUtils.findAnnotation(securedClass, SecuredByRole.class);
        if (securedByRole == null) {
            return true;
        }

        List<String> allowedRoles = Collections.singletonList(securedByRole.value());
        Authentication userAuthentication = SecurityContextHolder.getContext().getAuthentication();
        return userAuthentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(allowedRoles::contains);
    }
}
