package be.uantwerpen.iw.ei.se.formatters;

import be.uantwerpen.iw.ei.se.ClickOMaticApplication;
import be.uantwerpen.iw.ei.se.models.Permission;
import be.uantwerpen.iw.ei.se.repositories.PermissionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.ParseException;
import java.util.Locale;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Thomas on 02/11/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ClickOMaticApplication.class)
@WebAppConfiguration
public class PermissionFormatterTests
{
    @Autowired
    PermissionFormatter permissionFormatter;

    @Autowired
    PermissionRepository permissionRepository;

    Permission permission;
    Locale locale;

    @Before
    public void init()
    {
        permission = new Permission("formatterTest");

        permissionRepository.save(permission);

        locale = new Locale("default");
    }

    @Test
    public void parserTest() throws ParseException
    {
        Permission parserPermission = new Permission();

        parserPermission = permissionFormatter.parse(permission.getId().toString(), locale);

        assertTrue(parserPermission.equals(permission));
    }

    @Test
    public void toStringTest()
    {
        String parsedString = permissionFormatter.print(permission, locale);

        assertTrue(parsedString.equals(permission.getId().toString()));
    }
}
