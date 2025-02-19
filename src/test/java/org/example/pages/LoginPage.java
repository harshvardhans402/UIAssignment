package org.example.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

public class LoginPage extends BasePage{

    @FindBy(xpath = "//div[ @class=\"alert alert-error alert-danger\" ]")
    WebElement error;

    @FindBy(xpath="//input[@id=\"loginFrm_loginname\"]")
    WebElement nameInput;

    @FindBy(xpath="//input[@id=\"loginFrm_password\"]")
    WebElement password;

    @FindBy(xpath="//button[@type=\"submit\" and @title=\"Login\"]")
    WebElement loginButton;

    public LoginPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver,this);
    }

    public void login(String name, String pass){

        System.out.println("Typing Values");
        type(nameInput,name);
        type(password,pass);
        click(loginButton);

        List<WebElement> e= driver.findElements(By.xpath("//div[ @class=\"alert alert-error alert-danger\" ]"));
        if(!e.isEmpty()){
            System.out.println("Error message displayed");
        }
        else{
            System.out.println("Logged In Successfully");
        }

    }

}
