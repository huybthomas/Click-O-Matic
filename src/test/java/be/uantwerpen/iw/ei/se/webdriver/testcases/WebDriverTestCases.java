package be.uantwerpen.iw.ei.se.webdriver.testcases;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

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

        //Wait for main portal page to load
        Wait<WebDriver> wait = new WebDriverWait(driver, 1500);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("mainPortalPage")));

        Assert.assertTrue("Title should start with Click-O-Matic. Result: " + driver.getTitle(), driver.getTitle().startsWith("Click-O-Matic"));
    }
}
