# Selenium POM Automation Framework

A Selenium WebDriver test automation framework built in **Java**, using the
**Page Object Model (POM)** design pattern, **TestNG** as the test runner, and
**Maven** for dependency management. Tests are written against
[SauceDemo](https://www.saucedemo.com), a public e-commerce demo application,
covering login, product listing/sorting, cart, and the full checkout flow.

## Tech Stack

| Tool | Purpose |
|---|---|
| Java 17 | Programming language |
| Selenium WebDriver 4.x | Browser automation |
| TestNG | Test execution, assertions, suite management |
| Maven | Build & dependency management |
| WebDriverManager | Automatic browser driver binary management (no manual chromedriver setup) |
| Log4j2 | Logging |
| ExtentReports | HTML test reporting |

## Framework Design

This project follows the **Page Object Model**, which separates page-specific
locators and actions (in `pages/`) from test logic and assertions (in
`tests/`). This keeps tests readable and means a UI change only requires
updating one page class instead of every test that touches that page.

```
selenium-pom-framework/
├── pom.xml
├── README.md
├── src/
│   ├── main/
│   │   ├── java/com/ankita/
│   │   │   ├── base/
│   │   │   │   └── BaseTest.java        # WebDriver setup/teardown, browser config
│   │   │   ├── pages/
│   │   │   │   ├── LoginPage.java
│   │   │   │   ├── ProductsPage.java
│   │   │   │   ├── CartPage.java
│   │   │   │   └── CheckoutPage.java
│   │   │   └── utils/
│   │   │       └── ConfigReader.java    # Reads config.properties
│   │   └── resources/
│   │       └── config.properties        # Base URL, browser, credentials, timeouts
│   └── test/
│       ├── java/com/ankita/tests/
│       │   ├── LoginTest.java           # Valid/invalid/locked-out login scenarios
│       │   ├── ProductsTest.java        # Sorting, add-to-cart behavior
│       │   └── CheckoutTest.java        # End-to-end purchase flow
│       └── resources/
│           └── testng.xml               # TestNG suite definition
```

## What's Covered

- **Login**: valid login, invalid password, locked-out user, blank credentials
- **Products page**: sort by name (A–Z), sort by price (low–high), add-to-cart badge count updates
- **Cart**: verifying items added on the products page carry over correctly
- **Checkout**: full end-to-end flow — login → add to cart → fill shipping info → verify order total → confirm order

## How to Run

**Prerequisites:** Java 17+, Maven, Google Chrome installed.

1. Clone the repo
   ```bash
   git clone https://github.com/Ankita-Sharma-QA/Selenium.git
   cd Selenium
   ```

2. Run the full suite
   ```bash
   mvn clean test
   ```

   This uses the suite defined in `src/test/resources/testng.xml`, running
   Login, Products, and Checkout test classes in sequence.

3. Run a single test class
   ```bash
   mvn -Dtest=LoginTest test
   ```

WebDriverManager automatically downloads the correct ChromeDriver version for
your installed Chrome — no manual driver setup needed.

## Configuration

All environment values (base URL, browser choice, timeouts, test credentials)
live in `src/main/resources/config.properties`, so nothing is hardcoded in
test or page classes. To switch browsers, change `browser=chrome` to
`browser=firefox`.

## Notes

This is a learning/portfolio project built to demonstrate Selenium + Java +
TestNG + Maven fundamentals using the Page Object Model, alongside my
professional QA/SDET work in manual and automation testing across aviation,
government, and GIS domains.
