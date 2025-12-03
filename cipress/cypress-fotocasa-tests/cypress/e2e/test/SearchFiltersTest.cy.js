import homePage from '../pages/HomePage'; 
import searchResultsPage from '../pages/SearchResultsPage'; 

describe('Fotocasa: Filtro de Precio Mínimo', () => {

    it('Debe aplicar un filtro de precio mínimo y validar que todos los resultados cumplen', () => {
        const MIN_PRICE_VALUE = '100000'; // Valor que se pasa al select
        const MIN_PRICE_CHECK = 100000;   // Valor numérico para la validación

        cy.log('1. Abriendo la home y buscando "Madrid"');
        homePage.open();
        homePage.acceptCookiesIfPresent();
        homePage.searchCity("Madrid");

        cy.log('2. Cerrando popups y verificando carga inicial');
        searchResultsPage.closeNotificationsPopupIfPresent();
        searchResultsPage.isLoaded(); 

        cy.log(`3. Aplicando filtro de precio mínimo: ${MIN_PRICE_VALUE}`);
        searchResultsPage.openMinPriceDropdown();
        searchResultsPage.selectMinPrice(MIN_PRICE_VALUE);  
        searchResultsPage.applyFilters();

        cy.log('4. Cerrando popups y verificando recarga');
        searchResultsPage.closeNotificationsPopupIfPresent();
        searchResultsPage.isLoaded(); 
        
        cy.log(`5. VALIDACIÓN: Comprobar que todos los precios son mayores o iguales a ${MIN_PRICE_CHECK}`);
        // La aserción se realiza dentro del método allResultsAreAbove
        searchResultsPage.allResultsAreAbove(MIN_PRICE_CHECK); 
    });
});