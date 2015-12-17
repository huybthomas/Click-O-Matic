package be.uantwerpen.iw.ei.se.services;

import be.uantwerpen.iw.ei.se.fittsTest.models.FittsResult;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsStageResult;
import be.uantwerpen.iw.ei.se.models.User;
import be.uantwerpen.iw.ei.se.repositories.FittsResultRepository;
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
public class FittsResultServiceTests
{
    @InjectMocks
    private FittsResultService fittsResultService;

    @Mock
    private FittsResultRepository fittsResultRepository;

    private List<FittsResult> results;

    @Before
    public void init()
    {
        FittsResult result1 = new FittsResult("result001", "test001", new Date(), new ArrayList<FittsStageResult>());
        FittsResult result2 = new FittsResult("result002", "test003", new Date(), new ArrayList<FittsStageResult>());
        results = new ArrayList<FittsResult>(Arrays.asList(result1, result2));

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void resultIdAlreadyExistsTest()
    {
        when(fittsResultRepository.findAll()).thenReturn(results);

        assertTrue(fittsResultService.resultIdAlreadyExists(results.get(0).getResultID()));
    }

    @Test
    public void resultIdNotExistsTest()
    {
        FittsResult result2 = new FittsResult("result003", "test005", new Date(), new ArrayList<FittsStageResult>());

        when(fittsResultRepository.findAll()).thenReturn(results);

        assertTrue(!fittsResultService.resultIdAlreadyExists(result2.getResultID()));
    }
}
