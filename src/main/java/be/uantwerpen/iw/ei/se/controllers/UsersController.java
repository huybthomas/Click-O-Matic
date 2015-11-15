package be.uantwerpen.iw.ei.se.controllers;

import be.uantwerpen.iw.ei.se.models.User;
import be.uantwerpen.iw.ei.se.services.RoleService;
import be.uantwerpen.iw.ei.se.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Quinten on 21/10/2015.
 */
@Controller
public class UsersController
{
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

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

    @RequestMapping({"/users"})
    @PreAuthorize("hasRole('viewUsers') and hasRole('logon')")
    public String showViewUsers(final ModelMap model)
    {
        return "mainPortal/users";
    }

    @RequestMapping(value="/users/{userName}/", method=RequestMethod.GET)
    @PreAuthorize("hasRole('editUsers') and hasRole('logon')")      // rollen voor wie wat mag editen, bv enkel eigen profiel
    public String editUserForm(@PathVariable String userName, final ModelMap model)
    {
        User user = userService.findByUserName(userName);

        if(user != null)
        {
            model.addAttribute("user", user);
            model.addAttribute("allRoles", roleService.findAll());
            return "mainPortal/user-profile";
        }
        else
        {
            model.addAttribute("user", null);
            return "redirect:/users?errorUserNotFound";
        }
    }

    @RequestMapping(value={"/users"}, method=RequestMethod.POST)
    @PreAuthorize("hasRole('editUsers') and hasRole('logon')")
    public String saveUser(@Valid User user, BindingResult result, final ModelMap model)
    {
        if(result.hasErrors()){
            model.addAttribute("allRoles", roleService.findAll());
            return "mainPortal/user-profile";
        }
        userService.save(user);
        return "redirect:/users";
    }

    @RequestMapping(value="/users/{userName}/delete")
    @PreAuthorize("hasRole('editUsers') and hasRole('logon')")
    public String deleteUser(@PathVariable String userName, final ModelMap model)
    {
        userService.delete(userName);
        model.clear();
        return "redirect:/users";
    }
}
