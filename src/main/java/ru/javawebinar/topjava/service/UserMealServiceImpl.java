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
    public UserMeal save(UserMeal userMeal) {
        return repository.save(userMeal);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        ExceptionUtil.check(repository.delete(id), id);
    }

    @Override
    public void update(UserMeal userMeal) throws NotFoundException {
        ExceptionUtil.check(repository.save(userMeal), userMeal.getId());
    }

    @Override
    public UserMeal get(int id) throws NotFoundException {
        return ExceptionUtil.check(repository.get(id), id);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return repository.getAll(userId);
    }

    @Override
    public List<UserMeal> filter(Date start, Date end) {
        return repository.filter(start, end);
    }

}
