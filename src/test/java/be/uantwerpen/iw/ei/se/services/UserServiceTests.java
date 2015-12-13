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

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dries on 28/10/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests
{
    @InjectMocks
    private UserService userService;

    @Mock
    private RoleService roleService;

    @Mock
    private UserRepository userRepository;

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

        User u1 = new User("Steve", "Rogers");
        u1.setUserName("Captain");
        u1.setPassword("Vibranium");
        u1.setId(01L);
        u1.setRoles(Arrays.asList(administrator));

        userList = new ArrayList<User>();
        userList.add(u1);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addNewUserTest()
    {
        User u2 = new User("Hank", "Pym");
        u2.setUserName("Antman");
        u2.setPassword("PymParticles");
        u2.setId(02L);

        when(userRepository.findAll()).thenReturn(userList);
        when(userRepository.save(u2)).thenReturn(u2);

        assertTrue(userService.add(u2));
    }

    @Test
    public void addExistingUserTest()
    {
        when(userRepository.findAll()).thenReturn(userList);
        when(userRepository.save(userList.get(0))).thenReturn(userList.get(0));

        assertTrue(userService.add(userList.get(0)));
    }

    @Test
    public void checkExistingNameTest()
    {
        when(userRepository.findAll()).thenReturn(userList);

        assertTrue(userService.usernameAlreadyExists("Captain"));
    }

    @Test
    public void checkNotExistingNameTest()
    {
        when(userRepository.findAll()).thenReturn(userList);

        assertTrue(!userService.usernameAlreadyExists("Spiderman"));
    }

    @Test
    public void addUserWithDuplicatedUsernameTest()
    {
        User u3 = new User("Tony", "Stark");
        u3.setUserName("Captain");
        u3.setPassword("ArkReactor");
        u3.setId(03L);

        when(userRepository.findAll()).thenReturn(userList);
        when(userRepository.save(u3)).thenReturn(u3);

        assertTrue(!userService.add(u3));
    }

    @Test
    public void usernameAlreadyExistsTest()
    {
        when(userRepository.findAll()).thenReturn(userList);

        String existingUsername = new String("Captain");

        assertTrue(userService.usernameAlreadyExists(existingUsername));
    }

    @Test
    public void usernameNotExistsTest()
    {
        when(userRepository.findAll()).thenReturn(userList);

        String existingUsername = new String("Ironman");

        assertTrue(!userService.usernameAlreadyExists(existingUsername));
    }

    @Test
    public void saveNotExistingUserTest()
    {
        User u4 = new User("Tony", "Stark");
        u4.setUserName("IronMan");
        u4.setPassword("ArkReactor");
        u4.setId(04L);

        when(userRepository.save(u4)).thenReturn(u4);
        when(userRepository.findAll()).thenReturn(userList);

        assertTrue(!userService.save(u4));
    }

    @Test
    public void saveExistingUserTest()
    {
        when(userRepository.save(userList.get(0))).thenReturn(userList.get(0));
        when(userRepository.findAll()).thenReturn(userList);

        assertTrue(userService.save(userList.get(0)));
    }
}
