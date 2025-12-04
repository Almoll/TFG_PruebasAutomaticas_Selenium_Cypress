class DetailPage {

    // ==========================================
    //  SELECTORES
    // ==========================================
    // Selectores equivalentes a tu DetailPage.java
    get priceElement() { return cy.get("span[class*='price'], div[class*='price']"); }

    // ==========================================
    //  MÉTODOS
    // ==========================================

    isLoaded() {
        cy.log('Esperando carga del detalle del inmueble (Precio visible)...');
        // Aumentamos el timeout a 20000ms (20s) para coincidir con tu Java
        this.priceElement.should('be.visible', { timeout: 20000 });
    }

    getPrice() {
        return this.priceElement.invoke('text');
    }
}

export default new DetailPage();