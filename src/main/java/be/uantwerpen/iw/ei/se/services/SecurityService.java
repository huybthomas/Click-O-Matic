package be.uantwerpen.iw.ei.se.services;

import be.uantwerpen.iw.ei.se.models.Permission;
import be.uantwerpen.iw.ei.se.models.Role;
import be.uantwerpen.iw.ei.se.models.User;
import be.uantwerpen.iw.ei.se.repositories.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by Thomas on 22/10/2015.
 */
@Service
public class SecurityService implements UserDetailsService
{
    @Autowired
    UserService userService;

    @Autowired
    PermissionRepository permissionRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException
    {
        UserDetails userDetails;

        User user = userService.findByUserName(userName);
        if(user != null)
        {
            Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

            authorities.addAll(StreamSupport.stream(permissionRepository.findAllForUser(user).spliterator(), false).map(permission -> new SimpleGrantedAuthority(permission.getName())).collect(Collectors.toList()));

            userDetails = new org.springframework.security.core.userdetails.User(userName, user.getPassword(), true, true, true, true, authorities);
        }
        else
        {
            throw new UsernameNotFoundException("No user with username '" + userName + "' found!");
        }

        return userDetails;
    }
}
