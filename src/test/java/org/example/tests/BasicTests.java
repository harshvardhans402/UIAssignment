package org.example.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.example.pages.*;
import org.example.utility.DriverUtility;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Set;

public class BasicTests {
    WebDriver driver;
    HomePage homePage;
    LoginPage loginPage;
    SearchResultPage searchResultPage;
    ProductPage productPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;
    WebDriverWait wait;

    // ExtentReports objects
    private static ExtentReports extent;
    private static ExtentTest test;
    private static ExtentSparkReporter htmlReporter;

    @BeforeClass
    public static void setupReport() {

        htmlReporter = new ExtentSparkReporter("basictest-report.html");
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


    //setting up the driver
    @BeforeClass
    public void setup(){
        System.out.println("Setting up the driver");
        driver= DriverUtility.getDriver("chrome");
        driver.get("https://automationteststore.com/");
        homePage=new HomePage(driver);
        loginPage=new LoginPage(driver);
        searchResultPage=new SearchResultPage(driver);
        productPage=new ProductPage(driver);
        cartPage=new CartPage(driver);
        checkoutPage=new CheckoutPage(driver);
        wait=new WebDriverWait(driver, Duration.ofSeconds(2));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

    }


    @Test
    public void contactUsTest(){

        test.log(Status.INFO,"Getting contact details");
        System.out.println("Fetching contact details");
        homePage.getContactDetials();
    }




    @Test
    public void goToCart(){
        homePage.goToCart();
        String url=driver.getCurrentUrl();
        try {
            Assert.assertEquals(url, "https://automationteststore.com/index.php?rt=checkout/cart");
        }
        catch(AssertionError e){
            test.fail("Test Failed due to Assertion Error: "+e.getMessage());
        }

        }




    @Test
    public void cookiesTest(){

     test.log(Status.INFO,"Collecting cookies");
     Set<Cookie> cookies= driver.manage().getCookies();
     System.out.println("All Cookies");
     test.log(Status.INFO,"Printing all the cookies");

     for(Cookie cookie:cookies){
         System.out.println("Cookie Name: " + cookie.getName());
         System.out.println("Cookie Value: " + cookie.getValue());
     }

        test.log(Status.INFO,"Created new cookie");
        Cookie customCookie = new Cookie("username", "testUser", "automationteststore.com", "/", null);
        driver.manage().addCookie(customCookie);
        test.log(Status.INFO,"Deleting all cookies");


        driver.manage().deleteAllCookies();
        cookies= driver.manage().getCookies();
        test.log(Status.INFO,"Verifying deleted cookies");

        Assert.assertTrue(cookies.isEmpty());
        test.log(Status.INFO,"Cookies deleted");


    }




    @Test
    public void checkProductResult(){

        test.log(Status.INFO,"Going to Login Page");
        System.out.println("Going to Login page");
        homePage.goToLogin(); //Go To Login Page
        test.log(Status.INFO,"Logging In");
        System.out.println("Logging In");
        loginPage.login("test1user","testuser"); // Login
        test.log(Status.INFO,"LoggedIn");
        System.out.println("LoggedIn");


        test.log(Status.INFO,"Searching Product");
        System.out.println("Searching Product ");
        homePage.searchProduct("t-shirt"); // Search product from the search bar

        String productTitle= searchResultPage.goToResultIndex(1);// go to specific product
        test.log(Status.INFO,"Clicked on a product link");
        System.out.println("Clicked on a product link");

        String addedProductTitle=productPage.addToCart();// add product to cart
        try {
            Assert.assertTrue(productTitle.equalsIgnoreCase(addedProductTitle), "Visited wrong product");
            test.log(Status.INFO, "Clicked on Add to cart Button");
            System.out.println("Clicked on Add to cart button");
        }
        catch(AssertionError e){
            test.fail("test failed due to Assertion Error: "+e.getMessage());
        }

        String cartProducts=cartPage.checkOutCart();//go to cart and checkout
        try {
            Assert.assertTrue(cartProducts.toLowerCase().contains(productTitle.toLowerCase()), "the product is not in the cart");
            test.log(Status.INFO, "verified product in the list and checked out");
            System.out.println("verified product in the list and checked out ");
        }
        catch(AssertionError e){
            test.fail("test failed due to Assertion Error: "+e.getMessage());
        }

        checkoutPage.confirmOrder();// confirm the order
        String successUrl="https://automationteststore.com/index.php?rt=checkout/success";
        wait.until(ExpectedConditions.urlToBe(successUrl));
        try {
            Assert.assertEquals(successUrl, driver.getCurrentUrl());// assert the url of success of order placement
            System.out.println("Order Placed");
        }
        catch(AssertionError e){
            test.fail("test failed due to Assertion Error: "+e.getMessage());
        }
        homePage.logOut();// logout

        System.out.println("Logged Out");
    }
}
