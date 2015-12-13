package be.uantwerpen.iw.ei.se.services;

import be.uantwerpen.iw.ei.se.repositories.FittsTestRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

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

    @Before
    public void init()
    {
        MockitoAnnotations.initMocks(this);
    }
}
