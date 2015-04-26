package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.UserMeal;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.web.json.JsonUtil.CONTENT_TYPE;


/**
 * Created by eugene on 20.04.15.
 */
@RestController
@RequestMapping("/rest/profile/meals")
public class UserMealRestController {

    private  static final LoggerWrapper LOG = LoggerWrapper.get(UserMealRestController.class);

    @Autowired
    private UserMealHelper helper;

    @RequestMapping(method = RequestMethod.POST, consumes = CONTENT_TYPE, produces = "application/json; charset=utf-8")
    public ResponseEntity<UserMeal> create(@RequestBody UserMeal userMeal) {
        UserMeal created = helper.create(userMeal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/rest/profile/meals/{id}")
                .buildAndExpand(created.getId()).toUri();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriOfNewResource);
        return new ResponseEntity<>(created, httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) {
        helper.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = CONTENT_TYPE)
    public void update(@RequestBody UserMeal userMeal, @PathVariable("id") int id) {
        helper.update(userMeal, id);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteAll() {
        helper.deleteAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = CONTENT_TYPE)
    public UserMeal get(@PathVariable("id") int id) {

        return helper.get(id);
    }

    @RequestMapping(method = RequestMethod.GET, produces = CONTENT_TYPE)
    public List<UserMeal> getAll() { return helper.getAll(); }

    @RequestMapping(value = "/filter", method = RequestMethod.GET, produces = CONTENT_TYPE)
    public List<UserMeal> filterByDate(@RequestParam("startDate")LocalDateTime startDate,
                                       @RequestParam("endDate") LocalDateTime endDate) {
        return helper.filterByDate(startDate, endDate);
    }
}
