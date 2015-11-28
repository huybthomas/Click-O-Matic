package be.uantwerpen.iw.ei.se.services;

import be.uantwerpen.iw.ei.se.fittsTest.models.FittsResult;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTest;
import be.uantwerpen.iw.ei.se.repositories.FittsResultRepository;
import be.uantwerpen.iw.ei.se.repositories.FittsTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private FittsResultRepository fittsResultRepository;

    public FittsTest findTestById(String testID)
    {
        return this.fittsTestRepository.findByTestID(testID);
    }

    public Iterable<FittsTest> findAllTests()
    {
        return this.fittsTestRepository.findAll();
    }

    public Iterable<FittsResult> findAllResults()
    {
        return this.fittsResultRepository.findAll();
    }

    public Boolean saveTestResult(FittsResult result)
    {
        if(findTestById(result.getTestID()) != null)
        {
            fittsResultRepository.save(result);

            return true;
        }
        else
        {
            return false;
        }
    }

    public Iterable<FittsResult> findResultsByTestId(String testID)
    {
        return this.fittsResultRepository.findByTestID(testID);
    }

    public FittsResult findResultById(String resultID)
    {
        return this.fittsResultRepository.findByResultID(resultID);
    }

    public Iterable<FittsTest> findTestsByCompleteState(boolean completed)
    {
        return this.fittsTestRepository.findByCompleteState(completed);
    }

    public boolean addTest(final FittsTest test)
    {
        if(isDuplicatedTestId(test))
        {
            return false;
        }

        this.fittsTestRepository.save(test);

        return true;
    }

    public boolean saveTest(FittsTest test)
    {
        for(FittsTest t : findAllTests())
        {
            if(t.getId() == test.getId())
            {
                t.setTestID(test.getTestID());
                t.setCompleted(test.getCompleted());
                t.setTestStages(test.getTestStages());
                fittsTestRepository.save(t);

                return true;
            }
        }

        return false;
    }

    public boolean testIDAlreadyExists(final String testID)
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

    public boolean resultIDAlreadyExists(final String resultID)
    {
        List<FittsResult> results = fittsResultRepository.findAll();

        for(FittsResult resultIt : results)
        {
            if(resultIt.getResultID().equals(resultID))
            {
                return true;
            }
        }

        return false;
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

    private boolean isDuplicatedResultId(final FittsResult result)
    {
        List<FittsResult> results = fittsResultRepository.findAll();

        for(FittsResult resultIt : results)
        {
            if(resultIt.getTestID().equals(result.getTestID()) && !resultIt.getId().equals(result.getId()))
            {
                //Two different test objects with the same resultID
                return true;
            }
        }

        return false;
    }
}
