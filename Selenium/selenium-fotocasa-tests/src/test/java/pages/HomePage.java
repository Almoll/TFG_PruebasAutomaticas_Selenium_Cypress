package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;

public class HomePage extends BasePage {
    //private final By searchBar = By.xpath("//input[@type='search' or @role='searchbox']");

    private final By cookiesButton = By.id("didomi-notice-agree-button"); // puede cambiar
    private final By searchBar = By.cssSelector("input[aria-label='Buscar']");




    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get("https://www.fotocasa.es/");
    }

    public void acceptCookiesIfPresent() {
        try {
            WebElement btn = driver.findElement(cookiesButton);
            btn.click();
        } catch (Exception e) {
            // Si no aparece, no hacemos nada
        }
    }

    public boolean isLoaded() {
        try {
            return driver.findElement(searchBar).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    public void searchCity(String city) {
        driver.findElement(searchBar).sendKeys(city);

        // Pausa breve para que aparezca el autosugerido (opcional)
        try { Thread.sleep(800); } catch (Exception ignored) {}

        driver.findElement(searchBar).sendKeys(Keys.ARROW_DOWN);
        driver.findElement(searchBar).sendKeys(Keys.ENTER);
    }


}
