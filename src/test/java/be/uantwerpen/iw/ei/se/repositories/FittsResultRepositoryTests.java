package be.uantwerpen.iw.ei.se.repositories;

import be.uantwerpen.iw.ei.se.ClickOMaticApplication;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

/**
 * Created by Thomas on 28/11/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ClickOMaticApplication.class)
@WebAppConfiguration
public class FittsResultRepositoryTests
{
    @Autowired
    private FittsResultRepository fittsResultRepository;

    private int origFittsResultRepositorySize;

    @Before
    public void setup()
    {
        origFittsResultRepositorySize = (int)fittsResultRepository.count();
    }

    @Test
    public void testSaveResult()
    {
        //Setup result
        FittsResult fittsResult = new FittsResult();
        fittsResult.setResultID("Result");

        //Save result, verify has ID value after save
        assertNull(fittsResult.getId());       //Null before save
        fittsResultRepository.save(fittsResult);
        assertNotNull(fittsResult.getId());    //Not null after save

        //Fetch from database
        FittsResult fetchedResult = fittsResultRepository.findOne(fittsResult.getId());

        //Should not be null
        assertNotNull(fetchedResult);

        //Should equals
        assertEquals(fittsResult.getId(), fetchedResult.getId());
        assertEquals(fittsResult.getResultID(), fetchedResult.getResultID());

        //Update resultID and save
        fetchedResult.setResultID("New Result");
        fittsResultRepository.save(fetchedResult);

        //Get from database, should be updated
        FittsResult fetchedUpdatedResult = fittsResultRepository.findOne(fetchedResult.getId());
        assertEquals(fetchedResult.getResultID(), fetchedUpdatedResult.getResultID());

        //Verify count of results in database
        long resultCount = fittsResultRepository.count();
        assertEquals(resultCount, origFittsResultRepositorySize + 1);        //One result has been added to the database

        //Get all results, list should only have one more then initial value
        Iterable<FittsResult> results = fittsResultRepository.findAll();

        int count = 0;

        for(FittsResult r : results)
        {
            count++;
        }

        //There are originally 'origFittsResultRepositorySize' results declared in the database (+1 has been added in this test)
        assertEquals(count, origFittsResultRepositorySize + 1);
    }

    @Test
    public void testDeleteFittsResultTest()
    {
        //Setup result
        FittsResult result = new FittsResult();

        //Save result, verify if it has ID value after save
        assertNull(result.getId());              //Null before save
        fittsResultRepository.save(result);
        assertNotNull(result.getId());           //Not null after save

        //Fetched from database
        FittsResult fetchedResult = fittsResultRepository.findOne(result.getId());

        //Should not be null
        assertNotNull(fetchedResult);

        //Delete result from database
        fittsResultRepository.delete(fetchedResult.getId());

        //Fetch from database (should not exist anymore)
        fetchedResult = fittsResultRepository.findOne(fetchedResult.getId());

        //Should be null
        assertNull(fetchedResult);
    }
}
