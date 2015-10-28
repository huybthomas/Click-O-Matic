package be.uantwerpen.iw.ei.se.controllers;

import be.uantwerpen.iw.ei.se.models.User;
import be.uantwerpen.iw.ei.se.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by Quinten on 28/10/2015.
 */
public class RegistrationControllerTests
{
    @Mock
    private UserService userService;

    @InjectMocks
    private RegistrationController registrationController;

    private MockMvc mockMvc;

    User principalUser;

    @Before
    public void setup()
    {
        principalUser = new User("Test", "User");
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
    }

    @Test
    public void viewCreateUserTest() throws Exception
    {
        when(userService.getPrincipalUser()).thenReturn(principalUser);
        mockMvc.perform(get("/registration")).andExpect(view().name("mainPortal/registration"));
    }

    @Test
    public void viewSubmitNewUserTest() throws Exception
    {
        when(userService.getPrincipalUser()).thenReturn(principalUser);
        mockMvc.perform(get("/registration")).andExpect(view().name("mainPortal/registration"));
    }
}
