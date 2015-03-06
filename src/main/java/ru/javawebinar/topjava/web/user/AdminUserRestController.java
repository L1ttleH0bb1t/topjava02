package ru.javawebinar.topjava.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.service.UserService;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class AdminUserRestController {

    @Autowired
    private UserService service;
}
