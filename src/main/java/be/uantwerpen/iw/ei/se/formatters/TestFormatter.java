package be.uantwerpen.iw.ei.se.formatters;

import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTest;
import be.uantwerpen.iw.ei.se.models.User;
import be.uantwerpen.iw.ei.se.repositories.FittsTestRepository;
import be.uantwerpen.iw.ei.se.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

/**
 * Created by dries on 28/11/2015.
 */
@Component
public class TestFormatter implements Formatter<FittsTest> {
    @Autowired
    private FittsTestRepository fittsTestRepository;

    public FittsTest parse(final String text, final Locale locale) throws ParseException
    {
        if(text != null && !text.isEmpty())
        {
            return fittsTestRepository.findOne(new Long(text));
        }
        else
        {
            return null;
        }
    }

    public String print(final FittsTest object, final Locale locale)
    {
        return (object != null ? object.getId().toString() : "");
    }
}
