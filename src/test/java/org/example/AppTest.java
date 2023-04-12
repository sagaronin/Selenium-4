package org.example;

import static org.junit.Assert.assertTrue;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import javax.swing.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    WebDriver driver;

    @BeforeMethod
    public void setup(){
        //System.setProperty("webdriver.chrome.driver","S:\\Projects\\Selenium4Project\\drivers\\chromedriver.exe")
        //we can skip above line completely to set the property for chromedriver executable
        //if we use below webdriver manager setup method for chrome.
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://qbank.accelq.com/");
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
    public void AssetTitle(){
        String actualTitle = driver.getTitle();
        JOptionPane.showInputDialog("Title: "+actualTitle);
        String expectedTitle = "";
    }
}
