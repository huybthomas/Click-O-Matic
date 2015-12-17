package be.uantwerpen.iw.ei.se.fittsTest.models;

import be.uantwerpen.iw.ei.se.models.MyAbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 26/11/2015.
 */
@Entity
public class FittsStageResult extends MyAbstractPersistable<Long>
{
    private int stageOrderIndex;

    @OneToMany
    @JoinColumn(name="PATHRESULT_ID", referencedColumnName="ID")
    private List<FittsTrackPath> trackPaths;

    public FittsStageResult()
    {
        this.stageOrderIndex = 0;
        this.trackPaths = new ArrayList<FittsTrackPath>();
    }

    public FittsStageResult(int stageOrderIndex, List<FittsTrackPath> trackPaths)
    {
        this.stageOrderIndex = stageOrderIndex;
        this.trackPaths = trackPaths;
    }

    public void setStageOrderIndexIndex(int stageOrderIndex)
    {
        this.stageOrderIndex = stageOrderIndex;
    }

    public int getStageOrderIndex()
    {
        return this.stageOrderIndex;
    }

    public void setTrackPaths(List<FittsTrackPath> trackPaths)
    {
        this.trackPaths = trackPaths;
    }

    public List<FittsTrackPath> getFittsTrackPaths()
    {
        return this.trackPaths;
    }

}
