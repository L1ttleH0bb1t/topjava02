package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Date;
import java.util.List;

/**
 * Created by eugene on 3/9/15.
 */
public interface UserMealService {

    public UserMeal save(UserMeal userMeal);

    public void delete(int id) throws NotFoundException;

    public void update(UserMeal userMeal) throws NotFoundException;

    public UserMeal get(int id) throws NotFoundException;

    public List<UserMeal> getAll(int userId);

    public List<UserMeal> filter(Date start, Date end);

}
