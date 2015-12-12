package be.uantwerpen.iw.ei.se.data;

import be.uantwerpen.iw.ei.se.models.Permission;
import be.uantwerpen.iw.ei.se.models.Role;
import be.uantwerpen.iw.ei.se.models.User;
import be.uantwerpen.iw.ei.se.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Thomas on 12/12/2015.
 */
@Service
@Profile("default")
public class DatabaseLoader
{
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public DatabaseLoader(PermissionRepository permissionRepository, RoleRepository roleRepository, UserRepository userRepository)
    {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    private void initDatabase()
    {
        //Check if tables are initialized or empty
        if(permissionRepository.findAll().iterator().hasNext())
        {
            //Tables are initialized, no need to refill database
            return;
        }

        //Initialise user database
        initUserDatabase();
    }

    private void initUserDatabase()
    {
        List<Role> roles;
        List<Permission> allPermissions = new ArrayList<Permission>();

        // usermanagement algemene permission van maken
        allPermissions.add(new Permission("logon"));
        allPermissions.add(new Permission("secret-message"));
        allPermissions.add(new Permission("user-management"));
        allPermissions.add(new Permission("test-management"));

        Iterator<Permission> permissionIterator = allPermissions.iterator();
        while(permissionIterator.hasNext())
        {
            permissionRepository.save(permissionIterator.next());
        }

        Role administrator = new Role("Administrator");
        Role researcher = new Role("Researcher");
        Role tester = new Role("Tester");

        administrator.setPermissions(allPermissions);

        List<Permission> permissions = new ArrayList<Permission>();
        permissions.add(allPermissions.get(0));     // logon
        tester.setPermissions(permissions);

        permissions = new ArrayList<Permission>();
        permissions.add(allPermissions.get(0));     // logon
        permissions.add(allPermissions.get(3));     // test-management
        researcher.setPermissions(permissions);

        roleRepository.save(administrator);
        roleRepository.save(researcher);
        roleRepository.save(tester);

        User u1 = new User("Admin", "Click-O-Matic", "admin", "default");
        roles = new ArrayList<Role>();
        roles.add(administrator);
        u1.setRoles(roles);

        userRepository.save(u1);
    }
}
