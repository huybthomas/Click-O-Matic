package be.uantwerpen.iw.ei.se.controllers;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Created by Thomas on 29/11/2015.
 */
public class TestCreationControllerTests
{
    @InjectMocks
    private TestCreationController testCreationController;

    private MockMvc mockMvc;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(testCreationController).build();
    }
}
