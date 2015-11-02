package be.uantwerpen.iw.ei.se.formatters;

import be.uantwerpen.iw.ei.se.models.Permission;
import be.uantwerpen.iw.ei.se.repositories.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

/**
 * Created by Thomas on 30/10/2015.
 */
@Component
public class PermissionFormatter implements Formatter<Permission>
{
    @Autowired
    private PermissionRepository permissionRepository;

    public Permission parse(final String text, final Locale locale) throws ParseException
    {
        if(text != null && !text.isEmpty())
        {
            return permissionRepository.findOne(new Long(text));
        }
        else
        {
            return null;
        }
    }

    public String print(final Permission object, final Locale locale)
    {
        return (object != null ? object.getId().toString() : "");
    }
}
