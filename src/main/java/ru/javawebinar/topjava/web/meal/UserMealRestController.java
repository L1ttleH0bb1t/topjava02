package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.service.UserMealServiceImpl;

import java.time.LocalDateTime;
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
    private UserMealService service;

    public UserMeal create(UserMeal userMeal) {
        LOG.debug("create " + userMeal);
        return service.save(userMeal, LoggedUser.id());
    }

    public void delete(int id) {
        LOG.debug("delete " + id);
        service.delete(id, LoggedUser.id());
    }

    public boolean deleteAll() {
        LOG.debug("delete All");
        return  service.deleteAll(LoggedUser.id());
    }

    public void update(UserMeal userMeal) {
        LOG.debug("update " + userMeal);
        service.update(userMeal, LoggedUser.id());
    }

    public UserMeal get(int id) {
        LOG.debug("get " + id);
        return service.get(id, LoggedUser.id());
    }

    public List<UserMeal> getAll() {
        LOG.debug("getAll");
        return service.getAll(LoggedUser.id());
    }

    public List<UserMeal> filterByDate(LocalDateTime start, LocalDateTime end) {
        LOG.debug("filter " + start + " " + end);
        return service.filterByDate(start, end, LoggedUser.id());
    }

}
