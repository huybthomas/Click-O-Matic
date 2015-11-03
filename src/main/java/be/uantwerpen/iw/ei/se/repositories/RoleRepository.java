package be.uantwerpen.iw.ei.se.repositories;


import be.uantwerpen.iw.ei.se.models.Role;
import be.uantwerpen.iw.ei.se.models.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RoleRepository {
    private  List<Role> Roles = new ArrayList<>();
    public RoleRepository(){
        super();
        Role Researcher = new Role("Researcher");
        Role Patient = new Role("Patient");

        User testResearcher = new User("Researcher", "De Research", "researcher", "research");
        Roles.add(Researcher);
        testResearcher.setRoles(Roles);

        Roles = new ArrayList<>();
        User testPatient = new User("Tester", "De Test", "tester", "test");
        Roles.add(Patient);
        testPatient.setRoles(Roles);

    }

    public List<Role> findAll() {
        return new ArrayList<Role>(this.Roles);
    }

    public Role findByName( String role){
        for(int i = 0 ; i< Roles.size();i++) {
            if ((Roles.get(i).getName()) == role)
            {
                return Roles.get(i);
            }
            else
            {
                return null;
            }
        }
        return null;
    }
    public void add(final Role role){
        this.Roles.add(role);
    }
}
