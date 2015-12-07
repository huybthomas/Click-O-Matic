package be.uantwerpen.iw.ei.se.controllers;

import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTest;
import be.uantwerpen.iw.ei.se.models.User;
import be.uantwerpen.iw.ei.se.services.FittsService;
import be.uantwerpen.iw.ei.se.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by dries on 26/11/2015.
 */
@Controller
public class TestManagementController
{
    @Autowired
    private UserService userService;

    @Autowired
    private FittsService fittsService;

    @RequestMapping({"/AssignTest"})
    @PreAuthorize("hasRole('test-management') and hasRole('logon')")
    public String showAddTest(final ModelMap model)
    {
        return "mainPortal/assignTest";
    }

    @ModelAttribute("allUsers")
    public Iterable<User> populateUsers()
    {
        return userService.findAll();
    }

    @ModelAttribute("allTests")
    public Iterable<FittsTest> populateTest()
    {
        return fittsService.findAllTests();
    }

    @RequestMapping(value="/AssignTest/{userName}/", method=RequestMethod.GET)
    @PreAuthorize("hasRole('test-management') and hasRole('logon')")
    public String editAssignedTest(@PathVariable String userName, final ModelMap model)
    {
        User user = userService.findByUserName(userName);

        if(user != null)
        {
            model.addAttribute("user", user);
            return "mainPortal/assignTest";
        }
        else
        {
            model.addAttribute("user", null);
            return "redirect:/Users?errorUserNotFound";
        }
    }

    @RequestMapping(value={"/Users/Assign"}, method=RequestMethod.POST)
    @PreAuthorize("hasRole('test-management') and hasRole('logon')")
    public String saveAssign(@Valid User user, BindingResult result, final ModelMap model)
    {
        if(result.hasErrors())
        {
            return "redirect:/AssignTest/" + user.getUserName() + "/?error";
        }

        userService.save(user);
        return "redirect:/Users?testAssigned";
    }
}
