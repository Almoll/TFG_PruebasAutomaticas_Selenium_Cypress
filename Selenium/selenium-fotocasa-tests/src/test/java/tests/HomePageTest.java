package tests;

import pages.HomePage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomePageTest extends BaseTest {

    @Test
    public void testHomeLoadsCorrectly() {
        HomePage home = new HomePage(driver);
        home.open();
        home.acceptCookiesIfPresent();

        assertTrue(home.isLoaded(), "La página principal de Fotocasa no cargó correctamente");
    }
}
