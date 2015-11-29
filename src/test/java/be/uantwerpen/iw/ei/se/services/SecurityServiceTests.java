package be.uantwerpen.iw.ei.se.services;

import be.uantwerpen.iw.ei.se.models.Permission;
import be.uantwerpen.iw.ei.se.models.Role;
import be.uantwerpen.iw.ei.se.models.User;
import be.uantwerpen.iw.ei.se.repositories.PermissionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by Thomas on 20/10/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class SecurityServiceTests
{
    @InjectMocks
    private SecurityService securityService;

    @Mock
    private UserService userService;

    @Mock
    private PermissionRepository permissionRepository;

    private List<User> userList;
    private List<Permission> permissionListTester;
    private List<Permission> permissionListAdmin;

    @Before
    public void init()
    {
        Permission p1 = new Permission("logon");
        Permission p2 = new Permission("secret-message");

        Role administrator = new Role("Administrator");
        Role tester = new Role("Tester");
        permissionListTester = new ArrayList<Permission>();
        permissionListTester.add(p1);
        tester.setPermissions(permissionListTester);
        permissionListAdmin = new ArrayList<Permission>();
        permissionListAdmin.add(p1);
        permissionListAdmin.add(p2);
        administrator.setPermissions(permissionListAdmin);

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
        when(userService.findByUserName("bla")).thenReturn(null);

        securityService.loadUserByUsername("bla");
    }

    @Test
    public void loadedAuthoritiesTest()
    {
        when(userService.findByUserName("username")).thenReturn(userList.get(1));                       //Get User u2 (administrator)
        when(permissionRepository.findAllForUser(userList.get(1))).thenReturn(permissionListAdmin);     //Get permission of User u2 (administrator)

        UserDetails testUser = securityService.loadUserByUsername("username");
        assertTrue(!testUser.getAuthorities().isEmpty());
    }
}
