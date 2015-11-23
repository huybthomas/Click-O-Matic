package be.uantwerpen.iw.ei.se.webdriver.testcases;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by Thomas on 23/11/2015.
 */
public class WebDriverTestCases
{
    private static String baseURL;
    private static WebDriver driver;

    public WebDriverTestCases(String baseURL, WebDriver driver)
    {
        this.baseURL = baseURL;
        this.driver = driver;
    }

    public void loginWithCredentialsThomasHuybrechts()
    {
        //Login page
        driver.get(baseURL + "login");

        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("thomas.huybrechts");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("test");
        driver.findElement(By.id("login")).click();

        Assert.assertTrue("Title should start with Click-O-Matic", driver.getTitle().startsWith("Click-O-Matic"));
    }
}
