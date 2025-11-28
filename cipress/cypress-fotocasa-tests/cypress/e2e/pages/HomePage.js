class HomePage {
  get cookiesButton() { return cy.get('#didomi-notice-agree-button'); }
  get searchBar() { return cy.get("input[aria-label='Buscar']"); }
  get firstSuggestion() { return cy.get("ul[role='listbox'] li").first(); }

  open() {
    cy.visit('/'); 
  }

  acceptCookiesIfPresent() {
    cy.get('body').then(($body) => {
      if ($body.find('#didomi-notice-agree-button').length > 0) {
        cy.get('#didomi-notice-agree-button').click({ force: true });
      }
    });
  }

  isLoaded() {
    this.searchBar.should('be.visible');
  }

  searchCity(city) {
    this.searchBar.type(city);
    this.firstSuggestion.should('be.visible');
    this.searchBar.type('{downarrow}{enter}');
  }
}

export default new HomePage();