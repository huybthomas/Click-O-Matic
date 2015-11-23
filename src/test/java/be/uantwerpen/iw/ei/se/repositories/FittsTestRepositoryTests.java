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

import static org.junit.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

/**
 * Created by Verstraete on 4/11/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ClickOMaticApplication.class)
@WebAppConfiguration
public class FittsTestRepositoryTests
{
    @Autowired
    FittsTestRepository fittsRepository;

    FittsTest fittstest;
    int origFittsTestRepositorySize;

    @Before
    public void setup()
    {
        //Setup FittsTest
        fittstest = new FittsTest();
        fittstest.setTestID("TestName");
        fittstest.setNumberOfDots(11);
        fittstest.setDotDistance(100);
        fittstest.setDotSize(25);

        origFittsTestRepositorySize = (int)fittsRepository.count();
    }

    @Test
    public void testSaveFitsTest()
    {
        //Kijk of er inderdaad een ID wordt aangemaakt na de save!
        assertNull(fittstest.getId());       //Moet null zijn
        fittsRepository.save(fittstest);
        assertNotNull(fittstest.getId());    //Mag nu geen null zijn

        //Haal uit de database
        FittsTest fetchedFittsTest = fittsRepository.findOne(fittstest.getId());

        //Mag geen null zijn
        assertNotNull(fetchedFittsTest);

        //Moet gelijk zijn
        assertEquals(fittstest.getId(), fetchedFittsTest.getId());
        assertEquals(fittstest, fetchedFittsTest);

        //Update en save
        fetchedFittsTest.setTestID("NewTestName");
        fittsRepository.save(fetchedFittsTest);

        //Controle of het nu wel gesaved is
        FittsTest fetchedUpdatedFittsTest = fittsRepository.findOne(fetchedFittsTest.getId());
        assertEquals(fetchedFittsTest, fetchedUpdatedFittsTest);

        //Controle of het aantal klopt
        long userCount = fittsRepository.count();
        assertEquals(userCount, origFittsTestRepositorySize + 1);        //One test has been added to the database

        //Haal alle gebruikers eruit, er zou er nu 1 meer moeten zitten dan initieel.
        Iterable<FittsTest> tests = fittsRepository.findAll();

        int count = 0;

        for(FittsTest t : tests)
        {
            count++;
        }

        assertEquals(count, origFittsTestRepositorySize + 1);
    }
}
