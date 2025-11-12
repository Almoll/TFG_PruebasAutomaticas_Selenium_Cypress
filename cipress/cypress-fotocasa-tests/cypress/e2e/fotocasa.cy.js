describe('Fotocasa - Página principal', () => {
  it('Carga correctamente la home', () => {
    cy.visit('https://www.fotocasa.es')
    cy.title().should('include', 'Fotocasa')
  })
})
Cypress.on('uncaught:exception', (err, runnable) => {
  // returning false here prevents Cypress from
  // failing the test due to uncaught exceptions in the app
  return false;
});
