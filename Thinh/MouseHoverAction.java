import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class MouseHoverAction {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("http://demo.opencart.com/");
        driver.manage().window().maximize();

        WebElement desktops = driver.findElement(By.xpath("//a[normalize-space()='Desktops'])"));
        System.out.println("rrrrrrrrrrrrr");
        WebElement mac = driver.findElement(By.xpath("//a[normalize-space()='Mac (1)']"));
        System.out.println("tttttttttttt");
        Actions act = new Actions(driver);

        // Mouse hover
        act.moveToElement(desktops).moveToElement(mac).build().perform();
    }
}

