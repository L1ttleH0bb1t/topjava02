package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.web.meal.UserMealRestController;
import ru.javawebinar.topjava.web.user.AdminUserRestController;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("\n" + Arrays.toString(appCtx.getBeanDefinitionNames()) + "\n");
            AdminUserRestController adminController = appCtx.getBean(AdminUserRestController.class);
            adminController.delete(7);
            //adminController.getByMail("dummy");
            System.out.println("-----------------------------");
            UserMealRestController userMealController = appCtx.getBean(UserMealRestController.class);
            UserMeal userMeal = userMealController.create(new UserMeal("icecream", (short) 520, new Date()));
            userMeal.setCalories((short) 450);
            userMealController.update(userMeal);
            userMealController.delete(2);
            userMealController.getAll();
            userMealController.get(1);
            Calendar calendar = Calendar.getInstance();
            calendar.set(2015, 01, 11, 12, 55);
            Date start = calendar.getTime();
            calendar.set(2015, 02, 10, 14, 21);
            Date end = calendar.getTime();
            userMealController.filter(start, end);
        }
    }
}
