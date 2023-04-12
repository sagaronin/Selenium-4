package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class RectangleExample {

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
    public void getRectangleOfWebElementExample(){
        WebElement logo = driver.findElement(new By.ByClassName("qb-logo"));
        //In selenium 4 version we can see new class Rectangle which can give us
        // web elements x, y coordinate along with width and height
        // Earlier we had to use below two methods to return above mentioned things
        logo.getLocation();  // returns X and Y coordinate in Point class
        logo.getSize(); // returns Height and Width in Dimension class

        Rectangle rectLogo = logo.getRect();
        System.out.println("X: "+rectLogo.getX());
        System.out.println("Y: "+rectLogo.getY());
        System.out.println("Width: "+rectLogo.getWidth());
        System.out.println("Height: "+rectLogo.getHeight());
    }
}
