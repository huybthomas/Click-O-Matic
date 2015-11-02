package be.uantwerpen.iw.ei.se.repositories;

import be.uantwerpen.iw.ei.se.models.Permission;
import be.uantwerpen.iw.ei.se.models.Role;
import be.uantwerpen.iw.ei.se.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 19/10/2015.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long>
{
    List<User> findByLastName(String lastName);

    User findByUserName(String userName);

    List<User> findAll();

}
