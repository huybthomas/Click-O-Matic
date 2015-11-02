package be.uantwerpen.iw.ei.se.controllers;

import be.uantwerpen.iw.ei.se.models.Permission;
import be.uantwerpen.iw.ei.se.models.Role;
import be.uantwerpen.iw.ei.se.models.User;
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
import java.util.Iterator;

/**
 * Created by Thomas on 20/10/2015.
 */
@Controller
public class RegistrationController
{
    @Autowired
    private UserService userService;

    @RequestMapping(value="/registration", method= RequestMethod.GET)
    @PreAuthorize("hasRole('createUsers') and hasRole('logon')")
    public String createUserForm(ModelMap model)
    {
        model.addAttribute("user", new User());
        return "mainPortal/registration";
    }

    @RequestMapping(value="/registration", method=RequestMethod.POST)
    @PreAuthorize("hasRole('createUsers') and hasRole('logon')")
    public String createUserSubmit(@Valid User user, BindingResult bindingResult, ModelMap model)
    {
        if(bindingResult.hasErrors())
        {
            return "mainPortal/registration";
        }
        else
        {
            ArrayList<Permission> permissions = new ArrayList<Permission>();
            permissions.add(new Permission("logon"));

            Role tester = new Role("User");
            tester.setPermissions(permissions);

            ArrayList<Role> roles = new ArrayList<Role>();
            roles.add(tester);
            user.setRoles(roles);

            if(userService.usernameAlreadyExists(user.getUserName()))
            {
                return "redirect:/registration?errorAlreadyExists";
            }
            else
            {
                userService.add(user);
                return "redirect:/registration?success";
            }
        }
    }
}
