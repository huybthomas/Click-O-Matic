package be.uantwerpen.iw.ei.se.services;

import be.uantwerpen.iw.ei.se.models.Permission;
import be.uantwerpen.iw.ei.se.models.Role;
import be.uantwerpen.iw.ei.se.repositories.PermissionRepository;
import be.uantwerpen.iw.ei.se.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Quinten on 2/11/2015.
 */
@Service
public class RoleService
{
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    public void save(List<Role> roles)
    {
        for(Role role : roles)
        {
            //Save the permissions of the role to the database
            this.permissionRepository.save(role.getPermissions());
        }

        //Save the roles to the database
        this.roleRepository.save(roles);
    }

    public Iterable<Role> findAll()
    {
        return this.roleRepository.findAll();
    }
}
