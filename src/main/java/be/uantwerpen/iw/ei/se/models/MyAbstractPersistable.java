package be.uantwerpen.iw.ei.se.models;

import org.springframework.data.jpa.domain.AbstractPersistable;

import java.io.Serializable;

/**
 * Created by Thomas on 30/10/2015.
 */
public class MyAbstractPersistable<T extends Serializable> extends AbstractPersistable<T>
{
    //Patch to access the private setId method of AbstractPersistable
    @Override
    public void setId(T id)
    {
        super.setId(id);
    }
}
