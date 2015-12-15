package be.uantwerpen.iw.ei.se.formatters;

import be.uantwerpen.iw.ei.se.ClickOMaticApplication;
import be.uantwerpen.iw.ei.se.configurations.SystemPropertyActiveProfileResolver;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTestStage;
import be.uantwerpen.iw.ei.se.repositories.FittsTestStageRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.ParseException;
import java.util.Locale;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Thomas on 13/12/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ClickOMaticApplication.class)
@ActiveProfiles(profiles = {"dev"}, resolver = SystemPropertyActiveProfileResolver.class)
@WebAppConfiguration
public class FittsTestStageFormatterTests
{
    @Autowired
    private FittsTestStageFormatter fittsTestStageFormatter;

    @Autowired
    private FittsTestStageRepository fittsTestStageRepository;

    private FittsTestStage testStage;
    private Locale locale;

    @Before
    public void init()
    {
        testStage = new FittsTestStage("StageFormatter", 13, 4, 94);

        fittsTestStageRepository.save(testStage);

        locale = new Locale("default");
    }

    @Test
    public void parserTest() throws ParseException
    {
        FittsTestStage parserTestStage = fittsTestStageFormatter.parse(testStage.getId().toString(), locale);

        assertTrue(parserTestStage.equals(testStage));
    }

    @Test
    public void toStringTest()
    {
        String parsedString = fittsTestStageFormatter.print(testStage, locale);

        assertTrue(parsedString.equals(testStage.getId().toString()));
    }
}
