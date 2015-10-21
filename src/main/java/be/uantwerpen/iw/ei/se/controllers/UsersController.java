package be.uantwerpen.iw.ei.se.controllers;

import be.uantwerpen.iw.ei.se.models.User;
import be.uantwerpen.iw.ei.se.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Quinten on 21/10/2015.
 */
@Controller
public class UsersController
{
    @Autowired
    private UserService userService;

    @ModelAttribute("currentUser")
    public User getCurrentUser() {
        return userService.getPrincipalUser();
    }

    @ModelAttribute("allUsers")
    public List<User> populateUsers()
    {
        return userService.findAll();
    }

    @RequestMapping({"/users"})
    @PreAuthorize("hasRole('viewUsers') and hasRole('logon')")
    public String showViewUsers(ModelMap model) {   return "mainPortal/users";    }

    /* @RequestMapping({"/users(id="})
    @PreAuthorize("hasRole('viewUsers') and hasRole('logon')")
    public String showViewUsers(ModelMap model) {   return "mainPortal/users";    } */

}
