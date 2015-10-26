package be.uantwerpen.iw.ei.se.repositories;

import be.uantwerpen.iw.ei.se.models.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Thomas on 22/10/2015.
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Long>
{
    Iterable<Role> findByName(String role);
}
