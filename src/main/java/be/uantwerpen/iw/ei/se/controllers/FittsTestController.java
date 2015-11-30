package be.uantwerpen.iw.ei.se.controllers;

import be.uantwerpen.iw.ei.se.fittsTest.models.FittsResult;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsStageResult;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTest;
import be.uantwerpen.iw.ei.se.models.JSONResponse;
import be.uantwerpen.iw.ei.se.models.User;
import be.uantwerpen.iw.ei.se.services.FittsService;
import be.uantwerpen.iw.ei.se.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

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

    @RequestMapping({"/TestPortal"})
    @PreAuthorize("hasRole('logon')")
    public String showTestPortal(final ModelMap model)
    {
        if(userService.getPrincipalUser().hasTestManagementPermission()) {
            model.addAttribute("allUserFittsTests", fittsService.findAllTests());
        } else {
            model.addAttribute("allUserFittsTests", userService.getPrincipalUser().getTests());
        }
        return "testPortal/testPortal";
    }

    @RequestMapping(value={"/TestPortal/{testID}/"}, method=RequestMethod.GET)
    @PreAuthorize("hasRole('logon')")
    public String showFittsTest(@PathVariable String testID, final ModelMap model)
    {
        FittsTest test = fittsService.findTestById(testID);

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

    @RequestMapping(value={"/TestResult/{resultID}/"}, method=RequestMethod.GET)
    @PreAuthorize("hasRole('logon')")
    public String showFittsTestResult(@PathVariable String resultID, final ModelMap model)
    {
        FittsResult result = fittsService.findResultById(resultID);

        if(result != null)
        {
            model.addAttribute("fittsResult", result);
            return "testPortal/fittsTestResult";
        }
        else
        {
            return "redirect:/TestResult?errorResultNotFound";
        }
    }

    @RequestMapping(value={"/TestDetails/{testID}/"}, method=RequestMethod.GET)
    @PreAuthorize("hasRole('logon')")
    public String showFittsTestDetails(@PathVariable String testID, final ModelMap model)
    {
        FittsTest test = fittsService.findTestById(testID);
        int amtOfStages = test.getTestStages().size();

        if(test != null)
        {
            model.addAttribute("fittsTest", test);
            model.addAttribute("amtOfStages", amtOfStages);
            return "testPortal/fittsTestDetails";
        }
        else
        {
            return "redirect:/TestPortal?errorTestNotFound";
        }
    }

    @RequestMapping(value="/PostFittsResult/{testID}/", method=RequestMethod.POST, headers={"Content-type=application/json"})
    @PreAuthorize("hasRole('logon')")
    public @ResponseBody JSONResponse saveFittsResult(@RequestBody List<FittsStageResult> testResults, @PathVariable String testID, final ModelMap model)
    {
        FittsTest test = fittsService.findTestById(testID);

        if(test != null)
        {
            //Set complete state of test
            test.setCompleted(true);
            fittsService.saveTest(test);

            //Save result to the database
            //Get the next available result id
            int numberOfResults = 0;
            Iterator<FittsResult> resultIterator = fittsService.findResultsByTestId(testID).iterator();

            while(resultIterator.hasNext())
            {
                numberOfResults++;
            }

            String resultID = "result-" + numberOfResults;

            //Check for already existing resultID
            while(fittsService.resultIdAlreadyExists(resultID))
            {
                numberOfResults++;

                resultID = "result-" + numberOfResults;
            }

            FittsResult newResult = new FittsResult("result-" + numberOfResults, testID, new Date(), testResults);

            if(fittsService.saveTestResult(newResult))
            {
                //Search for not completed tests
                Iterable<FittsTest> notCompletedTests = fittsService.findTestsByCompleteState(false);

                if(notCompletedTests.iterator().hasNext())
                {
                    //Not completed test found
                    FittsTest nextNotCompletedTest = notCompletedTests.iterator().next();

                    //Give URL of next available test
                    return new JSONResponse("OK", "", "/TestPortal/" + nextNotCompletedTest.getTestID() + "/", true);
                }
                else
                {
                    //No other tests available, go back to main testportal page
                    return new JSONResponse("OK", "", "/TestPortal", false);
                }
            }
            else
            {
                return new JSONResponse("ERROR", "The test: " + testID + " could not be saved in the database!", "/TestPortal", false);
            }
        }
        else
        {
            return new JSONResponse("ERROR", "The specified test: " + testID + " could not be found!", "/TestPortal", false);
        }
    }
}
