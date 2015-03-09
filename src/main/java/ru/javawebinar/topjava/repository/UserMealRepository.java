package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.UserMeal;

import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
public interface UserMealRepository {

    public UserMeal save(UserMeal userMeal);

    public boolean delete(int id);

    public UserMeal get(int id);

    public List<UserMeal> getAll(int userId);

}
