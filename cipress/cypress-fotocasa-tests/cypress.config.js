const { defineConfig } = require("cypress");

module.exports = defineConfig({
  e2e: {
    baseUrl: 'https://www.fotocasa.es', // Esto nos ahorra escribir la URL entera siempre
    viewportWidth: 1400,
    viewportHeight: 1000,
    userAgent: 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36',
    
    setupNodeEvents(on, config) {
      // listeners
    },
  },
});