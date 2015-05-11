package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithLimit;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.to.DateTimeFilter;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import ru.javawebinar.topjava.util.TimeUtil;

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

    @Override
    public List<UserMealWithLimit> getAllWithLimit(int userId, short caloriesPerDay) {

        List<UserMeal> all = getAll(userId);
        List<UserMealWithLimit> allWithLimit = new ArrayList<>();

        Map<String, Integer> sumDay = all.stream().collect(Collectors.groupingBy(UserMeal::getDay,
                Collectors.summingInt(UserMeal::getCalories)));


        for (UserMeal meal : all) {
            UserMealWithLimit mealWithLimit;
            if (sumDay.get(meal.getDay()) > caloriesPerDay)
                mealWithLimit = new UserMealWithLimit(meal, true);
            else
                mealWithLimit = new UserMealWithLimit(meal, false);
            allWithLimit.add(mealWithLimit);
        }

        return allWithLimit;
    }

    @Override
    public List<UserMealWithLimit> filterByDateWithLimit(LocalDateTime start, LocalDateTime end, int userId, short caloriesPerDay) {

        DateTimeFilter filter = new DateTimeFilter();
        filter.setStartDate(start.toLocalDate().toString());
        filter.setEndDate(end.toLocalDate().toString());

        List<UserMeal> allWithoutTime = filterByDate(TimeUtil.parseStartDate(filter), TimeUtil.parseEndDate(filter), userId);
        List<UserMeal> all = filterByDate(start, end, userId);
        List<UserMealWithLimit> allWithLimit = new ArrayList<>();

        Map<String, Integer> sumDay = allWithoutTime.stream().collect(Collectors.groupingBy(UserMeal::getDay,
                Collectors.summingInt(UserMeal::getCalories)));


        for (UserMeal meal : all) {
            UserMealWithLimit mealWithLimit;
            if (sumDay.get(meal.getDay()) > caloriesPerDay)
                mealWithLimit = new UserMealWithLimit(meal, true);
            else
                mealWithLimit = new UserMealWithLimit(meal, false);
            allWithLimit.add(mealWithLimit);
        }

        return allWithLimit;

    }
}
