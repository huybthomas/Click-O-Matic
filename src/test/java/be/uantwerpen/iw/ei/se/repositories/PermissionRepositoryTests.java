package be.uantwerpen.iw.ei.se.repositories;

import be.uantwerpen.iw.ei.se.ClickOMaticApplication;
import be.uantwerpen.iw.ei.se.configurations.SystemPropertyActiveProfileResolver;
import be.uantwerpen.iw.ei.se.models.Permission;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

/**
 * Created by Thomas on 02/11/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ClickOMaticApplication.class)
@ActiveProfiles(profiles = {"dev"}, resolver = SystemPropertyActiveProfileResolver.class)
@WebAppConfiguration
public class PermissionRepositoryTests
{
    @Autowired
    private PermissionRepository permissionRepository;

    private int origPermissionRepositorySize;

    @Before
    public void setup()
    {
        origPermissionRepositorySize = (int)permissionRepository.count();
    }

    @Test
    public void testSavePermission()
    {
        //Setup permission
        Permission permission = new Permission();
        permission.setName("TestPermission");

        //Save permission, verify has ID value after save
        assertNull(permission.getId());       //Null before save
        permissionRepository.save(permission);
        assertNotNull(permission.getId());    //Not null after save

        //Fetch from database
        Permission fetchedPermission = permissionRepository.findOne(permission.getId());

        //Should not be null
        assertNotNull(fetchedPermission);

        //Should equals
        assertEquals(permission.getId(), fetchedPermission.getId());
        assertEquals(permission.getName(), fetchedPermission.getName());

        //Update description and save
        fetchedPermission.setName("NewPermissionName");
        permissionRepository.save(fetchedPermission);

        //Get from database, should be updated
        Permission fetchedUpdatedPermission = permissionRepository.findOne(fetchedPermission.getId());
        assertEquals(fetchedPermission.getName(), fetchedUpdatedPermission.getName());

        //Verify count of permission in database
        long permissionCount = permissionRepository.count();
        assertEquals(permissionCount, origPermissionRepositorySize + 1);        //One permission has been added to the database

        //Get all permission, list should only have one more then initial value
        Iterable<Permission> permissions = permissionRepository.findAll();

        int count = 0;

        for(Permission p : permissions)
        {
            count++;
        }

        //There are originally 'origPermissionRepositorySize' permissions declared in the database (+1 has been added in this test)
        assertEquals(count, origPermissionRepositorySize + 1);
    }

    @Test
    public void testDeletePermission()
    {
        //Setup permission
        Permission permission = new Permission();
        permission.setName("TestPermission");

        //Save permission, verify if it has ID value after save
        assertNull(permission.getId());       //Null before save
        permissionRepository.save(permission);
        assertNotNull(permission.getId());    //Not null after save

        //Fetch from database
        Permission fetchedPermission = permissionRepository.findOne(permission.getId());

        //Should not be null
        assertNotNull(fetchedPermission);

        //Delete permission from database
        permissionRepository.delete(fetchedPermission.getId());

        //Fetch from database (should not exist anymore)
        fetchedPermission = permissionRepository.findOne(permission.getId());

        //Should be null
        assertNull(fetchedPermission);
    }
}
