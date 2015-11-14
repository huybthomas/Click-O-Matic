package be.uantwerpen.iw.ei.se.controllers;

import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTest;
import be.uantwerpen.iw.ei.se.models.User;
import be.uantwerpen.iw.ei.se.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(value={"/TestPortal/{testID}"}, method=RequestMethod.GET)
    @PreAuthorize("hasRole('logon')")
    public String showFittsTest(@PathVariable String testID, final ModelMap model)
    {
        FittsTest test;

        //Temporary give default test attribute
        if(testID.equals("001"))
        {
            test = new FittsTest("001", 9, 25, 100);
        }
        else
        {
            test = new FittsTest("002", 15, 10, 150);
        }

        model.addAttribute("runningTest", test);
        return "testPortal/fittsTest";
    }
}
