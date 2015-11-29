package be.uantwerpen.iw.ei.se.formatters;

import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTestStage;
import be.uantwerpen.iw.ei.se.repositories.FittsTestStageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

/**
 * Created by Quinten on 29/11/2015.
 */
@Component
public class FittsTestStageFormatter implements Formatter<FittsTestStage>
{
    @Autowired
    private FittsTestStageRepository fittsTestStageRepository;

    public FittsTestStage parse(final String text, final Locale locale) throws ParseException
    {
        if(text != null && !text.isEmpty())
        {
            return fittsTestStageRepository.findOne(new Long(text));
        }
        else
        {
            return null;
        }
    }

    public String print(final FittsTestStage object, final Locale locale)
    {
        return (object != null ? object.getId().toString() : "");
    }
}
