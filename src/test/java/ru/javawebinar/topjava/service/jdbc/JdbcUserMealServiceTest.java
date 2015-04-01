package ru.javawebinar.topjava.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.UserMealServiceTest;

/**
 * Created by eugene on 01.04.15.
 */
@ActiveProfiles({"postgres", "jdbc"})
public class JdbcUserMealServiceTest extends UserMealServiceTest{
}
