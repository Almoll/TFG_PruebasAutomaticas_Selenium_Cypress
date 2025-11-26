package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait; // <-- Nueva Importación
import java.time.Duration; // <-- Nueva Importación

public class DetailPage extends BasePage {

    private final By priceElement =
            By.cssSelector("span[class*='price'], div[class*='price']");

    public DetailPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        // Creamos una nueva espera de 20 segundos para esta validación,
        // garantizando que el elemento de precio aparezca tras el cambio de pestaña.
        WebDriverWait detailWait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            detailWait.until(ExpectedConditions.visibilityOfElementLocated(priceElement));
            System.out.println(" Detalle de la propiedad cargado con éxito.");
            return true;
        } catch (Exception e) {
            System.err.println(" ERROR: El elemento clave (precio) no fue visible en 20 segundos.");
            return false;
        }
    }

    public String getPrice() {
        return driver.findElement(priceElement).getText();
    }
}