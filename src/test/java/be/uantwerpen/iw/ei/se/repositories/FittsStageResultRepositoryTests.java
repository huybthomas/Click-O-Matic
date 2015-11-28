package be.uantwerpen.iw.ei.se.repositories;

import be.uantwerpen.iw.ei.se.ClickOMaticApplication;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsStageResult;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTrackPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

/**
 * Created by Thomas on 28/11/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ClickOMaticApplication.class)
@WebAppConfiguration
public class FittsStageResultRepositoryTests
{
    @Autowired
    private FittsStageResultRepository fittsStageResultRepository;

    @Autowired
    private FittsTrackPathRepository fittsTrackPathRepository;

    private int origFittsStageResultRepositorySize;

    @Before
    public void setup()
    {
        origFittsStageResultRepositorySize = (int)fittsStageResultRepository.count();
    }

    @Test
    @Transactional
    public void testSaveStageResult()
    {
        //Setup stageResult
        FittsStageResult fittsStageResult = new FittsStageResult();

        //Save stageResult, verify has ID value after save
        assertNull(fittsStageResult.getId());       //Null before save
        fittsStageResultRepository.save(fittsStageResult);
        assertNotNull(fittsStageResult.getId());    //Not null after save

        //Fetch from database
        FittsStageResult fetchedStageResult = fittsStageResultRepository.findOne(fittsStageResult.getId());

        //Should not be null
        assertNotNull(fetchedStageResult);

        //Should equals
        assertEquals(fittsStageResult.getId(), fetchedStageResult.getId());
        assertEquals(fittsStageResult.getFittsTrackPaths(), fetchedStageResult.getFittsTrackPaths());

        //Update trackpatchs and save
        FittsTrackPath newTrackPath = new FittsTrackPath();
        fetchedStageResult.setTrackPaths(new ArrayList<FittsTrackPath>(Arrays.asList(newTrackPath)));
        fittsTrackPathRepository.save(newTrackPath);
        fittsStageResultRepository.save(fetchedStageResult);

        //Get from database, should be updated
        FittsStageResult fetchedUpdatedStageResult = fittsStageResultRepository.findOne(fetchedStageResult.getId());
        assertEquals(fetchedStageResult.getFittsTrackPaths(), fetchedUpdatedStageResult.getFittsTrackPaths());

        //Verify count of stage results in database
        long stageResultCount = fittsStageResultRepository.count();
        assertEquals(stageResultCount, origFittsStageResultRepositorySize + 1);        //One stage result has been added to the database

        //Get all stage results, list should only have one more then initial value
        Iterable<FittsStageResult> stageResults = fittsStageResultRepository.findAll();

        int count = 0;

        for(FittsStageResult s : stageResults)
        {
            count++;
        }

        //There are originally 'origFittsStageResultRepositorySize' stageResults declared in the database (+1 has been added in this test)
        assertEquals(count, origFittsStageResultRepositorySize + 1);
    }

    @Test
    public void testDeleteFittsStageResultTest()
    {
        //Setup stageResult
        FittsStageResult stageResult = new FittsStageResult();

        //Save stageResult, verify if it has ID value after save
        assertNull(stageResult.getId());              //Null before save
        fittsStageResultRepository.save(stageResult);
        assertNotNull(stageResult.getId());           //Not null after save

        //Fetched from database
        FittsStageResult fetchedStageResult = fittsStageResultRepository.findOne(stageResult.getId());

        //Should not be null
        assertNotNull(fetchedStageResult);

        //Delete test from database
        fittsStageResultRepository.delete(fetchedStageResult.getId());

        //Fetch from database (should not exist anymore)
        fetchedStageResult = fittsStageResultRepository.findOne(fetchedStageResult.getId());

        //Should be null
        assertNull(fetchedStageResult);
    }
}
