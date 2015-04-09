package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.repository.JpaUtil;

/**
 * Created by eugene on 09.04.15.
 */
abstract public class JpaTest extends UserServiceTest {
    @Autowired
    private JpaUtil jpaUtil;
    @Override
    public void setUp() throws Exception {
        super.setUp();
        jpaUtil.clear2ndLevelHibernateCache();
    }
}
