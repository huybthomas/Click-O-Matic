package be.uantwerpen.iw.ei.se.services;

import be.uantwerpen.iw.ei.se.ClickOMaticApplication;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsResult;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsStageResult;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTrackEvent;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTrackPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by Verstraete on 14/12/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ClickOMaticApplication.class)
@WebAppConfiguration
public class FittsResultServiceTests {
    @Autowired
    private FittsResultService fittsResultService;

    private List<FittsResult> fittsResultList;
    private List<FittsStageResult> fittsStageResults;
    private List<FittsTrackEvent> fittsTrackEvents;
    private List<FittsTrackPath> fittsTrackPaths;

    @Before
    public void init()
    {

        FittsTrackEvent trackEvent1 = new FittsTrackEvent(10,5,5,false);
        FittsTrackEvent trackEvent2 = new FittsTrackEvent(11,5,5,false);
        FittsTrackEvent trackEvent3 = new FittsTrackEvent(1,5,5,false);
        FittsTrackEvent trackEvent4 = new FittsTrackEvent(2,5,5,false);
        FittsTrackEvent trackEvent5 = new FittsTrackEvent(5,5,5,false);
        FittsTrackEvent trackEvent6 = new FittsTrackEvent(6,5,5,false);
        FittsTrackEvent trackEvent7 = new FittsTrackEvent(7,5,5,false);
        FittsTrackEvent trackEvent8 = new FittsTrackEvent(8,5,5,false);

        fittsTrackEvents = new ArrayList<FittsTrackEvent>();
        fittsTrackEvents.add(trackEvent1);
        fittsTrackEvents.add(trackEvent2);

        FittsTrackPath trackPath1 = new FittsTrackPath(fittsTrackEvents);

        fittsTrackEvents.clear();
        fittsTrackEvents.add(trackEvent3);
        fittsTrackEvents.add(trackEvent4);

        FittsTrackPath trackPath2 = new FittsTrackPath((fittsTrackEvents));

        fittsTrackPaths = new ArrayList<FittsTrackPath>();
        fittsTrackPaths.add(trackPath1);
        fittsTrackPaths.add(trackPath2);
        //
        FittsStageResult sr1 = new FittsStageResult(fittsTrackPaths);
        //
        fittsTrackEvents.clear();
        fittsTrackEvents.add(trackEvent5);
        fittsTrackEvents.add(trackEvent6);

        FittsTrackPath trackPath3 = new FittsTrackPath((fittsTrackEvents));

        fittsTrackEvents.clear();
        fittsTrackEvents.add(trackEvent7);
        fittsTrackEvents.add(trackEvent8);

        FittsTrackPath trackPath4 = new FittsTrackPath((fittsTrackEvents));

        fittsTrackPaths.clear();
        fittsTrackPaths.add(trackPath3);
        fittsTrackPaths.add(trackPath4);


        FittsStageResult sr2 = new FittsStageResult(fittsTrackPaths);

        fittsStageResults = new ArrayList<FittsStageResult>();
        fittsStageResults.add(sr1);
        fittsStageResults.add(sr2);


        FittsResult r1 = new FittsResult("e=mc2","RelativiteitsTest",new Date(),fittsStageResults);
        FittsResult r2 = new FittsResult("F=mg","Zwaartekracht",new Date(),fittsStageResults);

        fittsResultList = new ArrayList<FittsResult>();
        fittsResultList.add(r1);
        fittsResultList.add(r2);

        fittsResultService.save(fittsResultList);
    }



    @Test
    public void checkExistingResultId()
    {
        assertTrue(fittsResultService.resultIdAlreadyExists("e=mc2"));
    }

    @Test
    public void checkNotExistingResultId()
    {
        assertTrue(!fittsResultService.resultIdAlreadyExists("a=dv/dt"));
    }

    @Test
    public void checkDuplicateResultId()
    {
        FittsResult r3 = new FittsResult("e=mc2","RelativiteitsTest",new Date(),fittsStageResults);

        assertTrue(fittsResultService.resultIdAlreadyExists("e=mc2"));
    }

    @Test
    public void checkNotDuplicateResultId()
    {
        FittsResult r3 = new FittsResult("e=mc20","RelativiteitsTest",new Date(),fittsStageResults);

        assertTrue(!fittsResultService.resultIdAlreadyExists("e=mc20"));
    }

}
