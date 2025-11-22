package tests;

import org.junit.jupiter.api.Test;
import pages.HomePage;
import pages.SearchResultsPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchFiltersTest extends BaseTest {

    @Test
    public void testMinPriceFilter() {
        HomePage home = new HomePage(driver);
        home.open();
        home.acceptCookiesIfPresent();

        home.searchCity("Madrid");

        SearchResultsPage results = new SearchResultsPage(driver);
        results.closeNotificationsPopupIfPresent();

        // Abrir filtro de precio
        results.openMinPriceDropdown();
        results.selectMinPrice("100000");  // valor REAL en COMPRA
        results.applyFilters();

        results.closeNotificationsPopupIfPresent();

        assertTrue(results.isLoaded(), "Los resultados no se cargaron correctamente.");
        assertTrue(results.allResultsAreAbove(99999),
                "Hay anuncios con precio menor al mínimo aplicado.");
    }
}
