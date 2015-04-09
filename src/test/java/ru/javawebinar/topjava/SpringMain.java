package ru.javawebinar.topjava;

import org.springframework.context.support.GenericXmlApplicationContext;
import ru.javawebinar.topjava.web.meal.UserMealRestController;

import java.util.Arrays;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
//        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml","spring/mock.xml")) {
        try (GenericXmlApplicationContext ctx = new GenericXmlApplicationContext()) {
            ctx.getEnvironment().setActiveProfiles(Profiles.POSTGRES);
            ctx.load("spring/spring-app.xml", "spring/mock.xml");
            ctx.refresh();
            System.out.println("\n" + Arrays.toString(ctx.getBeanDefinitionNames()) + "\n");
            UserMealRestController adminController = ctx.getBean(UserMealRestController.class);
            adminController.delete(7);
        }
    }
}
