package be.uantwerpen.iw.ei.se.data;

import be.uantwerpen.iw.ei.se.models.Permission;
import be.uantwerpen.iw.ei.se.models.Role;
import be.uantwerpen.iw.ei.se.models.User;
import be.uantwerpen.iw.ei.se.repositories.PermissionRepository;
import be.uantwerpen.iw.ei.se.repositories.RoleRepository;
import be.uantwerpen.iw.ei.se.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Thomas on 22/10/2015.
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
        List<Permission> allPermissions = new ArrayList<Permission>();
        List<Role> roles = new ArrayList<Role>();

        allPermissions.add(new Permission("logon"));
        allPermissions.add(new Permission("secret-message"));
        allPermissions.add(new Permission("createUsers"));
        allPermissions.add(new Permission("editUsers"));
        allPermissions.add(new Permission("viewUsers"));

        Iterator<Permission> permissionIterator = allPermissions.iterator();
        while(permissionIterator.hasNext())
        {
            permissionRepository.save(permissionIterator.next());
        }

        Role administrator = new Role("Administrator");
        Role tester = new Role("Tester");

        administrator.setPermissions(allPermissions);

        List<Permission> permissions = new ArrayList<Permission>();
        permissions.add(allPermissions.get(0));     //Logon
        tester.setPermissions(permissions);

        roleRepository.save(administrator);
        roleRepository.save(tester);

        User u1 = new User("Thomas", "Huybrechts", "thomas.huybrechts", "test");
        roles = new ArrayList<>();
        roles.add(administrator);
        u1.setRoles(roles);

        User u2 = new User("Dries", "Blontrock", "dries.blontrock", "test");
        roles = new ArrayList<>();
        roles.add(administrator);
        u2.setRoles(roles);

        User u3 = new User("Quinten", "Van Hasselt", "quinten.vanhasselt", "test");
        roles = new ArrayList<>();
        roles.add(administrator);
        u3.setRoles(roles);

        User u4 = new User("Timothy", "Verstraete", "timothy.verstraete", "test");
        roles = new ArrayList<>();
        roles.add(administrator);
        u3.setRoles(roles);

        User u5 = new User("Tester", "De Test", "tester", "test");
        roles = new ArrayList<>();
        roles.add(tester);
        u4.setRoles(roles);

        userRepository.save(u1);
        userRepository.save(u2);
        userRepository.save(u3);
        userRepository.save(u4);
    }
}
