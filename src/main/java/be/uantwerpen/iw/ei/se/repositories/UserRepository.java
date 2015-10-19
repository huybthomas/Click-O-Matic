package be.uantwerpen.iw.ei.se.repositories;

import be.uantwerpen.iw.ei.se.models.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 19/10/2015.
 */
@Repository
public class UserRepository
{
    private final List<User> users = new ArrayList<User>();

    public UserRepository()
    {
        super();
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
