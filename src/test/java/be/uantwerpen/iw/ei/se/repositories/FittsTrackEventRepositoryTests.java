package be.uantwerpen.iw.ei.se.repositories;

import be.uantwerpen.iw.ei.se.ClickOMaticApplication;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTrackEvent;
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
public class FittsTrackEventRepositoryTests
{
    @Autowired
    private FittsTrackEventRepository fittsTrackEventRepository;

    private int origFittsTrackEventRepositorySize;

    @Before
    public void setup()
    {
        origFittsTrackEventRepositorySize = (int)fittsTrackEventRepository.count();
    }

    @Test
    public void testSaveTrackEvent()
    {
        //Setup trackEvent
        FittsTrackEvent fittsTrackEvent = new FittsTrackEvent();
        fittsTrackEvent.setTimestamp(1000L);

        //Save trackEvent, verify has ID value after save
        assertNull(fittsTrackEvent.getId());       //Null before save
        fittsTrackEventRepository.save(fittsTrackEvent);
        assertNotNull(fittsTrackEvent.getId());    //Not null after save

        //Fetch from database
        FittsTrackEvent fetchedTrackEvent = fittsTrackEventRepository.findOne(fittsTrackEvent.getId());

        //Should not be null
        assertNotNull(fetchedTrackEvent);

        //Should equals
        assertEquals(fittsTrackEvent.getId(), fetchedTrackEvent.getId());
        assertEquals(fittsTrackEvent.getTimestamp(), fetchedTrackEvent.getTimestamp());

        //Update timestamp and save
        fetchedTrackEvent.setTimestamp(9000L);
        fittsTrackEventRepository.save(fetchedTrackEvent);

        //Get from database, should be updated
        FittsTrackEvent fetchedUpdatedTrackEvent = fittsTrackEventRepository.findOne(fetchedTrackEvent.getId());
        assertEquals(fetchedTrackEvent.getTimestamp(), fetchedUpdatedTrackEvent.getTimestamp());

        //Verify count of trackEvents in database
        long trackEventCount = fittsTrackEventRepository.count();
        assertEquals(trackEventCount, origFittsTrackEventRepositorySize + 1);        //One trackEvent has been added to the database

        //Get all trackEvents, list should only have one more then initial value
        Iterable<FittsTrackEvent> trackEvents = fittsTrackEventRepository.findAll();

        int count = 0;

        for(FittsTrackEvent e : trackEvents)
        {
            count++;
        }

        //There are originally 'origFittsTrackEventRepositorySize' trackEvents declared in the database (+1 has been added in this test)
        assertEquals(count, origFittsTrackEventRepositorySize + 1);
    }

    @Test
    public void testDeleteFittsTrackEventTest()
    {
        //Setup trackEvent
        FittsTrackEvent trackEvent = new FittsTrackEvent();

        //Save trackEvent, verify if it has ID value after save
        assertNull(trackEvent.getId());              //Null before save
        fittsTrackEventRepository.save(trackEvent);
        assertNotNull(trackEvent.getId());           //Not null after save

        //Fetched from database
        FittsTrackEvent fetchedTrackEventResult = fittsTrackEventRepository.findOne(trackEvent.getId());

        //Should not be null
        assertNotNull(fetchedTrackEventResult);

        //Delete trackEvent from database
        fittsTrackEventRepository.delete(fetchedTrackEventResult.getId());

        //Fetch from database (should not exist anymore)
        fetchedTrackEventResult = fittsTrackEventRepository.findOne(fetchedTrackEventResult.getId());

        //Should be null
        assertNull(fetchedTrackEventResult);
    }
}
