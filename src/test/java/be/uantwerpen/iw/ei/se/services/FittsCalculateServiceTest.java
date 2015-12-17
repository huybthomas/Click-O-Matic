package be.uantwerpen.iw.ei.se.services;

import be.uantwerpen.iw.ei.se.data.DatabaseLoaderDevelopment;
import be.uantwerpen.iw.ei.se.fittsTest.models.*;
import be.uantwerpen.iw.ei.se.models.Role;
import be.uantwerpen.iw.ei.se.models.User;
import be.uantwerpen.iw.ei.se.repositories.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by dries on 13/12/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class FittsCalculateServiceTest {

    @InjectMocks
    private FittsCalculateService fittsCalculateService;

    @Mock
    private FittsService fittsService;

    private Iterable trackEvents1;
    private Iterable trackEvents2;
    private Iterable trackEvents3;
    private Iterable trackEvents4;
    private Iterable trackEvents5;
    private Iterable trackEvents6;
    private Iterable trackEvents7;
    private Iterable trackEvents8;
    private Iterable trackEvents9;
    private Iterable trackEvents10;
    private Iterable trackPaths;
    private Iterable stages;
    private FittsResult result;
    private FittsTest test;
    private FittsTestStage testStage1;
    private FittsTestStage testStage2;
    private List<FittsTestStage> testStages;

    @Before
    public void init()
    {
        //Trackpath 01
        FittsTrackEvent event1 = new FittsTrackEvent(1450050149077L, 4,	-95, false);
        FittsTrackEvent event2 = new FittsTrackEvent(1450050149122L, 24, -24, false);
        FittsTrackEvent event3 = new FittsTrackEvent(1450050149133L, 35, 3, false);
        FittsTrackEvent event4 = new FittsTrackEvent(1450050149149L, 43, 25, false);
        FittsTrackEvent event5 = new FittsTrackEvent(1450050149173L, 56, 47, false);
        FittsTrackEvent event6 = new FittsTrackEvent(1450050149213L, 65, 62, false);
        FittsTrackEvent event7 = new FittsTrackEvent(1450050149381L, 63, 63, false);
        FittsTrackEvent event8 = new FittsTrackEvent(1450050149470L, 59, 73, true);
        FittsTrackEvent event9 = new FittsTrackEvent(1450050149541L, 59, 73, false);
        trackEvents1 = new ArrayList<>(Arrays.asList(event1, event2, event3, event4, event5, event6, event7, event8, event9));

        //Trackpath 02
        event1 = new FittsTrackEvent(1450050149566L, 58, 70, false);
        event2 = new FittsTrackEvent(1450050149605L, 13, 38, false);
        event3 = new FittsTrackEvent(1450050149629L, -34, 16, false);
        event4 = new FittsTrackEvent(1450050149654L, -61, 0, false);
        event5 = new FittsTrackEvent(1450050149685L, -77, -10, false);
        event6 = new FittsTrackEvent(1450050149709L, -80, -15, false);
        event7 = new FittsTrackEvent(1450050149797L, -83, -17, false);
        event8 = new FittsTrackEvent(1450050149927L, -85, -19, true);
        event9 = new FittsTrackEvent(1450050149989L, -85, -19, false);
        trackEvents2 = new ArrayList<>(Arrays.asList(event1, event2, event3, event4, event5, event6, event7, event8, event9));

        //Trackpath 03
        event1 = new FittsTrackEvent(1450050150029L, -84, -20, false);
        event2 = new FittsTrackEvent(1450050150069L, -49, -22, false);
        event3 = new FittsTrackEvent(1450050150094L, -15, -22, false);
        event4 = new FittsTrackEvent(1450050150149L, 64, -22, false);
        event5 = new FittsTrackEvent(1450050150181L, 73, -22, false);
        event6 = new FittsTrackEvent(1450050150653L, 80, -23, true);
        event7 = new FittsTrackEvent(1450050150718L, 80, -23, false);
        trackEvents3 = new ArrayList<>(Arrays.asList(event1, event2, event3, event4, event5, event6, event7));

        //Trackpath 04
        event1 = new FittsTrackEvent(1450050150821L, 79, -22, false);
        event2 = new FittsTrackEvent(1450050150853L, 42, -1, false);
        event3 = new FittsTrackEvent(1450050150861L, 30, 7, false);
        event4 = new FittsTrackEvent(1450050150893L, -15, 48, false);
        event5 = new FittsTrackEvent(1450050150927L, -38, 73, false);
        event6 = new FittsTrackEvent(1450050151028L, -45, 85, false);
        event7 = new FittsTrackEvent(1450050151085L, -45, 89, false);
        event8 = new FittsTrackEvent(1450050151133L, -48, 90, false);
        event9 = new FittsTrackEvent(1450050151184L, -54, 535, false);
        FittsTrackEvent event10 = new FittsTrackEvent(1450050151189L, -54, 91, true);
        FittsTrackEvent event11 = new FittsTrackEvent(1450050151253L, -54, 91, false);
        trackEvents4 = new ArrayList<>(Arrays.asList(event1, event2, event3, event4, event5, event6, event7, event8, event9, event10, event11));

        //Trackpath 05
        event1 = new FittsTrackEvent(1450050151275L, -53, 90, false);
        event2 = new FittsTrackEvent(1450050151301L, -48, 65, false);
        event3 = new FittsTrackEvent(1450050151355L, -27, -11, false);
        event4 = new FittsTrackEvent(1450050151384L, -18, -61, false);
        event5 = new FittsTrackEvent(1450050151396L, -15, -69, false);
        event6 = new FittsTrackEvent(1450050151425L, -13, -74, false);
        event7 = new FittsTrackEvent(1450050151557L, -10, -84, false);
        event8 = new FittsTrackEvent(1450050151670L, 1, -107, true);
        event9 = new FittsTrackEvent(1450050151756L, 1, -107, false);
        trackEvents5 = new ArrayList<>(Arrays.asList(event1, event2, event3, event4, event5, event6, event7, event8, event9));

        //Trackpath 06
        event1 = new FittsTrackEvent(1450050149077L, 4,	-95, false);
        event2 = new FittsTrackEvent(1450050149122L, 24, -24, false);
        event3 = new FittsTrackEvent(1450050149133L, 35, 3, false);
        event4 = new FittsTrackEvent(1450050149149L, 43, 25, false);
        event5 = new FittsTrackEvent(1450050149173L, 56, 47, false);
        event6 = new FittsTrackEvent(1450050149213L, 65, 62, false);
        event7 = new FittsTrackEvent(1450050149381L, 63, 63, false);
        event8 = new FittsTrackEvent(1450050149470L, 59, 73, true);
        event9 = new FittsTrackEvent(1450050149541L, 59, 73, false);
        trackEvents6 = new ArrayList<>(Arrays.asList(event1, event2, event3, event4, event5, event6, event7, event8, event9));

        //Trackpath 07
        event1 = new FittsTrackEvent(1450050149566L, 58, 70, false);
        event2 = new FittsTrackEvent(1450050149605L, 13, 38, false);
        event3 = new FittsTrackEvent(1450050149629L, -34, 16, false);
        event4 = new FittsTrackEvent(1450050149654L, -61, 0, false);
        event5 = new FittsTrackEvent(1450050149685L, -77, -10, false);
        event6 = new FittsTrackEvent(1450050149709L, -80, -15, false);
        event7 = new FittsTrackEvent(1450050149797L, -83, -17, false);
        event8 = new FittsTrackEvent(1450050149927L, -85, -19, true);
        event9 = new FittsTrackEvent(1450050149989L, -85, -19, false);
        trackEvents7 = new ArrayList<>(Arrays.asList(event1, event2, event3, event4, event5, event6, event7, event8, event9));

        //Trackpath 08
        event1 = new FittsTrackEvent(1450050150029L, -84, -20, false);
        event2 = new FittsTrackEvent(1450050150069L, -49, -22, false);
        event3 = new FittsTrackEvent(1450050150094L, -15, -22, false);
        event4 = new FittsTrackEvent(1450050150149L, 64, -22, false);
        event5 = new FittsTrackEvent(1450050150181L, 73, -22, false);
        event6 = new FittsTrackEvent(1450050150653L, 80, -23, true);
        event7 = new FittsTrackEvent(1450050150718L, 80, -23, false);
        trackEvents8 = new ArrayList<>(Arrays.asList(event1, event2, event3, event4, event5, event6, event7));

        //Trackpath 09
        event1 = new FittsTrackEvent(1450050150821L, 79, -22, false);
        event2 = new FittsTrackEvent(1450050150853L, 42, -1, false);
        event3 = new FittsTrackEvent(1450050150861L, 30, 7, false);
        event4 = new FittsTrackEvent(1450050150893L, -15, 48, false);
        event5 = new FittsTrackEvent(1450050150927L, -38, 73, false);
        event6 = new FittsTrackEvent(1450050151028L, -45, 85, false);
        event7 = new FittsTrackEvent(1450050151085L, -45, 89, false);
        event8 = new FittsTrackEvent(1450050151133L, -48, 90, false);
        event9 = new FittsTrackEvent(1450050151184L, -54, 535, false);
        event10 = new FittsTrackEvent(1450050151189L, -54, 91, true);
        event11 = new FittsTrackEvent(1450050151253L, -54, 91, false);
        trackEvents9 = new ArrayList<>(Arrays.asList(event1, event2, event3, event4, event5, event6, event7, event8, event9, event10, event11));

        //Trackpath 10
        event1 = new FittsTrackEvent(1450050151275L, -53, 90, false);
        event2 = new FittsTrackEvent(1450050151301L, -48, 65, false);
        event3 = new FittsTrackEvent(1450050151355L, -27, -11, false);
        event4 = new FittsTrackEvent(1450050151384L, -18, -61, false);
        event5 = new FittsTrackEvent(1450050151396L, -15, -69, false);
        event6 = new FittsTrackEvent(1450050151425L, -13, -74, false);
        event7 = new FittsTrackEvent(1450050151557L, -10, -84, false);
        event8 = new FittsTrackEvent(1450050151670L, 1, -107, true);
        event9 = new FittsTrackEvent(1450050151756L, 1, -107, false);
        trackEvents10 = new ArrayList<>(Arrays.asList(event1, event2, event3, event4, event5, event6, event7, event8, event9));

        //Trackpaths
        FittsTrackPath trackPath1 = new FittsTrackPath((List<FittsTrackEvent>)trackEvents1);
        FittsTrackPath trackPath2 = new FittsTrackPath((List<FittsTrackEvent>)trackEvents2);
        FittsTrackPath trackPath3 = new FittsTrackPath((List<FittsTrackEvent>)trackEvents3);
        FittsTrackPath trackPath4 = new FittsTrackPath((List<FittsTrackEvent>)trackEvents4);
        FittsTrackPath trackPath5 = new FittsTrackPath((List<FittsTrackEvent>)trackEvents5);
        FittsTrackPath trackPath6 = new FittsTrackPath((List<FittsTrackEvent>)trackEvents6);
        FittsTrackPath trackPath7 = new FittsTrackPath((List<FittsTrackEvent>)trackEvents7);
        FittsTrackPath trackPath8 = new FittsTrackPath((List<FittsTrackEvent>)trackEvents8);
        FittsTrackPath trackPath9 = new FittsTrackPath((List<FittsTrackEvent>)trackEvents9);
        FittsTrackPath trackPath10 = new FittsTrackPath((List<FittsTrackEvent>)trackEvents10);
        trackPaths = new ArrayList<>(Arrays.asList(trackPath1, trackPath2, trackPath3, trackPath4, trackPath5,
                trackPath6, trackPath7, trackPath8, trackPath9, trackPath10));

        //Stages
        FittsStageResult stage1 = new FittsStageResult(0, Arrays.asList(trackPath1, trackPath2, trackPath3, trackPath4, trackPath5));
        FittsStageResult stage2 = new FittsStageResult(1, Arrays.asList(trackPath6, trackPath7, trackPath8, trackPath9, trackPath10));
        stages = new ArrayList<>(Arrays.asList(stage1, stage2));

        //Result
        result = new FittsResult("003", "test01", new Date(), Arrays.asList(stage1, stage2));

        //TestStage
        testStage1 = new FittsTestStage("stage1", 5, 20, 100);
        testStage2 = new FittsTestStage("stage2", 5, 20, 100);

        //TestStages
        testStages = new ArrayList<FittsTestStage>();
        testStages.add(testStage1);
        testStages.add(testStage2);

        //Test
        test = new FittsTest("test01", testStages);
    }

    @Test
    public void CalculateThroughput()
    {
        when(fittsService.findTestById(result.getTestID())).thenReturn(test);
        FittsThroughput fittsThroughput = fittsCalculateService.calculateThroughput(result);
        Double totalThroughput = fittsThroughput.getTotalThroughput();
        assertTrue(totalThroughput >4.0 && totalThroughput<6.0);
    }
}
