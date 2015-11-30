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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by Thomas on 20/10/2015.
 */
@Controller
public class RegistrationController
{
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value="/Registration", method= RequestMethod.GET)
    @PreAuthorize("hasRole('createUsers') and hasRole('logon')")
    public String createUserForm(ModelMap model)
    {
        User user = new User();

        //Set default role on Tester
        Iterable<Role> roleList = roleService.findAll();
        Iterator<Role> it = roleList.iterator();
        while(it.hasNext())
        {
            Role temp = it.next();

            if(temp.getName().equals("Tester"))
            {
                //Add role: 'Tester' to list
                user.setRoles(new ArrayList<Role>(Arrays.asList(temp)));
                break;  // break from while loop
            }
        }

        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.findAll());
        return "mainPortal/registration";
    }

    @RequestMapping(value="/Registration", method=RequestMethod.POST)
    @PreAuthorize("hasRole('createUsers') and hasRole('logon')")
    public String createUserSubmit(@Valid User user, BindingResult bindingResult, ModelMap model)
    {
        if(bindingResult.hasErrors())
        {
            return "mainPortal/registration";
        }
        else
        {
            if(userService.usernameAlreadyExists(user.getUserName()))
            {
                return "redirect:/Registration?errorAlreadyExists";
            }
            else
            {
                userService.add(user);
                return "redirect:/Users?success";
            }
        }
    }
}
