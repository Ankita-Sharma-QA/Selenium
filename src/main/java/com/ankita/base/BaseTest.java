package com.ankita.base;

import com.ankita.utils.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

/**
 * BaseTest centralizes WebDriver setup and teardown so every test class
 * simply extends this instead of repeating driver initialization logic.
 * Browser choice and timeouts are driven entirely from config.properties.
 */
public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        String browser = ConfigReader.get("browser").toLowerCase();

        switch (browser) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;

            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                if (ConfigReader.getBoolean("headless")) {
                    options.addArguments("--headless=new");
                }
                options.addArguments("--start-maximized");
                driver = new ChromeDriver(options);
                break;
        }

        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(ConfigReader.getInt("implicit.wait")));
        driver.manage().window().maximize();
        driver.get(ConfigReader.get("base.url"));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
