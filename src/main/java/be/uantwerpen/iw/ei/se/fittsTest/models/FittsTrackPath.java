package be.uantwerpen.iw.ei.se.fittsTest.models;

import be.uantwerpen.iw.ei.se.models.MyAbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quinten on 15/11/2015.
 */
@Entity
public class FittsTrackPath extends MyAbstractPersistable<Long>
{
    @OneToMany
    @JoinColumn(name="PATH_ID", referencedColumnName="ID")
    private List<FittsTrackEvent> path;

    public FittsTrackPath()
    {
        this.path = new ArrayList<FittsTrackEvent>();
    }

    public FittsTrackPath(List<FittsTrackEvent> path)
    {
        this.path = path;
    }

    public List<FittsTrackEvent> getPath()
    {
        return this.path;
    }

    public void setPath(List<FittsTrackEvent> path)
    {
        this.path = path;
    }
}
