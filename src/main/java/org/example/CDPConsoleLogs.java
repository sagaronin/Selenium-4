package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.log.Log;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class CDPConsoleLogs {
    WebDriver driver;

    @BeforeMethod
    public void setup(){
        //System.setProperty("webdriver.chrome.driver","S:\\Projects\\Selenium4Project\\drivers\\chromedriver.exe")
        //we can skip above line completely to set the property for chromedriver executable
        //if we use below webdriver manager setup method for chrome.
        ChromeOptions option = new ChromeOptions();
        option.addArguments("--remote-allow-origins=*");
        //above code needs to be added for "java.io.IOException: Invalid Status code=403 text=Forbidden" error
        //WebDriverManager.chromedriver().setup();
        //driver = new ChromeDriver(option);
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
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
    public void getBrowserConsoleLogs(){
        //Get devtools and create session
        //Note: We have to cast driver to ChromeDriver or EdgeDriver because getDevTools()
        // method is only available to these two classes.
        // ChromeDriver and EdgeDriver are child classes of ChromiumDriver in selenium 4.
        //ChromiumDriver implements interface HasDevTools which is where this methods resides.
        //DevTools devTools = ((ChromeDriver)driver).getDevTools();
        DevTools devTools = ((EdgeDriver)driver).getDevTools();

        // Below method helps in creating devtools session with the current window.
        //Since there can be multiple windows hence this method was created.
        //we can create session for particular window by passing window handle.
        devTools.createSession();

        //Enable console logs
        //Send() method allows us to interact with DevTools. It accepts command as input parameter
        // enable() method of Log class enables in listening to logs
        devTools.send(Log.enable());

        //Add a listener for console logs.
        devTools.addListener(Log.entryAdded(),logEntry -> {
            System.out.println("------------------");
            System.out.println("Level: "+logEntry.getLevel());
            System.out.println("Text: "+logEntry.getText());
            System.out.println("Broken URL: "+logEntry.getUrl());
        });

        //load the URL in browser.
        driver.get("https://the-internet.herokuapp.com/broken_images");
    }
}
