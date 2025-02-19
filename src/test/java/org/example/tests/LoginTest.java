package org.example.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.example.pages.*;
import org.example.utility.DriverUtility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.time.Duration;

public class LoginTest {

    WebDriver driver;
    WebDriverWait wait;
    LoginPage loginPage;
    HomePage homePage;
    // ExtentReports objects
    private static ExtentReports extent;
    private static ExtentTest test;
    private static ExtentSparkReporter htmlReporter;

    @BeforeClass
    public static void setupReport() {

        htmlReporter = new ExtentSparkReporter("logintest-report.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @BeforeMethod
    public void beforeTest(Method method) {
        test = extent.createTest(method.getName());
    }

    @AfterMethod
    public void afterTest() {
        extent.flush();
    }

    @BeforeClass
    public void setup(){
        System.out.println("Setting up the driver");
        driver= DriverUtility.getDriver("edge");
        driver.get("https://automationteststore.com/");
        homePage=new HomePage(driver);
        loginPage=new LoginPage(driver);

        wait=new WebDriverWait(driver, Duration.ofSeconds(2));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
    }

    @Test
    public void partialInputTest(){
        test.log(Status.INFO,"Going to login page");
        homePage.goToLogin();
        test.log(Status.INFO,"Entering partial credentials");
        loginPage.login("Test1User","");
        test.log(Status.PASS,"Error Message displayed");
    }

    @Test
    public void correctCredentialTest(){
        test.log(Status.INFO,"Goind to login page");
        homePage.goToLogin();
        test.log(Status.INFO,"Entering credentials");
        loginPage.login("Test1User","testuser");
        test.log(Status.PASS,"Logged In Successfully");

        homePage.logOut();
    }

    @Test
    public void wrongCredentialTest(){
        test.log(Status.INFO,"Going to login page");
        homePage.goToLogin();
        test.log(Status.INFO,"Entering wrong credentials");
        loginPage.login("testuser","12");
        test.log(Status.PASS,"Error Message displayed");
    }


    @AfterClass
    public void releaseResources(){

        DriverUtility.quitDriver();
    }


}
