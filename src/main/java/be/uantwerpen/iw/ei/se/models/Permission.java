package be.uantwerpen.iw.ei.se.models;

/**
 * Created by Thomas on 19/10/2015.
 */
public class Permission
{
    private String name;

    public Permission(String name)
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
}