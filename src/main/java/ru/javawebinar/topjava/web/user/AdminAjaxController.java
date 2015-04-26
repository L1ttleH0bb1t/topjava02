package ru.javawebinar.topjava.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.List;

/**
 * User: javawebinar.topjava
 */
@RestController
@RequestMapping("/ajax/admin/users")
public class AdminAjaxController {

    @Autowired
    private UserHelper helper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        return helper.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) {
        helper.delete(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void update(@RequestParam("item_id") int id,
                       @RequestParam("name") String name,
                       @RequestParam("email") String email,
                       @RequestParam("password") String password) {
        if (id == 0) {
            User user = new User(null, name, email, password, true, Role.ROLE_USER);
            helper.create(user);
        }
    }
}
