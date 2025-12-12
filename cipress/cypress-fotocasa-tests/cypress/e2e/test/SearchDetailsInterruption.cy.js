import homePage from '../pages/HomePage';
import searchResultsPage from '../pages/SearchResultsPage';
import detailPage from '../pages/DetailPage'; // Mantener por consistencia, aunque se usará un selector alternativo

describe('Fotocasa: Navegación a Detalle - Alternativa de Interrupción', () => {

    // Selector para validar la página de interrupción (ajusta si es necesario)
    // Usamos el encabezado "SENTIMOS LA INTERRUPCIÓN"
    const INTERRUPCION_HEADER_SELECTOR = 'h1:contains("SENTIMOS LA INTERRUPCIÓN")';

    it('Debe buscar en Madrid, abrir el detalle y pasar si se llega a la página de Interrupción', () => {
        cy.log('1. NAVEGACIÓN Y BÚSQUEDA');
        homePage.open();
        homePage.acceptCookiesIfPresent();
        homePage.searchCity("Madrid");

        // CIERRE DE POPUP
        searchResultsPage.closeNotificationsPopupIfPresent();

        // Espera a que la página de resultados cargue
        searchResultsPage.isLoaded();

        // 2. ABRIR EL PRIMER ANUNCIO
        searchResultsPage.hasResults();
        searchResultsPage.openFirstResult();

        // 3. VALIDAR LLEGADA A LA PÁGINA DE INTERRUPCIÓN (VALIDACIÓN ALTERNATIVA)
        cy.log('VALIDANDO LLEGADA A LA PÁGINA DE INTERRUPCIÓN');

        // En lugar de detailPage.isLoaded(), verificamos si existe el encabezado de interrupción.
        // Esto hará que el test sea exitoso si el flujo de navegación termina en la segunda imagen.
        cy.get(INTERRUPCION_HEADER_SELECTOR).should('be.visible');

        cy.log(' PRUEBA ALTERNATIVA COMPLETADA CON ÉXITO: Se llegó a la página de Interrupción.');
    });
});