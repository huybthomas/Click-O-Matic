package be.uantwerpen.iw.ei.se.formatters;

import be.uantwerpen.iw.ei.se.ClickOMaticApplication;
import be.uantwerpen.iw.ei.se.configurations.SystemPropertyActiveProfileResolver;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTest;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTestStage;
import be.uantwerpen.iw.ei.se.repositories.FittsTestRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Thomas on 29/11/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ClickOMaticApplication.class)
@ActiveProfiles(profiles = {"dev"}, resolver = SystemPropertyActiveProfileResolver.class)
@WebAppConfiguration
public class FittsTestFormatterTests
{
    @Autowired
    private FittsTestFormatter fittsTestFormatter;

    @Autowired
    private FittsTestRepository fittsTestRepository;

    private FittsTest test;
    private Locale locale;

    @Before
    public void init()
    {
        test = new FittsTest("TestFormatter", new ArrayList<FittsTestStage>(), "TestComment", "");

        fittsTestRepository.save(test);

        locale = new Locale("default");
    }

    @Test
    public void parserTest() throws ParseException
    {
        FittsTest parserFittsTest = fittsTestFormatter.parse(test.getId().toString(), locale);

        assertTrue(parserFittsTest.equals(test));
    }

    @Test
    public void toStringTest()
    {
        String parsedString = fittsTestFormatter.print(test, locale);

        assertTrue(parsedString.equals(test.getId().toString()));
    }
}
