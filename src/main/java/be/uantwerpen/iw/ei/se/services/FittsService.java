package be.uantwerpen.iw.ei.se.services;

import be.uantwerpen.iw.ei.se.fittsTest.models.FittsResult;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTest;
import be.uantwerpen.iw.ei.se.repositories.FittsResultRepository;
import be.uantwerpen.iw.ei.se.repositories.FittsTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Boolean saveTestResult(String testID, String trackPathsJSON)
    {
        if(findTestById(testID) != null)
        {


           // FittsResult fittsResult = new FittsResult(testID, testID, trackPathsJSON);

           // fittsResultRepository.save(fittsResult);

            return true;
        }
        else
        {
            return false;
        }
    }

    public Iterable<FittsResult> findResultsByTestId(String testID)
    {
        return null;
    }

    public FittsResult findResultById(String resultID)
    {
        return this.fittsResultRepository.findByResultID(resultID);
    }
}
