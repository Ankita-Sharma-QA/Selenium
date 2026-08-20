package com.ankita.tests;

import com.ankita.base.BaseTest;
import com.ankita.pages.LoginPage;
import com.ankita.pages.ProductsPage;
import com.ankita.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Functional test suite for the SauceDemo product listing page:
 * sorting behavior and add-to-cart functionality.
 */
public class ProductsTest extends BaseTest {

    private ProductsPage productsPage;

    @BeforeMethod
    public void loginBeforeEachTest() {
        LoginPage loginPage = new LoginPage(driver);
        productsPage = loginPage.loginAs(
                ConfigReader.get("valid.username"),
                ConfigReader.get("valid.password"));
    }

    @Test(description = "Sorting products A to Z should list product names in ascending alphabetical order")
    public void testSortProductsAZ() {
        productsPage.sortBy("az");
        List<String> actual = productsPage.getAllProductNames();

        List<String> expected = new ArrayList<>(actual);
        Collections.sort(expected);

        Assert.assertEquals(actual, expected, "Products were not sorted A to Z correctly");
    }

    @Test(description = "Sorting products by price low to high should list prices in ascending order")
    public void testSortProductsPriceLowToHigh() {
        productsPage.sortBy("lohi");
        List<Double> actual = productsPage.getAllProductPrices();

        List<Double> expected = new ArrayList<>(actual);
        Collections.sort(expected);

        Assert.assertEquals(actual, expected, "Products were not sorted by price low to high correctly");
    }

    @Test(description = "Adding products to cart should update the cart badge count accordingly")
    public void testAddMultipleProductsToCart() {
        productsPage.addFirstNProductsToCart(3);
        Assert.assertEquals(productsPage.getCartItemCount(), 3,
                "Cart badge count did not match the number of items added");
    }

    @Test(description = "Cart page should list the same items that were added from the products page")
    public void testCartReflectsAddedItems() {
        productsPage.addFirstNProductsToCart(2);
        int cartCount = productsPage.goToCart().getCartItemCount();

        Assert.assertEquals(cartCount, 2, "Cart page item count did not match items added");
    }
}
