package be.uantwerpen.iw.ei.se.controllers;

import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTest;
import be.uantwerpen.iw.ei.se.models.JSONResponse;
import be.uantwerpen.iw.ei.se.services.FileService;
import be.uantwerpen.iw.ei.se.services.FittsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Verstraete on 20/11/2015.
 */
@Controller
@SessionAttributes({TestCreationController.ATTRIBUTE_NAME})
public class TestCreationController
{
    static final String ATTRIBUTE_NAME = "fittsTest";
    static final String BINDING_RESULT_NAME = "org.springframework.validation.BindingResult." + ATTRIBUTE_NAME;

    @Autowired
    private FittsService fittsService;

    @InitBinder
    private void allowFields(WebDataBinder webDataBinder)
    {
        webDataBinder.setAllowedFields("testID", "testStages");
    }

    @RequestMapping(value={"/TestCreator"})
    @PreAuthorize("hasRole('test-management') and hasRole('logon')")
    public String getFittsTestCreator(final ModelMap model)
    {
        return "testPortal/fittsTestCreator";
    }

    @RequestMapping(value="/FittsTestCreator", method=RequestMethod.GET)
    @PreAuthorize("hasRole('test-management') and hasRole('logon')")
    public String createFittsForm(ModelMap model)
    {
        //Check for already open session
        if(!model.containsAttribute(BINDING_RESULT_NAME))
        {
            model.addAttribute(ATTRIBUTE_NAME, new FittsTest());
        }

        return "testPortal/fittsTestCreator";
    }

    @RequestMapping(value="/FittsTestCreator/{testID}/", method=RequestMethod.GET)
    @PreAuthorize("hasRole('test-management') and hasRole('logon')")
    public String editFittsForm(@PathVariable String testID, ModelMap model)
    {
        //Check for already open session
        if(!model.containsAttribute(BINDING_RESULT_NAME))
        {
            FittsTest fittsTest = fittsService.findTestById(testID);

            if(fittsTest != null)
            {
                model.addAttribute(ATTRIBUTE_NAME, fittsTest);
            }
            else
            {
                model.clear();
                return "redirect:/TestPortal?errorTestNotFound";
            }
        }

        return "testPortal/fittsTestCreator";
    }

    @RequestMapping(value="/PostFittsTest/", method=RequestMethod.POST, headers={"Content-type=application/json"})
    @PreAuthorize("hasRole('test-management') and hasRole('logon')")
    public @ResponseBody JSONResponse submitFittsTest(@ModelAttribute(ATTRIBUTE_NAME) FittsTest fittsTestModel, @RequestBody FittsTest fittsTestRequest, BindingResult result, HttpServletRequest request, SessionStatus sessionStatus, final ModelMap model)
    {
        if(result.hasErrors())
        {
            return new JSONResponse("ERROR", "Connection error", "#?error", null);
        }

        fittsTestModel.setTestID(fittsTestRequest.getTestID());
        fittsTestModel.setTestStages(fittsTestRequest.getTestStages());

        //If test existed already
        if(fittsService.saveTest(fittsTestModel))
        {
            //Finish session
            sessionStatus.setComplete();

            return new JSONResponse("OK", "", "/TestManagement?testEdited", null);
        }
        else
        {
            if(fittsService.addTest(fittsTestModel))
            {
                //Finish session
                sessionStatus.setComplete();

                return new JSONResponse("OK", "", "/TestManagement?testAdded", null);
            }
            else
            {
                return new JSONResponse("ERROR", "The test: " + fittsTestModel.getTestID() + " could not be saved in the database!", "#?errorAlreadyExists", null);
            }
        }
    }
}
