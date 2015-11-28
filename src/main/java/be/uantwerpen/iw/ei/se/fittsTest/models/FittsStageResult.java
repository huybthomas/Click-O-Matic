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
    private String stageResultID;

    @OneToMany
    @JoinColumn(name="PATHRESULT_ID", referencedColumnName="ID")
    private List<FittsTrackPath> trackPaths;

    public FittsStageResult()
    {
        this.stageResultID = "";
        this.trackPaths = new ArrayList<FittsTrackPath>();
    }

    public FittsStageResult(String stageResultID, List<FittsTrackPath> trackPaths)
    {
        this.stageResultID = stageResultID;
        this.trackPaths = trackPaths;
    }

    public void setStageResultID(String stageResultID)
    {
        this.stageResultID = stageResultID;
    }

    public String getStageResultID()
    {
        return this.stageResultID;
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
