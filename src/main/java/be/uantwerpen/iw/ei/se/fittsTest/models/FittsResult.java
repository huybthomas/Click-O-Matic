package be.uantwerpen.iw.ei.se.fittsTest.models;

import be.uantwerpen.iw.ei.se.models.MyAbstractPersistable;
import be.uantwerpen.iw.ei.se.models.User;

import javax.persistence.*;
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
    @JoinColumn(name="RESULTSTAGE_ID", referencedColumnName="ID")
    private List<FittsStageResult> stages;

    @ManyToOne
    private User user;

    public FittsResult()
    {
        this.resultID = "";
        this.testID = "";
        this.resultDate = new Date();
        this.stages = new ArrayList<FittsStageResult>();
        this.user = null;
    }

    public FittsResult(String resultID, String testID, Date resultDate, List<FittsStageResult> stageResults, User user)
    {
        this.resultID = resultID;
        this.testID = testID;
        this.resultDate = resultDate;
        this.stages = stageResults;
        this.user = user;
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

    public void setStageResults(List<FittsStageResult> stageResults)
    {
        this.stages = stageResults;
    }

    public List<FittsStageResult> getStageResults()
    {
        return this.stages;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public User getUser()
    {
        return this.user;
    }

    //Remove all existing links between the user and this result in the database
    @PreRemove
    private void removeResultsFromUsers()
    {
        user.getResults().remove(this);
    }
}
