package be.uantwerpen.iw.ei.se.repositories;

import be.uantwerpen.iw.ei.se.ClickOMaticApplication;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTest;
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
 * Created by Thomas on 20/11/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ClickOMaticApplication.class)
@WebAppConfiguration
public class FittsTestRepositoryTests
{
    @Autowired
    private FittsTestRepository fittsTestRepository;

    private FittsTest fittsTest;
    private int origFittsTestRepositorySize;

    @Before
    public void setup()
    {
        //Setup FittsTest
        fittsTest = new FittsTest();
        fittsTest.setTestID("Test 001");

        origFittsTestRepositorySize = (int)fittsTestRepository.count();
    }

    @Test
    public void testSaveFittsTestStage()
    {
        //Kijk of er inderdaad een ID wordt aangemaakt na de save!
        assertNull(fittsTest.getId());       //Moet null zijn
        fittsTestRepository.save(fittsTest);
        assertNotNull(fittsTest.getId());    //Mag nu geen null zijn

        //Haal uit de database
        FittsTest fetchedFittsTest = fittsTestRepository.findOne(fittsTest.getId());

        //Mag geen null zijn
        assertNotNull(fetchedFittsTest);

        //Moet gelijk zijn
        assertEquals(fittsTest.getId(), fetchedFittsTest.getId());
        assertEquals(fittsTest, fetchedFittsTest);

        //Update en save
        fetchedFittsTest.setTestID("New 001");
        fittsTestRepository.save(fetchedFittsTest);

        //Controle of het nu wel gesaved is
        FittsTest fetchedUpdatedFittsTest = fittsTestRepository.findOne(fetchedFittsTest.getId());
        assertEquals(fetchedFittsTest, fetchedUpdatedFittsTest);

        //Controle of het aantal klopt
        long userCount = fittsTestRepository.count();
        assertEquals(userCount, origFittsTestRepositorySize + 1);        //One test has been added to the database

        //Haal alle gebruikers eruit, er zou er nu 1 meer moeten zitten dan initieel.
        Iterable<FittsTest> tests = fittsTestRepository.findAll();

        int count = 0;

        for(FittsTest t : tests)
        {
            count++;
        }

        assertEquals(count, origFittsTestRepositorySize + 1);
    }

    @Test
    public void testDeleteFittsTest()
    {
        //Setup test
        FittsTest test = new FittsTest();
        test.setTestID("Test");

        //Save test, verify if it has ID value after save
        assertNull(test.getId());              //Null before save
        fittsTestRepository.save(test);
        assertNotNull(test.getId());           //Not null after save

        //Fetched from database
        FittsTest fetchedTest = fittsTestRepository.findOne(test.getId());

        //Should not be null
        assertNotNull(fetchedTest);

        //Delete test from database
        fittsTestRepository.delete(fetchedTest.getId());

        //Fetch from database (should not exist anymore)
        fetchedTest = fittsTestRepository.findOne(fetchedTest.getId());

        //Should be null
        assertNull(fetchedTest);
    }
}
