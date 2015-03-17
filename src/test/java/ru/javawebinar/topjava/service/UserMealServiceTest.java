package ru.javawebinar.topjava.service;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.BaseEntity;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.MealTestData.*;
/**
 * Created by eugene on 16.03.15.
 */

@ContextConfiguration({
        "classpath:spring/spring-app-jdbc.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMealServiceTest {
    @Autowired
    protected UserMealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testSave() throws Exception {
        MealTestData.TestMeal testMeal = TEA;
        UserMeal created = service.save(testMeal.asMeal(), USER.getId());
        testMeal.setId(created.getId());
        MealTestData.MATCHER.assertListEquals(Arrays.asList(FISH, SALAD, testMeal), service.getAll(USER.getId()));
    }

    @Test
    public void testDelete() {
        service.delete(BaseEntity.START_SEQ + 3, USER.getId());
        MealTestData.MATCHER.assertListEquals(Arrays.asList(FISH), service.getAll(USER.getId()));
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(1, USER.getId());
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotMy() {
        service.delete(BaseEntity.START_SEQ + 3, ADMIN.getId());
    }

    @Test
    public void deleteAll() {
        service.deleteAll(USER.getId());
        MealTestData.MATCHER.assertListEquals(Arrays.asList(), service.getAll(USER.getId()));
    }

    @Test
    public void testGet() throws Exception {
        UserMeal userMeal = service.get(BaseEntity.START_SEQ + 3, USER.getId());
        MealTestData.MATCHER.assertEquals(SALAD, userMeal);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testGetNotMy() throws Exception {
        UserMeal userMeal = service.get(BaseEntity.START_SEQ + 3, ADMIN.getId());
    }

    @Test
    public void testGetAll() throws Exception {
        List<UserMeal> all = service.getAll(USER.getId());
        MealTestData.MATCHER.assertListEquals(Arrays.asList(FISH, SALAD), all);
    }

    @Test
    public void testUpdate() throws Exception {
        TestMeal updated = new TestMeal(SALAD);
        updated.setMeal("UpdatedMeal");
        service.update(updated.asMeal(), USER.getId());
        MealTestData.MATCHER.assertEquals(updated, service.get(BaseEntity.START_SEQ + 3, USER.getId()));
    }

    @Test
    public void filterByDate() {
        Calendar start = Calendar.getInstance();
        start.set(2015, Calendar.MARCH, 16, 20, 10);
        Calendar end = Calendar.getInstance();
        end.set(2015, Calendar.MARCH, 17, 20, 10);
        List<UserMeal> meals = service.filterByDate(start.getTime(), end.getTime(), USER.getId());
        MealTestData.MATCHER.assertListEquals(Arrays.asList(SALAD), meals);
    }


}
