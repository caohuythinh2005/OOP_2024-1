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

/***
 * Đang khá phân vân việc chia class như thế này
 * Tuy nhiên, nhìn chung vẫn phải có một cái Siumulation lại các cái giả lập thao tác trước -> trừu tượng hóa
 * Một cái ưu điểm khác là có thế sử dụng tính kế thừa để thao tác
 */


public class TwitterSimulation {
    private WebDriver driver;
    private WebDriverWait wait;

    public TwitterSimulation(WebDriver driver, WebDriverWait wait) {
        /***
         * Việc khởi tạo phải nhận được driver, wait
         * Do vấn đề mình đang không thể kiểm soát được user của Chrome, mặc định nó ra ẩn danh
         * Hai cái này nhận được từ TwitterLogin
         * https://x.com/search?q=messi&src=typed_query
         */
        this.driver = driver;
        this.wait = wait;
    }

    public void searchUser(String hashtag) {
        String url = "https://x.com/search?q=" + hashtag + "&src=typed_query&f=user";
        driver.get(url);
        System.out.println("Searching for users with hashtag: " + hashtag);
    }

    public void gotoThePeopleTab() {
        try {
            //WebElement peopleTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.ByXPath()))
        } catch (Exception e) {

        }
    }
    // Cái này vấn đề thật sự, chưa rõ bằng index hay bằng link
    // Phụ thuộc vào quá trình mình thu thập
    // Hàm bên dưới là thu thập dựa trên index của tab people
    // Thực tế, hàm bên dưới chỉ search tầm 15-20 người, tuy nhiên, nếu nổi tiếng thì phải đứng đầu
    // Ví dụ Messi
    public void clickOnProfile(int userIndex) {
        try {
            List<WebElement> profiles = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("button[data-testid='UserCell']")));
            if (userIndex < profiles.size()) {
                profiles.get(userIndex).click();
                System.out.println("Clicked on the profile with userIndex : " + userIndex);
            } else {
                System.out.println("Invaild index: " + userIndex);
            }
        } catch (Exception e) {
            System.out.println("Failed to click on the profile, try another userIndex ; " + userIndex);
        }
    }


    // đếm số followers
    public int getFollowersCount() {
        int ans = 0;
        try {
            String username = "/a[@href='/" + TwitterSimulation.getUserName(this.driver.getCurrentUrl()) + "/verified_followers']";
            WebElement followers = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/leomessisite/verified_followers']")));
            System.out.println(followers.getText());
            //System.out.println(followersElement.getAttribute("outerHTML"));
            // System.out.println(followersElement.getText());

        } catch (Exception e) {
            System.out.println("Failed to get followers count");
        }
        return ans;
    }


    // user
    public static String getProfileUrl(String username) {
        return "https://x.com/" + username;
    }

    public static String getUserName(String url) {
        // Tách username từ 1 url profile (ví dụ https://x.com/leomessisite)
        String[] parts = url.split("/");
        return parts[parts.length - 1];
    }

    // tweet
    public static String getTweetUrlFromTweetID(String username, String TweetID) {
        return "https://x.com/" + username + "/status/" + TweetID;
    }

    public void searchTweet(Tweet obj) {
        String tweetUrl = getTweetUrlFromTweetID(obj.getAuthor(), obj.getTweetId());
        driver.get(tweetUrl);
    }

    // giả sử ta đã truy cập rồi
    public int getLikesCount(Tweet obj) {
        int ans = 0;
        try {
            String username = "/a[@href='/" + TwitterSimulation.getUserName(this.driver.getCurrentUrl()) + "/verified_followers']";
            WebElement followers = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/leomessisite/verified_followers']")));
            System.out.println(followers.getText());
        } catch (Exception e) {
            System.out.println("Failed to get Likes count");
        }
        return ans;
    }
}
