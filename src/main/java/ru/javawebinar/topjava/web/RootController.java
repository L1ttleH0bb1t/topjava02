package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.BaseEntity;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.DateTimeFilter;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.UserUtil;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
@Controller
public class RootController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMealService userMealService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "redirect:meals";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model,
                        @RequestParam(value = "error", required = false) boolean error,
                        @RequestParam(value = "message", required = false) String message) {

        model.put("error", error);
        model.put("message", message);
        return "login";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String mealList(Model model) {
        model.addAttribute("meals", userMealService.getAll(LoggedUser.id()));
        return "mealsList";
    }

    //    @Secured("ROLE_ADMIN")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String userList() {
        return "userList";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(ModelMap model) {
        return "profile";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String updateProfile(@Valid UserTo userTo, BindingResult result, SessionStatus status, ModelMap model) {
        if (result.hasErrors()) {
            return "profile";
        } else {
            status.setComplete();
            LoggedUser.get().updateUserTo(userTo);
            userService.update(userTo);
            return "redirect:meals";
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(ModelMap model) {
        model.addAttribute("userTo", new UserTo());
        model.addAttribute("register", true);
        return "profile";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String saveRegister(@Valid UserTo userTo, BindingResult result, SessionStatus status, ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute("register", true);
            return "profile";
        } else {
            status.setComplete();
            userService.save(UserUtil.createFromTo(userTo));
            return "redirect:login?message=app.registered";
        }
    }
}
