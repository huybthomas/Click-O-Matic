package be.uantwerpen.iw.ei.se.fittsTest.models;

import java.io.Serializable;

/**
 * Created by Thomas on 12/11/2015.
 */
public class FittsTest implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String testID;
    private int numberOfDots;
    private int dotSize;
    private int dotDistance;
    private boolean completed;

    public FittsTest()
    {
        this.testID = "";
        this.numberOfDots = -1;
        this.dotSize = -1;
        this.dotDistance = -1;
        this.completed = false;
    }

    public FittsTest(String testID, int numberOfDots, int dotSize, int dotDistance)
    {
        this.testID = testID;
        this.numberOfDots = numberOfDots;
        this.dotSize = dotSize;
        this.dotDistance = dotDistance;
        this.completed = false;
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

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean getCompleted() {
        return completed;
    }
}
