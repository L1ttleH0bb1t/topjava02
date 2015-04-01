package ru.javawebinar.topjava.service.datajpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.UserServiceTest;

/**
 * Created by eugene on 01.04.15.
 */
@ActiveProfiles({"postgres", "datajpa", "datajpa,jpa"})
public class DatajpaUserServiceTest extends UserServiceTest{
}
