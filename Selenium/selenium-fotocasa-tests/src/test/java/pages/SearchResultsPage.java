package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchResultsPage extends BasePage {

    private final By resultsTitle = By.cssSelector("h1.text-headline-2.m-none");

    private final By resultCard = By.cssSelector("article");//[class*='re-Card']

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(resultsTitle));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getTitleText() {
        return driver.findElement(resultsTitle).getText();
    }
    public void closeNotificationsPopupIfPresent() {
        try {
            By closePopup = By.cssSelector("button.sui-MoleculeModal-close");
            wait.until(ExpectedConditions.elementToBeClickable(closePopup)).click();
        } catch (Exception ignored) {
            // Si no aparece, no hacemos nada
        }
    }



    public boolean hasResults() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(resultCard));
            return driver.findElements(resultCard).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

}
