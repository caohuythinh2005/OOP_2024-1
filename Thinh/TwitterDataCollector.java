import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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

public class TwitterDataCollector {
    private WebDriver driver;
    private WebDriverWait wait;

    public TwitterDataCollector(TwitterBot bot) {
        this.driver = bot.getDriver();
    }
    public void searchUser() {

    }

    public void searchHashtag(String hashtag) {
        try {
            // Mở trang tìm kiếm hashtag
            String searchUrl = "https://x.com/search?q=" + hashtag + "&src=typed_query";
            driver.get(searchUrl);
            TimeUnit.SECONDS.sleep(10);
//
//            WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[autocomplete='username']")));
//            usernameInput.sendKeys(hashtag);
//            usernameInput.sendKeys(org.openqa.selenium.Keys.ENTER);

            // Lấy danh sách các tweet
            List<WebElement> tweets = driver.findElements(By.cssSelector("article"));
            System.out.println("Danh sách người dùng có liên quan đến hashtag " + hashtag + ":");
//
//            // Duyệt qua các tweet và thu thập thông tin người dùng
            for (WebElement tweet : tweets) {
                try {
                    WebElement user1 = tweet.findElement(By.cssSelector("div[dir='ltr'] span"));
                    String username1 = user1.getText();
                    System.out.println("User: " + username1);

                    // Tìm số lượng tương tác (lượt like/retweet) nếu có
//                    List<WebElement> interactions = tweet.findElements(By.cssSelector("div[data-testid='like'], div[data-testid='retweet']"));
//                    for (WebElement interaction : interactions) {
//                        System.out.println("Interaction: " + interaction.getText());
//                    }

                } catch (Exception e) {
                    System.out.println("Không thể lấy thông tin từ tweet này.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
