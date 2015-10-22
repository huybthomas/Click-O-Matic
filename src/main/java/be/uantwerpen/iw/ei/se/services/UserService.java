package be.uantwerpen.iw.ei.se.services;

import be.uantwerpen.iw.ei.se.models.Permission;
import be.uantwerpen.iw.ei.se.models.Role;
import be.uantwerpen.iw.ei.se.models.User;
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
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    public Iterable<User> findAll()
    {
        return this.userRepository.findAll();
    }

    public void add(final User user)
    {
        this.userRepository.save(user);
    }

    public void delete(Long id)
    {
        this.userRepository.delete(id);
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
