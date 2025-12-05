class HomePage {
  
  // Selectores
  get cookiesButton() { return cy.get('#didomi-notice-agree-button'); }
  get searchBar() { return cy.get("input[aria-label='Buscar']"); }
  get firstSuggestion() { return cy.get("ul[role='listbox'] li").first(); }

  open() {
    cy.visit('/'); 
    // Esperamos a que la barra sea visible, indicando que la app cargó
    this.searchBar.should('be.visible', { timeout: 10000 }); 
  }

  acceptCookiesIfPresent() {
    cy.get('body').then(($body) => {
      if ($body.find('#didomi-notice-agree-button').length > 0) {
        
        // 1. Clic en Aceptar
        cy.get('#didomi-notice-agree-button')
          .filter(':visible')
          .click({ force: true });
        
        // 2. Esperar a que el modal desaparezca del DOM
        cy.get('#didomi-notice-agree-button').should('not.exist', { timeout: 5000 });
      }
    });
  }

  isLoaded() {
    this.searchBar.should('be.visible');
  }

  // --------------------------------------
  // ✅ searchCity actualizado (tu versión + mejoras)
  // --------------------------------------
  searchCity(city) {

    this.searchBar.should('be.visible');

    // Espera fuerte hasta que realmente NO esté deshabilitado
    this.searchBar.should(($el) => {
      expect($el).to.be.visible;
      expect($el).not.to.have.attr('disabled');
      expect($el).not.to.have.attr('aria-disabled', 'true');
      expect($el).not.to.have.class('is-disabled');
    });

    // Click previo que resuelve la mayoría de errores
    this.searchBar.click({ force: true });

    // Escribir la ciudad
    this.searchBar.clear().type(city, { delay: 100 });

    // Esperar la primera sugerencia visible
    this.firstSuggestion.should('be.visible');

    // Seleccionar con Enter
    this.searchBar.type('{enter}');
  }
}

export default new HomePage();
