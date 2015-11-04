package be.uantwerpen.iw.ei.se.repositories;


import be.uantwerpen.iw.ei.se.models.Permission;
import be.uantwerpen.iw.ei.se.models.Role;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
// De bedoeling van een Permission Repository is om de persistantie te testen, maw of alle data goed toekomt, gebruikt wordt en opgehaald word
@Repository
public class PermissionRepository {
    private final List<Permission> permissions_patient      = new ArrayList();
    private final List<Permission> permissions_researcher   = new ArrayList();
    private final List<Permission> permissions_all          = new ArrayList();
    public PermissionRepository(){
        Permission p1 = new Permission("Logon");
        Permission p2 = new Permission("Watch User Data");
        Permission p3 = new Permission("Print Data");
        Permission p4 = new Permission("Add New Accounts");
        Permission p5 = new Permission("Make a Test");


        permissions_researcher.add(p1);
        permissions_researcher.add(p2);
        permissions_researcher.add(p3);
        permissions_researcher.add(p4);

        permissions_patient.add(p1);
        permissions_patient.add(p5);

        permissions_all.add(p1);
        permissions_all.add(p2);
        permissions_all.add(p3);
        permissions_all.add(p4);
        permissions_all.add(p5);


        Role Patient    = new Role("Patient");
        Role Researcher = new Role("Researcher");

        Patient.setPermissions(permissions_patient);
        Researcher.setPermissions(permissions_researcher);
    }
    public List<Permission> findAll(){
        return new ArrayList<Permission>(this.permissions_all);
    }
    public Permission findByName(String permission){
        for(int i = 0 ;i< this.permissions_all.size();i++)
        {
            if(this.permissions_all.get(i).getName()==permission)
            {
                return permissions_all.get(i);
            }
            else
            {
               return null;
            }
        }
        return null;
    }
    public void add(final Permission permission){
        this.permissions_all.add(permission);
    }
}

