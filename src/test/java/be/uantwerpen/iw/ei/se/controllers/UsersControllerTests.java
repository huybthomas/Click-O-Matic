package be.uantwerpen.iw.ei.se.controllers;

import be.uantwerpen.iw.ei.se.models.User;
import be.uantwerpen.iw.ei.se.repositories.UserRepository;
import be.uantwerpen.iw.ei.se.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by Quinten on 28/10/2015.
 */
public class UsersControllerTests {

    @Mock
    private UserService userService;
    private UserRepository userRepository;

    @InjectMocks
    private UsersController usersController;

    private MockMvc mockMvc;

    User principalUser;

    @Before
    public void setup()
    {
        // Create principal user and save
        principalUser = new User("Test", "User", "testusername", "test");
        userRepository.save(principalUser);

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();
    }

    @Test
    public void viewUsersTest() throws Exception
    {
        when(userService.getPrincipalUser()).thenReturn(principalUser);
        mockMvc.perform(get("/users")).andExpect(view().name("mainPortal/users"));
    }

    @Test
    public void viewEditSpecificUser() throws Exception
    {
        when(userService.findByUserName("userName")).thenReturn(principalUser);
        mockMvc.perform(get("/users/{userName}/")).andExpect(view().name("mainPortal/profile"));
    }

}
