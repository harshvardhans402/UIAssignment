package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProductPage extends BasePage {

    public ProductPage(WebDriver driver){
        super(driver);
    }

    public String addToCart(){
        String productTtile=getText(driver.findElement(By.xpath("//h1")));
        List< WebElement> addToCartButton=driver.findElements(By.xpath("//div//ul[@class=\"productpagecart\"]//a"));
        if(!addToCartButton.isEmpty()){
            click(addToCartButton.get(0));
        }
        else{
            System.out.println("This product is OUT OF STOCK!");
        }
        return productTtile;
    }

}
