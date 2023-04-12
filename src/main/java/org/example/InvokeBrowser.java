package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.By.*;


/**
 * Hello world!
 *
 */
public class InvokeBrowser
{
    WebDriver driver;

    @BeforeMethod
    public void setup(){
        //System.setProperty("webdriver.chrome.driver","S:\\Projects\\Selenium4Project\\drivers\\chromedriver.exe")
        //we can skip above line completely to set the property for chromedriver executable
        //if we use below webdriver manager setup method for chrome.
        ChromeOptions option = new ChromeOptions();
        option.addArguments("--remote-allow-origins=*");
        //above code needs to be added for "java.io.IOException: Invalid Status code=403 text=Forbidden" error
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(option);
        driver.manage().window().maximize();
        driver.get("https://qbank.accelq.com/");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @AfterMethod
    public void tearDown(){
        //close() closes only the current window on which Selenium is running automated tests.
        // The WebDriver session, however, remains active
        //driver.close();
        //driver. quit() method closes all browser windows and ends the WebDriver session
        driver.quit();
    }

    @Test
    public void AssertLogoPresent(){
        WebElement logo;

        logo = driver.findElement(new ByClassName("qb-logo"));
        boolean isPresent = logo.isDisplayed();
        Assert.assertTrue(isPresent,"Logo is not present");
    }
}
