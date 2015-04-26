package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.BaseEntity;
import ru.javawebinar.topjava.model.Role;

import java.util.Collections;
import java.util.Set;

/**
 * GKislin
 * 06.03.2015.
 *
 * Mock implementation
 */
public class LoggedUser {
    protected int id = BaseEntity.START_SEQ;

    protected Set<Role> roles = Collections.singleton(Role.ROLE_USER);
    protected boolean enabled = true;

    private static LoggedUser LOGGED_USER = new LoggedUser();

    public static LoggedUser get() {
        return LOGGED_USER;
    }

    public static int id() {
        return get().id;
    }

    public Set<Role> getAuthorities() {
        return roles;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
