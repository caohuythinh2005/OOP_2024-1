package automation;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SeleniumAutomationTool implements AutomationTool {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private int timeout;
    private ChromeOptions options;

    public SeleniumAutomationTool(int timeout) {
        options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        this.timeout = timeout;
    }

    @Override
    public void open() {
        this.driver = new ChromeDriver(options);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        this.js = (JavascriptExecutor) driver;
        System.out.println("dang mo....");
    }

    @Override
    public void close() {
        this.driver.close();
    }

    @Override
    public List<Object> findElements(Object parentElement, String locatorType, String locatorValue) {
        if (parentElement instanceof WebElement) {
            By by = convertToBy(locatorType, locatorValue);
            return ((WebElement) parentElement).findElements(by)
                    .stream()
                    .map(element -> (Object) element)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }


    @Override
    public List<Object> findElements(String locatorType, String locatorValue) {
        By by = convertToBy(locatorType, locatorValue);
        return driver.findElements(by)
                .stream()
                .map(element -> (Object) element)
                .collect(Collectors.toList());
    }

    @Override
    public void navigateTo(String url) {
        driver.get(url);
    }

    @Override
    public String getAttribute(Object element, String attribute) {
        if (element instanceof WebElement) {
            return ((WebElement) element).getAttribute(attribute);
        }
        return null;
    }

    @Override
    public String getText(Object element) {
        if (element instanceof WebElement) {
            return ((WebElement) element).getText();
        }
        return null;
    }

    @Override
    public void scroll(int x, int y) {
        js.executeScript(String.format("window.scrollBy(%d, %d);", x, y));
    }

    @Override
    public Object waitForElement(String locatorType, String locatorValue) {
        try {
            By by = convertToBy(locatorType, locatorValue);
            return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Object> waitForElements(String locatorType, String locatorValue) {
        By by = convertToBy(locatorType, locatorValue);
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by))
                .stream()
                .map(element -> (Object) element)
                .collect(Collectors.toList());
    }

    @Override
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Override
    public Object executeScript(String script) {
        return js.executeScript(script);
    }

    @Override
    public void sendKeys(Object element, String text) {
        if (element instanceof WebElement) {
            ((WebElement) element).sendKeys(text);
        }
    }

    @Override
    public void sendEnter(Object element) {
        if (element instanceof WebElement) {
            ((WebElement) element).sendKeys(org.openqa.selenium.Keys.ENTER);
        }
    }

    @Override
    public int getTimeout() {
        return timeout;
    }

    private By convertToBy(String locatorType, String locatorValue) {
        switch (locatorType) {
            case "id":
                return By.id(locatorValue);
            case "xpath":
                return By.xpath(locatorValue);
            case "css":
                return By.cssSelector(locatorValue);
            default:
                throw new IllegalArgumentException("Unsupported locator type: " + locatorType);
        }
    }

}