package tests;

import org.junit.jupiter.api.Test;
import pages.HomePage;
import pages.SearchResultsPage;
import pages.DetailPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

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

        // Abrimos el primer anuncio en una nueva pestaña
        results.openFirstResult();

        DetailPage detail = new DetailPage(driver);
        assertTrue(detail.isLoaded(), "La página del inmueble no cargó correctamente.");
    }
}
