package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends BasePage {

    public CartPage(WebDriver driver){
        super(driver);

    }

    public String checkOutCart(){
      WebElement all_products=driver.findElement(By.xpath("//form[@id='cart']//table"))  ;

      List<WebElement> cartCheck=driver.findElements(By.xpath("//*[@id=\"maincontainer\"]/div/div/div/div/text()"));
      String products=getText(all_products);
      if(cartCheck.isEmpty()){
        List<WebElement> checkout=driver.findElements(By.xpath("//div//a[@title=\"Checkout\"]"));
        click(checkout.get(1));
        return products;
      }
      else{
          System.out.println("Cart is Empty!");
          return null;
      }


    }
}
