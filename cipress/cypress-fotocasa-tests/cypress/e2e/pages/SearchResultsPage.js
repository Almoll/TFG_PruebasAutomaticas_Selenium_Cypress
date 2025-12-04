import 'cypress-xpath';

class SearchResultsPage {

    // ==========================================
    //  SELECTORES
    // ==========================================

    get resultsTitle() { return cy.get('h1.text-headline-2'); }
    get resultCard() { return cy.get('article'); }
    get closePopupBtn() { return cy.get('button.sui-MoleculeModal-close'); }
    get priceElements() { return cy.get("span[class*='price'], div[class*='price']"); }

    // --- FILTRO DE PRECIO ---
    get priceFilterButton() { return cy.get('#filters-bar-filter-price'); }
    get minPriceDropdownContainer() { return cy.get('div.sui-MoleculeSelect div.sui-MoleculeSelect-inputSelect-container'); }
    get popoverApplyButton() { return cy.get('.sui-MoleculeSelectPopover-popoverActionBar button'); }


    // --- FILTROS AVANZADOS (MODAL) ---
    get mainFiltersButton() { return cy.get('button.re-SearchFiltersTop-filtersButton'); }
    get districtDropdown() { return cy.get('.sui-MoleculeModal-dialog').find('div[title="Distrito"]'); } 
    get arganzuelaOption() { return cy.get("a[title='Arganzuela']"); }
    get transactionTypeDropdown() { return cy.get(".re-FiltersFilterTransactionType .sui-MoleculeSelect-inputSelect-container"); }
    get rentOption() { return cy.xpath("//li[./span[normalize-space(text())='Alquilar']]"); }
    get longTermRentalCheckbox() { return cy.get("label[for='filter-rental-duration-LONG_TERM']"); }
    get elevatorFeatureButton() { return cy.xpath("//button[.//span[text()='Ascensor']]"); }
    get finalApplyButton() { return cy.xpath("//footer//button[contains(normalize-space(.), 'Mostrar')]"); }

    


    // ==========================================
    //  MÉTODOS DE INTERACCIÓN
    // ==========================================

    isLoaded() {
        this.resultsTitle.should('be.visible');
        this.resultCard.should('have.length.at.least', 1);
    }

    getTitleText(){
        cy.get(this.resultsTitle).should('have.text', 'Texto esperado');


    }

    closeNotificationsPopupIfPresent() {
        cy.get('body').then(($body) => {
            // Buscamos el botón de cierre del modal por el selector común
            const closeModalButtonSelector = 'button.sui-MoleculeModal-close';

            if ($body.find(closeModalButtonSelector).length > 0) {
                // Si el botón existe, lo forzamos a cerrarse.
                cy.get(closeModalButtonSelector)
                    .filter(':visible')
                    .click({ force: true });
                
                // Agregamos una espera explícita para que el modal desaparezca completamente
                cy.get(closeModalButtonSelector).should('not.exist', { timeout: 5000 });
                cy.log('Popup de notificaciones/alertas cerrado.');
            } else {
                cy.log('No se encontró el popup de notificaciones/alertas.');
            }
        });
    }

    // ---------------------------
    // FILTRO DE PRECIO
    // ---------------------------
    openMinPriceDropdown() {
        this.priceFilterButton.click(); 
        this.minPriceDropdownContainer.eq(0).should('be.visible').click(); 
    }

    selectMinPrice(value) {
        cy.get(`li.sui-MoleculeDropdownOption[data-value='${value}']`)
          .filter(':visible') 
          .click({ force: true });
    }

    applyFilters() {
        this.popoverApplyButton.click();
        this.popoverApplyButton.should('not.exist');
    }

    allResultsAreAbove(minPrice) {
        this.priceElements.each(($el) => {
            const text = $el.text();
            const cleanText = text.replace(/\./g, '').match(/\d+/);
            
            if (cleanText) {
                const priceValue = parseInt(cleanText[0], 10);
                expect(priceValue).to.be.gte(minPrice);
            }
        });
    }

    // ---------------------------
    // FILTROS AVANZADOS (CORREGIDO)
    // ---------------------------
    openAllFiltersModal() {
        
        this.mainFiltersButton
            .should('exist')
            .and('not.be.disabled')
            .click({ force: true });
            
        // Espera a que el modal (el diálogo principal) se abra
        cy.get('.sui-MoleculeModal-dialog').should('be.visible');
    }

    selectDistrictArganzuela() {
        this.districtDropdown
            .should('be.visible') // Aseguramos visibilidad
            .click();

        this.arganzuelaOption
            .should('be.visible')
            .scrollIntoView()
            .click();

        // Aplicamos el filtro en el POP-UP del distrito y volvemos al modal principal.
        cy.get('.sui-MoleculeSelectPopover-popoverActionBar button')
            .should('be.visible')
            .click({ force: true }); 
    }

    selectTransactionTypeRent() {
        this.transactionTypeDropdown
            .scrollIntoView()
            .should('be.visible')
            .click({ force: true }); // Click forzado en el dropdown.

        this.rentOption
            .should('be.visible')
            .click(); // Clic en la opción 'Alquilar'.
        cy.log('Filtro de Transacción (Alquiler) aplicado.');
    }

    selectLongTermRental() {
        this.longTermRentalCheckbox
            .scrollIntoView()
            .click();
        cy.log('Filtro de Larga duración aplicado.');
    }

    selectElevatorFeature() {
        this.elevatorFeatureButton
            .scrollIntoView({ block: 'center' }) 
            .should('be.visible'); 
          
        this.elevatorFeatureButton.click({ force: true });
        cy.log('Filtro de Ascensor aplicado.');
    }

    applyFinalFiltersAndSearch() {
        this.finalApplyButton
            .should('be.visible')
            .click();
        
        // Esperamos a que el MODAL PRINCIPAL desaparezca antes de continuar con la validación.
        cy.get('.sui-MoleculeModal-dialog', { timeout: 10000 }).should('not.exist');
        cy.log('Botón final clickeado. Modal cerrado.');
    
    }
    
    goToPage(pageNumber) {
        cy.log(`Navegando a la página ${pageNumber}...`);

        this.resultCard.last().scrollIntoView();

        cy.get("nav[data-panot-component='pagination']", { timeout: 10000 })
            .should('be.visible');

        cy.get(`a[aria-label='Página ${pageNumber}']`)
            .scrollIntoView({ block: 'center' }) 
            .should('be.visible')
            .click();
    }

    isOnPage(pageNumber) {
        cy.log(`Verificando que estamos en la página ${pageNumber}`);
        const activePageSelector = `a[aria-label='Página ${pageNumber}'][aria-current='page']`;
        
        // La aserción 'should('be.visible')' actúa como la validación final del `assertTrue` de Selenium.
        cy.get(activePageSelector).should('be.visible');
    }

    hasResults() {
        return this.resultCard.should('have.length.gt', 0);
    }

    // Abre el primer resultado forzando que sea en la MISMA pestaña
    openFirstResult() {
        cy.log('Abriendo el primer resultado…');

        cy.get('article').first().within(() => {
        // Buscar el enlace seguro del anuncio
            cy.get("a[href*='/es/']")
              .filter(':visible')
              .first()
              .scrollIntoView()
              .invoke('removeAttr', 'target')
              .click({ force: true });
        });

       // Asegurar navegación
       cy.url({ timeout: 10000 }).should('include', '/es/');
    }
}


export default new SearchResultsPage();