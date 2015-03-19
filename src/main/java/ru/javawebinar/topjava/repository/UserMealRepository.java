package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
public interface UserMealRepository {

    public UserMeal save(UserMeal userMeal, int userId);

    public boolean delete(int id, int userId);

    public boolean deleteAll(int userId);

    public UserMeal get(int id, int userId);

    public List<UserMeal> getAll(int userId);

    public List<UserMeal> filterByDate(LocalDateTime start, LocalDateTime end, int userId);

}
