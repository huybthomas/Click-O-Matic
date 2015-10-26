package be.uantwerpen.iw.ei.se.models;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;

/**
 * Created by Thomas on 19/10/2015.
 */
@Entity
public class Permission extends AbstractPersistable<Long>
{
    private String name;

    public Permission()
    {
        this.name = "";
    }

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
