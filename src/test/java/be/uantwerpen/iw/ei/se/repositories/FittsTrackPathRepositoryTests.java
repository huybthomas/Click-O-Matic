package be.uantwerpen.iw.ei.se.repositories;

import be.uantwerpen.iw.ei.se.ClickOMaticApplication;
import be.uantwerpen.iw.ei.se.configurations.SystemPropertyActiveProfileResolver;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTrackEvent;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTrackPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
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
@ActiveProfiles(profiles = {"dev"}, resolver = SystemPropertyActiveProfileResolver.class)
@WebAppConfiguration
public class FittsTrackPathRepositoryTests
{
    @Autowired
    private FittsTrackPathRepository fittsTrackPathRepository;

    @Autowired
    private FittsTrackEventRepository fittsTrackEventRepository;

    private int origFittsTrackPathRepositorySize;

    @Before
    public void setup()
    {
        origFittsTrackPathRepositorySize = (int)fittsTrackPathRepository.count();
    }

    @Test
    @Transactional
    public void testSaveTrackPath()
    {
        //Setup trackPath
        FittsTrackPath fittsTrackPath = new FittsTrackPath();

        //Save trackPath, verify has ID value after save
        assertNull(fittsTrackPath.getId());       //Null before save
        fittsTrackPathRepository.save(fittsTrackPath);
        assertNotNull(fittsTrackPath.getId());    //Not null after save

        //Fetch from database
        FittsTrackPath fetchedTrackPath = fittsTrackPathRepository.findOne(fittsTrackPath.getId());

        //Should not be null
        assertNotNull(fetchedTrackPath);

        //Should equals
        assertEquals(fittsTrackPath.getId(), fetchedTrackPath.getId());
        assertEquals(fittsTrackPath.getPath(), fetchedTrackPath.getPath());

        //Update trackEvents and save
        FittsTrackEvent newTrackEvent = new FittsTrackEvent();
        fetchedTrackPath.setPath(new ArrayList<FittsTrackEvent>(Arrays.asList(newTrackEvent)));
        fittsTrackEventRepository.save(newTrackEvent);
        fittsTrackPathRepository.save(fetchedTrackPath);

        //Get from database, should be updated
        FittsTrackPath fetchedUpdatedTrackPath = fittsTrackPathRepository.findOne(fetchedTrackPath.getId());
        assertEquals(fetchedTrackPath.getPath(), fetchedUpdatedTrackPath.getPath());

        //Verify count of trackPaths in database
        long trackEventsCount = fittsTrackPathRepository.count();
        assertEquals(trackEventsCount, origFittsTrackPathRepositorySize + 1);        //One trackPath has been added to the database

        //Get all trackPaths, list should only have one more then initial value
        Iterable<FittsTrackPath> trackPaths = fittsTrackPathRepository.findAll();

        int count = 0;

        for(FittsTrackPath p : trackPaths)
        {
            count++;
        }

        //There are originally 'origFittsTrackPathRepositorySize' trackPaths declared in the database (+1 has been added in this test)
        assertEquals(count, origFittsTrackPathRepositorySize + 1);
    }

    @Test
    public void testDeleteFittsTrackPathTest()
    {
        //Setup trackPath
        FittsTrackPath trackPath = new FittsTrackPath();

        //Save trackPath, verify if it has ID value after save
        assertNull(trackPath.getId());              //Null before save
        fittsTrackPathRepository.save(trackPath);
        assertNotNull(trackPath.getId());           //Not null after save

        //Fetched from database
        FittsTrackPath fetchedTrackPath = fittsTrackPathRepository.findOne(trackPath.getId());

        //Should not be null
        assertNotNull(fetchedTrackPath);

        //Delete trackPath from database
        fittsTrackPathRepository.delete(fetchedTrackPath.getId());

        //Fetch from database (should not exist anymore)
        fetchedTrackPath = fittsTrackPathRepository.findOne(fetchedTrackPath.getId());

        //Should be null
        assertNull(fetchedTrackPath);
    }
}
