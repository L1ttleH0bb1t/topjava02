package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithLimit;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.to.DateTimeFilter;
import ru.javawebinar.topjava.web.ExceptionInfoHandler;

import java.time.LocalDateTime;
import java.util.List;

public class AbstractMealController extends ExceptionInfoHandler {
    private static final LoggerWrapper LOG = LoggerWrapper.get(AbstractMealController.class);

    @Autowired
    private UserMealService service;

    public UserMeal get(int id) {
        int userId = LoggedUser.id();
        LOG.info("get meal {} for User {}", id, userId);
        return service.get(id, userId);
    }

    public void delete(int id) {
        int userId = LoggedUser.id();
        LOG.info("delete meal {} for User {}", id, userId);
        service.delete(id, userId);
    }

    public List<UserMeal> getAll() {
        int userId = LoggedUser.id();
        LOG.info("getAll for User {}", userId);
        return service.getAll(userId);
    }

    public List<UserMealWithLimit> getAllWithLimit() {
        int userId = LoggedUser.id();
        short caloriesPerDay = LoggedUser.caloriesPerDay();
        LOG.info("getAllWithLimit for User {}", userId);
        return service.getAllWithLimit(userId, caloriesPerDay);
    }

    public List<UserMeal> filterByDate(LocalDateTime startDate, LocalDateTime endDate) {
        int userId = LoggedUser.id();
        LOG.info("getBetween {} and {} for User {}", startDate, endDate, userId);
        return service.filterByDate(startDate, endDate, userId);
    }

    public List<UserMealWithLimit> filterByDateWithLimit(LocalDateTime startDate, LocalDateTime endDate) {
        int userId = LoggedUser.id();
        short caloriesPerDay = LoggedUser.caloriesPerDay();
        LOG.info("getBetweenWithLimit {} and {} for User {}", startDate, endDate, userId);
        return service.filterByDateWithLimit(startDate, endDate, userId, caloriesPerDay);
    }

    public void deleteAll() {
        int userId = LoggedUser.id();
        LOG.info("deleteAll for User {}", userId);
        service.deleteAll(userId);
    }

    public void update(UserMeal meal, int id) {
        meal.setId(id);
        int userId = LoggedUser.id();
        LOG.info("update {} for User {}", meal, userId);
        service.update(meal, userId);
    }

    public UserMeal create(UserMeal meal) {
        meal.setId(null);
        int userId = LoggedUser.id();
        LOG.info("create {} for User {}" + meal, userId);
        return service.save(meal, userId);
    }

    public LocalDateTime toLocalDateTime(String dateTime) {
        LOG.info("toLocalDateTime {}", dateTime);
        return LocalDateTime.parse(dateTime);
    }

    public List<UserMeal> filterList(DateTimeFilter filter) {
        int userId = LoggedUser.id();
        LOG.info("filter for User {}", userId);
        // TODO implement
        return service.getAll(userId);
    }
}
