package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.BaseEntity;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.function.Function;
import static ru.javawebinar.topjava.UserTestData.*;
/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    private static final LoggerWrapper LOG = LoggerWrapper.get(MealTestData.class);

    public static final TestMeal FISH = new TestMeal(BaseEntity.START_SEQ + 2,
            "Семга на гриле", (short) 230, initDateTime(2015, 02, 16, 19, 54), USER.getId());

    public static final TestMeal SALAD = new TestMeal(BaseEntity.START_SEQ + 3,
            "Салат цезарь", (short) 303, initDateTime(2015, 02, 16, 20, 14), USER.getId());

    public static final TestMeal TEA = new TestMeal(
            "Чай", (short) 50, initDateTime(2015, 02, 16, 21, 14), USER.getId());

    public static Date initDateTime(int y, int mm, int d, int h, int m) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(y, mm, d, h, m);
        return calendar.getTime();
    }


    public static class TestMeal extends UserMeal {

        public TestMeal(Integer id, String meal, Short calories, Date date, Integer userId) {
            super(id, meal, calories, date, userId);
        }

        public  TestMeal(TestMeal testMeal) {
            super(testMeal.asMeal());
        }

        public TestMeal(String meal, Short calories, Date date) {
            super(meal, calories, date);
        }

        public TestMeal(String meal, Short calories, Date date, Integer userId) {
            super(meal, calories, date, userId);
        }

        public UserMeal asMeal() {
            return new UserMeal(this);
        }

        @Override
        public String toString() {
            return "UserMeal{" +
                    "id=" + id +
                    ", meal=" + meal +
                    ", calories=" + calories +
                    ", date=" + TimeUtil.toString(date) +
                    ", userId=" + userId +
                    "}";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestMeal that = (TestMeal) o;

            return Objects.equals(this.id, that.id)
                    && Objects.equals(this.meal, that.meal)
                    && Objects.equals(this.date, that.date)
                    && Objects.equals(this.calories, that.calories)
                    && Objects.equals(this.userId, that.userId);
        }
    }

    public static final ModelMatcher<UserMeal, String> MATCHER = new ModelMatcher<>(
            new Function<UserMeal, String>() {
                @Override
                public String apply(UserMeal meal) {
                    return meal.toString();
                }
            });

}
