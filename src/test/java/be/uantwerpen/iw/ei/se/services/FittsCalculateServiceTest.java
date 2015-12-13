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
    private Iterable trackPaths;
    private Iterable stages;
    private FittsResult result;
    private FittsTest test;
    private FittsTestStage testStage;
    private List<FittsTestStage> testStages;

    @Before
    public void init()
    {
        //Trackpath 01
        FittsTrackEvent event1 = new FittsTrackEvent(1450017881937L, 554, 391, false);
        FittsTrackEvent event2 = new FittsTrackEvent(1450017881995L, 570, 408, false);
        FittsTrackEvent event3 = new FittsTrackEvent(1450017882027L, 596, 446, false);
        FittsTrackEvent event4 = new FittsTrackEvent(1450017882059L, 613, 491, false);
        FittsTrackEvent event5 = new FittsTrackEvent(1450017882108L, 621, 538, false);
        FittsTrackEvent event6 = new FittsTrackEvent(1450017882140L, 623, 550, false);
        FittsTrackEvent event7 = new FittsTrackEvent(1450017882212L, 628, 569, false);
        FittsTrackEvent event8 = new FittsTrackEvent(1450017882332L, 632, 574, true);
        FittsTrackEvent event9 = new FittsTrackEvent(1450017882412L, 632, 574, false);
        trackEvents1 = new ArrayList<>(Arrays.asList(event1, event2, event3, event4, event5, event6, event7, event8, event9));

        //Trackpath 02
        event1 = new FittsTrackEvent(1450017882413L, 632, 574, false);
        event2 = new FittsTrackEvent(1450017882492L, 522, 459, false);
        event3 = new FittsTrackEvent(1450017882548L, 492, 450, false);
        event4 = new FittsTrackEvent(1450017882588L, 489, 448, false);
        event5 = new FittsTrackEvent(1450017882636L, 483, 443, false);
        event6 = new FittsTrackEvent(1450017882676L, 470, 440, false);
        event7 = new FittsTrackEvent(1450017882692L, 466, 440, false);
        event8 = new FittsTrackEvent(1450017882748L, 465, 440, true);
        event9 = new FittsTrackEvent(1450017882820L, 465, 440, false);
        trackEvents2 = new ArrayList<>(Arrays.asList(event1, event2, event3, event4, event5, event6, event7, event8, event9));

        //Trackpath 03
        event1 = new FittsTrackEvent(1450017882821L, 465, 440, false);
        event2 = new FittsTrackEvent(1450017882940L, 616, 440, false);
        event3 = new FittsTrackEvent(1450017882996L, 672, 440, false);
        event4 = new FittsTrackEvent(1450017883036L, 680, 440, false);
        event5 = new FittsTrackEvent(1450017883228L, 679, 445, true);
        event6 = new FittsTrackEvent(1450017883259L, 678, 445, true);
        event7 = new FittsTrackEvent(1450017883268L, 678, 445, false);
        trackEvents3 = new ArrayList<>(Arrays.asList(event1, event2, event3, event4, event5, event6, event7));

        //Trackpath 04
        event1 = new FittsTrackEvent(1450017883269L, 678, 445, false);
        event2 = new FittsTrackEvent(1450017883308L, 661, 451, false);
        event3 = new FittsTrackEvent(1450017883339L, 639, 457, false);
        event4 = new FittsTrackEvent(1450017883380L, 613, 478, false);
        event5 = new FittsTrackEvent(1450017883404L, 598, 493, false);
        event6 = new FittsTrackEvent(1450017883436L, 582, 514, false);
        event7 = new FittsTrackEvent(1450017883452L, 575, 522, false);
        event8 = new FittsTrackEvent(1450017883500L, 561, 535, false);
        event9 = new FittsTrackEvent(1450017883620L, 522, 555, false);
        FittsTrackEvent event10 = new FittsTrackEvent(1450017883756L, 511, 563, true);
        FittsTrackEvent event11 = new FittsTrackEvent(1450017883819L, 511, 563, false);
        trackEvents4 = new ArrayList<>(Arrays.asList(event1, event2, event3, event4, event5, event6, event7, event8, event9, event10, event11));

        //Trackpath 05
        event1 = new FittsTrackEvent(1450017883821L, 511, 563, false);
        event2 = new FittsTrackEvent(1450017883852L, 517, 545, false);
        event3 = new FittsTrackEvent(1450017883908L, 555, 462, false);
        event4 = new FittsTrackEvent(1450017883956L, 581, 411, false);
        event5 = new FittsTrackEvent(1450017883980L, 584, 399, false);
        event6 = new FittsTrackEvent(1450017884075L, 586, 382, false);
        event7 = new FittsTrackEvent(1450017884108L, 586, 379, false);
        event8 = new FittsTrackEvent(1450017884188L, 586, 373, true);
        event9 = new FittsTrackEvent(1450017884267L, 586, 373, false);
        trackEvents5 = new ArrayList<>(Arrays.asList(event1, event2, event3, event4, event5, event6, event7, event8, event9));

        //Trackpaths
        FittsTrackPath trackPath1 = new FittsTrackPath((List<FittsTrackEvent>)trackEvents1);
        FittsTrackPath trackPath2 = new FittsTrackPath((List<FittsTrackEvent>)trackEvents2);
        FittsTrackPath trackPath3 = new FittsTrackPath((List<FittsTrackEvent>)trackEvents3);
        FittsTrackPath trackPath4 = new FittsTrackPath((List<FittsTrackEvent>)trackEvents4);
        FittsTrackPath trackPath5 = new FittsTrackPath((List<FittsTrackEvent>)trackEvents5);
        trackPaths = new ArrayList<>(Arrays.asList(trackPath1, trackPath2, trackPath3, trackPath4, trackPath5));

        //Stages
        FittsStageResult stage1 = new FittsStageResult(0, Arrays.asList(trackPath1, trackPath2, trackPath3, trackPath4, trackPath5));
        stages = new ArrayList<>(Arrays.asList(stage1));

        //Result
        result = new FittsResult("003", "test01", new Date(), Arrays.asList(stage1));

        //TestStage
        testStage = new FittsTestStage("stage1", 5, 20, 100);

        //TestStages
        testStages = new ArrayList<FittsTestStage>();
        testStages.add(testStage);

        //Test
        test = new FittsTest("test01", testStages);
    }

    @Test
    public void CalculateThroughput()
    {
        when(fittsService.findTestById(result.getTestID())).thenReturn(test);
        FittsThroughput fittsThroughput = fittsCalculateService.calculateThroughput(result);
        Double totalThroughput = fittsThroughput.getTotalThroughput();
        assertTrue(totalThroughput >4.5 && totalThroughput<5.5);
    }
}
