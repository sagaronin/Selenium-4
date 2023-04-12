package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.emulation.Emulation;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class MockGeoLocation {
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
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
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
    public void mockGeoLocation_ExecuteCdpCommand(){
        Map coordinates = new HashMap();
        //Adding corrdinates for Kartavya Path Delhi India
        coordinates.put("latitude",28.614040);
        coordinates.put("longitude",77.206300);
        coordinates.put("accuracy",1);

        //ExecuteCDPCommand method is only available for ChromeDriver and EdgeDriver
        ((ChromeDriver)driver).executeCdpCommand(
                "Emulation.setGeolocationOverride",coordinates);
        driver.get("https://where-am-i.org/");
    }

    @Test
    public void mockGeoLocation_devTools(){
        DevTools devtools =  ((ChromeDriver)driver).getDevTools();
        devtools.createSession();
        //Adding corrdinates for Kartavya Path Delhi India
        devtools.send(Emulation.setGeolocationOverride(
                Optional.of(28.614040),
                Optional.of(77.206300),
                Optional.of(1)
                ));
        driver.get("https://where-am-i.org/");
    }
}
