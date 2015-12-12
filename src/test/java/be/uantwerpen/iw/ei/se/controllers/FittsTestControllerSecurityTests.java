package be.uantwerpen.iw.ei.se.controllers;

import be.uantwerpen.iw.ei.se.ClickOMaticApplication;
import be.uantwerpen.iw.ei.se.configurations.SystemPropertyActiveProfileResolver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ui.ModelMap;

import javax.transaction.Transactional;

/**
 * Created by Thomas on 15/11/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ClickOMaticApplication.class)
@ActiveProfiles(profiles = {"dev"}, resolver = SystemPropertyActiveProfileResolver.class)
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
    @Transactional
    @WithUserDetails("thomas.huybrechts")
    public void testOpenFittsTestUserThomas()
    {
        fittsTestController.showFittsTest("001", new ModelMap());
    }
}
