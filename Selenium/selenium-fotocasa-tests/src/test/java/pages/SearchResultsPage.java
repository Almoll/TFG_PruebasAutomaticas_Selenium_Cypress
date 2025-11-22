package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class SearchResultsPage extends BasePage {

    // --- SELECTORES COMUNES ---
    private final By resultsTitle = By.cssSelector("h1.text-headline-2");
    private final By resultCard = By.cssSelector("article");

    private final By closePopupBtn = By.cssSelector("button.sui-MoleculeModal-close");

    // Selector de precios dentro de los anuncios
    private final By priceElements = By.cssSelector("span[class*='price'], div[class*='price']");


    // --- FILTRO DE PRECIO ---
    // 1. Botón principal: PRECIO (en la barra superior)
    private final By priceFilterButton = By.cssSelector("#filters-bar-filter-price");

    // 2. Dropdown del precio mínimo dentro del panel
    private final By minPriceDropdown =
            By.cssSelector("div.sui-MoleculeSelect div.sui-MoleculeSelect-inputSelect-container");

    // 3. Opciones del dropdown usando data-value
    private By minPriceOption(String value) {
        return By.cssSelector("li.sui-MoleculeDropdownOption[data-value='" + value + "']");
    }

    // 4. Botón “Mostrar X viviendas / Aplicar filtros”
    private final By popoverApplyButton =
            By.cssSelector(".sui-MoleculeSelectPopover-popoverActionBar button");


    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    // ---------------------------
    // MÉTODOS DE LA PÁGINA
    // ---------------------------

    public boolean isLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(resultsTitle));
            wait.until(ExpectedConditions.visibilityOfElementLocated(resultCard));
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
            wait.until(ExpectedConditions.elementToBeClickable(closePopupBtn)).click();
        } catch (Exception ignored) {}
    }

    public boolean hasResults() {
        try {
            return driver.findElements(resultCard).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // ---------------------------
    // FILTRO: PRECIO MÍNIMO
    // ---------------------------

    public void openMinPriceDropdown() {
        wait.until(ExpectedConditions.elementToBeClickable(priceFilterButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(minPriceDropdown)).click();
    }

    public void selectMinPrice(String value) {
        wait.until(ExpectedConditions.elementToBeClickable(minPriceOption(value))).click();
    }

    public void applyFilters() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(popoverApplyButton)).click();
        } catch (Exception ignored) {}
    }

    // ---------------------------
    // VALIDAR EL PRECIO
    // ---------------------------

    public boolean allResultsAreAbove(int minPrice) {
        try {
            List<WebElement> prices = driver.findElements(priceElements);

            for (WebElement price : prices) {
                String text = price.getText().replace(".", ""); // quitar separador de miles

                // Extraer solo los dígitos usando regex
                String digitsOnly = text.replaceAll("[^0-9]", "");

                if (digitsOnly.isEmpty()) continue;

                int value = Integer.parseInt(digitsOnly);

                if (value < minPrice) return false;
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
