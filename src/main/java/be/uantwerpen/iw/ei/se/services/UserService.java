package be.uantwerpen.iw.ei.se.services;

import be.uantwerpen.iw.ei.se.models.Permission;
import be.uantwerpen.iw.ei.se.models.Role;
import be.uantwerpen.iw.ei.se.models.User;
import be.uantwerpen.iw.ei.se.repositories.PermissionRepository;
import be.uantwerpen.iw.ei.se.repositories.RoleRepository;
import be.uantwerpen.iw.ei.se.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Thomas on 19/10/2015.
 */
@Service
public class UserService implements UserDetailsService
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

    public void add(final User user)
    {
        List <Role> roles = user.getRoles();

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
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException
    {
        UserDetails userDetails = null;

        for(User user : findAll())
        {
            if(userName.equals(user.getUserName()))
            {
                Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                for(Role role : user.getRoles())
                {
                    for(Permission permission : role.getPermissions())
                    {
                        authorities.add(new SimpleGrantedAuthority(permission.getName()));
                    }
                }

                userDetails = new org.springframework.security.core.userdetails.User(userName, user.getPassword(), true, true, true, true, authorities);
            }
        }

        if(userDetails == null)
        {
            throw new UsernameNotFoundException("No user with username '" + userName + "' found!");
        }

        return userDetails;
    }

    public void delete(Long id)
    {
        this.userRepository.delete(id);
    }
    
    public User loadSimpleUserByUsername(String userName) throws UsernameNotFoundException
    {
        for(User user : findAll())
        {
            if(user.getUserName().equals(userName))
            {
                return user;
            }
        }
        throw new UsernameNotFoundException("No user with username '" + userName + "' found!");
    }

    public User findByUserName(String userName)
    {
        return userRepository.findByUserName(userName);
    }

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
}
