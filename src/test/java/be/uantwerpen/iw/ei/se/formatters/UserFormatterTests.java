package be.uantwerpen.iw.ei.se.formatters;

import be.uantwerpen.iw.ei.se.ClickOMaticApplication;
import be.uantwerpen.iw.ei.se.configurations.SystemPropertyActiveProfileResolver;
import be.uantwerpen.iw.ei.se.models.User;
import be.uantwerpen.iw.ei.se.repositories.UserRepository;
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
 * Created by Thomas on 29/11/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ClickOMaticApplication.class)
@ActiveProfiles(profiles = {"dev"}, resolver = SystemPropertyActiveProfileResolver.class)
@WebAppConfiguration
public class UserFormatterTests
{
    @Autowired
    private UserFormatter userFormatter;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private Locale locale;

    @Before
    public void init()
    {
        user = new User("Test", "Formatter", "FormatterTest", "test");

        userRepository.save(user);

        locale = new Locale("default");
    }

    @Test
    public void parserTest() throws ParseException
    {
        User parserUser = userFormatter.parse(user.getId().toString(), locale);

        assertTrue(parserUser.equals(user));
    }

    @Test
    public void toStringTest()
    {
        String parsedString = userFormatter.print(user, locale);

        assertTrue(parsedString.equals(user.getId().toString()));
    }
}
