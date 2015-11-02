package be.uantwerpen.iw.ei.se.services;

import be.uantwerpen.iw.ei.se.models.Role;
import be.uantwerpen.iw.ei.se.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Quinten on 2/11/2015.
 */
@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Iterable<Role> findAll()
    {
        return this.roleRepository.findAll();
    }

}
