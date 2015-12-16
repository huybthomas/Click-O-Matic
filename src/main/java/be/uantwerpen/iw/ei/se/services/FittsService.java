package be.uantwerpen.iw.ei.se.services;

import be.uantwerpen.iw.ei.se.fittsTest.models.FittsResult;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTest;
import be.uantwerpen.iw.ei.se.models.User;
import be.uantwerpen.iw.ei.se.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 15/11/2015.
 */
@Service
public class FittsService
{
    @Autowired
    private FittsTestRepository fittsTestRepository;

    @Autowired
    private FittsTestStageRepository fittsTestStageRepository;

    @Autowired
    private FittsResultService fittsResultService;

    public FittsTest findTestById(String testID)
    {
        return this.fittsTestRepository.findByTestID(testID);
    }

    public Iterable<FittsTest> findAllTests()
    {
        return this.fittsTestRepository.findAll();
    }

    public Iterable<FittsTest> findAllTestsForUser(User user)
    {
        return this.fittsTestRepository.findAllForUser(user);
    }

    public Iterable<FittsResult> findAllResults()
    {
        return this.fittsResultService.findAll();
    }

    public boolean saveTestResult(FittsResult result)
    {
        if(findTestById(result.getTestID()) != null)
        {
            fittsResultService.save(result);

            return true;
        }
        else
        {
            return false;
        }
    }

    public Iterable<FittsResult> findResultsByTestId(String testID)
    {
        return this.fittsResultService.findByTestID(testID);
    }

    public Iterable<FittsResult> findResultsByTestIdForUser(String testID, User user)
    {
        return this.fittsResultService.findByTestIDForUser(testID, user);
    }

    public FittsResult findResultById(String resultID)
    {
        return this.fittsResultService.findByResultID(resultID);
    }

    public Iterable<FittsTest> findTestsByCompleteStateForUser(boolean completed, User user)
    {
        List<FittsTest> selectedTests = new ArrayList<FittsTest>();

        for(FittsTest test : user.getTests())
        {
            Iterable<FittsResult> testResults = this.findResultsByTestIdForUser(test.getTestID(), user);

            if(testResults.iterator().hasNext() == completed)
            {
                //User has at least one result and completed the test
                selectedTests.add(test);
            }
        }

        return selectedTests;
    }

    public boolean addTest(final FittsTest test)
    {
        if(isDuplicatedTestId(test))
        {
            return false;
        }

        //Save the stages of the test to the database
        this.fittsTestStageRepository.save(test.getTestStages());

        //Save the test to the database
        this.fittsTestRepository.save(test);

        return true;
    }

    public boolean saveTest(FittsTest test)
    {
        for(FittsTest t : findAllTests())
        {
            if(t.getId().equals(test.getId()))
            {
                if(!this.isDuplicatedTestId(test))
                {
                    t.setTestID(test.getTestID());
                    t.setComment(test.getComment());
                    t.setTestStages(test.getTestStages());

                    //Save the stages of the test to the database
                    this.fittsTestStageRepository.save(test.getTestStages());

                    //Save the test to the database
                    this.fittsTestRepository.save(t);

                    return true;
                }
                else
                {
                    return false;
                }
            }
        }

        return false;
    }

    public boolean deleteTest(String testID)
    {
        FittsTest test = findTestById(testID);

        if(test != null)
        {
            //Delete all existing results of this test
            for(FittsResult result : this.findResultsByTestId(testID))
            {
                this.fittsResultService.delete(result.getResultID());
            }

            //Delete all test stages
            this.fittsTestStageRepository.delete(test.getTestStages());

            //Delete test
            this.fittsTestRepository.delete(test);

            return true;
        }

        return false;
    }

    public boolean deleteResult(String resultID)
    {
        return this.fittsResultService.delete(resultID);
    }

    public boolean testIdAlreadyExists(final String testID)
    {
        List<FittsTest> tests = fittsTestRepository.findAll();

        for(FittsTest testIt : tests)
        {
            if(testIt.getTestID().equals(testID))
            {
                return true;
            }
        }

        return false;
    }

    public boolean resultIdAlreadyExists(final String resultID)
    {
        return this.fittsResultService.resultIdAlreadyExists(resultID);
    }

    private boolean isDuplicatedTestId(final FittsTest test)
    {
        List<FittsTest> tests = fittsTestRepository.findAll();

        for(FittsTest testIt : tests)
        {
            if(testIt.getTestID().equals(test.getTestID()) && !testIt.getId().equals(test.getId()))
            {
                //Two different test objects with the same testID
                return true;
            }
        }

        return false;
    }
}
