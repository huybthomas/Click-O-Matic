package be.uantwerpen.iw.ei.se.services;

import be.uantwerpen.iw.ei.se.models.Permission;
import be.uantwerpen.iw.ei.se.models.Role;
import be.uantwerpen.iw.ei.se.models.User;
import be.uantwerpen.iw.ei.se.repositories.PermissionRepository;
import be.uantwerpen.iw.ei.se.repositories.RoleRepository;
import be.uantwerpen.iw.ei.se.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dries on 28/10/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    PermissionRepository permissionRepository;

    List<User> userList;
    List<Permission> permissionListTester;
    List<Permission> permissionListAdmin;

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
        u1.setRoles(Arrays.asList(administrator));
        User u2 = new User("Hank", "Pam");
        u2.setUserName("Antman");
        u2.setRoles(Arrays.asList(tester));
        userList = new ArrayList<User>();
        userList.add(u1);
        userList.add(u2);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addUser()
    {
        userService.add(userList.get(0));
    }

}
