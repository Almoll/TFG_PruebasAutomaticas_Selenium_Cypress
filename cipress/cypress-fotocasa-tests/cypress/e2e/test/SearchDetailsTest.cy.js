import homePage from '../pages/HomePage'; 
import searchResultsPage from '../pages/SearchResultsPage'; 
import detailPage from '../pages/DetailPage'; 

describe('Fotocasa: Navegación a Detalle de Inmueble', () => {

    it('Debe buscar en Madrid y abrir el detalle del primer resultado', () => {
        cy.log('1. NAVEGACIÓN Y BÚSQUEDA');
        homePage.open();
        homePage.acceptCookiesIfPresent();
        homePage.searchCity("Madrid");

        // CIERRE DE POPUP (CORREGIDO)
        searchResultsPage.closeNotificationsPopupIfPresent();
        
        // Espera a que la página de resultados cargue (CRÍTICO)
        searchResultsPage.isLoaded(); 
        
        // 2. ABRIR EL PRIMER ANUNCIO
        searchResultsPage.hasResults();
        searchResultsPage.openFirstResult();

        // 3. VALIDAR CARGA DEL DETALLE
        detailPage.isLoaded(); 
        
        cy.log(' PRUEBA DE DETALLE COMPLETADA CON ÉXITO.');
    });
});