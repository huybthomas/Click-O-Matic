package be.uantwerpen.iw.ei.se.services;

import be.uantwerpen.iw.ei.se.models.Permission;
import be.uantwerpen.iw.ei.se.models.Role;
import be.uantwerpen.iw.ei.se.models.User;
import be.uantwerpen.iw.ei.se.repositories.PermissionRepository;
import be.uantwerpen.iw.ei.se.repositories.RoleRepository;
import be.uantwerpen.iw.ei.se.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Thomas on 19/10/2015.
 */
@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    public Iterable<User> findAll()
    {
        return this.userRepository.findAll();
    }

    public boolean add(final User user)
    {
        if(isDuplicatedUsername(user))
        {
            return false;
        }

        List<Role> roles = user.getRoles();

        for(Role role : roles)
        {
            for(Permission permission : role.getPermissions())
            {
                //Save the permissions of the role to the database
                this.permissionRepository.save(permission);
            }

            //Save the role of the user to the database
            this.roleRepository.save(role);
        }

        this.userRepository.save(user);

        return true;
    }

    public void delete(String userName)
    {
        User u = findByUserName(userName);
        this.userRepository.delete(u.getId());
    }

    public boolean save(User user)
    {
        for(User u : findAll())
        {
            if(u.getId() == user.getId())
            {
                u.setFirstName(user.getFirstName());
                u.setLastName(user.getLastName());
                u.setUserName(user.getUserName());
                u.setPassword(user.getPassword());
                u.setRoles(user.getRoles());
                userRepository.save(u);

                return true;
            }
        }

        return false;
    }

    public User findByUserName(String userName)
    {
        return userRepository.findByUserName(userName);
    }

    //Get logged in users
    public User getPrincipalUser()
    {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        for(User u : findAll())
        {
            if(u.getUserName().compareTo(user.getUsername()) == 0)
            {
                return u;
            }
        }

        return null;
    }

    public boolean usernameAlreadyExists(final String username)
    {
        List<User> users = userRepository.findAll();

        for(User userIt : users)
        {
            if(userIt.getUserName().equals(username))
            {
                return true;
            }
        }

        return false;
    }

    private boolean isDuplicatedUsername(final User user)
    {
        List<User> users = userRepository.findAll();

        for(User userIt : users)
        {
            if(userIt.getUserName().equals(user.getUserName()) && !userIt.getId().equals(user.getId()))
            {
                //Two different user objects with the same username
                return true;
            }
        }

        return false;
    }
}
