package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.web.WebTest;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.Profiles.POSTGRES;
import static ru.javawebinar.topjava.MealTestData.FISH;
import static ru.javawebinar.topjava.MealTestData.SALAD;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * Created by eugene on 20.04.15.
 */
@ActiveProfiles({POSTGRES,DATAJPA})
public class UserMealControllerTest extends WebTest{

    @Test
    public void testUserMealList() throws Exception{
        mockMvc.perform(get("/meals"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("mealsList"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/mealsList.jsp"))
                .andExpect(model().attribute("meals", hasSize(2)))
                .andExpect(model().attribute("meals", hasItem(
                        allOf(
                                hasProperty("id", is(START_SEQ + 3)),
                                hasProperty("meal", is(FISH.getMeal()))
                        )
                )))
                .andExpect(model().attribute("meals", hasItem(
                        allOf(
                                hasProperty("id", is(START_SEQ + 2)),
                                hasProperty("meal", is(SALAD.getMeal()))
                        )
                )));
    }
}
