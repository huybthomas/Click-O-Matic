package be.uantwerpen.iw.ei.se.fittsTest.models;

import be.uantwerpen.iw.ei.se.models.MyAbstractPersistable;

import javax.persistence.Entity;

/**
 * Created by Thomas on 19/11/2015.
 */
@Entity
public class FittsTestStage extends MyAbstractPersistable<Long>
{
    private String testStageID;
    private int numberOfDots;
    private int dotRadius;
    private int dotDistance;

    public FittsTestStage()
    {
        this.testStageID = "";
        this.numberOfDots = -1;
        this.dotRadius = -1;
        this.dotDistance = -1;
    }

    public FittsTestStage(String testStageID, int numberOfDots, int dotRadius, int dotDistance)
    {
        this.testStageID = testStageID;
        this.numberOfDots = numberOfDots;
        this.dotRadius = dotRadius;
        this.dotDistance = dotDistance;
    }

    public void setTestStageID(String testStageID)
    {
        this.testStageID = testStageID;
    }

    public String getTestStageID()
    {
        return this.testStageID;
    }

    public void setNumberOfDots(int numberOfDots)
    {
        this.numberOfDots = numberOfDots;
    }

    public int getNumberOfDots()
    {
        return this.numberOfDots;
    }

    public void setDotRadius(int dotRadius)
    {
        this.dotRadius = dotRadius;
    }

    public int getDotRadius()
    {
        return this.dotRadius;
    }

    public void setDotDistance(int dotDistance)
    {
        this.dotDistance = dotDistance;
    }

    public int getDotDistance()
    {
        return this.dotDistance;
    }

    @Override
    public boolean equals(Object object)
    {
        if(this == object)
        {
            return true;
        }

        if(object == null || this.getClass() != object.getClass())
        {
            return false;
        }

        FittsTestStage test = (FittsTestStage) object;

        return this.testStageID.equals(test.getTestStageID());
    }
}
