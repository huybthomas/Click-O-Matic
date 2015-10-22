package be.uantwerpen.iw.ei.se.repositories;

import be.uantwerpen.iw.ei.se.models.Permission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Thomas on 22/10/2015.
 */
@Repository
public interface PermissionRepository extends CrudRepository<Permission, Long>
{
    Iterable<Permission> findByName(String permission);
}
