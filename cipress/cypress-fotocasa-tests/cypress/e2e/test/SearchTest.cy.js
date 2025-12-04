import homePage from '../pages/HomePage'; // Ruta a la clase HomePage
import searchResultsPage from '../pages/SearchResultsPage'; // Ruta a la clase SearchResultsPage

describe('Fotocasa: Búsqueda de Ciudad (Test Base)', () => {

    it('Debe buscar la ciudad de Madrid, cargar los resultados y verificar el título', () => {
        
        cy.log('1. Abriendo la home y aceptando cookies');
        homePage.open();
        homePage.acceptCookiesIfPresent();

        cy.log('2. Ejecutando la búsqueda por "Madrid"');
        homePage.searchCity("Madrid");

        cy.log('3. Esperando la carga de resultados y cerrando popups');
        
        // Cierra popup antes de validar el título
        searchResultsPage.closeNotificationsPopupIfPresent();
        
        // Verifica que la página de resultados cargó correctamente
        searchResultsPage.isLoaded(); 

        // Cierra popup también después (por si apareció tarde)
        searchResultsPage.closeNotificationsPopupIfPresent();

        
        searchResultsPage.isLoaded(); // Verifica que el título y la tarjeta existen
        //searchResultsPage.getTitleText().should('include', 'Madrid'); // Verifica que el título contiene "Madrid"
        searchResultsPage.hasResults(); // Verifica que hay al menos un resultado (article)
    });
});