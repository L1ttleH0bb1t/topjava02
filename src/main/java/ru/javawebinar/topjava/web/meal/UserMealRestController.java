package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealServiceImpl;

import java.util.Date;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class UserMealRestController {

    private LoggerWrapper LOG = LoggerWrapper.get(UserMealRestController.class);

    @Autowired
    private UserMealServiceImpl service;

    public UserMeal create(UserMeal userMeal) {
        LOG.debug("create " + userMeal);
        userMeal.setId(1);
        return service.save(userMeal);
    }

    public void delete(int id) {
        LOG.debug("delete " + id);
        service.delete(id);
    }

    public void update(UserMeal userMeal) {
        LOG.debug("update " + userMeal);
        service.update(userMeal);
    }

    public UserMeal get(int id) {
        LOG.debug("get " + id);
        return service.get(id);
    }

    public List<UserMeal> getAll() {
        LOG.debug("getAll");
        return service.getAll(LoggedUser.id());
    }

    public List<UserMeal> filter(Date start, Date end) {
        LOG.debug("filter");
        return service.filter(start, end);
    }

}
