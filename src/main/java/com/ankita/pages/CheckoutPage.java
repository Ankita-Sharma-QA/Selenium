package com.ankita.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page Object covering the SauceDemo checkout flow, which spans three
 * screens: information entry, order overview, and confirmation.
 */
public class CheckoutPage {

    private final WebDriver driver;

    // Step One: information
    private final By firstNameField = By.id("first-name");
    private final By lastNameField = By.id("last-name");
    private final By postalCodeField = By.id("postal-code");
    private final By continueButton = By.id("continue");

    // Step Two: overview
    private final By finishButton = By.id("finish");
    private final By totalLabel = By.className("summary_total_label");

    // Step Three: confirmation
    private final By completeHeader = By.className("complete-header");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillShippingInfo(String firstName, String lastName, String postalCode) {
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(postalCodeField).sendKeys(postalCode);
        driver.findElement(continueButton).click();
    }

    public String getOrderTotal() {
        WebElement total = driver.findElement(totalLabel);
        return total.getText().replace("Total: $", "");
    }

    public void finishOrder() {
        driver.findElement(finishButton).click();
    }

    public String getConfirmationMessage() {
        WebElement header = driver.findElement(completeHeader);
        return header.getText();
    }
}
