package ru.javawebinar.topjava.util;

/**
 * GKislin
 * 24.04.2015.
 */
public interface AbstractUser {

    Integer getId();

    void setId(Integer id);

    String getName();

    void setName(String password);

    String getEmail();

    void setEmail(String password);

    String getPassword();

    void setPassword(String password);
}
