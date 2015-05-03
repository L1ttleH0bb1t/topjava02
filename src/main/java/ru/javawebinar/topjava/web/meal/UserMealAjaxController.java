package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by eugene on 26.04.15.
 */



@RestController
@RequestMapping("ajax/profile/meals")
public class UserMealAjaxController extends AbstractMealController{

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
    public List<UserMeal> getAll() {
        return super.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }
}
