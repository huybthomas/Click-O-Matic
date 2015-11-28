package be.uantwerpen.iw.ei.se.controllers;

import be.uantwerpen.iw.ei.se.models.Role;
import be.uantwerpen.iw.ei.se.models.User;
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
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by Quinten on 28/10/2015.
 */
public class UsersControllerTests
{
    @Mock
    private UserService userService;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private UsersController usersController;

    private MockMvc mockMvc;

    private User principalUser;
    private User testUser;
    private Iterable<User> users;
    private Iterable<Role> roles;

    @Before
    public void setup()
    {
        List<User> userList = new ArrayList<User>();
        List<Role> rolesList = new ArrayList<Role>();

        // Create principal user and test user
        principalUser = new User("Test", "User", "testusername", "test");
        testUser = new User("User", "Testing", "userName", "test");

        userList.add(principalUser);
        userList.add(testUser);

        users = (Iterable<User>) userList;
        roles = (Iterable<Role>) rolesList;

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
        when(userService.findAll()).thenReturn(users);
        when(roleService.findAll()).thenReturn(roles);

        mockMvc.perform(get("/users/userName/")).andExpect(view().name("mainPortal/user-profile"));
    }
}
