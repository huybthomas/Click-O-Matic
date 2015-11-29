package be.uantwerpen.iw.ei.se.fittsTest.models;

import be.uantwerpen.iw.ei.se.models.MyAbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 12/11/2015.
 */
@Entity
public class FittsTest extends MyAbstractPersistable<Long>
{
    private String testID;
    private boolean completed;

    @ManyToMany
    @JoinTable(
            name="TEST_STAGE",
            joinColumns={
                    @JoinColumn(name="TEST_ID", referencedColumnName="ID")},
            inverseJoinColumns={
                    @JoinColumn(name="SESSION_ID", referencedColumnName="ID")})
    private List<FittsTestStage> testStages;

    public FittsTest()
    {
        this.testID = "";
        this.testStages = new ArrayList<FittsTestStage>();
        this.completed = false;
    }

    public FittsTest(String testID, List<FittsTestStage> testStages)
    {
        this.testID = testID;
        this.testStages = testStages;
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

    public void setTestStages(List<FittsTestStage> testStages)
    {
        this.testStages = testStages;
    }

    public List<FittsTestStage> getTestStages()
    {
        return this.testStages;
    }

    public int getNumberOfStages()
    {
        return this.testStages.size();
    }

    public void setCompleted(Boolean completed)
    {
        this.completed = completed;
    }

    public Boolean getCompleted()
    {
        return this.completed;
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
