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
 * Created by dries on 26/11/2015.
 */
@Controller
public class TestManagementController {

    @Autowired
    private UserService userService;

    @RequestMapping({"/AssignTest"})
    @PreAuthorize("hasRole('logon')")
    public String showAddTest(final ModelMap model)
    {
        return "mainPortal/assignTest";
    }
}
