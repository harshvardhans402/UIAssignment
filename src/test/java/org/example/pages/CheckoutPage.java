package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckoutPage extends BasePage {

    public CheckoutPage(WebDriver driver){
        super(driver);
    }
    public void confirmOrder(){
        WebElement confirmButton=driver.findElement(By.xpath("//button[@id=\"checkout_btn\"]"));
        click(confirmButton);


    }
}
