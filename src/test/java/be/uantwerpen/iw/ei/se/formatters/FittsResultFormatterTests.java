package be.uantwerpen.iw.ei.se.formatters;

import be.uantwerpen.iw.ei.se.ClickOMaticApplication;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsResult;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsStageResult;
import be.uantwerpen.iw.ei.se.repositories.FittsResultRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertTrue;

/**
 * Created by Verstraete on 14/12/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ClickOMaticApplication.class)
@WebAppConfiguration
public class FittsResultFormatterTests {


        @Autowired
        private FittsResultFormatter fittsResultFormatter;

        @Autowired
        private FittsResultRepository fittsResultRepository;

        private FittsResult testresult;
        private Locale locale;

        @Before
        public void init()
        {
            testresult = new FittsResult("TestResultID","TestTestID",new Date(), new ArrayList<FittsStageResult>());

            fittsResultRepository.save(testresult);

            locale = new Locale("default");
        }

        @Test
        public void parserTest() throws ParseException
        {
            FittsResult parserFittsResultTest = fittsResultFormatter.parse(testresult.getId().toString(), locale);

            assertTrue(parserFittsResultTest.equals(testresult));
        }

        @Test
        public void toStringTest()
        {
            String parsedString = fittsResultFormatter.print(testresult, locale);

            assertTrue(parsedString.equals(testresult.getId().toString()));
        }
}
