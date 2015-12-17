package be.uantwerpen.iw.ei.se.controllers;

import be.uantwerpen.iw.ei.se.fittsTest.models.FittsResult;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTest;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTestStage;
import be.uantwerpen.iw.ei.se.models.Permission;
import be.uantwerpen.iw.ei.se.models.Role;
import be.uantwerpen.iw.ei.se.models.User;
import be.uantwerpen.iw.ei.se.services.FittsService;
import be.uantwerpen.iw.ei.se.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by Thomas on 15/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class FittsTestControllerTests
{
    @Mock
    private UserService userService;

    @Mock
    private FittsService fittsService;

    @InjectMocks
    private FittsTestController fittsTestController;

    private MockMvc mockMvc;

    private User principalUser;
    private Iterable<FittsTest> tests;


    @Before
    public void setup()
    {
        principalUser = new User("Test", "User");

        List<FittsTest> testList = new ArrayList<FittsTest>();
        testList.add(new FittsTest("001", new ArrayList<FittsTestStage>(), "TestComment", ""));
        tests = testList;

        principalUser.setTests(testList);

        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(fittsTestController).build();
    }

    @Test
    public void viewFittsTestOverviewTestManagementPageTest() throws Exception
    {
        when(userService.getPrincipalUser()).thenReturn(principalUser);

        for(FittsTest test : principalUser.getTests())
        {
            when(fittsService.findResultsByTestIdForUser(test.getTestID(), principalUser)).thenReturn(new ArrayList<FittsResult>());
        }

        mockMvc.perform(get("/TestPortal")).andExpect(view().name("testPortal/testPortal"));
    }

    @Test
    public void viewExistingFittsTestPageTest() throws Exception
    {
        FittsTest existingTest = tests.iterator().next();

        when(fittsService.findTestById(existingTest.getTestID())).thenReturn(existingTest);
        when(userService.getPrincipalUser()).thenReturn(principalUser);

        mockMvc.perform(get("/TestPortal/" + existingTest.getTestID() + "/")).andExpect(view().name("testPortal/fittsTest")).andExpect(model().attribute("runningTest", existingTest));
    }

    @Test
    public void viewNotExistingFittsTestPageTest() throws Exception
    {
        FittsTest existingTest = tests.iterator().next();

        when(fittsService.findTestById(existingTest.getTestID())).thenReturn(null);

        mockMvc.perform(get("/TestPortal/" + existingTest.getTestID() + "/")).andExpect(view().name("redirect:/TestPortal?errorTestNotFound")).andExpect(model().attributeDoesNotExist("runningTest"));
    }
}
