package be.uantwerpen.iw.ei.se.controllers;

import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTest;
import be.uantwerpen.iw.ei.se.models.JSONResponse;
import be.uantwerpen.iw.ei.se.models.User;
import be.uantwerpen.iw.ei.se.models.UserListWrapper;
import be.uantwerpen.iw.ei.se.services.FittsService;
import be.uantwerpen.iw.ei.se.services.UserService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 15/12/2015.
 */
@Controller
public class AssignTestController {
    @Autowired
    private UserService userService;

    @Autowired
    private FittsService fittsService;

    @ModelAttribute("allUsers")
    public Iterable<User> populateUsers()
    {
        return userService.findAll();
    }

    @ModelAttribute("allTests")
    public Iterable<FittsTest> populateTest()
    {
        return fittsService.findAllTests();
    }

    @RequestMapping({"/Assign/TestsToUser"})
    @PreAuthorize("hasRole('test-management') and hasRole('logon')")
    public String showAddTest(final ModelMap model)
    {
        return "mainPortal/testsToUser";
    }

    @RequestMapping({"/Assign/UsersToTest"})
    @PreAuthorize("hasRole('test-management') and hasRole('logon')")
    public String showAddUser(final ModelMap model)
    {
        return "mainPortal/usersToTest";
    }

    @RequestMapping(value="/Assign/TestsToUser/{userName}/", method= RequestMethod.GET)
    @PreAuthorize("hasRole('test-management') and hasRole('logon')")
        public String editAssignedTest(@PathVariable String userName, final ModelMap model)
        {
            User user = userService.findByUserName(userName);

            if(user != null)
            {
                model.addAttribute("user", user);
                return "mainPortal/testsToUser";
            }
            else
            {
                model.addAttribute("user", null);
                return "redirect:/Users?errorUserNotFound";
            }
    }

    @RequestMapping(value="/Assign/UsersToTest/{testID}/", method= RequestMethod.GET)
    @PreAuthorize("hasRole('test-management') and hasRole('logon')")
    public String editAssignedUser(@PathVariable String testID, final ModelMap model)
    {
        FittsTest test = fittsService.findTestById(testID);

        // put arraylist in wrapper class
        UserListWrapper usersAll = new UserListWrapper();
        usersAll.setUsers(Lists.newArrayList(userService.findAll()));

        // Add every user that already has this test assigned to him/her
        ArrayList<User> usersSelected = new ArrayList<>();
        for(User u : userService.findAll()) {
            if(u.getTests().contains(test)) {
                usersSelected.add(u);
            }
        }

        // put arraylist in wrapper class
        UserListWrapper userListWrapper = new UserListWrapper();
        userListWrapper.setUsers(usersSelected);

        if(testID != null)
        {
            model.addAttribute("test", test);
            model.addAttribute("usersAll", usersAll);
            model.addAttribute("userListWrapper", userListWrapper);
            return "mainPortal/assignUser";
        }
        else
        {
            model.addAttribute("test", null);
            return "redirect:/Tests?errorTestsNotFound";
        }
    }

    @RequestMapping(value={"/Assign/TestsToUser/"}, method=RequestMethod.POST)
    @PreAuthorize("hasRole('test-management') and hasRole('logon')")
    public String saveAssign(@Valid User user, BindingResult result, final ModelMap model)
    {
        if(result.hasErrors())
        {
            return "redirect:/Assign/TestsToUser/" + user.getUserName() + "/?error";
        }

        userService.save(user);
        return "redirect:/Assign/TestsToUser";
    }

    @RequestMapping(value={"/Assign/UsersToTest/{testID}/"}, method=RequestMethod.POST, headers={"Content-type=application/json"})
    @PreAuthorize("hasRole('test-management') and hasRole('logon')")
    public @ResponseBody JSONResponse saveAssignUser(@PathVariable String testID, @RequestBody UserListWrapper userListWrapper, BindingResult result, final ModelMap model)
    {
        if(result.hasErrors())
        {
            return new JSONResponse("ERROR", "Could not save to database", "#?error", null);
        }

        FittsTest test = fittsService.findTestById(testID);

        for(User user : userService.findAll()) {

            String userName = user.getUserName();
            boolean contains = false;

            for(User u : userListWrapper.getUsers()) {
                if(u.getUserName().equals(userName)) {
                    contains = true;
                    break;
                }
            }

            List<FittsTest> list = user.getTests();
            if(contains) {


                if(!list.contains(test)) {
                    list.add(test);
                    user.setTests(list);
                    userService.save(user);
                }
            } else {
                if(list.contains(test)) {
                    list.remove(test);
                    user.setTests(list);
                    userService.save(user);
                }
            }
        }


        /*for(User u : userListWrapper.getUsers()) {
            List<FittsTest> list = u.getTests();
            list.add(fittsService.findTestById(testID));
            u.setTests(list);
            userService.save(u);
        }*/
        //return "redirect:/Assign/UsersToTest";
        return new JSONResponse("OK", "", "/Assign/UsersToTest", null);

        //If test existed already
        /*if(fittsService.saveTest(fittsTest))
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
        }*/
    }

}
