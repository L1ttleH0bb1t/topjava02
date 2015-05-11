package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithLimit;
import ru.javawebinar.topjava.to.DateTimeFilter;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by eugene on 26.04.15.
 */



@RestController
@RequestMapping("ajax/profile/meals")
public class UserMealAjaxController extends AbstractMealController{

    private static final LoggerWrapper LOG = LoggerWrapper.get(UserMealAjaxController.class);

    @RequestMapping(method = RequestMethod.POST)
    public void createUpdate(@RequestParam("id") int id,
                       @RequestParam("meal") String meal,
                       @RequestParam("date") LocalDateTime date,
                       @RequestParam("calories") short calories) {

        UserMeal userMeal = new UserMeal(meal, calories, date);

        if (id == 0)
            super.create(userMeal);
        else
            super.update(userMeal, id);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = JsonUtil.CONTENT_TYPE)
    public UserMeal get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @RequestMapping(method = RequestMethod.GET, produces = JsonUtil.CONTENT_TYPE)
    public List<UserMealWithLimit> getAllWithLimit() {
        return super.getAllWithLimit();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserMealWithLimit> filterListWithLimit(DateTimeFilter filter) {
        return super.filterByDateWithLimit(TimeUtil.parseStartDate(filter), TimeUtil.parseEndDate(filter));
    }
}
