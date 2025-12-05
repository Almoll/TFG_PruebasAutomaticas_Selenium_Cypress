import homePage from '../pages/HomePage'; 
import searchResultsPage from '../pages/SearchResultsPage'; 

describe('Fotocasa: Aplicación de Múltiples Filtros Avanzados', () => {

    // 💡 SOLUCIÓN CLAVE: Ejecutar la navegación y limpieza de cookies antes de CADA test.
    beforeEach(() => {
        cy.log('Iniciando prueba: Limpieza de estado y navegación a Home.');
        homePage.open();
        homePage.acceptCookiesIfPresent();
        // Opcional: Si sabes que la prueba anterior deja un pop-up de alerta, ciérralo aquí.
        searchResultsPage.closeNotificationsPopupIfPresent(); 
    });

    it('Debe aplicar filtros: Distrito, Alquiler, Larga Duración y Ascensor', () => {
        
        cy.log('1. BÚSQUEDA INICIAL');
        // Usamos la búsqueda sin open() ni acceptCookies(), ya que están en beforeEach
        homePage.searchCity("Madrid"); 

        // Verificación de carga y cierre de pop-ups después de la búsqueda.
        searchResultsPage.closeNotificationsPopupIfPresent();
        searchResultsPage.isLoaded(); 

        cy.log('2. ABRIR MODAL DE FILTROS AVANZADOS');
        searchResultsPage.openAllFiltersModal();

        // 3. FILTRAR POR DISTRITO (Arganzuela)
        searchResultsPage.selectDistrictArganzuela();

        // [MANTENER EL RESTO DE LA LÓGICA DE FILTROS SIN CAMBIOS]
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
        searchResultsPage.isLoaded();
        
        cy.log('9. Validar que se encontraron resultados');
        searchResultsPage.hasResults();
        
        // Esta pausa de 3s no es necesaria en Cypress a menos que sea por estética
        // cy.wait(3000); 

        cy.log(' Prueba de Filtros Avanzados completada con éxito.');
    });
});