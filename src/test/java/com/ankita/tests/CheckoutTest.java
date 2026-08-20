package com.ankita.tests;

import com.ankita.base.BaseTest;
import com.ankita.pages.*;
import com.ankita.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * End-to-end test covering the full purchase journey:
 * login -> add to cart -> checkout -> order confirmation.
 * This is the kind of regression-critical flow that's typically
 * automated first in an e-commerce style application.
 */
public class CheckoutTest extends BaseTest {

    @Test(description = "User should be able to complete a full purchase from login through order confirmation")
    public void testEndToEndCheckoutFlow() {
        // Step 1: Login
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = loginPage.loginAs(
                ConfigReader.get("valid.username"),
                ConfigReader.get("valid.password"));

        // Step 2: Add products and go to cart
        productsPage.addFirstNProductsToCart(2);
        CartPage cartPage = productsPage.goToCart();
        Assert.assertEquals(cartPage.getCartItemCount(), 2, "Cart did not contain the expected number of items");

        // Step 3: Proceed to checkout and fill shipping info
        CheckoutPage checkoutPage = cartPage.goToCheckout();
        checkoutPage.fillShippingInfo("Ankita", "Sharma", "201301");

        // Step 4: Verify order total is populated, then complete the order
        String total = checkoutPage.getOrderTotal();
        Assert.assertFalse(total.isEmpty(), "Order total was not displayed on the overview screen");

        checkoutPage.finishOrder();

        // Step 5: Verify order confirmation
        String confirmationMessage = checkoutPage.getConfirmationMessage();
        Assert.assertEquals(confirmationMessage, "Thank you for your order!",
                "Order confirmation message did not match expected text");
    }
}
