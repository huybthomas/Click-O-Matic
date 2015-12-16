package be.uantwerpen.iw.ei.se.formatters;

import be.uantwerpen.iw.ei.se.ClickOMaticApplication;
import be.uantwerpen.iw.ei.se.configurations.SystemPropertyActiveProfileResolver;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsResult;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsStageResult;
import be.uantwerpen.iw.ei.se.models.User;
import be.uantwerpen.iw.ei.se.repositories.FittsResultRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Thomas on 13/12/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ClickOMaticApplication.class)
@ActiveProfiles(profiles = {"dev"}, resolver = SystemPropertyActiveProfileResolver.class)
@WebAppConfiguration
public class FittsResultFormatterTests
{
    @Autowired
    private FittsResultFormatter fittsResultFormatter;

    @Autowired
    private FittsResultRepository fittsResultRepository;

    private FittsResult result;
    private Locale locale;

    @Before
    public void init()
    {
        result = new FittsResult("ResultFormatter", "TestID", new Date(), new ArrayList<FittsStageResult>(), new User());

        fittsResultRepository.save(result);

        locale = new Locale("default");
    }

    @Test
    @Transactional
    public void parserTest() throws ParseException
    {
        FittsResult parserFittsResult = fittsResultFormatter.parse(result.getId().toString(), locale);

        assertTrue(parserFittsResult.equals(result));
    }

    @Test
    @Transactional
    public void toStringTest()
    {
        String parsedString = fittsResultFormatter.print(result, locale);

        assertTrue(parsedString.equals(result.getId().toString()));
    }
}
