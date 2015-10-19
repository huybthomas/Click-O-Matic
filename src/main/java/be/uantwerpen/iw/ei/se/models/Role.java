package be.uantwerpen.iw.ei.se.models;

import java.util.List;

/**
 * Created by Thomas on 19/10/2015.
 */
public class Role
{
    private String name;
    private List<Permission> permissions;

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
}
