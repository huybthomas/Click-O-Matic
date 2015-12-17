package be.uantwerpen.iw.ei.se.services;

import be.uantwerpen.iw.ei.se.fittsTest.models.FittsResult;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsStageResult;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTest;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTestStage;
import be.uantwerpen.iw.ei.se.models.User;
import be.uantwerpen.iw.ei.se.repositories.FittsTestRepository;
import be.uantwerpen.iw.ei.se.repositories.FittsTestStageRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by Thomas on 13/12/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class FittsServiceTests
{
    @InjectMocks
    private FittsService fittsService;

    @Mock
    private FittsTestRepository fittsTestRepository;

    @Mock
    private FittsTestStageRepository fittsTestStageRepository;

    @Mock
    private FittsResultService fittsResultService;

    private List<FittsTest> tests;
    private List<FittsResult> results;
    private User testUser;

    @Before
    public void init()
    {
        tests = new ArrayList<FittsTest>();
        results = new ArrayList<FittsResult>();

        FittsTest fittsTest1 = new FittsTest("test001", new ArrayList<FittsTestStage>());
        fittsTest1.setId(01L);
        FittsTest fittsTest2 = new FittsTest("test002", new ArrayList<FittsTestStage>());
        fittsTest2.setId(02L);
        FittsTest fittsTest3 = new FittsTest("test003", new ArrayList<FittsTestStage>());
        fittsTest3.setId(03L);
        FittsTest fittsTest4 = new FittsTest("test004", new ArrayList<FittsTestStage>());
        fittsTest4.setId(04L);

        tests.add(fittsTest1);
        tests.add(fittsTest2);
        tests.add(fittsTest3);
        tests.add(fittsTest4);

        FittsResult fittsResult1 = new FittsResult("result001", "test001", new Date(), new ArrayList<FittsStageResult>());
        fittsResult1.setId(01L);
        FittsResult fittsResult2 = new FittsResult("result002", "test001", new Date(), new ArrayList<FittsStageResult>());
        fittsResult2.setId(02L);
        FittsResult fittsResult3 = new FittsResult("result003", "test002", new Date(), new ArrayList<FittsStageResult>());
        fittsResult3.setId(03L);
        FittsResult fittsResult4 = new FittsResult("result004", "test003", new Date(), new ArrayList<FittsStageResult>());
        fittsResult4.setId(04L);

        results.add(fittsResult1);
        results.add(fittsResult2);
        results.add(fittsResult3);
        results.add(fittsResult4);

        testUser = new User("Tester", "@ClickOMatic", "tester", "test");
        testUser.setTests(Arrays.asList(tests.get(0), tests.get(1), tests.get(2)));
        testUser.setResults(Arrays.asList(results.get(0), results.get(2)));

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addNewTestTest()
    {
        FittsTest test = new FittsTest("test005", new ArrayList<FittsTestStage>());
        test.setId(05L);

        when(fittsTestRepository.findAll()).thenReturn(tests);
        when(fittsTestRepository.save(test)).thenReturn(test);
        when(fittsTestStageRepository.save(test.getTestStages())).thenReturn(test.getTestStages());

        assertTrue(fittsService.addTest(test));
    }

    @Test
    public void addExistingTestTest()
    {
        when(fittsTestRepository.findAll()).thenReturn(tests);
        when(fittsTestRepository.save(tests.get(0))).thenReturn(tests.get(0));
        when(fittsTestStageRepository.save(tests.get(0).getTestStages())).thenReturn(tests.get(0).getTestStages());

        assertTrue(fittsService.addTest(tests.get(0)));
    }

    @Test
    public void addTestWithDuplicatedIdTest()
    {
        FittsTest test = new FittsTest("test001", new ArrayList<FittsTestStage>());
        test.setId(13L);

        when(fittsTestRepository.findAll()).thenReturn(tests);
        when(fittsTestRepository.save(test)).thenReturn(test);
        when(fittsTestStageRepository.save(test.getTestStages())).thenReturn(test.getTestStages());

        assertTrue(!fittsService.addTest(test));
    }

    @Test
    public void addNewResultWithExistingTestTest()
    {
        FittsResult result = new FittsResult("result005", "test001", new Date(), new ArrayList<FittsStageResult>());
        result.setId(05L);

        when(fittsTestRepository.findByTestID(result.getTestID())).thenReturn(tests.get(0));

        assertTrue(fittsService.saveTestResult(result));
    }

    @Test
    public void addNewResultWithNonExistingTestTest()
    {
        FittsResult result = new FittsResult("result006", "test099", new Date(), new ArrayList<FittsStageResult>());
        result.setId(99L);

        when(fittsTestRepository.findByTestID(result.getTestID())).thenReturn(null);

        assertTrue(!fittsService.saveTestResult(result));
    }

    @Test
    public void findTestsByCompleteStateTrueForUserTest()
    {
        List<FittsTest> completedTestsByUser = new ArrayList<FittsTest>();
        completedTestsByUser.add(tests.get(0));        //Test001
        completedTestsByUser.add(tests.get(1));        //Test002

        when(fittsResultService.findByTestIDForUser(testUser.getTests().get(0).getTestID(), testUser)).thenReturn(Arrays.asList(testUser.getResults().get(0)));
        when(fittsResultService.findByTestIDForUser(testUser.getTests().get(1).getTestID(), testUser)).thenReturn(Arrays.asList(testUser.getResults().get(1)));
        when(fittsResultService.findByTestIDForUser(testUser.getTests().get(2).getTestID(), testUser)).thenReturn(new ArrayList<FittsResult>());

        assertTrue(fittsService.findTestsByCompleteStateForUser(true, testUser).equals(completedTestsByUser));
    }

    @Test
    public void findTestsByCompleteStateFalseForUserTest()
    {
        List<FittsTest> notCompletedTestsByUser = new ArrayList<FittsTest>();
        notCompletedTestsByUser.add(tests.get(2));        //Test003

        when(fittsResultService.findByTestIDForUser(testUser.getTests().get(0).getTestID(), testUser)).thenReturn(Arrays.asList(testUser.getResults().get(0)));
        when(fittsResultService.findByTestIDForUser(testUser.getTests().get(1).getTestID(), testUser)).thenReturn(Arrays.asList(testUser.getResults().get(1)));
        when(fittsResultService.findByTestIDForUser(testUser.getTests().get(2).getTestID(), testUser)).thenReturn(new ArrayList<FittsResult>());

        assertTrue(fittsService.findTestsByCompleteStateForUser(false, testUser).equals(notCompletedTestsByUser));
    }

    @Test
    public void saveNotExistingTestTest()
    {
        FittsTest test = new FittsTest("test006", new ArrayList<FittsTestStage>());
        test.setId(06L);

        when(fittsTestRepository.findAll()).thenReturn(tests);
        when(fittsTestRepository.save(test)).thenReturn(test);
        when(fittsTestStageRepository.save(test.getTestStages())).thenReturn(test.getTestStages());

        assertTrue(!fittsService.saveTest(test));
    }

    @Test
    public void saveExistingTestTest()
    {
        when(fittsTestRepository.findAll()).thenReturn(tests);
        when(fittsTestRepository.save(tests.get(0))).thenReturn(tests.get(0));
        when(fittsTestStageRepository.save(tests.get(0).getTestStages())).thenReturn(tests.get(0).getTestStages());

        assertTrue(fittsService.saveTest(tests.get(0)));
    }

    @Test
    public void testIdAlreadyExistsTest()
    {
        when(fittsTestRepository.findAll()).thenReturn(tests);

        String existingTestId = new String("test001");

        assertTrue(fittsService.testIdAlreadyExists(existingTestId));
    }

    @Test
    public void testIdNotExistsTest()
    {
        when(fittsTestRepository.findAll()).thenReturn(tests);

        String nonExistingTestId = new String("newID");

        assertTrue(!fittsService.testIdAlreadyExists(nonExistingTestId));
    }
}
