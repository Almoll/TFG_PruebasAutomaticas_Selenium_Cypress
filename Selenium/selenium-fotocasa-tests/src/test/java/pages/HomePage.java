package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions; // <--- Importación necesaria

public class HomePage extends BasePage {

    private final By cookiesButton = By.id("didomi-notice-agree-button"); // puede cambiar
    private final By searchBar = By.cssSelector("input[aria-label='Buscar']");

    // NUEVO: Selector para el primer elemento de la lista de sugerencias
    // Este es un selector robusto para listas de sugerencias (asumiendo UL/LI en el DOM)
    private final By firstSuggestion = By.xpath("//ul[@role='listbox']//li[1]");


    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get("https://www.fotocasa.es/");
    }

    public void acceptCookiesIfPresent() {
        try {
            // En lugar de findElement sin espera, usamos una espera rápida para el botón
            wait.until(ExpectedConditions.elementToBeClickable(cookiesButton)).click();
        } catch (Exception e) {
            // Si no aparece, no hacemos nada
        }
    }

    public boolean isLoaded() {
        try {
            // Usamos una espera para la barra de búsqueda en lugar de solo isDisplayed()
            return wait.until(ExpectedConditions.visibilityOfElementLocated(searchBar)) != null;
        } catch (Exception e) {
            return false;
        }
    }

    public void searchCity(String city) {
        driver.findElement(searchBar).sendKeys(city);

        // --- SOLUCIÓN AL PROBLEMA ALEATORIO ---
        // 1. ESPERA EXPLÍCITA: Esperamos hasta que la primera sugerencia sea visible.
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(firstSuggestion));
        } catch (Exception e) {
            // Si hay un error de Timeout (no cargó la sugerencia), notificamos, pero seguimos.
            System.err.println("Advertencia: La lista de sugerencias de búsqueda no apareció a tiempo.");
        }
        // -------------------------------------

        // Una vez que la lista está visible, navegamos y presionamos ENTER.
        driver.findElement(searchBar).sendKeys(Keys.ARROW_DOWN);
        driver.findElement(searchBar).sendKeys(Keys.ENTER);
    }
}