package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;
import org.openqa.selenium.support.ui.WebDriverWait; // Para resolver 'WebDriverWait'
import java.time.Duration; // Para resolver 'Duration'

public class SearchResultsPage extends BasePage {

    // --- SELECTORES COMUNES ---
    private final By resultsTitle = By.cssSelector("h1.text-headline-2");
    private final By resultCard = By.cssSelector("article");

    private final By closePopupBtn = By.cssSelector("button.sui-MoleculeModal-close");

    // Selector de precios dentro de los anuncios
    private final By priceElements = By.cssSelector("span[class*='price'], div[class*='price']");
    // Localizador del primer anuncio
    private final By firstResultLink = By.cssSelector("article a[href*='/es/']");

    public void openFirstResult() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(firstResultLink));

        // Pausa humana para evitar bloqueo
        try { Thread.sleep(800); } catch (Exception ignored) {}

        // Abrir en nueva pestaña
        String newTab = Keys.chord(Keys.CONTROL, Keys.RETURN);
        element.sendKeys(newTab);

        // Cambiar a la nueva pestaña abierta
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        // Otra pequeña pausa
        try { Thread.sleep(1200); } catch (Exception ignored) {}
    }



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
    // Primer anuncio de la lista



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
    // Selector genérico para ir a una página concreta
    private By pageByNumber(String number) {
        return By.cssSelector("a[aria-label='Página " + number + "']");
    }

    // Ir a la página N
    public void goToPage(String pageNumber) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        By paginationContainer = By.cssSelector("nav[data-panot-component='pagination']");
        int maxAttempts = 30;        // muchos intentos porque la paginación tarda en aparecer
        int scrollStep = 800;        // scroll gradual

        for (int i = 0; i < maxAttempts; i++) {

            // ¿La paginación ya existe y es visible?
            if (driver.findElements(paginationContainer).size() > 0) {
                WebElement container = driver.findElement(paginationContainer);

                if (container.isDisplayed()) {
                    break; // ¡Paginación encontrada!
                }
            }

            // Scroll hacia abajo
            js.executeScript("window.scrollBy(0, arguments[0]);", scrollStep);

            // Pequeña pausa para que carguen los anuncios
            try { Thread.sleep(300); } catch (InterruptedException ignored) {}
        }

        // Ahora esperamos directamente el botón de página
        By pageButton = By.cssSelector("a[aria-label='Página " + pageNumber + "']");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(pageButton));

        // Centrarlo para evitar errores
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);

        element.click();
    }
    // --- SELECTOR COMÚN PARA BOTONES DE ACCIÓN EN MODALES/POPOVERS ---
// Usado para "Mostrar X viviendas" o "Aplicar filtros" en pop-ups/modales, incluyendo el botón de "volver"
    private final By modalActionButton =
            By.cssSelector("div.sui-MoleculeModal-footer button.sui-AtomButton--primary, .sui-MoleculeSelectPopover-popoverActionBar button");

    // ---------------------------------
// FILTRO PRINCIPAL: "Filtros (0)"
// ---------------------------------
// Botón principal "Filtros (0)" en la barra de búsqueda
    private final By mainFiltersButton =
            By.cssSelector("button.re-SearchFiltersTop-filtersButton");

    public void openAllFiltersModal() {
        wait.until(ExpectedConditions.elementToBeClickable(mainFiltersButton)).click();
    }

    // ---------------------------
// FILTRO: DISTRITO
// ---------------------------
// 1. Dropdown de Distrito dentro del modal
    private final By districtDropdown =
            By.cssSelector("#search-geographic-select-popover-\\:rt\\:");

    // 2. Opción "Arganzuela" (ejemplo de la primera opción a seleccionar)
    private final By arganzuelaOption = By.cssSelector("a[title='Arganzuela']");

    public void selectDistrictArganzuela() {
        // 1. Abrir el dropdown de Distrito
        wait.until(ExpectedConditions.elementToBeClickable(districtDropdown)).click();

        // 2. Seleccionar Arganzuela
        wait.until(ExpectedConditions.elementToBeClickable(arganzuelaOption)).click();

        // 3. Aplicar filtros (Botón "Mostrar anuncios") y volver al modal principal
        applyFiltersAndBack();
    }

    // ---------------------------
// FILTRO: TRANSACCIÓN (Comprar/Alquilar)
// ---------------------------
// 1. Dropdown de Transacción
// Uso de XPath para localizar el input 'Comprar' y subir al contenedor principal
    private final By transactionTypeDropdown =
            By.cssSelector(".re-FiltersFilterTransactionType .sui-MoleculeSelect-inputSelect-container");

    // 2. Opción de dropdown con valor 'alquiler'
    private By transactionOption(String value) {
        return By.cssSelector("li.sui-MoleculeDropdownOption[data-value='" + value + "']");
    }

    public void selectTransactionTypeRent() {
        // Localizador del dropdown de Transacción (el que abre el menú)
        final By transactionTypeDropdown =
                By.cssSelector(".re-FiltersFilterTransactionType .sui-MoleculeSelect-inputSelect-container");

        wait.until(ExpectedConditions.elementToBeClickable(transactionTypeDropdown)).click();
        System.out.println("-> Dropdown de Transacción abierto.");

        // --- NUEVO SELECTOR DEFINITIVO: Busca el <li> que contiene el <span> con texto 'Alquilar' ---
        // Usamos 'normalize-space()' para manejar espacios extra.
        By rentOptionNestedSpan = By.xpath("//li[./span[normalize-space(text())='Alquilar']]");

        // Opcional: Pausa de sincronización si es necesario
        try { Thread.sleep(500); } catch (InterruptedException e) {}

        // Esperar y seleccionar
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(rentOptionNestedSpan));
        System.out.println("-> Opción 'Alquilar' localizada y lista para clic.");
        element.click();
    }

    // ---------------------------
// FILTRO: TIPO DE ALQUILER (Larga Duración)
// ---------------------------
// Checkbox para "Larga duración" (usando el 'for' del label)
    private final By longTermRentalCheckbox =
            By.cssSelector("label[for='filter-rental-duration-LONG_TERM']");

    public void selectLongTermRental() {
        wait.until(ExpectedConditions.elementToBeClickable(longTermRentalCheckbox)).click();
    }

    // ---------------------------
// FILTRO: EXTRAS (Ascensor)
// ---------------------------
// Botón para "Ascensor" (Uso de XPath para localizar por texto 'Ascensor')
    // Selector mejorado: Busca el botón que contiene el texto "Ascensor" en cualquier descendiente
    private final By elevatorFeatureButton =
            By.xpath("//button[.//span[text()='Ascensor']]");

    private final By extrasTitle = By.xpath("//div[text()='Extras']");

    public void selectElevatorFeature() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // 1. FORZAR SCROLL HASTA EL TÍTULO "Extras"
        // Esto asegura que el área del filtro se cargue en el viewport antes de buscar el botón.
        WebElement extrasHeader = wait.until(ExpectedConditions.presenceOfElementLocated(extrasTitle));
        js.executeScript("arguments[0].scrollIntoView(true);", extrasHeader);

        // Pequeña pausa para que se complete el scroll y la carga (crucial en modals)
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}

        // 2. Localizar el botón de Ascensor (usando el selector mejorado de la Solución 1)
        // Usaremos el XPath simplificado que busca el botón que contiene el texto.
        By elevatorButton = By.xpath("//button[.//span[text()='Ascensor']]");

        // 3. Esperar a que el botón sea visible y clickeable
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elevatorButton));

        // 4. Hacer clic
        element.click();
        System.out.println("-> Clic realizado en 'Ascensor'.");
    }
    // --- NUEVO SELECTOR PARA EL BOTÓN FINAL (Añadir a SearchResultsPage.java) ---

    private final By finalApplyButton =
            By.xpath("//footer//button[contains(normalize-space(.), 'Mostrar')]");
    // -------------------------------------------------------------------

    public void applyFinalFiltersAndSearch() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        System.out.println("-> Intentando hacer clic en el botón final 'Mostrar X anuncios'...");

        // 1. Esperar la PRESENCIA del botón (usando el nuevo selector corregido)
        WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(finalApplyButton));

        // 2. FORZAR EL CLIC CON JAVASCRIPT
        // Esto ignora si hay algo encima o si Selenium piensa que no es visible.
        js.executeScript("arguments[0].click();", button);

        System.out.println("-> Clic forzado por JavaScript ejecutado. Esperando que el modal se cierre.");

        // 3. Esperar que el modal desaparezca
        By modalDialog = By.cssSelector("div.sui-MoleculeModal-dialog");
        try {
            // Damos un margen amplio (15s) para que recargue la página
            new WebDriverWait(driver, Duration.ofSeconds(15)).until(
                    ExpectedConditions.invisibilityOfElementLocated(modalDialog)
            );
            System.out.println("-> El modal de filtros se cerró correctamente.");
        } catch (Exception e) {
            System.err.println("¡Advertencia! El modal no se cerró a tiempo (o ya se había cerrado), continuamos.");
        }
    }


    // ---------------------------
// MÉTODO COMÚN DE APLICAR/VOLVER DEL MODAL
// ---------------------------
    public void applyFiltersAndBack() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(modalActionButton)).click();
        } catch (Exception ignored) {}
    }
    // Selector común para botones de acción en modales/popovers





    // Validar que estamos en la página N
    public boolean isOnPage(String pageNumber) {
        try {
            By selector = By.cssSelector("a[aria-label='Página " + pageNumber + "'][aria-current='page']");
            wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
