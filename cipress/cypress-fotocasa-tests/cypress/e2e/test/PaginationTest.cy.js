import homePage from '../pages/HomePage'; 
import searchResultsPage from '../pages/SearchResultsPage'; 

describe('Fotocasa: Navegación de Paginación', () => {

    it('Debe navegar a la página 2 y verificar que es la página activa', () => {
        const targetPage = "2";

        cy.log('1. NAVEGACIÓN Y BÚSQUEDA INICIAL');
        homePage.open();
        homePage.acceptCookiesIfPresent();
        homePage.searchCity("Madrid");

        searchResultsPage.closeNotificationsPopupIfPresent();
        searchResultsPage.isLoaded(); // Verificar que la página 1 ha cargado

        cy.log(`2. Ir a la página ${targetPage}`);
        // Llamada al nuevo método de paginación
        searchResultsPage.goToPage(targetPage);

        cy.log(`3. Validar que estamos en la página ${targetPage}`);
        // Llamada al nuevo método de validación
        searchResultsPage.isOnPage(targetPage);

        cy.log(' PRUEBA DE PAGINACIÓN COMPLETADA CON ÉXITO.');
    });
});