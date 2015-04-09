package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.model.BaseEntity;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.service.UserService;

/**
 * Created by eugene on 09.04.15.
 */
@Controller
public class MealController {
    @Autowired
    private UserMealService userMealService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String userList(Model model) {
        model.addAttribute("meals", userMealService.getAll(BaseEntity.START_SEQ));
        model.addAttribute("userName", userService.get(BaseEntity.START_SEQ).getName());
        return "mealsList";
    }
}