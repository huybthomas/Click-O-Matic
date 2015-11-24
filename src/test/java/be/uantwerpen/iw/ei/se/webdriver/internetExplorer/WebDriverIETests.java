package be.uantwerpen.iw.ei.se.webdriver.internetExplorer;

import be.uantwerpen.iw.ei.se.ClickOMaticApplication;
import be.uantwerpen.iw.ei.se.webdriver.testcases.WebDriverTestCases;
import org.junit.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by Thomas on 24/11/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ClickOMaticApplication.class, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
@IntegrationTest
public class WebDriverIETests
{
    //IExplorer driver locations
    private static String WINDOWS_DRIVER = "/drivers/iexplorerdriver/windows/iedriver.exe";

    private static WebDriver driver;
    private static boolean webdriverOK;
    private static boolean osCompatible;
    private static String baseURL;
    private static WebDriverTestCases testCases;

    @BeforeClass
    public static void setup()
    {
        String OS = System.getProperty("os.name").toLowerCase();

        //Determine OS
        if(OS.contains("win"))
        {
            //Windows system
            System.setProperty("webdriver.ie.driver", ClickOMaticApplication.class.getResource(WINDOWS_DRIVER).getFile());

            osCompatible = true;
        }
        else
        {
            //Other system (only Windows systems are supported for IE WebDriver)
            osCompatible = false;
        }

        Assume.assumeTrue("No compatible OS to run tests, only Windows systems are supported for IE WebDriver! Tests will be cancelled...", osCompatible);

        webdriverOK = false;
        baseURL = "http://localhost:1304/";

        //Internet Explorer fix: Protected Mode settings are not the same for all zones
        DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
        ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

        driver = new InternetExplorerDriver(ieCapabilities);
        testCases = new WebDriverTestCases(baseURL, driver);
    }

    @Test
    public void startWebDriverIE()
    {
        //Only Windows systems are compatible with the IE WebDriver
        Assume.assumeTrue(osCompatible);

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

    @AfterClass
    public static void destroy()
    {
        if(driver != null)
        {
            driver.quit();
        }
    }
}
