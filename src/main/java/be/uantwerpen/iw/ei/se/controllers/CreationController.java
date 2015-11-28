package be.uantwerpen.iw.ei.se.controllers;

import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTest;
import be.uantwerpen.iw.ei.se.repositories.FittsRepository;
import be.uantwerpen.iw.ei.se.services.FittsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Verstraete on 20/11/2015.
 */
@Controller
public class CreationController {

    @Autowired
    private FittsService fittsService;

    @RequestMapping(value="/fittsTestCreator", method= RequestMethod.GET)
    @PreAuthorize("hasRole('logon')")
    public String createFittsForm(ModelMap model)
    {
        model.addAttribute("fittstest", new FittsTest());
        return "testPortal/fittsTestCreator";
    }
    //bollen zijn oneven aantallen
    @RequestMapping(value="/fittsTestCreator", method=RequestMethod.POST)
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


            fittsService.add(fittstest);
            return "redirect:/fittsTestCreator?success";
        }
    }

}
