package be.uantwerpen.iw.ei.se.services;

import be.uantwerpen.iw.ei.se.ClickOMaticApplication;
import be.uantwerpen.iw.ei.se.models.Permission;
import be.uantwerpen.iw.ei.se.models.Role;
import be.uantwerpen.iw.ei.se.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dries on 28/10/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ClickOMaticApplication.class)
@WebAppConfiguration
public class UserServiceTest
{
    @Autowired
    UserService userService;

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
        u1.setPassword("Vibranium");
        u1.setRoles(Arrays.asList(administrator));
        User u2 = new User("Hank", "Pym");
        u2.setUserName("Antman");
        u2.setPassword("PymParticles");
        u2.setRoles(Arrays.asList(tester));
        userList = new ArrayList<User>();
        userList.add(u1);
        userList.add(u2);

        userService.add(userList.get(0));
    }

    //Adds user with help of the userService, checks if it's really in there.
    @Test
    public void addUser()
    {
        assertTrue(userService.add(userList.get(1)));
        assertNotNull(userService.findByUserName("Antman"));
    }

    @Test
    public void checkExistingName()
    {
        assertTrue(userService.usernameAlreadyExists("Captain"));
    }

    @Test
    public void checkNotExistingName()
    {
        assertTrue(!userService.usernameAlreadyExists("Spiderman"));
    }

    @Test
    public void checkDuplicate()
    {
        User u3 = new User("Tony", "Stark");
        u3.setUserName("Captain");
        u3.setPassword("ArkReactor");
        assertTrue(!userService.add(u3));
    }

    @Test
    public void checkNotDuplicate()
    {
        User u3 = new User("Tony", "Stark");
        u3.setUserName("IronMan");
        u3.setPassword("ArkReactor");
        assertTrue(userService.add(u3));
    }
}
