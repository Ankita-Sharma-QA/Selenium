package com.ankita.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;


 // Page Object for the SauceDemo cart page.
 
public class CartPage {

    private final WebDriver driver;

    private final By cartItemNames = By.className("inventory_item_name");
    private final By checkoutButton = By.id("checkout");
    private final By removeButtons = By.cssSelector("button[data-test^='remove']");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public List<String> getCartItemNames() {
        return driver.findElements(cartItemNames)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public int getCartItemCount() {
        return driver.findElements(cartItemNames).size();
    }

    public void removeFirstItem() {
        List<WebElement> buttons = driver.findElements(removeButtons);
        if (!buttons.isEmpty()) {
            buttons.get(0).click();
        }
    }

    public CheckoutPage goToCheckout() {
        driver.findElement(checkoutButton).click();
        return new CheckoutPage(driver);
    }
}
