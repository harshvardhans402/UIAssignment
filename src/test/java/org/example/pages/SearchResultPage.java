package org.example.pages;

import com.google.common.base.StandardSystemProperty;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;

import java.util.List;

public class SearchResultPage extends BasePage {

    public SearchResultPage(WebDriver driver){
        super(driver);
    }

    public String goToResultIndex(int index){
      List<WebElement> allCards= driver.findElements(By.xpath("//div[@class=\"thumbnails grid row list-inline\"]/div"));

       if(allCards.size()>index) {
           WebElement card=allCards.get(index);
           System.out.println(card.getText());
           WebElement e = card.findElement(By.xpath(".//div[@class='thumbnail']//div[@class='pricetag jumbotron']"));
           String str=getText(e).toLowerCase();
           WebElement productLink=card.findElement(By.xpath(".//div[@class=\"fixed_wrapper\"]//a"));
           String productTitle=getText(productLink);
           if(str.contains("out of stock")) {
               System.out.println("The product is out of stock");
               return productTitle;
           }
           else{
               click(productLink);
               return productTitle;
           }
       }
       else{
           System.out.println("Product not found!");
           System.out.println("The given index is exceeding the result list");
           return null;
       }
    }
}
