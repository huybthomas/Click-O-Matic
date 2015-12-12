package be.uantwerpen.iw.ei.se.formatters;

import be.uantwerpen.iw.ei.se.ClickOMaticApplication;
import be.uantwerpen.iw.ei.se.configurations.SystemPropertyActiveProfileResolver;
import be.uantwerpen.iw.ei.se.models.Role;
import be.uantwerpen.iw.ei.se.repositories.RoleRepository;
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
 * Created by Thomas on 09/11/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ClickOMaticApplication.class)
@ActiveProfiles(profiles = {"dev"}, resolver = SystemPropertyActiveProfileResolver.class)
@WebAppConfiguration
public class RoleFormatterTests
{
    @Autowired
    private RoleFormatter roleFormatter;

    @Autowired
    private RoleRepository roleRepository;

    private Role role;
    private Locale locale;

    @Before
    public void init()
    {
        role = new Role("formatterTest");

        roleRepository.save(role);

        locale = new Locale("default");
    }

    @Test
    public void parserTest() throws ParseException
    {
        Role parserRole = roleFormatter.parse(role.getId().toString(), locale);

        assertTrue(parserRole.equals(role));
    }

    @Test
    public void toStringTest()
    {
        String parsedString = roleFormatter.print(role, locale);

        assertTrue(parsedString.equals(role.getId().toString()));
    }
}
