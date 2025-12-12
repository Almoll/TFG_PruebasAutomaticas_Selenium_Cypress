package tests;

import org.junit.jupiter.api.Test;
import pages.HomePage;
import pages.SearchResultsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchDetailsInterruptionTest extends BaseTest {

    // Selector para identificar el encabezado de la página de interrupción
    private static final By INTERRUPCION_HEADER = By.xpath("//h1[contains(text(), 'SENTIMOS LA INTERRUPCIÓN')]");

    @Test
    public void testOpenPropertyDetailsAndExpectInterruption() {
        System.out.println("TEST INICIADO: Búsqueda con validación de Interrupción.");

        HomePage home = new HomePage(driver);
        home.open();
        home.acceptCookiesIfPresent();

        home.searchCity("Madrid");

        SearchResultsPage results = new SearchResultsPage(driver);
        results.closeNotificationsPopupIfPresent();

        assertTrue(results.hasResults(), "No hay resultados para abrir.");

        // 1. Guardamos el ID de la ventana/pestaña original
        String originalWindowHandle = driver.getWindowHandle();
        Set<String> originalHandles = driver.getWindowHandles();

        // 2. Abrimos el primer anuncio en una nueva pestaña
        results.openFirstResult();

        // 3. Espera explícita para asegurar que la nueva pestaña se haya abierto
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Aumentamos la espera a 10s por si acaso
        wait.until(d -> d.getWindowHandles().size() > originalHandles.size());

        // 4. CAMBIO DE FOCO: Iteramos sobre las ventanas abiertas para encontrar la nueva
        String newWindowHandle = null;
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalHandles.contains(windowHandle)) {
                newWindowHandle = windowHandle;
                break;
            }
        }

        // 5. Verificamos que se encontró una nueva ventana y cambiamos el foco
        if (newWindowHandle != null) {
            driver.switchTo().window(newWindowHandle);
        } else {
            // Falla si no se abrió la nueva pestaña (aunque la espera del paso 3 debería evitarlo)
            throw new RuntimeException("No se detectó una nueva pestaña/ventana después de hacer clic.");
        }

        System.out.println("Foco cambiado a la nueva pestaña. Validando contenido...");

        // 6. VALIDACIÓN CRÍTICA: Esperamos a que el encabezado de interrupción sea visible
        // Si este elemento aparece, la prueba pasa. Si expira el tiempo, la prueba falla.
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(INTERRUPCION_HEADER));
            System.out.println("VALIDACIÓN EXITOSA: Se encontró el encabezado 'SENTIMOS LA INTERRUPCIÓN'.");

            // 7. Aseguramos que la aserción de JUnit sea explícitamente True para que el test pase.
            assertTrue(driver.findElement(INTERRUPCION_HEADER).isDisplayed(), "El encabezado de Interrupción no es visible.");

        } catch (Exception e) {
            // Esto se ejecuta si la espera falla (si no se encuentra el encabezado en 10 segundos)
            throw new AssertionError("Fallo en la validación: No se encontró la página de Interrupción o el elemento esperado: " + e.getMessage());
        } finally {
            // 8. Opcional: Cerrar la pestaña de detalle para volver a la principal (limpieza)
            driver.close();
            driver.switchTo().window(originalWindowHandle);
        }
    }
}