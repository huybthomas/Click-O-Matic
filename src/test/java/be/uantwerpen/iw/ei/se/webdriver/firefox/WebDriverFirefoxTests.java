package be.uantwerpen.iw.ei.se.webdriver.firefox;

import be.uantwerpen.iw.ei.se.ClickOMaticApplication;
import be.uantwerpen.iw.ei.se.webdriver.testcases.WebDriverTestCases;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by Thomas on 20/11/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ClickOMaticApplication.class, loader = SpringApplicationContextLoader.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@WebAppConfiguration
@IntegrationTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
        baseURL = "http://localhost:1304";
        driver = new FirefoxDriver();
        testCases = new WebDriverTestCases(baseURL, driver);
    }

    @Test
    public void test01_startWebDriverFirefox()
    {
        driver.navigate().to(baseURL);

        Assert.assertTrue("Title should start with Login", driver.getTitle().startsWith("Login"));

        //Webdriver initial test succeeded
        webdriverOK = true;
    }

    @Test
    public void test02_loginWithCredentialsThomasHuybrechts()
    {
        //Integrity test needs to be succeeded
        Assume.assumeTrue(webdriverOK);

        testCases.loginWithCredentialsThomasHuybrechts();
    }

    @Test
    public void test03_createNewUser()
    {
        //Integrity test needs to be succeeded
        Assume.assumeTrue(webdriverOK);

        testCases.createNewUser();
    }

    @Test
    public void test04_editUser()
    {
        //Integrity test needs to be succeeded
        Assume.assumeTrue(webdriverOK);

        testCases.editUser();
    }

    @Test
    public void test05_deleteUser()
    {
        //Integrity test needs to be succeeded
        Assume.assumeTrue(webdriverOK);

        testCases.deleteUser();
    }

    @Test
    public void test06_testCreate()
    {
        //Integrity test needs to be succeeded
        Assume.assumeTrue(webdriverOK);

        testCases.testCreate();
    }

    @Test
    public void test07_assignTest()
    {
        //Integrity test needs to be succeeded
        Assume.assumeTrue(webdriverOK);

        testCases.assignTest();
    }

    @Test
    @Ignore
    public void test08_editTest()
    {
        //Integrity test needs to be succeeded
        Assume.assumeTrue(webdriverOK);

        testCases.editTest();
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
