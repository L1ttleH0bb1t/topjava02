package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by eugene on 3/9/15.
 */
@Repository
public class MockUserMealRepository implements UserMealRepository{

    private LoggerWrapper LOG = LoggerWrapper.get(MockUserMealRepository.class);

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        LOG.debug("save " + userMeal);
        return userMeal;
    }

    @Override
    public boolean delete(int id, int userId) {
        LOG.debug("delete " + id);
        return true;
    }

    @Override
    public UserMeal get(int id, int userId) {
        LOG.debug("get " + id);
        return new UserMeal();
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        LOG.debug("getAll");
        return Collections.emptyList();
    }

    @Override
    public List<UserMeal> filterByDate(Date start, Date end, int userId) {
        LOG.debug("filter " + start + " " + end);
        return Collections.emptyList();
    }

    @Override
    public boolean deleteAll(int userId) {
        LOG.debug("delete all");
        return true;
    }


}
