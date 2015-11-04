package be.uantwerpen.iw.ei.se.repositories;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

/**
 * Created by Verstraete on 4/11/2015.
 */
public class FittsRepositoryTests {
    @Autowired
    FittsRepository fittsRepository;

    @Test
    public void testSaveFitsTest()
    {
        //Setup FitsTest
        FittsTest fittstest = new Fittstest();
        fittstest.setName("TestName");
        //nog meer setters nodig, te behalen uit de FittsTest klasse

        //Kijk of er inderdaad een ID wordt aangemaakt na de save! ( kijk Dries nu wel met DT ;p )
        assertNull(fittstest.getId());       //Moet null zijn
        FittsRepository.save(fittstest);
        assertNotNull(fittstest.getId());    //Mag nu geen null zijn

        //Haal uit de database
        FittsTest fetchedFittsTest = FittsRepository.findOne(fittstest.getId());

        //Mag geen null zijn
        assertNotNull(fetchedFittsTest);

        //Moet gelijk zijn
        assertEquals(fittstest.getId(), fetchedTest.getId());
        assertEquals(fittstest.getName(), fetchedTest.getUserName());

        //Update en save
        fetchedFittsTest.setName("NewTestName");
        FittsRepository.save(fetchedFittsTest);

        //Controle of het nu wel gesaved is
        FittsTest fetchedUpdatedFittsTest = FittsRepository.findOne(fetchedFittsTest.getId());
        assertEquals(fetchedFittsTest.getName(), fetchedUpdatedFittsTest.getName());

        //Controle of het aantal klopt
        long userCount = FittsRepository.count();
        assertEquals(userCount, origFittsTestRepositorySize + 1);        //One user has been added to the database

        //Haal alle gebruikers eruit, er zou er nu 1 meer moeten zitten dan initieel.
        Iterable<FittsTest> users = FittsRepository.findAll();

        int count = 0;

        for(FittsTest p : users)
        {
            count++;
        }

        assertEquals(count, origFittsTestRepositorySize + 1);

    }
}
