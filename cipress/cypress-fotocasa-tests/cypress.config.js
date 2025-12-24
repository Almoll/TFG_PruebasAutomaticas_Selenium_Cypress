const { defineConfig } = require("cypress");
const allureWriter = require("@shelex/cypress-allure-plugin/writer");

module.exports = defineConfig({
  e2e: {
    baseUrl: 'https://www.fotocasa.es',
    viewportWidth: 1400,
    viewportHeight: 1000,
    userAgent: 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36',

    setupNodeEvents(on, config) {
      allureWriter(on, config);   
      return config;              
    },
  },
});
