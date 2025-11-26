package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.HomePage;
import pages.SearchResultsPage;

// BaseTest debe extenderse para heredar la configuración
public class AdvancedFiltersTest extends BaseTest {

    private HomePage homePage;
    private SearchResultsPage searchResultsPage;

    @Test
    public void applyMultipleFiltersAndValidateResults() {
        homePage = new HomePage(driver);

        // 1. NAVEGACIÓN Y BÚSQUEDA INICIAL
        homePage.open();
        homePage.acceptCookiesIfPresent();
        homePage.searchCity("Madrid");

        searchResultsPage = new SearchResultsPage(driver);
        searchResultsPage.closeNotificationsPopupIfPresent();

        // 2. ABRIR FILTROS AVANZADOS
        searchResultsPage.openAllFiltersModal();

        // 3. FILTRAR POR DISTRITO (Arganzuela)
        searchResultsPage.selectDistrictArganzuela();

        // 4. FILTRAR POR TRANSACCIÓN (Alquiler)
        searchResultsPage.selectTransactionTypeRent();

        // 5. FILTRAR POR TIPO DE ALQUILER (Larga duración)
        searchResultsPage.selectLongTermRental();

        // 6. FILTRAR POR EXTRAS (Ascensor)
        searchResultsPage.selectElevatorFeature();

        System.out.println("Paso 6: Todos los filtros seleccionados con éxito.");

        // 7. APLICAR FILTROS FINALES Y CERRAR EL MODAL (Mostrar X anuncios)
        searchResultsPage.applyFinalFiltersAndSearch();
        System.out.println("Paso 7: Clic en 'Mostrar X anuncios'. Esperando resultados finales.");

        // --- VALIDACIÓN Y ESPERA FINAL ---

        // 8. Esperar a que la página de resultados cargue con los nuevos filtros
        Assertions.assertTrue(searchResultsPage.isLoaded(), "La página de resultados no cargó correctamente después de aplicar los filtros.");

        // 9. Validar que se encontraron resultados
        Assertions.assertTrue(searchResultsPage.hasResults(), "No se encontraron resultados después de aplicar los filtros avanzados.");

        // 10. Pausa visual para ver los resultados (3 segundos)
        try {
            System.out.println("Resultados cargados. Pausa de 3 segundos para visualización.");
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {}

        // 11. Finalizar la prueba
        System.out.println(" PRUEBA DE FILTROS AVANZADOS COMPLETADA CON ÉXITO.");
    }
}