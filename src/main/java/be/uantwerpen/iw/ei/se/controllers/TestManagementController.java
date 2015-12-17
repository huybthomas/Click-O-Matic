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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dries on 26/11/2015.
 */
@Controller
@SessionAttributes({TestManagementController.ATTRIBUTE_NAME})
public class TestManagementController
{
    static final String ATTRIBUTE_NAME = "user";
    static final String BINDING_RESULT_NAME = "org.springframework.validation.BindingResult." + ATTRIBUTE_NAME;

    @Autowired
    private UserService userService;

    @Autowired
    private FittsService fittsService;

    @InitBinder
    private void allowFields(WebDataBinder webDataBinder)
    {
        webDataBinder.setAllowedFields("tests");
    }

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
        //Check for already open session
        if(!model.containsAttribute(BINDING_RESULT_NAME))
        {
            User user = userService.findByUserName(userName);

            if(user != null)
            {
                model.addAttribute(ATTRIBUTE_NAME, user);
            }
            else
            {
                model.clear();
                return "redirect:/Users?errorUserNotFound";
            }
        }

        return "mainPortal/assignTest";
    }

    @RequestMapping(value={"/Users/Assign/"}, method=RequestMethod.POST)
    @PreAuthorize("hasRole('test-management') and hasRole('logon')")
    public String saveAssign(@Validated @ModelAttribute(ATTRIBUTE_NAME) User user, BindingResult result, HttpServletRequest request, SessionStatus sessionStatus, final ModelMap model)
    {
        if(result.hasErrors())
        {
            return "redirect:" + request.getRequestURI() + "/?error";
        }

        if(userService.save(user))
        {
            //Finish session
            sessionStatus.setComplete();
            return "redirect:/Users?testsAssigned";
        }
        else
        {
            model.clear();
            return "redirect:/Users?errorUserNotFound";
        }
    }

    @RequestMapping({"/TestManagement"})
    @PreAuthorize("hasRole('test-management') and hasRole('logon')")
    public String showTestPortal(final ModelMap model) {

        Iterable<FittsTest> allTests = fittsService.findAllTests();
        model.addAttribute("allTests", allTests);

        return "testPortal/allTests";
    }

    @RequestMapping(value="/FittsTestDelete/{testID}/")
    @PreAuthorize("hasRole('test-management') and hasRole('logon')")
    public String deleteTest(@PathVariable String testID, final ModelMap model)
    {
        fittsService.deleteTest(testID);
        model.clear();

        return "redirect:/TestManagement?testDeleted";
    }
}
