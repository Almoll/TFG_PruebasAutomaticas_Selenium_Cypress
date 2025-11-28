

class SearchResultsPage {
    // --- SELECTORES (Mapeo de los By.cssSelector/By.xpath a getters de Cypress) ---
    
    // Selectores Comunes
    get resultsTitle() { return cy.get('h1.text-headline-2'); }
    get resultCard() { return cy.get('article'); }
    get closePopupBtn() { return cy.get('button.sui-MoleculeModal-close'); }
    get priceElements() { return cy.get("span[class*='price'], div[class*='price']"); }
    get firstResultLink() { return cy.get('article a[href*="/es/"]').first(); }

    // Selectores de Filtro de Precio
    get priceFilterButton() { return cy.get('#filters-bar-filter-price'); }
    get minPriceDropdown() { return cy.get("div.sui-MoleculeSelect-inputSelect-container").first(); }
    minPriceOption(value) { 
        return cy.get(`li.sui-MoleculeDropdownOption[data-value="${value}"]`); 
    }
    get popoverApplyButton() { return cy.get(".sui-MoleculeSelectPopover-popoverActionBar button"); }
    
    // Selectores del Modal de Filtros Avanzados
    get mainFiltersButton() { return cy.get("button.re-SearchFiltersTop-filtersButton"); }
    get finalApplyButton() { return cy.xpath("//footer//button[contains(normalize-space(.), 'Mostrar')]"); }
    get modalActionButton() { 
        return cy.get("div.sui-MoleculeModal-footer button.sui-AtomButton--primary, .sui-MoleculeSelectPopover-popoverActionBar button"); 
    }
    
    // Selectores de filtros específicos
    get districtDropdown() { return cy.get("#search-geographic-select-popover-\\:rt\\:"); }
    get arganzuelaOption() { return cy.get("a[title='Arganzuela']"); }
    get transactionTypeDropdown() { return cy.get(".re-FiltersFilterTransactionType .sui-MoleculeSelect-inputSelect-container"); }
    get longTermRentalCheckbox() { return cy.get("label[for='filter-rental-duration-LONG_TERM']"); }
    get extrasTitle() { return cy.xpath("//div[text()='Extras']"); }
    get elevatorFeatureButton() { return cy.xpath("//button[.//span[text()='Ascensor']]"); }
    
    // --- MÉTODOS DE LA PÁGINA ---
    
    isLoaded() {
        // Cypress espera automáticamente a que estos elementos sean visibles
        this.resultsTitle.should('be.visible');
        this.resultCard.should('exist');
    }

    getTitleText() {
        // .invoke('text') obtiene el texto de jQuery
        return this.resultsTitle.invoke('text');
    }

    hasResults() {
        // Verificar que hay al menos 1 tarjeta de resultado
        return this.resultCard.should('have.length.at.least', 1);
    }
    
    closeNotificationsPopupIfPresent() {
        // En Cypress, usamos .get('body').find() para manejar el "if present"
        cy.get('body').then(($body) => {
            if ($body.find('button.sui-MoleculeModal-close').length > 0) {
                this.closePopupBtn.click({ force: true });
            }
        });
    }

    // Nota: El método openFirstResult() requiere manejo de múltiples pestañas, 

    // FILTRO DE PRECIO 💰
    openMinPriceDropdown() {
        this.priceFilterButton.click();
        // Usamos { multiple: true } ya que hay varios selectores de tipo input-select
        this.minPriceDropdown.click({ multiple: true });
    }

    selectMinPrice(value) {
        this.minPriceOption(value).click({ force: true });
    }

    applyFilters() {
        this.popoverApplyButton.click({ force: true });
    }
    
    // VALIDAR EL PRECIO (Conversión a aserción Cypress)
    allResultsAreAbove(minPrice) {
        // 1. Obtener todos los elementos de precio
        this.priceElements.each(($priceElement) => {
            // 2. Obtener el texto, limpiar puntos y extraer solo dígitos
            const priceText = $priceElement.text();
            // Esto replica la lógica de tu regex: obtener solo números
            const digitsOnly = priceText.replace(/\./g, '').match(/\d+/); 
            
            if (digitsOnly) {
                const value = parseInt(digitsOnly[0], 10);
                // 3. Aserción: El precio debe ser mayor o igual al mínimo
                expect(value).to.be.gte(minPrice);
            }
            // Si no hay dígitos, simplemente se ignora (como tu 'continue')
        });
    }

    // PAGINACIÓN (Simplificando el JavascriptExecutor con scrollIntoView de Cypress) 🗺️
    goToPage(pageNumber) {
        // Selector: a[aria-label='Página N']
        const pageSelector = `a[aria-label='Página ${pageNumber}']`;

        // 1. Scroll: forzamos el scroll para asegurar la carga de la paginación (tu JS)
        cy.get(pageSelector).scrollIntoView({ duration: 1000, easing: 'swing' });
        
        // 2. Click
        cy.get(pageSelector).click();
    }

    isOnPage(pageNumber) {
        const selector = `a[aria-label='Página ${pageNumber}'][aria-current='page']`;
        cy.get(selector).should('be.visible');
    }
    
    // FILTROS AVANZADOS
    openAllFiltersModal() {
        this.mainFiltersButton.click();
    }

    selectDistrictArganzuela() {
        this.districtDropdown.click();
        this.arganzuelaOption.click();
        this.applyFiltersAndBack(); 
    }
    
    selectTransactionTypeRent() {
        this.transactionTypeDropdown.click();
        // Buscamos la opción "Alquilar" por el texto, es más robusto en Cypress/jQuery
        cy.xpath("//li[./span[normalize-space(text())='Alquilar']]").click();
    }

    selectLongTermRental() {
        this.longTermRentalCheckbox.click();
    }

    selectElevatorFeature() {
        // Scroll: Aseguramos que el título "Extras" esté visible
        this.extrasTitle.scrollIntoView(); 
        
        // Espera implícita de Cypress. Clicamos.
        this.elevatorFeatureButton.click();
    }

    applyFiltersAndBack() {
        this.modalActionButton.click({ force: true });
    }

    applyFinalFiltersAndSearch() {
        // En Cypress no necesitamos JSExecutor para forzar el click, 
        // usamos la opción { force: true }
        this.finalApplyButton.click({ force: true });

        // Esperar que el modal desaparezca (invisiblityOfElementLocated)
        cy.get("div.sui-MoleculeModal-dialog").should('not.exist');
    }
}

export default new SearchResultsPage();