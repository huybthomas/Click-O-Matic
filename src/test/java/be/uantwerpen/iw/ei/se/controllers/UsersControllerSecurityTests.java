package be.uantwerpen.iw.ei.se.controllers;

import be.uantwerpen.iw.ei.se.ClickOMaticApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ui.ModelMap;

/**
 * Created by Quinten on 28/10/2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ClickOMaticApplication.class)
@WebAppConfiguration
public class UsersControllerSecurityTests
{
    @Autowired
    private UsersController usersController;

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void testShowViewUsers_NoCredentials()
    {
        usersController.showViewUsers(new ModelMap());
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles={"viewUsers"})
    public void testShowViewUsers_MayView_NotLoggedIn()
    {
        usersController.showViewUsers(new ModelMap());
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles={"logon"})
    public void testShowViewUsers_LoggedIn_MayNotView()
    {
        usersController.showViewUsers(new ModelMap());
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(username = "tester")
    public void testShowViewUsers_NormalUser()
    {
        usersController.showViewUsers(new ModelMap());
    }

    @Test
    @WithUserDetails("quinten.vanhasselt")
    public void testShowViewUsers_AllowedUser()
    {
        usersController.showViewUsers(new ModelMap());
    }
}
