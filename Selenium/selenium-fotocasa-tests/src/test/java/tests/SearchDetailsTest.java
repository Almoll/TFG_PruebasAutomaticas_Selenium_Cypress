package tests;

import org.junit.jupiter.api.Test;
import pages.HomePage;
import pages.SearchResultsPage;
import pages.DetailPage;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SearchDetailsTest extends BaseTest {

    @Test
    public void testOpenPropertyDetails() throws InterruptedException {

        HomePage home = new HomePage(driver);
        home.open();
        home.acceptCookiesIfPresent();

        home.searchCity("Madrid");

        SearchResultsPage results = new SearchResultsPage(driver);
        results.closeNotificationsPopupIfPresent();

        assertTrue(results.hasResults(), "No hay resultados para abrir.");

        // 1. Guardamos el ID de la ventana/pestaña original
        String originalWindowHandle = driver.getWindowHandle();

        // 2. Abrimos el primer anuncio en una nueva pestaña
        results.openFirstResult();

        // 3. Espera explícita para asegurar que la nueva pestaña se haya abierto
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(d -> d.getWindowHandles().size() == 2);

        // 4. CAMBIO DE FOCO: Iteramos sobre las ventanas abiertas para encontrar la nueva
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindowHandle.contentEquals(windowHandle)) {
                // Cambiamos el foco a la nueva pestaña
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        // 5. PAUSA CRÍTICA: Esperar 1 segundo para que el contenido de la nueva página comience a renderizar
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}

        // 6. Ahora el driver está en la nueva pestaña y esperamos que cargue
        DetailPage detail = new DetailPage(driver);

        // 7. Validamos que la página de detalle ha cargado correctamente
        assertTrue(detail.isLoaded(), "La página del inmueble no cargó correctamente.");

        // Opcional: Cerrar la pestaña de detalle para volver a la principal (limpieza)
        driver.close();
        driver.switchTo().window(originalWindowHandle);
    }
}