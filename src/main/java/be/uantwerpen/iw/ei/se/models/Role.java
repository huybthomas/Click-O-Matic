package be.uantwerpen.iw.ei.se.models;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Thomas on 19/10/2015.
 */
@Entity
public class Role extends MyAbstractPersistable<Long>
{
    private String name;

    @ManyToMany
    @JoinTable(
            name="ROLE_PERMISSION",
            joinColumns={
                    @JoinColumn(name="ROLE_ID", referencedColumnName="ID")},
            inverseJoinColumns={
                    @JoinColumn(name="PERMISSION_ID", referencedColumnName="ID")})
    private List<Permission> permissions;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    public Role()
    {
        this.name = "";
        this.permissions = null;
    }

    public Role(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<Permission> getPermissions()
    {
        return this.permissions;
    }

    public void setPermissions(List<Permission> permissions)
    {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object object)
    {
        if(this == object)
        {
            return true;
        }

        if(object == null || this.getClass() != object.getClass())
        {
            return false;
        }

        Role role  = (Role) object;

        return this.name.equals(role.getName());
    }

    //Remove first all existing links between users and this role in the database
    @PreRemove
    private void removeRolesFromUsers()
    {
        for(User user : users)
        {
            user.getRoles().remove(this);
        }
    }
}
