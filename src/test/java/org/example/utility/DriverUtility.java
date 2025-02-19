package org.example.utility;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.io.File;
import java.sql.DriverManager;
import java.time.Duration;

public class DriverUtility {
    private static WebDriver driver;

    public static WebDriver getDriver(String browserName){


        if(driver==null){
            if(browserName.equalsIgnoreCase("chrome")) {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            }
            else if(browserName.equalsIgnoreCase("edge")){
                WebDriverManager.edgedriver().setup();
                driver=new EdgeDriver();
            }
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            driver.manage().window().maximize();
        }
        return driver;
    }

    public static void quitDriver(){
        if(driver!=null){
            driver.quit();
            driver=null;
        }
    }


    public static void takeSnapShot(WebDriver webdriver,String fileWithPath) {
    //Convert web driver object to TakeScreenshot
       try {
           TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
           //Call getScreenshotAs method to create image file
           File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
           //Move image file to new destination
           File DestFile = new File(fileWithPath);
           //Copy file at destination
           FileUtils.copyFile(SrcFile, DestFile);
       }
       catch(Exception e){
           System.out.println(e.getMessage());

    }
}
}
