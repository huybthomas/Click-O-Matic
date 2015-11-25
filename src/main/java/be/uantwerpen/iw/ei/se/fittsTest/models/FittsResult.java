package be.uantwerpen.iw.ei.se.fittsTest.models;

import be.uantwerpen.iw.ei.se.models.MyAbstractPersistable;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dries on 3/11/2015.
 */
@Entity
public class FittsResult extends MyAbstractPersistable<Long>
{
    private String resultID;
    private String testID;
    private Date resultDate;

    @OneToMany
    @JoinColumn(name="RESULT_ID", referencedColumnName="ID")
    private List<FittsTrackPath> trackPaths;

    public FittsResult()
    {
        this.resultID = "";
        this.testID = "";
        this.resultDate = new Date();
        this.trackPaths = new ArrayList<FittsTrackPath>();
    }

    public FittsResult(String resultID, String testID, Date resultDate, List<FittsTrackPath> trackPaths)
    {
        this.resultID = resultID;
        this.testID = testID;
        this.resultDate = resultDate;
        this.trackPaths = trackPaths;
    }

    public void setResultID(String resultID)
    {
        this.resultID = resultID;
    }

    public String getResultID()
    {
        return this.resultID;
    }

    public void setTestID(String testID)
    {
        this.testID = testID;
    }

    public String getTestID()
    {
        return this.testID;
    }

    public void setResultDate(Date resultDate)
    {
        this.resultDate = resultDate;
    }

    public Date getResultDate()
    {
        return this.resultDate;
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
