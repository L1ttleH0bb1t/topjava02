package ru.javawebinar.topjava.web.user;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.web.WebTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.Profiles.HSQLDB;
import static ru.javawebinar.topjava.Profiles.POSTGRES;
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

@ActiveProfiles({POSTGRES, DATAJPA})
public class AdminRestControllerTest extends WebTest {

    public static final String REST_URL = "/rest/admin/users/";

    @Autowired
    private UserService service;

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + (START_SEQ + 1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(ADMIN));
    }


    /*
        @Test
        public void testGetNotFound() throws Exception {
            mockMvc.perform(get(REST_URL + (1)))
                    .andExpect(status().isNotFound())
                    .andDo(print())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        public void testGetUnauth() throws Exception {
            mockMvc.perform(get(REST_URL).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        }

    */
    @Test
    public void testGetByEmail() throws Exception {
        mockMvc.perform(get(REST_URL + "by?email=" + USER.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(USER));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + START_SEQ).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        MATCHER.assertListEquals(Collections.singletonList(ADMIN), service.getAll());
    }

    @Test
    public void testUpdate() throws Exception {
        User updated = USER.copyAsUser();
        updated.setName("UpdatedName");
        updated.setRoles(Role.ROLE_ADMIN);
        mockMvc.perform(put(REST_URL + START_SEQ).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        MATCHER.assertEquals(updated, service.get(START_SEQ));
    }

    @Test
    public void testCreate() throws Exception {
        TestUser expected = new TestUser("New", "new@gmail.com", "newPass", Role.ROLE_USER, Role.ROLE_ADMIN);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected.copyAsUser()))).andExpect(status().isCreated());

        User returned = MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());

        MATCHER.assertEquals(expected, returned);
        MATCHER.assertListEquals(Arrays.asList(ADMIN, expected, USER), service.getAll());
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentListMatcher(ADMIN, USER)));
    }
}