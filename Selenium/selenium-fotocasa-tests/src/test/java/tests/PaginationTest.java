package tests;

import org.junit.jupiter.api.Test;
import pages.HomePage;
import pages.SearchResultsPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaginationTest extends BaseTest {

    @Test
    public void testGoToPage2() {
        HomePage home = new HomePage(driver);
        home.open();
        home.acceptCookiesIfPresent();

        home.searchCity("Madrid");

        SearchResultsPage results = new SearchResultsPage(driver);
        results.closeNotificationsPopupIfPresent();

        // Ir a página 2
        results.goToPage("2");

        // Validar que estamos en página 2
        assertTrue(results.isOnPage("2"), "No se ha cargado la página 2 de resultados.");
    }
}
