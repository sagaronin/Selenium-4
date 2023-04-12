package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.network.Network;
import org.openqa.selenium.devtools.v85.network.model.ConnectionType;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class EmulateNetworkConditions {

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
    public void emulateNetworkSpeed_Slow(){
        DevTools devtools =  ((ChromeDriver)driver).getDevTools();
        devtools.createSession();
        //Adding corrdinates for Kartavya Path Delhi India
        devtools.send(Network.enable(
                Optional.empty(),
        Optional.empty(),
        Optional.empty()
        ));
        devtools.send(Network.emulateNetworkConditions(
                false,
                5,
                5000,
                4000,
                Optional.of(ConnectionType.CELLULAR3G)
        ));
        driver.get("https://imisstheoffice.eu/");
        System.out.println("Title(Enabling slow network): "+driver.getTitle());
    }
    @Test
    public void disableEmulateNetwork(){
        driver.get("https://imisstheoffice.eu/");
        System.out.println("Title(Enabling Normal network): "+driver.getTitle());

    }
}
