package be.uantwerpen.iw.ei.se.controllers;

import be.uantwerpen.iw.ei.se.models.User;
import be.uantwerpen.iw.ei.se.services.RoleService;
import be.uantwerpen.iw.ei.se.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Quinten on 21/10/2015.
 */
@Controller
@SessionAttributes({UsersController.ATTRIBUTE_NAME})
public class UsersController
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
        webDataBinder.setAllowedFields("userName", "firstName", "lastName", "password", "roles");
    }

    @ModelAttribute("currentUser")
    public User getCurrentUser()
    {
        return userService.getPrincipalUser();
    }

    @ModelAttribute("allUsers")
    public Iterable<User> populateUsers()
    {
        return userService.findAll();
    }

    @RequestMapping({"/Users"})
    @PreAuthorize("hasRole('user-management') and hasRole('logon')")
    public String showViewUsers(final ModelMap model)
    {
        return "mainPortal/users";
    }

    @RequestMapping(value="/Users/{userName}/", method=RequestMethod.GET)
    @PreAuthorize("hasRole('user-management') and hasRole('logon')")
    public String editUserForm(@PathVariable String userName, final ModelMap model)
    {
        //Check for already open session
        if(!model.containsAttribute(BINDING_RESULT_NAME))
        {
            User user = userService.findByUserName(userName);

            if (user != null)
            {
                model.addAttribute(ATTRIBUTE_NAME, user);
                model.addAttribute("allRoles", roleService.findAll());
            }
            else
            {
                model.clear();
                return "redirect:/Users?errorUserNotFound";
            }
        }

        return "mainPortal/user-profile";
    }

    @RequestMapping(value={"/Users"}, method=RequestMethod.POST)
    @PreAuthorize("hasRole('user-management') and hasRole('logon')")
    public String saveUser(@Validated @ModelAttribute(ATTRIBUTE_NAME) User user, BindingResult result, HttpServletRequest request, SessionStatus sessionStatus, final ModelMap model)
    {
        if(result.hasErrors())
        {
            model.addAttribute("allRoles", roleService.findAll());
            return "mainPortal/user-profile";
        }

        if(userService.save(user))
        {
            //Finish session
            sessionStatus.setComplete();

            //Check if logged in user has been edited
            if(userService.getPrincipalUser() == null)
            {
                userService.setPrincipalUser(user);
            }

            return "redirect:/Users?userEdited";
        }
        else
        {
            return "redirect:" + request.getRequestURI() + "?errorAlreadyExists";
        }
    }

    @RequestMapping(value="/Users/{userName}/Delete")
    @PreAuthorize("hasRole('user-management') and hasRole('logon')")
    public String deleteUser(@PathVariable String userName, final ModelMap model)
    {
        userService.delete(userName);
        model.clear();

        return "redirect:/Users?userRemoved";
    }
}
