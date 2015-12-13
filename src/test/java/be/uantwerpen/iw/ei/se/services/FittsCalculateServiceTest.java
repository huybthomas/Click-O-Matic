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

/**
 * Created by dries on 13/12/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class FittsCalculateServiceTest {

    @InjectMocks
    private FittsCalculateService fittsCalculateService;

    @Mock
    private FittsService fittsService;

    @Mock
    private FittsTrackEventRepository fittsTrackEventRepository;

    @Mock
    private FittsTrackPathRepository fittsTrackPathRepository;

    @Mock
    private FittsStageResultRepository fittsStageResultRepository;

    @Mock
    private FittsResultRepository fittsResultRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @Before
    public void init()
    {
        List<Role> roles;
        Role administrator = new Role("Administrator");

        User u1 = new User("Black", "Panther", "T'Challa", "test");
        roles = new ArrayList<Role>();
        roles.add(administrator);
        u1.setRoles(roles);

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
        Iterable trackEvents1 = new ArrayList<>(Arrays.asList(event1, event2, event3, event4, event5, event6, event7, event8, event9));
        fittsTrackEventRepository.save(trackEvents1);

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
        Iterable trackEvents2 = new ArrayList<>(Arrays.asList(event1, event2, event3, event4, event5, event6, event7, event8, event9));
        fittsTrackEventRepository.save(trackEvents2);

        //Trackpath 03
        event1 = new FittsTrackEvent(1450017882821L, 465, 440, false);
        event2 = new FittsTrackEvent(1450017882940L, 616, 440, false);
        event3 = new FittsTrackEvent(1450017882996L, 672, 440, false);
        event4 = new FittsTrackEvent(1450017883036L, 680, 440, false);
        event5 = new FittsTrackEvent(1450017883228L, 679, 445, true);
        event6 = new FittsTrackEvent(1450017883259L, 678, 445, true);
        event7 = new FittsTrackEvent(1450017883268L, 678, 445, false);
        Iterable trackEvents3 = new ArrayList<>(Arrays.asList(event1, event2, event3, event4, event5, event6, event7));
        fittsTrackEventRepository.save(trackEvents3);

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
        Iterable trackEvents4 = new ArrayList<>(Arrays.asList(event1, event2, event3, event4, event5, event6, event7, event8, event9, event10, event11));
        fittsTrackEventRepository.save(trackEvents4);

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
        Iterable trackEvents5 = new ArrayList<>(Arrays.asList(event1, event2, event3, event4, event5, event6, event7, event8, event9));
        fittsTrackEventRepository.save(trackEvents5);

        //Trackpaths
        FittsTrackPath trackPath1 = new FittsTrackPath((List<FittsTrackEvent>)trackEvents1);
        FittsTrackPath trackPath2 = new FittsTrackPath((List<FittsTrackEvent>)trackEvents2);
        FittsTrackPath trackPath3 = new FittsTrackPath((List<FittsTrackEvent>)trackEvents3);
        FittsTrackPath trackPath4 = new FittsTrackPath((List<FittsTrackEvent>)trackEvents4);
        FittsTrackPath trackPath5 = new FittsTrackPath((List<FittsTrackEvent>)trackEvents5);
        Iterable trackPaths = new ArrayList<>(Arrays.asList(trackPath1, trackPath2, trackPath3, trackPath4, trackPath5));
        fittsTrackPathRepository.save(trackPaths);

        //Stages
        FittsStageResult stage1 = new FittsStageResult(0, Arrays.asList(trackPath1, trackPath2, trackPath3, trackPath4, trackPath5));
        Iterable stages = new ArrayList<>(Arrays.asList(stage1));
        fittsStageResultRepository.save(stages);

        //Result
        FittsResult result = new FittsResult("test01", "003", new Date(), Arrays.asList(stage1));
        fittsResultRepository.save(result);

        u1 = userRepository.findByUserName("T'Challa");
        u1.setResults(Arrays.asList(result));
        userRepository.save(u1);
    }

    @Test
    public void CalculateThroughput()
    {
        FittsThroughput fittsThroughput = fittsCalculateService.calculateThroughput(userRepository.findByUserName("T'Challa").getResults().get(0));
        assertTrue(fittsThroughput.getTotalThroughput() >4.5 && fittsThroughput.getTotalThroughput()<5.5);
    }
}
