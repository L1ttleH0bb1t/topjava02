package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Role;

import java.util.Set;

/**
 * GKislin
 * 06.03.2015.
 */
public class LoggedUser {
    protected int id;
    protected Set<Role> roles;
    protected boolean enabled;

    public int getId() {
        return id;
    }
}
