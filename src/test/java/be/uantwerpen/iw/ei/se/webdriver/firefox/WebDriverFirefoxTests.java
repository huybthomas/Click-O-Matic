package be.uantwerpen.iw.ei.se.webdriver.firefox;

import be.uantwerpen.iw.ei.se.ClickOMaticApplication;
import be.uantwerpen.iw.ei.se.webdriver.testcases.WebDriverTestCases;
import org.junit.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by Thomas on 20/11/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ClickOMaticApplication.class, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
@IntegrationTest
public class WebDriverFirefoxTests
{
    private static WebDriver driver;
    private static boolean webdriverOK;
    private static String baseURL;
    private static WebDriverTestCases testCases;

    @BeforeClass
    public static void setup()
    {
        webdriverOK = false;
        baseURL = "http://localhost:1304/";
        driver = new FirefoxDriver();
        testCases = new WebDriverTestCases(baseURL, driver);
    }

    @Test
    public void startWebDriverFirefox()
    {
        driver.navigate().to("http://localhost:1304");

        Assert.assertTrue("Title should start with Login", driver.getTitle().startsWith("Login"));

        //Webdriver initial test succeeded
        webdriverOK = true;
    }

    @Test
    public void loginWithCredentialsThomasHuybrechts()
    {
        //Integrity test needs to be succeeded
        Assume.assumeTrue(webdriverOK);

        testCases.loginWithCredentialsThomasHuybrechts();
    }
    @Test
    public void createANewUser()
    {
        Assume.assumeTrue(webdriverOK);

        testCases.createANewUser();
    }

    @Test
    public void editUser()
    {
        Assume.assumeTrue(webdriverOK);

        testCases.editUser();
    }

    @AfterClass
    public static void destroy()
    {
        if(driver != null)
        {
            driver.quit();
        }
    }
}
