package ru.javawebinar.topjava.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.UserServiceTest;

/**
 * Created by eugene on 01.04.15.
 */
@ActiveProfiles({"postgres", "jpa", "datajpa,jpa"})
public class JpaUserServiceTest extends UserServiceTest {
}
