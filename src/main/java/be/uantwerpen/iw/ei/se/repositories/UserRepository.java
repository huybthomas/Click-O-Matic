package be.uantwerpen.iw.ei.se.repositories;

import be.uantwerpen.iw.ei.se.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Thomas on 19/10/2015.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long>
{
    List<User> findByLastName(String lastName);
    User findByUserName(String userName);
}
/*
public class UserRepository
{
    private final List<User> users = new ArrayList<User>();

    public UserRepository()
    {
        super();

        Permission p1 = new Permission("logon");
        Permission p2 = new Permission("secret-message");
        Permission p3 = new Permission("createUsers");
        Permission p4 = new Permission("editUsers");
        Permission p5 = new Permission("viewUsers");
        Role administrator = new Role("Administrator");
        Role tester = new Role("Tester");

        List<Permission> permissions = new ArrayList<Permission>();
        permissions.add(p1);
        tester.setPermissions(permissions);

        permissions = new ArrayList<Permission>();
        permissions.add(p1);
        permissions.add(p2);
        permissions.add(p3);
        permissions.add(p4);
        permissions.add(p5);
        administrator.setPermissions(permissions);

        User u1 = new User("Thomas", "Huybrechts", "thomas.huybrechts", "test");
        List<Role> roles = new ArrayList<>();
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

        User u4 = new User("Tester", "De Test", "tester", "test");
        roles = new ArrayList<>();
        roles.add(tester);
        u4.setRoles(roles);

        users.add(u1);
        users.add(u2);
        users.add(u3);
        users.add(u4);
    }

    public List<User> findAll()
    {
        return new ArrayList<User>(this.users);
    }

    public void add(final User user)
    {
        this.users.add(user);
    }
}
*/