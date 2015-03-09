package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import java.util.Collections;
import java.util.List;

/**
 * Created by eugene on 3/9/15.
 */
@Repository
public class MockUserMealRepository implements UserMealRepository{

    private LoggerWrapper LOG = LoggerWrapper.get(MockUserMealRepository.class);

    @Override
    public UserMeal save(UserMeal userMeal) {
        LOG.debug("save " + userMeal);
        return userMeal;
    }

    @Override
    public boolean delete(int id) {
        LOG.debug("delete " + id);
        return true;
    }

    @Override
    public UserMeal get(int id) {
        LOG.debug("get " + id);
        return null;
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        LOG.debug("getAll " + userId);
        return Collections.emptyList();
    }
}
