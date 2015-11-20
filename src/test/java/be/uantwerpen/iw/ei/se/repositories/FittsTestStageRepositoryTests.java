package be.uantwerpen.iw.ei.se.repositories;

import be.uantwerpen.iw.ei.se.ClickOMaticApplication;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTestStage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

/**
 * Created by Verstraete on 4/11/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ClickOMaticApplication.class)
@WebAppConfiguration
public class FittsTestStageRepositoryTests
{
    @Autowired
    FittsTestStageRepository fittsTestStageRepository;

    FittsTestStage fittsTestStage;
    int origFittsTestStageRepositorySize;

    @Before
    public void setup()
    {
        //Setup FittsTestStage
        fittsTestStage = new FittsTestStage();
        fittsTestStage.setTestStageID("TestName");
        fittsTestStage.setNumberOfDots(11);
        fittsTestStage.setDotDistance(100);
        fittsTestStage.setDotRadius(25);

        origFittsTestStageRepositorySize = (int)fittsTestStageRepository.count();
    }

    @Test
    public void testSaveFittsTestStage()
    {
        //Kijk of er inderdaad een ID wordt aangemaakt na de save!
        assertNull(fittsTestStage.getId());       //Moet null zijn
        fittsTestStageRepository.save(fittsTestStage);
        assertNotNull(fittsTestStage.getId());    //Mag nu geen null zijn

        //Haal uit de database
        FittsTestStage fetchedFittsTestStage = fittsTestStageRepository.findOne(fittsTestStage.getId());

        //Mag geen null zijn
        assertNotNull(fetchedFittsTestStage);

        //Moet gelijk zijn
        assertEquals(fittsTestStage.getId(), fetchedFittsTestStage.getId());
        assertEquals(fittsTestStage, fetchedFittsTestStage);

        //Update en save
        fetchedFittsTestStage.setTestStageID("NewTestName");
        fittsTestStageRepository.save(fetchedFittsTestStage);

        //Controle of het nu wel gesaved is
        FittsTestStage fetchedUpdatedFittsTestStage = fittsTestStageRepository.findOne(fetchedFittsTestStage.getId());
        assertEquals(fetchedFittsTestStage, fetchedUpdatedFittsTestStage);

        //Controle of het aantal klopt
        long userCount = fittsTestStageRepository.count();
        assertEquals(userCount, origFittsTestStageRepositorySize + 1);        //One test stage has been added to the database

        //Haal alle gebruikers eruit, er zou er nu 1 meer moeten zitten dan initieel.
        Iterable<FittsTestStage> stages = fittsTestStageRepository.findAll();

        int count = 0;

        for(FittsTestStage s : stages)
        {
            count++;
        }

        assertEquals(count, origFittsTestStageRepositorySize + 1);
    }
}
