import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TwitterBot {
    private WebDriver driver;
    private WebDriverWait wait;

    public TwitterBot() {
        // Cấu hình ChromeOptions
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized"); // toàn màn hình
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"}); // tắt thông báo Chorme đang được điều khiển bởi bot

        // Khởi tạo trình duyệt Chorme
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
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