package be.uantwerpen.iw.ei.se.controllers;

import be.uantwerpen.iw.ei.se.ClickOMaticApplication;
import be.uantwerpen.iw.ei.se.models.User;
import org.junit.Before;
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
import org.springframework.validation.BindingResult;

import static org.mockito.Mockito.mock;

/**
 * Created by Quinten on 28/10/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ClickOMaticApplication.class)
@WebAppConfiguration
public class RegistrationControllerSecurityTests
{
    @Autowired
    private RegistrationController registrationController;

    private BindingResult bindingResult;
    private User newUser;

    @Before
    public void init()
    {
        newUser = new User("Registration", "Test", "regTest", "test");

        bindingResult = mock(BindingResult.class);
    }

    // --- Create User Form --- \\\

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void testCreateUserForm_NoCredentials()
    {
        registrationController.createUserForm(new ModelMap());
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles={"createUsers"})
    public void testCreateUserForm_MayCreate_NotLoggedIn()
    {
        registrationController.createUserForm(new ModelMap());
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles={"logon"})
    public void testCreateUserForm_LoggedIn_MayNotCreate()
    {
        registrationController.createUserForm(new ModelMap());
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(username = "tester")
    public void testCreateUserForm_NormalUser()
    {
        registrationController.createUserForm(new ModelMap());
    }

    @Test
    @WithUserDetails("quinten.vanhasselt")
    public void testCreateUserForm_AllowedUser()
    {
        registrationController.createUserForm(new ModelMap());
    }


    // --- Create User Submit --- \\\

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void testCreateUserSubmit_NoCredentials()
    {
        registrationController.createUserSubmit(newUser, bindingResult, new ModelMap());
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles={"createUsers"})
    public void testCreateUserSubmit_MayCreate_NotLoggedIn()
    {
        registrationController.createUserSubmit(newUser, bindingResult, new ModelMap());
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles={"logon"})
    public void testCreateUserSubmit_LoggedIn_MayNotCreate()
    {
        registrationController.createUserSubmit(newUser, bindingResult, new ModelMap());
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(username = "tester")
    public void testCreateUserSubmit_NormalUser()
    {
        registrationController.createUserSubmit(newUser, bindingResult, new ModelMap());
    }

    @Test
    @WithUserDetails("quinten.vanhasselt")
    public void testCreateUserSubmit_AllowedUser()
    {
        registrationController.createUserSubmit(newUser, bindingResult, new ModelMap());
    }
}
