package tests;

import org.junit.jupiter.api.Test;
import pages.HomePage;
import pages.SearchResultsPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchTest extends BaseTest {

    @Test
    public void testSearchMadrid() {
        HomePage home = new HomePage(driver);
        home.open();
        home.acceptCookiesIfPresent();

        home.searchCity("Madrid");

        SearchResultsPage results = new SearchResultsPage(driver);
        // cerrar popup antes de validar el título
        results.closeNotificationsPopupIfPresent();

        assertTrue(results.isLoaded());

        // cerrar popup también después (por si aparece tarde)
        results.closeNotificationsPopupIfPresent();

        assertTrue(results.isLoaded(), "La página de resultados no cargó correctamente");
        assertTrue(results.getTitleText().contains("Madrid"));
        assertTrue(results.hasResults());;
    }
}
