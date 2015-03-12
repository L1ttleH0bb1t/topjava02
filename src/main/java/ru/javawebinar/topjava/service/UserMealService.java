package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Date;
import java.util.List;

/**
 * Created by eugene on 3/9/15.
 */
public interface UserMealService {

    public UserMeal save(UserMeal userMeal, int userId);

    public void delete(int id, int userId) throws NotFoundException;

    public boolean deleteAll(int userId);

    public void update(UserMeal userMeal, int userId) throws NotFoundException;

    public UserMeal get(int id, int userId) throws NotFoundException;

    public List<UserMeal> getAll(int userId);

    public List<UserMeal> filter(Date start, Date end, int userId);

}
