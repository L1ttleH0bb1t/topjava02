package ru.javawebinar.topjava.service;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.BaseEntity;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.MealTestData.*;
/**
 * Created by eugene on 16.03.15.
 */


public abstract class UserMealServiceTest extends DbTest{

    @Test
    public void testSave() throws Exception {
        UserMeal testMeal = new UserMeal(
                "Чай", (short) 50, initDateTime(2015, 02, 16, 21, 14));
        UserMeal created = userMealService.save(testMeal, USER.getId());
        testMeal.setId(created.getId());
        MealTestData.MATCHER.assertListEquals(Arrays.asList(FISH, SALAD, testMeal), userMealService.getAll(USER.getId()));
    }

    @Test
    public void testDelete() throws Exception {
        userMealService.delete(BaseEntity.START_SEQ + 2, USER.getId());
        MealTestData.MATCHER.assertListEquals(Arrays.asList(FISH), userMealService.getAll(USER.getId()));
    }

    @Test
    public void testNotFoundDelete() throws Exception {
        thrown.expect(NotFoundException.class);
        userMealService.delete(1, USER.getId());
    }

    @Test
    public void testDeleteNotMy() throws Exception {
        thrown.expect(NotFoundException.class);
        userMealService.delete(BaseEntity.START_SEQ + 3, ADMIN.getId());
    }

    @Test
    public void deleteAll() throws Exception {
        userMealService.deleteAll(USER.getId());
        MealTestData.MATCHER.assertListEquals(Arrays.asList(), userMealService.getAll(USER.getId()));
    }

    @Test
    public void testGet() throws Exception {
        UserMeal userMeal = userMealService.get(BaseEntity.START_SEQ + 2, USER.getId());
        MealTestData.MATCHER.assertEquals(SALAD, userMeal);
    }

    @Test
    public void testGetNotMy() throws Exception {
        thrown.expect(NotFoundException.class);
        UserMeal userMeal = userMealService.get(BaseEntity.START_SEQ + 3, ADMIN.getId());
    }

    @Test
    public void testGetAll() throws Exception {
        List<UserMeal> all = userMealService.getAll(USER.getId());
        MealTestData.MATCHER.assertListEquals(Arrays.asList(FISH, SALAD), all);
    }

    @Test
    public void testUpdate() throws Exception {
        UserMeal updated = new UserMeal(SALAD);
        updated.setMeal("UpdatedMeal");
        userMealService.update(updated, USER.getId());
        MealTestData.MATCHER.assertEquals(updated, userMealService.get(BaseEntity.START_SEQ + 2, USER.getId()));
    }

    @Test
    public void testUpdateNotMy() throws Exception {
        UserMeal updated = new UserMeal(SALAD);
        updated.setMeal("UpdatedMeal");
        thrown.expect(NotFoundException.class);
        userMealService.update(updated, ADMIN.getId());
    }

    @Test
    public void filterByDate() throws Exception {
        LocalDateTime start = LocalDateTime.of(2015, 3, 16, 20, 10);
        LocalDateTime end = LocalDateTime.of(2015, 3, 17, 20, 10);
        List<UserMeal> meals = userMealService.filterByDate(start, end, USER.getId());
        MealTestData.MATCHER.assertListEquals(Arrays.asList(FISH), meals);
    }


}
