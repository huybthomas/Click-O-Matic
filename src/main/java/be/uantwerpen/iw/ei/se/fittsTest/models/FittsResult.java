package be.uantwerpen.iw.ei.se.fittsTest.models;

import be.uantwerpen.iw.ei.se.models.MyAbstractPersistable;

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

    @ManyToMany
    @JoinTable(
            name="RESULT_STAGE",
            joinColumns={
                    @JoinColumn(name="RESULT_ID", referencedColumnName="ID")},
            inverseJoinColumns={
                    @JoinColumn(name="STAGE_ID", referencedColumnName="ID")})
    private List<FittsStageResult> stages;

    public FittsResult()
    {
        this.resultID = "";
        this.testID = "";
        this.resultDate = new Date();
        this.stages = new ArrayList<FittsStageResult>();
    }

    public FittsResult(String resultID, String testID, Date resultDate, List<FittsStageResult> stageResults)
    {
        this.resultID = resultID;
        this.testID = testID;
        this.resultDate = resultDate;
        this.stages = stageResults;
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
}
