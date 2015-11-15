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
 * Created by Thomas on 15/11/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ClickOMaticApplication.class)
@WebAppConfiguration
public class FittsTestControllerSecurityTests
{
    @Autowired
    private FittsTestController fittsTestController;

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void testOpenFittsTestWithoutLoginCredentials()
    {
        fittsTestController.showFittsTest("001", new ModelMap());
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles={"ADMIN"})
    public void testOpenFittsTestAdmin()
    {
        fittsTestController.showFittsTest("001", new ModelMap());
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(username = "ravan")
    public void testOpenFittsTestUsernameRavan()
    {
        fittsTestController.showFittsTest("001", new ModelMap());
    }

    @Test
    @WithUserDetails("thomas.huybrechts")
    public void testOpenFittsTestUserThomas()
    {
        fittsTestController.showFittsTest("001", new ModelMap());
    }
}
