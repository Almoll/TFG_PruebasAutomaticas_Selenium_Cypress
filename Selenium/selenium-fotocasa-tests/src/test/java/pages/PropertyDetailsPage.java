package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PropertyDetailsPage extends BasePage {

    private final By titleLocator = By.cssSelector("h1, h2");
    private final By interruptionMessage = By.xpath("//*[contains(text(),'SENTIMOS LA INTERRUPCIÓN')]");

    public PropertyDetailsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        try {
            // Esperar si carga el mensaje de bloqueo
            if (driver.findElements(interruptionMessage).size() > 0) {
                System.out.println("Fotocasa devolvió la página de interrupción");
                return false;
            }

            wait.until(ExpectedConditions.visibilityOfElementLocated(titleLocator));
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public String getPropertyTitle() {
        return driver.findElement(titleLocator).getText();
    }
}
