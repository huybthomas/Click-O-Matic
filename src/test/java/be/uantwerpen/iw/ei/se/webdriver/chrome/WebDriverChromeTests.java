package be.uantwerpen.iw.ei.se.webdriver.chrome;

import be.uantwerpen.iw.ei.se.ClickOMaticApplication;
import be.uantwerpen.iw.ei.se.webdriver.testcases.WebDriverTestCases;
import org.junit.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;

/**
 * Created by Thomas on 23/11/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ClickOMaticApplication.class, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
@IntegrationTest
public class WebDriverChromeTests
{
    //Chromedriver locations
    private static String WINDOWS_DRIVER = "/drivers/chromedriver/windows/chromedriver.exe";
    private static String LINUX_DRIVER = "/drivers/chromedriver/linux/chromedriver";

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
            System.setProperty("webdriver.chrome.driver", ClickOMaticApplication.class.getResource(WINDOWS_DRIVER).getFile());

            osCompatible = true;
        }
        else if(OS.contains("nix") || OS.contains("nux") || OS.contains("aix"))
        {
            //Unix system
            File linuxDriver = new File(ClickOMaticApplication.class.getResource(LINUX_DRIVER).getFile());

            //Set to executable
            if(!linuxDriver.canExecute())
            {
                linuxDriver.setExecutable(true);
            }

            System.setProperty("webdriver.chrome.driver", ClickOMaticApplication.class.getResource(LINUX_DRIVER).getFile());

            osCompatible = true;

        }
        else
        {
            //Other system (no compatible drivers installed)
            osCompatible = false;
        }

        Assume.assumeTrue("OS is not compatible with the Chrome driver! Tests will be cancelled...", osCompatible);

        webdriverOK = false;
        baseURL = "http://localhost:1304/";
        driver = new ChromeDriver();
        testCases = new WebDriverTestCases(baseURL, driver);
    }

    @Test
    public void startWebDriverChrome()
    {
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

   @Test
    public void createNewUser()
    {
        //Integrity test needs to be succeeded
        Assume.assumeTrue(webdriverOK);

        testCases.createNewUser();
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
