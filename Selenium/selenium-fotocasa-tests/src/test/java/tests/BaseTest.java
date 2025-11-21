package tests;

import utils.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

public class BaseTest {

    protected WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
