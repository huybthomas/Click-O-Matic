package be.uantwerpen.iw.ei.se.controllers;

import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTest;
import be.uantwerpen.iw.ei.se.models.User;
import be.uantwerpen.iw.ei.se.services.FittsService;
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

    @Autowired
    private FittsService fittsService;

    @ModelAttribute("currentUser")
    public User getCurrentUser()
    {
        return userService.getPrincipalUser();
    }

    @ModelAttribute("allFittsTest")
    public Iterable<FittsTest> populateFitts()
    {
        return fittsService.findAll();
    }

    @RequestMapping({"/TestPortal"})
    @PreAuthorize("hasRole('logon')")
    public String showTestPortal(final ModelMap model)
    {
        model.addAttribute("allUserFittsTests", fittsService.findAll());

        return "testPortal/testPortal";
    }

    @RequestMapping(value={"/TestPortal/{testID}"}, method=RequestMethod.GET)
    @PreAuthorize("hasRole('logon')")
    public String showFittsTest(@PathVariable String testID, final ModelMap model)
    {
        FittsTest test = fittsService.findById(testID);

        if(test != null)
        {
            model.addAttribute("runningTest", test);
            return "testPortal/fittsTest";
        }
        else
        {
            return "redirect:/TestPortal?errorTestNotFound";
        }
    }

    @RequestMapping(value={"/TestResult/{testID}"}, method=RequestMethod.GET)
    @PreAuthorize("hasRole('logon')")
    public String showFittsTestResult(@PathVariable String testID, final ModelMap model)
    {

        return "testPortal/fittsTestResult";
    }

    @RequestMapping(value={"/TestDetails/{testID}"}, method=RequestMethod.GET)
    @PreAuthorize("hasRole('logon')")
    public String showFittsTestDetails(@PathVariable String testID, final ModelMap model)
    {

        return "testPortal/fittsTestDetails";
    }
    @RequestMapping(value={"/TestCreator"})
    @PreAuthorize("hasRole('logon')")
    public String FittsTestCreator(final ModelMap model)
    {

        return "testPortal/fittsTestCreator";
    }
}
