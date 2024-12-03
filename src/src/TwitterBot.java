import org.bouncycastle.crypto.prng.drbg.DualECPoints;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class TwitterBot {
    private WebDriver driver;
    private WebDriverWait wait;

    public TwitterBot() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    private boolean checkAvailableUserInput() throws InterruptedException {
        System.out.println("Pausing for 10 seconds...");
        Thread.sleep(10000);
        try {
            WebElement element = driver.findElement(By.cssSelector("input[autocomplete='on']"));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public void login(String username, String user, String password) {
        try {
            String url = "https://twitter.com/i/flow/login";
            driver.get(url);
            WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[autocomplete='username']")));
            usernameInput.sendKeys(username);
            usernameInput.sendKeys(org.openqa.selenium.Keys.ENTER);

            if (checkAvailableUserInput()) {
                WebElement userInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[autocomplete='on']")));
                userInput.sendKeys(user);
                userInput.sendKeys(org.openqa.selenium.Keys.ENTER);
            }
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='password']")));
            passwordInput.sendKeys(password);
            passwordInput.sendKeys(org.openqa.selenium.Keys.ENTER);
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    public WebDriverWait getWait() {
        return wait;
    }

    public void close() {
        driver.quit();
    }
}
