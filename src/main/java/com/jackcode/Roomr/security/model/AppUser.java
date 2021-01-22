package com.jackcode.Roomr.security.model;

import com.jackcode.Roomr.backend.model.AbstractDocument;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document(collection = "users")
public class AppUser extends AbstractDocument {

    @NotNull
    private String username;
    @NotNull
    private String password;
    private String roles;

    public AppUser() {}

    public AppUser(String username, String password, String roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
