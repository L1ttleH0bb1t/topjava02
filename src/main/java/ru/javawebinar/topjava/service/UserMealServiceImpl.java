package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */

@Service
public class UserMealServiceImpl implements UserMealService{

    @Autowired
    private UserMealRepository repository;

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        return repository.save(userMeal, userId);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        ExceptionUtil.check(repository.delete(id, userId), id);
    }

    @Override
    public void update(UserMeal userMeal, int userId) throws NotFoundException {
        ExceptionUtil.check(repository.save(userMeal, userId), userMeal.getId());
    }

    @Override
    public UserMeal get(int id, int userId) throws NotFoundException {
        return ExceptionUtil.check(repository.get(id, userId), id);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return repository.getAll(userId);
    }

    @Override
    public List<UserMeal> filterByDate(LocalDateTime start, LocalDateTime end, int userId) {
        return repository.filterByDate(start, end, userId);
    }

    @Override
    public boolean deleteAll(int userId) {
        return repository.deleteAll(userId);
    }

}
