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
 * Created by Nick on 15/12/2015.
 */
@Controller
public class AssignTestController {
    @Autowired
    private UserService userService;

    @Autowired
    private FittsService fittsService;

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

    @RequestMapping({"/Assign/TestsToUser"})
    @PreAuthorize("hasRole('test-management') and hasRole('logon')")
    public String showAddTest(final ModelMap model)
    {
        return "mainPortal/testsToUser";
    }

    @RequestMapping({"/Assign/UsersToTest"})
    @PreAuthorize("hasRole('test-management') and hasRole('logon')")
    public String showAddUser(final ModelMap model)
    {
        return "mainPortal/usersToTest";
    }

    @RequestMapping(value="/Assign/TestsToUser/{userName}/", method= RequestMethod.GET)
    @PreAuthorize("hasRole('test-management') and hasRole('logon')")
        public String editAssignedTest(@PathVariable String userName, final ModelMap model)
        {
            User user = userService.findByUserName(userName);

            if(user != null)
            {
                model.addAttribute("user", user);
                return "mainPortal/testsToUser";
            }
            else
            {
                model.addAttribute("user", null);
                return "redirect:/Users?errorUserNotFound";
            }
    }

    @RequestMapping(value="/Assign/UsersToTest/{testID}/", method= RequestMethod.GET)
    @PreAuthorize("hasRole('test-management') and hasRole('logon')")
    public String editAssignedUser(@PathVariable String testID, final ModelMap model)
    {
        FittsTest test = fittsService.findTestById(testID);

        if(testID != null)
        {
            model.addAttribute("test", testID);
            return "mainPortal/usersToTest";
        }
        else
        {
            model.addAttribute("test", null);
            return "redirect:/Tests?errorTestsNotFound";
        }
    }

    @RequestMapping(value={"/Assign/TestsToUser/"}, method=RequestMethod.POST)
    @PreAuthorize("hasRole('test-management') and hasRole('logon')")
    public String saveAssign(@Valid User user, BindingResult result, final ModelMap model)
    {
        if(result.hasErrors())
        {
            return "redirect:/Assign/TestsToUser/" + user.getUserName() + "/?error";
        }

        userService.save(user);
        return "redirect:/Users";
    }

    @RequestMapping(value={"/Assign/UsersToTest/"}, method=RequestMethod.POST)
    @PreAuthorize("hasRole('test-management') and hasRole('logon')")
    public String saveAssignUser(@Valid FittsTest testID , BindingResult result, final ModelMap model)
    {
        if(result.hasErrors())
        {
            return "redirect:/Assign/UsersToTest/" + testID.getTestID() + "/?error";
        }


        fittsService.saveTest(testID);
        return "redirect:/Users";
    }

}
