package be.uantwerpen.iw.ei.se.services;

import be.uantwerpen.iw.ei.se.models.Permission;
import be.uantwerpen.iw.ei.se.models.Role;
import be.uantwerpen.iw.ei.se.models.User;
import be.uantwerpen.iw.ei.se.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by Thomas on 20/10/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests
{
    @InjectMocks
    UserDetailsService securityService;

    @Mock
    private UserRepository userRepository;

    List<User> userList;

    @Before
    public void init()
    {
        Permission p1 = new Permission("logon");
        Permission p2 = new Permission("secret-message");
        Role administrator = new Role("Administrator");
        Role tester = new Role("Tester");
        List<Permission> permissions = new ArrayList<Permission>();
        permissions.add(p1);
        tester.setPermissions(permissions);
        permissions = new ArrayList<Permission>();
        permissions.add(p1);
        permissions.add(p2);
        administrator.setPermissions(permissions);

        User u1 = new User("U", "1");
        u1.setUserName("username");
        u1.setRoles(Arrays.asList(administrator));
        User u2 = new User("U", "2");
        u2.setUserName("username");
        u2.setRoles(Arrays.asList(tester));
        userList = new ArrayList<User>();
        userList.add(u1);
        userList.add(u2);
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void nonExistingUsernameTest()
    {
        when(userRepository.findAll()).thenReturn(userList);
        securityService.loadUserByUsername("bla");
    }

    @Test
    public void loadedAuthoritiesTest()
    {
        when(userRepository.findAll()).thenReturn(userList);
        UserDetails testUser = securityService.loadUserByUsername("username");
        assertTrue(!testUser.getAuthorities().isEmpty());
    }
}
