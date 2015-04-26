package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.util.DbPopulator;

/**
 * User: gkislin
 * Date: 10.08.2014
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
abstract public class DbTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private DbPopulator dbPopulator;

    @Autowired
    protected UserService userService;

    @Autowired
    protected UserMealService userMealService;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
        userService.evictCache();
    }
}
