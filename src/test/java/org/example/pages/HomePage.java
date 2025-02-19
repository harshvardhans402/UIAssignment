package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePage extends BasePage{

    @FindBy(xpath="//input[@id=\"filter_keyword\"]")
    WebElement searchBox;

    @FindBy(xpath="//a[@href='https://automationteststore.com/index.php?rt=checkout/cart' and contains(@class, 'dropdown-toggle')]")
    WebElement cartButton;

    @FindBy(xpath="//div[@class=\"button-in-search\"]")
    WebElement searchButton;

    @FindAll({
            @FindBy(xpath="//ul[@class='contact']//li")
    })
    List<WebElement> contactDetails;

    @FindBy(xpath="//a[contains(text(),\"Contact Us\")]")
    WebElement contactUsLink;


    @FindBy(xpath="//a[contains(text(),\"Login or register\")]")
    WebElement loginRegisterButton;
    public HomePage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver,this);
    }

    public void searchProduct(String item){
        type(searchBox,item);
        click(searchButton);

    }


    public void getContactDetials(){

        System.out.println("Printing Contact Details");
        System.out.println("Phone: "+getText(contactDetails.get(0)));
        System.out.println("Email: "+getText(contactDetails.get(1)));
        click(contactUsLink);
        WebElement address=driver.findElement(By.xpath("//address"));
        System.out.println("Address: "+getText(address));


    }

    public void goToCart(){
        click(cartButton);
    }

    public void goToLogin(){
        click(loginRegisterButton);
    }

    public void logOut(){
        WebElement logout=driver.findElement(By.xpath("//div[@class='info']//li/a[contains(text(),\"Logoff\")]"));
        click(logout);
    }


}
