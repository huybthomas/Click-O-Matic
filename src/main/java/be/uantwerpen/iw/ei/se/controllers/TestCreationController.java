package be.uantwerpen.iw.ei.se.controllers;

import be.uantwerpen.iw.ei.se.fittsTest.models.FittsResult;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsStageResult;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTest;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTestStage;
import be.uantwerpen.iw.ei.se.models.JSONResponse;
import be.uantwerpen.iw.ei.se.services.FittsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Verstraete on 20/11/2015.
 */
@Controller
public class TestCreationController
{
    @Autowired
    private FittsService fittsService;

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
        model.addAttribute("fittsTest", new FittsTest());
        return "testPortal/fittsTestCreator";
    }

    @RequestMapping(value="/FittsTestCreator/{testID}/", method=RequestMethod.GET)
    @PreAuthorize("hasRole('test-management') and hasRole('logon')")
    public String editFittsForm(@PathVariable String testID, ModelMap model)
    {
        FittsTest fittsTest = fittsService.findTestById(testID);
        model.addAttribute("fittsTest", fittsTest);
        return "testPortal/fittsTestCreator";
    }

    @RequestMapping(value="/PostFittsTest/", method=RequestMethod.POST, headers={"Content-type=application/json"})
    @PreAuthorize("hasRole('test-management') and hasRole('logon')")
    public @ResponseBody JSONResponse submitFittsTest(@RequestBody FittsTest fittsTest, final ModelMap model)
    {
        //If test existed already
        if(fittsService.saveTest(fittsTest))
        {
            return new JSONResponse("OK", "", "/TestPortal?testEdited", null);
        }
        else
        {
            if (fittsService.addTest(fittsTest))
            {
                return new JSONResponse("OK", "", "/TestPortal?testAdded", null);
            }
            else
            {
                return new JSONResponse("ERROR", "The test: " + fittsTest.getTestID() + " could not be saved in the database!", "#?errorAlreadyExists", null);
            }
        }
    }

}
