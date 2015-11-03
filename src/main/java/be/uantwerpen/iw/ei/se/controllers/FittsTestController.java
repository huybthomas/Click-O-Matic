package be.uantwerpen.iw.ei.se.controllers;

import be.uantwerpen.iw.ei.se.models.User;
import be.uantwerpen.iw.ei.se.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Quinten on 3/11/2015.
 */
@Controller
public class FittsTestController
{
    @Autowired
    private UserService userService;

    @ModelAttribute("currentUser")
    public User getCurrentUser()
    {
        return userService.getPrincipalUser();
    }

    @RequestMapping({"/FittsTest"})
    @PreAuthorize("hasRole('logon')")
    public String showFittsTestMain(final ModelMap model)
    {
        return "testPortal/fittsTest";
    }

}
