package ru.javawebinar.topjava.web;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.Profiles.HSQLDB;
import static ru.javawebinar.topjava.Profiles.POSTGRES;

/**
 * GKislin
 * 10.04.2015.
 */
@ActiveProfiles({POSTGRES, DATAJPA})
public class ResourceControllerTest extends WebTest{

    @Test
    public void testUserList() throws Exception {
        mockMvc.perform(get("/resources/css/style.css"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.valueOf("text/css")))
                .andExpect(status().isOk());
    }
}