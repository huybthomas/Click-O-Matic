package be.uantwerpen.iw.ei.se.selenium.firefox;

import be.uantwerpen.iw.ei.se.ClickOMaticApplication;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.htmlunit.webdriver.MockMvcHtmlUnitDriverBuilder;

/**
 * Created by Thomas on 20/11/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ClickOMaticApplication.class)
@WebAppConfiguration
public class LoginWebDriverTests
{
    @Autowired
    private WebApplicationContext context;

    private WebDriver driver;

    @Before
    public void setup()
    {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        driver = MockMvc.webAppContextSetup(context).build();
    }

    @Test
    public void loginWithUserThomas()
    {
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("thomas.huybrechts");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("test");
        driver.findElement(By.id("login")).click();
    }

    @After
    public void destroy()
    {
        if(driver != null)
        {
            driver.quit();
        }
    }
}
