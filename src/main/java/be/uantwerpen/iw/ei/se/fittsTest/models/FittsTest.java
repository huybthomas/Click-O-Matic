package be.uantwerpen.iw.ei.se.fittsTest.models;

import be.uantwerpen.iw.ei.se.models.MyAbstractPersistable;

import javax.persistence.Entity;

/**
 * Created by Thomas on 12/11/2015.
 */
@Entity
public class FittsTest extends MyAbstractPersistable<Long>
{
    private String testID;
    private int numberOfDots;
    private int dotSize;
    private int dotDistance;

    public FittsTest()
    {
        this.testID = "";
        this.numberOfDots = -1;
        this.dotSize = -1;
        this.dotDistance = -1;
    }

    public FittsTest(String testID, int numberOfDots, int dotSize, int dotDistance)
    {
        this.testID = testID;
        this.numberOfDots = numberOfDots;
        this.dotSize = dotSize;
        this.dotDistance = dotDistance;
    }

    public void setTestID(String testID)
    {
        this.testID = testID;
    }

    public String getTestID()
    {
        return this.testID;
    }

    public void setNumberOfDots(int numberOfDots)
    {
        this.numberOfDots = numberOfDots;
    }

    public int getNumberOfDots()
    {
        return this.numberOfDots;
    }

    public void setDotSize(int dotSize)
    {
        this.dotSize = dotSize;
    }

    public int getDotSize()
    {
        return this.dotSize;
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

        FittsTest test = (FittsTest) object;

        return this.testID.equals(test.getTestID());
    }
}
