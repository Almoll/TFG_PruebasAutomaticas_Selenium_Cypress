package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DetailPage extends BasePage {

    private final By priceElement =
            By.cssSelector("span[class*='price'], div[class*='price']");

    public DetailPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(priceElement));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getPrice() {
        return driver.findElement(priceElement).getText();
    }
}
