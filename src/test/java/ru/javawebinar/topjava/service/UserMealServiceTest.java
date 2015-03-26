package ru.javawebinar.topjava.service;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.MealTestData.*;
/**
 * Created by eugene on 16.03.15.
 */

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMealServiceTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
        UserMeal testMeal = new UserMeal(
                "Чай", (short) 50, initDateTime(2015, 02, 16, 21, 14));
        UserMeal created = service.save(testMeal, USER.getId());
        testMeal.setId(created.getId());
        MealTestData.MATCHER.assertListEquals(Arrays.asList(FISH, SALAD, testMeal), service.getAll(USER.getId()));
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(BaseEntity.START_SEQ + 2, USER.getId());
        MealTestData.MATCHER.assertListEquals(Arrays.asList(FISH), service.getAll(USER.getId()));
    }

    @Test
    public void testNotFoundDelete() throws Exception {
        thrown.expect(NotFoundException.class);
        service.delete(1, USER.getId());
    }

    @Test
    public void testDeleteNotMy() throws Exception {
        thrown.expect(NotFoundException.class);
        service.delete(BaseEntity.START_SEQ + 3, ADMIN.getId());
    }

    @Test
    public void deleteAll() throws Exception {
        service.deleteAll(USER.getId());
        MealTestData.MATCHER.assertListEquals(Arrays.asList(), service.getAll(USER.getId()));
    }

    @Test
    public void testGet() throws Exception {
        UserMeal userMeal = service.get(BaseEntity.START_SEQ + 2, USER.getId());
        MealTestData.MATCHER.assertEquals(SALAD, userMeal);
    }

    @Test
    public void testGetNotMy() throws Exception {
        thrown.expect(NotFoundException.class);
        UserMeal userMeal = service.get(BaseEntity.START_SEQ + 3, ADMIN.getId());
    }

    @Test
    public void testGetAll() throws Exception {
        List<UserMeal> all = service.getAll(USER.getId());
        MealTestData.MATCHER.assertListEquals(Arrays.asList(FISH, SALAD), all);
    }

    @Test
    public void testUpdate() throws Exception {
        UserMeal updated = new UserMeal(SALAD);
        updated.setMeal("UpdatedMeal");
        service.update(updated, USER.getId());
        MealTestData.MATCHER.assertEquals(updated, service.get(BaseEntity.START_SEQ + 2, USER.getId()));
    }

    @Test
    public void testUpdateNotMy() throws Exception {
        UserMeal updated = new UserMeal(SALAD);
        updated.setMeal("UpdatedMeal");
        thrown.expect(NotFoundException.class);
        service.update(updated, ADMIN.getId());
    }

    @Test
    public void filterByDate() throws Exception {
        LocalDateTime start = LocalDateTime.of(2015, 3, 16, 20, 10);
        LocalDateTime end = LocalDateTime.of(2015, 3, 17, 20, 10);
        List<UserMeal> meals = service.filterByDate(start, end, USER.getId());
        MealTestData.MATCHER.assertListEquals(Arrays.asList(FISH), meals);
    }


}
