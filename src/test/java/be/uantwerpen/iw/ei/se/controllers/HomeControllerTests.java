package be.uantwerpen.iw.ei.se.controllers;

import be.uantwerpen.iw.ei.se.services.UserService;
import com.sun.scenario.effect.impl.sw.java.JSWBlend_EXCLUSIONPeer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by Thomas on 20/10/2015.
 */
public class HomeControllerTests
{
    @Mock
    private UserService userService;

    @InjectMocks
    private HomeController homeController;

    private MockMvc mockMvc;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
    }

    @Test
    public void viewHomepageTest() throws Exception
    {
        MockHttpServletRequestBuilder loginCredentials = post("login").param("username", "thomas.huybrechts").param("password", "test");
        mockMvc.perform(loginCredentials);
        mockMvc.perform(get("/homepage")).andExpect(view().name("mainPortal/homepage"));
    }
}