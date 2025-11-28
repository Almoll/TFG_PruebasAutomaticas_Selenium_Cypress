import homePage from '../pages/HomePage';

describe('Tests de Home Page Fotocasa', () => {

  it('Debe cargar la página principal correctamente', () => {
    // 1. Abrir
    homePage.open();

    // 2. Aceptar cookies si aparecen
    homePage.acceptCookiesIfPresent();

    // 3. Verificar que ha cargado (Assertion)
    homePage.isLoaded(); 
    
    // Opcional: una verificación extra de URL
    cy.url().should('include', 'fotocasa.es');
  });

});