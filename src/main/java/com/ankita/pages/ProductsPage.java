package com.ankita.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Page Object for the SauceDemo product inventory page shown after login.
 */
public class ProductsPage {

    private final WebDriver driver;

    private final By pageTitle = By.className("title");
    private final By productNames = By.className("inventory_item_name");
    private final By productPrices = By.className("inventory_item_price");
    private final By sortDropdown = By.className("product_sort_container");
    private final By addToCartButtons = By.cssSelector("button[data-test^='add-to-cart']");
    private final By cartBadge = By.className("shopping_cart_badge");
    private final By cartIcon = By.className("shopping_cart_link");

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isProductsPageDisplayed() {
        return driver.findElement(pageTitle).getText().equalsIgnoreCase("Products");
    }

    public List<String> getAllProductNames() {
        return driver.findElements(productNames)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<Double> getAllProductPrices() {
        return driver.findElements(productPrices)
                .stream()
                .map(el -> Double.parseDouble(el.getText().replace("$", "")))
                .collect(Collectors.toList());
    }

    /**
     * Sorts products using the on-page dropdown.
     * Valid values on SauceDemo: "az", "za", "lohi", "hilo"
     */
    public void sortBy(String value) {
        Select select = new Select(driver.findElement(sortDropdown));
        select.selectByValue(value);
    }

    public void addFirstNProductsToCart(int n) {
        List<WebElement> buttons = driver.findElements(addToCartButtons);
        for (int i = 0; i < n && i < buttons.size(); i++) {
            buttons.get(i).click();
        }
    }

    public int getCartItemCount() {
        try {
            return Integer.parseInt(driver.findElement(cartBadge).getText());
        } catch (Exception e) {
            return 0;
        }
    }

    public CartPage goToCart() {
        driver.findElement(cartIcon).click();
        return new CartPage(driver);
    }
}
