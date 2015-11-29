package be.uantwerpen.iw.ei.se.controllers;

import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTest;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTestStage;
import be.uantwerpen.iw.ei.se.services.FittsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.ArrayList;
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
    @PreAuthorize("hasRole('logon')")
    public String getFittsTestCreator(final ModelMap model)
    {
        return "testPortal/fittsTestCreator";
    }

    @RequestMapping(value="/FittsTestCreator", method=RequestMethod.GET)
    @PreAuthorize("hasRole('logon')")
    public String createFittsForm(ModelMap model)
    {
        List<FittsTestStage> fittsTestStages = new ArrayList<FittsTestStage>();
        FittsTestStage temp = new FittsTestStage("001", 9, 25, 100);
        temp.setId(1L);
        fittsTestStages.add(temp);

        temp = new FittsTestStage("002", 11, 15, 150);
        temp.setId(2L);
        fittsTestStages.add(temp);

        temp = new FittsTestStage("003", 5, 50, 110);
        temp.setId(3L);
        fittsTestStages.add(temp);

        temp = new FittsTestStage("004", 2, 100, 200);
        temp.setId(4L);
        fittsTestStages.add(temp);

        model.addAttribute("test", new FittsTest("019", fittsTestStages));
        return "testPortal/fittsTestCreator";
    }

    //bollen zijn oneven aantallen
    @RequestMapping(value="/FittsTestCreator", method=RequestMethod.POST)
    @PreAuthorize(" hasRole('logon')")
    public String createFittsSubmit(@Valid FittsTest fittstest, BindingResult bindingResult, ModelMap model)
    {
        if(bindingResult.hasErrors())
        {
            return "testPortal/fittsTestCreator";
        }
        else
        {
           /* if(fittstest.getNumberOfDots()<2)
            {
                fittstest.setNumberOfDots(2);
            }
            if(fittstest.getDotSize()>70)
            {
                fittstest.setDotSize(70);
            }
            if(fittstest.getDotDistance()>250)
            {
                fittstest.setDotDistance(250);
            }


            for(FittsTest u : findAll())
            {
                int size = u.size();
                String givenID = fittstest.getTestID();
                fittstest.setTestID(givenID + "1");
            }*/


            fittsService.addTest(fittstest);
            return "redirect:/FittsTestCreator?success";
        }
    }

}
