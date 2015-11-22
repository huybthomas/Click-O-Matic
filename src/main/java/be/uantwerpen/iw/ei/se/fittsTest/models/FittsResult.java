package be.uantwerpen.iw.ei.se.fittsTest.models;

import be.uantwerpen.iw.ei.se.models.MyAbstractPersistable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dries on 3/11/2015.
 */
@Entity
public class FittsResult extends MyAbstractPersistable<Long>
{
    private String resultID;
    private String testID;

/*
    @OneToMany
    @JoinColumn(name="RESULT_ID", referencedColumnName="ID")
    private List<FittsTrackPath> fittsTrackPaths;
*/
    public FittsResult()
    {
        this.resultID = "";
        this.testID = "";
     //   this.fittsTrackPaths = new ArrayList<FittsTrackPath>();
    }

    public FittsResult(String resultID, String testID, List<FittsTrackPath> fittsTrackPaths)
    {
        this.resultID = resultID;
        this.testID = testID;
     //   this.fittsTrackPaths = fittsTrackPaths;
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

    public void setFittsTrackPaths(List<FittsTrackPath> fittsTrackPaths)
    {
      //  this.fittsTrackPaths = fittsTrackPaths;
    }

    //public List<FittsTrackPath> getFittsTrackPaths()
    {
     //   return this.fittsTrackPaths;
    }
}
