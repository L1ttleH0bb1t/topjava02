package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.web.WebTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.Profiles.POSTGRES;
import static ru.javawebinar.topjava.TestUtil.userHttpBasic;
import static ru.javawebinar.topjava.UserTestData.ADMIN;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;
import static ru.javawebinar.topjava.web.json.JsonUtil.CONTENT_TYPE;


/**
 * Created by eugene on 22.04.15.
 */
@ActiveProfiles({POSTGRES, DATAJPA})
public class UserMealRestControllerTest extends WebTest {

    private static final String URL = "/rest/profile/meals/";

    @Autowired
    UserMealService service;

    @Test
    public void testCreate() throws Exception {
        UserMeal expected = new UserMeal(
                "Чай", (short) 50, initDateTime(2015, 02, 16, 21, 14));
        ResultActions action = mockMvc.perform(post(URL)
                .with(userHttpBasic(USER))
                .contentType(CONTENT_TYPE)
                .content(JsonUtil.writeValue(expected))).andExpect(status().isCreated());

        UserMeal returned = MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());

        MATCHER.assertEquals(expected, returned);
        MATCHER.assertListEquals(Arrays.asList(FISH, SALAD, expected), service.getAll(USER.getId()));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(URL + (START_SEQ + 2))
                .with(userHttpBasic(USER))
                .contentType(CONTENT_TYPE))
                .andDo(print())
                .andExpect(status().isOk());
        MATCHER.assertListEquals(Collections.singletonList(FISH), service.getAll(USER.getId()));
    }

    @Test
    public void testDeleteAll() throws Exception {
        mockMvc.perform(delete(URL)
                .with(userHttpBasic(USER))
                .contentType(CONTENT_TYPE))
                .andDo(print())
                .andExpect(status().isOk());
        MATCHER.assertListEquals(Collections.emptyList(), service.getAll(USER.getId()));
    }

    @Test
    public void testUpdate() throws Exception {
        UserMeal expected = new UserMeal(SALAD);
        expected.setMeal("Борщ");
        mockMvc.perform(put(URL + (START_SEQ + 2))
                .with(userHttpBasic(USER))
                .contentType(CONTENT_TYPE)
                .content(JsonUtil.writeValue(expected))).andExpect(status().isOk());
        MATCHER.assertListEquals(Arrays.asList(FISH, expected), service.getAll(USER.getId()));
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(URL + (START_SEQ + 2))
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(MATCHER.contentMatcher(SALAD));
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(MATCHER.contentListMatcher(FISH, SALAD));
    }

    @Test
    public void testFilterByDate() throws Exception {
        mockMvc.perform(get(URL + "filter?startDate=2015-03-15 20:14&endDate=2015-03-16 20:00")
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(MATCHER.contentListMatcher(SALAD));
    }



}
