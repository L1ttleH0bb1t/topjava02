package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.BaseEntity;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.function.Function;
import static ru.javawebinar.topjava.UserTestData.*;
/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    private static final LoggerWrapper LOG = LoggerWrapper.get(MealTestData.class);

    public static final UserMeal SALAD = new UserMeal(BaseEntity.START_SEQ + 2,
            "Салат цезарь", (short) 303, initDateTime(2015, 03, 16, 19, 54));

    public static final UserMeal FISH = new UserMeal(BaseEntity.START_SEQ + 3,
            "Семга на гриле", (short) 230, initDateTime(2015, 03, 16, 20, 14));

    public static LocalDateTime initDateTime(int year, int month, int day, int hour, int minute) {
         return LocalDateTime.of(year, month, day, hour, minute);
    }




    public static final ModelMatcher<UserMeal, String> MATCHER = new ModelMatcher<>(
            meal -> meal.toString(), UserMeal.class);

}
