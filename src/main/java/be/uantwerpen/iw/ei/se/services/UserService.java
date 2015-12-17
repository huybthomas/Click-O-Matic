package be.uantwerpen.iw.ei.se.services;

import be.uantwerpen.iw.ei.se.fittsTest.models.FittsResult;
import be.uantwerpen.iw.ei.se.models.User;
import be.uantwerpen.iw.ei.se.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
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
    private FittsService fittsService;

    @Autowired
    private UserDetailsService securityService;

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

        if(this.userRepository.save(user) != null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean delete(String userName)
    {
        User u = findByUserName(userName);

        if(u != null)
        {
            List<String> resultIDs = new ArrayList<String>();

            for(FittsResult result : u.getResults())
            {
                resultIDs.add(result.getResultID());
            }

            for(String resultID : resultIDs)
            {
                this.fittsService.deleteResult(resultID);
            }

            this.userRepository.delete(u.getId());

            return true;
        }

        return false;
    }

    public boolean save(User user)
    {
        for(User u : findAll())
        {
            if(u.getId().equals(user.getId()))
            {
                if(!this.isDuplicatedUsername(user))
                {
                    u.setFirstName(user.getFirstName());
                    u.setLastName(user.getLastName());
                    u.setUserName(user.getUserName());
                    u.setPassword(user.getPassword());
                    u.setRoles(user.getRoles());
                    u.setTests(user.getTests());
                    u.setResults(user.getResults());

                    if(userRepository.save(u) != null)
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public User findByUserName(String userName)
    {
        return userRepository.findByUserName(userName);
    }

    //Get logged in user
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

    public User findUserWithResult(FittsResult result)
    {
        List<User> users = userRepository.findAll();
        User resultUser = null;

        Iterator<User> it = users.iterator();
        while(it.hasNext() && resultUser == null)
        {
            User user = it.next();

            if(user.getResults().contains(result))
            {
                resultUser = user;
            }
        }

        return resultUser;
    }

    //Set logged in user
    public void setPrincipalUser(User user)
    {
        UserDetails newUserDetails = securityService.loadUserByUsername(user.getUserName());
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUserDetails, newUserDetails.getPassword(), newUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
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
