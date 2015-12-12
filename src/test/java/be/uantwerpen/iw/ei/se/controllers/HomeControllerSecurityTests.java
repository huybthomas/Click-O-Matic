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

/**
 * Created by Thomas on 20/10/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ClickOMaticApplication.class)
@ActiveProfiles(profiles = {"dev"}, resolver = SystemPropertyActiveProfileResolver.class)
@WebAppConfiguration
public class HomeControllerSecurityTests
{
    @Autowired
    private HomeController homeController;

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void testOpenHomepageWithoutLoginCredentials()
    {
        homeController.showHomepage(new ModelMap());
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles={"ADMIN"})
    public void testOpenHomepageAdmin()
    {
        homeController.showHomepage(new ModelMap());
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(username = "ravan")
    public void testOpenHomepageUsernameRavan()
    {
        homeController.showHomepage(new ModelMap());
    }

    @Test
    @WithUserDetails("thomas.huybrechts")
    public void testOpenHomepageUserThomas()
    {
        homeController.showHomepage(new ModelMap());
    }
}