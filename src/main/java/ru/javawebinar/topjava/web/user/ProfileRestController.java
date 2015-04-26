package ru.javawebinar.topjava.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.User;

/**
 * GKislin
 * 06.03.2015.
 */
@RestController
@RequestMapping("/rest/profile")
public class ProfileRestController {

    @Autowired
    private UserHelper helper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public User get() {
        return helper.get(LoggedUser.id());
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void delete() {
        helper.delete(LoggedUser.id());
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody User user) {
        helper.update(user, LoggedUser.id());
    }

    @RequestMapping(value = "/text", method = RequestMethod.GET)
    public String testUTF() {
        return "Русский текст";
    }
}

