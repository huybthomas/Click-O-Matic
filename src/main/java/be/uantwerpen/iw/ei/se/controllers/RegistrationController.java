package be.uantwerpen.iw.ei.se.controllers;

import be.uantwerpen.iw.ei.se.models.Role;
import be.uantwerpen.iw.ei.se.models.User;
import be.uantwerpen.iw.ei.se.services.RoleService;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by Thomas on 20/10/2015.
 */
@Controller
@SessionAttributes({RegistrationController.ATTRIBUTE_NAME})
public class RegistrationController
{
    static final String ATTRIBUTE_NAME = "user";
    static final String BINDING_RESULT_NAME = "org.springframework.validation.BindingResult." + ATTRIBUTE_NAME;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @InitBinder
    private void allowFields(WebDataBinder webDataBinder)
    {
        webDataBinder.setAllowedFields("firstName", "lastName", "userName", "password", "roles");
    }

    @RequestMapping(value="/Registration", method= RequestMethod.GET)
    @PreAuthorize("hasRole('user-management') and hasRole('logon')")
    public String createUserForm(ModelMap model)
    {
        //Check for already open session
        if(!model.containsAttribute(BINDING_RESULT_NAME))
        {
            User user = new User();

            //Set default role on Tester
            Iterable<Role> roleList = roleService.findAll();
            Iterator<Role> it = roleList.iterator();
            while(it.hasNext())
            {
                Role role = it.next();

                if(role.getName().equals("Tester"))
                {
                    //Add role: 'Tester' to list
                    user.setRoles(new ArrayList<Role>(Arrays.asList(role)));
                    break;  // break from while loop
                }
            }

            model.addAttribute(ATTRIBUTE_NAME, user);
            model.addAttribute("allRoles", roleService.findAll());
        }

        return "mainPortal/registration";
    }

    @RequestMapping(value="/Registration", method=RequestMethod.POST)
    @PreAuthorize("hasRole('user-management') and hasRole('logon')")
    public String createUserSubmit(@Validated @ModelAttribute(ATTRIBUTE_NAME) User user, BindingResult bindingResult, HttpServletRequest request, SessionStatus sessionStatus, ModelMap model)
    {
        if(bindingResult.hasErrors())
        {
            return "redirect:" + request.getRequestURI() + "/?error";
        }
        else
        {
            if(userService.add(user))
            {
                //Finish session
                sessionStatus.setComplete();
                return "redirect:/Users?userAdded";
            }
            else
            {
                return "redirect:"  + request.getRequestURI() + "?errorAlreadyExists";
            }
        }
    }
}
