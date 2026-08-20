package com.ankita.tests;

import com.ankita.base.BaseTest;
import com.ankita.pages.LoginPage;
import com.ankita.pages.ProductsPage;
import com.ankita.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Functional test suite for the SauceDemo login page.
 * Covers positive login, invalid credentials, and the locked-out-user scenario.
 */
public class LoginTest extends BaseTest {

    @Test(description = "Valid credentials should log the user in and land on the Products page")
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = loginPage.loginAs(
                ConfigReader.get("valid.username"),
                ConfigReader.get("valid.password"));

        Assert.assertTrue(productsPage.isProductsPageDisplayed(),
                "Products page was not displayed after a valid login");
    }

    @Test(description = "Invalid password should show an error and keep the user on the login page")
    public void testInvalidPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(ConfigReader.get("valid.username"));
        loginPage.enterPassword(ConfigReader.get("invalid.password"));
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Expected an error message for invalid password");
        Assert.assertTrue(loginPage.getErrorMessage().contains("do not match"),
                "Error message text did not match expected content");
    }

    @Test(description = "Locked-out user should be blocked from logging in with a clear error message")
    public void testLockedOutUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(ConfigReader.get("locked.username"));
        loginPage.enterPassword(ConfigReader.get("valid.password"));
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Expected an error message for locked-out user");
        Assert.assertTrue(loginPage.getErrorMessage().toLowerCase().contains("locked out"),
                "Error message did not indicate the account was locked out");
    }

    @Test(description = "Blank username and password should not allow login")
    public void testBlankCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Expected an error message for blank credentials");
    }
}
