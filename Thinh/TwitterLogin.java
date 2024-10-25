import org.bouncycastle.crypto.prng.drbg.DualECPoints;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;



public class TwitterLogin {
    private WebDriver driver;
    private WebDriverWait wait;


    public TwitterLogin(TwitterBot bot) {
        // System.setProperty("chromedriver.exe", )
        this.driver = bot.getDriver();
        this.wait = bot.getWait();
    }

    public void login(String username, String user, String password) {
        try {
            // Mở trang đăng nhập Twitter
            String url = "https://twitter.com/i/flow/login";
            driver.get(url);

            // Nhập tên đăng nhập
            WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[autocomplete='username']")));
            usernameInput.sendKeys(username);
            usernameInput.sendKeys(org.openqa.selenium.Keys.ENTER);
            // Nhập tên tài khoản (nếu cần)
            WebElement userInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[autocomplete='on']")));
            userInput.sendKeys(user);
            userInput.sendKeys(org.openqa.selenium.Keys.ENTER);
            // Nhập mật khẩu
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='password']")));
            passwordInput.sendKeys(password);
            passwordInput.sendKeys(org.openqa.selenium.Keys.ENTER);

            // Đợi thêm thời gian để hoàn tất đăng nhập
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public List<String> searchHashtag(String hashtag) {
        List<String> kolList = new ArrayList<String>();
        kolList.add("eee");
        return kolList;
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
