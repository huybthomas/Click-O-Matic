package be.uantwerpen.iw.ei.se.webdriver.testcases;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Thomas on 23/11/2015.
 */
public class WebDriverTestCases
{
    private static String baseURL;
    private static WebDriver driver;
    private static Wait<WebDriver> wait;

    public WebDriverTestCases(String baseURL, WebDriver driver)
    {
        this.baseURL = baseURL;
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 6);

        //Maximize window for testing
        driver.manage().window().maximize();
    }

    public void loginWithCredentialsThomasHuybrechts()
    {
        //Login page
        driver.get(baseURL + "Login?lang=en");

        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("thomas.huybrechts");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("test");
        driver.findElement(By.id("login")).click();

        //Wait for main portal page to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("homePage")));

        Assert.assertTrue("Title should start with 'Click-O-Matic'. Result: " + driver.getTitle(), driver.getTitle().startsWith("Click-O-Matic"));
    }

    public void createNewUser()
    {
        //Homepage
        driver.get(baseURL + "/");

        //Click Users in navbar
        driver.findElement(By.linkText("Users")).click();

        //Wait for Users page to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Create user")));

        //Click Create user link
        driver.findElement(By.linkText("Create user")).click();

        //Wait for Registration page to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("firstName")));

        //Fill in firstname
        driver.findElement(By.id("firstName")).clear();
        driver.findElement(By.id("firstName")).sendKeys("selenium");

        //Fill in lastname
        driver.findElement(By.id("lastName")).clear();
        driver.findElement(By.id("lastName")).sendKeys("test");

        //Fill in username
        driver.findElement(By.id("userName")).clear();
        driver.findElement(By.id("userName")).sendKeys("test");

        //Fill in password
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("test");

        //Click create button
        driver.findElement(By.id("submit")).click();

        //Wait for reply server with alert
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("alert")));

        Assert.assertTrue("Alert should start with 'New user has been created'. Result: " + driver.findElement(By.id("userAddedAlert")).getText(), driver.findElement(By.id("userAddedAlert")).getText().startsWith("New user has been created"));
    }
    
    public void editUser()
    {
        //Homepage
        driver.get(baseURL + "/");

        //Click Users in navbar
        driver.findElement(By.linkText("Users")).click();

        //Wait for User page to load
        wait.until(ExpectedConditions.titleContains("User settings"));

        //Click on Edit user of User Timothy.Verstraete
        driver.findElement(By.xpath("//tr[4]/td[3]/a/span")).click();

        //Wait for User edit page to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("firstName")));

        //Fill in firstname
        driver.findElement(By.id("firstName")).clear();
        driver.findElement(By.id("firstName")).sendKeys("Tim");

        //Fill in lastname
        driver.findElement(By.id("lastName")).clear();
        driver.findElement(By.id("lastName")).sendKeys("Verstraete");

        //Fill in password
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("test");

        //Click save button
        driver.findElement(By.id("submit")).click();

        //Wait for redirect to User page
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("usersPage")));

        Assert.assertTrue("User Timothy Verstraete should be renamed to Tim Verstraete. Result: " + driver.findElement(By.xpath("//tr[4]/td[1]")).getText(), driver.findElement(By.xpath("//tr[4]/td[1]")).getText().contentEquals("Tim Verstraete"));
    }

    public void deleteUser()
    {
        //Homepage
        driver.get(baseURL + "/");

        //Click Users in navbar
        driver.findElement(By.linkText("Users")).click();

        //Wait for User page to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("usersPage")));

        //Click on Delete user of User Timothy.Verstraete
        driver.findElement(By.xpath("//tr[4]/td[5]/a/span")).click();

        //Wait for redirect to User page
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("alert")));

        Assert.assertFalse("User Timothy.Verstraete should be removed. Result: user timothy available: "  + driver.findElement(By.xpath("//tr[4]/td[2]")).getText().contentEquals("Timothy.Verstraete"), driver.findElement(By.xpath("//tr[4]/td[2]")).getText().contentEquals("Timothy.Verstraete"));
    }

    public void assignTest() {
        driver.get(baseURL + "/");

        driver.findElement(By.xpath("//div[@id='bs-example-navbar-collapse-1']/ul/li[3]/a/span[3]")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("usersPage")));

        driver.findElement(By.xpath("//div[@id='usersPage']/div/div[3]/table/tbody/tr[4]/td[4]/a/span")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("assignTest")));

        Select select = new Select(driver.findElement(By.tagName("select"))); //goes to the first "select" item on the page
        select.deselectAll(); // deselects them all
        select.selectByVisibleText("001"); //  and selects the one with the displayed text of "001"
        driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("usersPage")));


        Assert.assertTrue("Title should start with 'User settings'. Result: " + driver.getTitle(), driver.getTitle().startsWith("User settings"));
    }

    public void testCreate(){
        //Homepage
        driver.get(baseURL + "/");

        driver.findElement(By.xpath("//div[@id='bs-example-navbar-collapse-1']/ul/li[4]/a/span[3]")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("fittsTestCreator")));

        driver.findElement(By.id("AddStage")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("AddStage")));


        driver.findElement(By.id("fittsTestID")).clear();
        driver.findElement(By.id("fittsTestID")).sendKeys("test06");

        driver.findElement(By.cssSelector("input.btn.btn-primary")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("dotDistanceSlider")));

        driver.findElement(By.xpath("//div[@id='bs-example-navbar-collapse-1']/ul/li[4]/a/span[3]")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("createTest")));
    }


}
