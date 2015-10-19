package be.uantwerpen.iw.ei.se.controllers;

import be.uantwerpen.iw.ei.se.models.User;
import be.uantwerpen.iw.ei.se.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Thomas on 19/10/2015.
 */
@Controller
public class UserHomeController
{
    @Autowired
    private UserService userService;

    @ModelAttribute("allUsers")
    public List<User> populateUsers()
    {
        return this.userService.findAll();
    }

    @RequestMapping({"/", "/homepage"})
    public String showHomepage(ModelMap model)
    {
        return "mainPortal/homepage";
    }
}
