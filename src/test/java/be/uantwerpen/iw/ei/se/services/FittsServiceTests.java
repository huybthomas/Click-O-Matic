package be.uantwerpen.iw.ei.se.services;

import be.uantwerpen.iw.ei.se.ClickOMaticApplication;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTest;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTestStage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by Verstraete on 14/12/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ClickOMaticApplication.class)
@WebAppConfiguration
public class FittsServiceTests {
    @Autowired
    private FittsService fittsService;

    private List<FittsTest> fittsTestList;
    private List<FittsTestStage> fittsTestStageList;



    @Before
    public void init()
    {
        FittsTestStage stage1= new FittsTestStage("Stage001",5,51,501);
        FittsTestStage stage2= new FittsTestStage("Stage002",10,52,502);
        FittsTestStage stage3= new FittsTestStage("Stage003",15,53,503);

        fittsTestStageList = new ArrayList<FittsTestStage>();
        fittsTestStageList.add(stage1);
        fittsTestStageList.add(stage2);
        fittsTestStageList.add(stage3);

        FittsTest fittsTest1 = new FittsTest("Test001",fittsTestStageList);

        FittsTestStage stage4= new FittsTestStage("Stage004",5,54,501);
        FittsTestStage stage5= new FittsTestStage("Stage005",10,55,502);
        FittsTestStage stage6= new FittsTestStage("Stage006",15,56,503);

        fittsTestStageList = new ArrayList<FittsTestStage>();
        fittsTestStageList.add(stage4);
        fittsTestStageList.add(stage5);
        fittsTestStageList.add(stage6);

        FittsTest fittsTest2 = new FittsTest("Test002",fittsTestStageList);

        fittsTestList = new ArrayList<FittsTest>();
        fittsTestList.add(fittsTest1);
        fittsTestList.add(fittsTest2);

        fittsService.addTest(fittsTest1);
        fittsService.addTest(fittsTest2);
    }


    @Test
    public void checkExistingTestId()
    {

        assertTrue(fittsService.testIdAlreadyExists("Test001"));
    }

    @Test
    public void checkNotExistingTestId()
    {
        assertTrue(!fittsService.testIdAlreadyExists("Test003"));
    }

    @Test
    public void checkDuplicateFittsTest()
    {
        FittsTest fittsTest3 = new FittsTest("Test001", fittsTestStageList);

        assertTrue(!fittsService.addTest(fittsTest3));
    }

    @Test
    public void checkNotDuplicateFittsTest()
    {
        FittsTest fittsTest3 = new FittsTest("Test003", fittsTestStageList);

        assertTrue(fittsService.addTest(fittsTest3));
    }

    @Test
    public void checkFindExistingResultByTestId()
    {
        if(!fittsService.findResultsByTestId("Test001").equals(null))
        {
            assertTrue(true);
        }
        else
        {
            assertTrue(false);
        }
    }
}
