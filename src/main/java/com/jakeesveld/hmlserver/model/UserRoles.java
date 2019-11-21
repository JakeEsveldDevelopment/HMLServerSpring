package com.jakeesveld.hmlserver.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table
public class UserRoles implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "id")
    @JsonIgnoreProperties("userRoles")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "id")
    @JsonIgnoreProperties("userRoles")
    private Role role;

    public UserRoles(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    public UserRoles() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRoles)) return false;
        UserRoles userRoles = (UserRoles) o;
        return getUser().equals(userRoles.getUser()) &&
                getRole().equals(userRoles.getRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getRole());
    }
}
