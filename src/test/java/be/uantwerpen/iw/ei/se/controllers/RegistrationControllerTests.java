package be.uantwerpen.iw.ei.se.controllers;

import be.uantwerpen.iw.ei.se.models.User;
import be.uantwerpen.iw.ei.se.models.Role;
import be.uantwerpen.iw.ei.se.services.RoleService;
import be.uantwerpen.iw.ei.se.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;

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

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RegistrationController registrationController;

    private MockMvc mockMvc;

    User principalUser;
    Iterable<Role> roleList;

    @Before
    public void setup()
    {
        principalUser = new User("Test", "User");

        roleList = new ArrayList<Role>(Arrays.asList(new Role("Tester")));

        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
    }

    @Test
    public void viewCreateUserTest() throws Exception
    {
        when(userService.getPrincipalUser()).thenReturn(principalUser);
        when(roleService.findAll()).thenReturn(roleList);
        mockMvc.perform(get("/registration")).andExpect(view().name("mainPortal/registration"));
    }
}
