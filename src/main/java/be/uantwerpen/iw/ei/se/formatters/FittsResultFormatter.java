package be.uantwerpen.iw.ei.se.formatters;

import be.uantwerpen.iw.ei.se.fittsTest.models.FittsResult;
import be.uantwerpen.iw.ei.se.repositories.FittsResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

/**
 * Created by Thomas on 03/12/2015.
 */
@Component
public class FittsResultFormatter implements Formatter<FittsResult>
{
    @Autowired
    private FittsResultRepository fittsResultRepository;

    public FittsResult parse(final String text, final Locale locale) throws ParseException
    {
        if(text != null && !text.isEmpty())
        {
            return fittsResultRepository.findOne(new Long(text));
        }
        else
        {
            return null;
        }
    }

    public String print(final FittsResult object, final Locale locale)
    {
        return (object != null ? object.getId().toString() : "");
    }
}
