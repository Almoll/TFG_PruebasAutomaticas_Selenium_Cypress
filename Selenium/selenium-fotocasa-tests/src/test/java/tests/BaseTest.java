package tests;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;
import java.util.Map;

public class BaseTest {

    protected WebDriver driver;

    @BeforeEach
    public void setUp() {

        ChromeOptions options = new ChromeOptions();

        // --- 1. Oculta que es automation ---
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);

        // --- 2. Camufla propiedades internas ---
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        // --- 3. User-Agent humano (no detectado) ---
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");

        // --- 4. Desactiva cabeceras sospechosas ---
        options.addArguments("--disable-blink-features=AutomationControlled");

        // --- 5. Tamaño realista de usuario ---
        options.addArguments("--window-size=1400,1000");

        // --- 6. (Opcional) Quitar info de webdriver ---
        System.setProperty("webdriver.chrome.silentOutput", "true");

        driver = new ChromeDriver(options);

        // --- 7. Inyección JS anti-detector ---
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript(
                "Object.defineProperty(navigator, 'webdriver', {get: () => false})"
        );

        js.executeScript(
                "Object.defineProperty(navigator, 'plugins', {get: () => [1,2,3,4]})"
        );

        js.executeScript(
                "Object.defineProperty(navigator, 'languages', {get: () => ['es-ES', 'es']})"
        );

        js.executeScript(
                "Object.defineProperty(navigator, 'mimeTypes', {get: () => [1,2,3]})"
        );
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
