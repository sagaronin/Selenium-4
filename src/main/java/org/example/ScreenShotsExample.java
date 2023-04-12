package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class ScreenShotsExample {
    WebDriver driver;

    @BeforeMethod
    public void setup(){
        //System.setProperty("webdriver.chrome.driver","S:\\Projects\\Selenium4Project\\drivers\\chromedriver.exe")
        //we can skip above line completely to set the property for chromedriver executable
        //if we use below webdriver manager setup method for chrome.
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("https://qbank.accelq.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

    }

    @AfterMethod
    public void tearDown(){
        //close() closes only the current window on which Selenium is running automated tests.
        // The WebDriver session, however, remains active
        //driver.close();
        //driver. quit() method closes all browser windows and ends the WebDriver session
        //driver.quit();
    }

    @Test
    public void GetElementScreenshot() throws IOException {
        //waiting for element to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("qb-hero-illustration")));

        WebElement heroImage = driver.findElement(new By.ByClassName("qb-hero-illustration"));
        //Now we have a built-in method "getScreenshotAs" in WebElement interface
        //which takes screenshot of particular web element
        //Note: This method works in all browsers
        File source = heroImage.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(source,new File("Qbank Hero banner Image.png"));
    }

    @Test
    public void GetFullScreenshot() throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("qb-hero-illustration")));

        //Now we have a built-in method "getScreenshotAs" in WebDriver interface
        //which takes screenshot of entire page without us having to scroll
        //earlier only visible page screenshot was taken
        //Note: This method only works in Firefox browser
        File source = ((FirefoxDriver)driver).getFullPageScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(source,new File("Full Screen.png"));
    }
}
