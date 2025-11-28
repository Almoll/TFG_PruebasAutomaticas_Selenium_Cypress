import homePage from '../pages/HomePage';
import searchResultsPage from '../pages/SearchResultsPage';

describe('Fotocasa: Tests de Filtros de Búsqueda', () => {

    it('Debe aplicar un filtro de precio mínimo (100.000) y validar los resultados', () => {
        
        const MIN_PRICE_VALUE = 100000;
        const MIN_PRICE_SELECTOR_KEY = "100000"; // El valor que se envía al selector del dropdown

        cy.log('1. Abrir la Home, aceptar cookies y buscar "Madrid"');
        homePage.open();
        homePage.acceptCookiesIfPresent();
        homePage.searchCity("Madrid");

        cy.log('2. Cerrar popups de notificaciones si existen');
        searchResultsPage.closeNotificationsPopupIfPresent();
        searchResultsPage.isLoaded(); 

        cy.log('3. Abrir el filtro de precio y seleccionar el valor mínimo');
        searchResultsPage.openMinPriceDropdown();
        searchResultsPage.selectMinPrice(MIN_PRICE_SELECTOR_KEY); 
        searchResultsPage.applyFilters();

        cy.log('4. Cerrar posibles popups post-carga y verificar la carga de la página');
        // Este paso es crucial, ya que aplicar filtros recarga la página.
        searchResultsPage.closeNotificationsPopupIfPresent();
        searchResultsPage.isLoaded(); 
        
        cy.log('5. Aserción: Verificar que todos los precios son mayores o iguales a 100.000');
        searchResultsPage.allResultsAreAbove(MIN_PRICE_VALUE); 
    });
    
    // Podemos añadir más tests de filtros aquí si lo deseas.
});