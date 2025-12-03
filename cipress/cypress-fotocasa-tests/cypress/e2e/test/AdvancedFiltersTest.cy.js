import homePage from '../pages/HomePage'; 
import searchResultsPage from '../pages/SearchResultsPage'; 

describe('Fotocasa: Aplicación de Múltiples Filtros Avanzados', () => {

    it('Debe aplicar filtros: Distrito, Alquiler, Larga Duración y Ascensor', () => {
        cy.log('1. NAVEGACIÓN Y BÚSQUEDA INICIAL');
        homePage.open();
        homePage.acceptCookiesIfPresent();
        homePage.searchCity("Madrid");

        searchResultsPage.closeNotificationsPopupIfPresent();
        searchResultsPage.isLoaded();

        cy.log('2. ABRIR MODAL DE FILTROS AVANZADOS');
        searchResultsPage.openAllFiltersModal();

        cy.log('3. FILTRAR POR DISTRITO (Arganzuela)');
        searchResultsPage.selectDistrictArganzuela();

        cy.log('4. FILTRAR POR TRANSACCIÓN (Alquiler)');
        searchResultsPage.selectTransactionTypeRent();

        cy.log('5. FILTRAR POR TIPO DE ALQUILER (Larga duración)');
        searchResultsPage.selectLongTermRental();

        cy.log('6. FILTRAR POR EXTRAS (Ascensor)');
        searchResultsPage.selectElevatorFeature();

        cy.log('7. APLICAR FILTROS FINALES Y CERRAR EL MODAL');
        searchResultsPage.applyFinalFiltersAndSearch();

        // --- VALIDACIÓN Y ESPERA FINAL ---

        cy.log('8. Esperar a que la página de resultados cargue con los nuevos filtros');
        searchResultsPage.isLoaded(); // Verifica el título y las tarjetas

        cy.log('9. Validar que se encontraron resultados');
        searchResultsPage.resultCard.its('length').should('be.gt', 0); // Verifica al menos 1 resultado

        cy.log(' PRUEBA DE FILTROS AVANZADOS COMPLETADA CON ÉXITO.');
    });
});