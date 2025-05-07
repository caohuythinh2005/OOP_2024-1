package automation;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
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
        try {
            options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
            this.timeout = timeout;
        } catch (Exception e) {
            System.err.println("Error initializing ChromeOptions: " + e.getMessage());
        }
    }

    @Override
    public void open() {
        try {
            this.driver = new ChromeDriver(options);
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            this.js = (JavascriptExecutor) driver;
            System.out.println("Browser opened successfully.");
        } catch (Exception e) {
            System.err.println("Error opening browser: " + e.getMessage());
        }
    }

    @Override
    public void close() {
        try {
            if (driver != null) {
                driver.close();
                System.out.println("Browser closed successfully.");
            }
        } catch (Exception e) {
            System.err.println("Error closing browser: " + e.getMessage());
        }
    }

    @Override
    public List<Object> findElements(Object parentElement, String locatorType, String locatorValue) {
        try {
            if (parentElement instanceof WebElement) {
                By by = convertToBy(locatorType, locatorValue);
                return ((WebElement) parentElement).findElements(by)
                        .stream()
                        .map(element -> (Object) element)
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            System.err.println("Error finding elements: " + e.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public List<Object> findElements(String locatorType, String locatorValue) {
        try {
            By by = convertToBy(locatorType, locatorValue);
            return driver.findElements(by)
                    .stream()
                    .map(element -> (Object) element)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error finding elements: " + e.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public void navigateTo(String url) {
        try {
            driver.get(url);
        } catch (Exception e) {
            System.err.println("Error navigating to URL: " + e.getMessage());
        }
    }

    @Override
    public String getAttribute(Object element, String attribute) {
        try {
            if (element instanceof WebElement) {
                return ((WebElement) element).getAttribute(attribute);
            }
        } catch (Exception e) {
            System.err.println("Error getting attribute: " + e.getMessage());
        }
        return null;
    }

    @Override
    public String getText(Object element) {
        try {
            if (element instanceof WebElement) {
                return ((WebElement) element).getText();
            }
        } catch (Exception e) {
            System.err.println("Error getting text: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void scroll(int x, int y) {
        try {
            js.executeScript(String.format("window.scrollBy(%d, %d);", x, y));
        } catch (Exception e) {
            System.err.println("Error scrolling: " + e.getMessage());
        }
    }

    @Override
    public Object waitForElement(String locatorType, String locatorValue) {
        try {
            By by = convertToBy(locatorType, locatorValue);
            return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Exception e) {
            System.err.println("Error waiting for element: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Object> waitForElements(String locatorType, String locatorValue) {
        try {
            By by = convertToBy(locatorType, locatorValue);
            if (by == null) {
                // System.err.println("Error: Locator type or value is invalid.");
                return Collections.emptyList();
            }
            return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by))
                    .stream()
                    .map(element -> (Object) element)
                    .collect(Collectors.toList());
        } catch (TimeoutException e) {
            // System.err.println("Timeout waiting for elements: LocatorType=" + locatorType + ", LocatorValue=" + locatorValue);
        } catch (Exception e) {
            // System.err.println("Unexpected error waiting for elements: " + e.getMessage());
        }
        return Collections.emptyList();
    }


    @Override
    public String getCurrentUrl() {
        try {
            return driver.getCurrentUrl();
        } catch (Exception e) {
            System.err.println("Error getting current URL: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Object executeScript(String script) {
        try {
            return js.executeScript(script);
        } catch (Exception e) {
            System.err.println("Error executing script: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void sendKeys(Object element, String text) {
        try {
            if (element instanceof WebElement) {
                ((WebElement) element).sendKeys(text);
            }
        } catch (Exception e) {
            System.err.println("Error sending keys: " + e.getMessage());
        }
    }

    @Override
    public void sendEnter(Object element) {
        try {
            if (element instanceof WebElement) {
                ((WebElement) element).sendKeys(Keys.ENTER);
            }
        } catch (Exception e) {
            System.err.println("Error sending enter: " + e.getMessage());
        }
    }

    @Override
    public int getTimeout() {
        return timeout;
    }

    private By convertToBy(String locatorType, String locatorValue) {
        try {
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
        } catch (Exception e) {
            System.err.println("Error converting to By: " + e.getMessage());
            throw e;
        }
    }
}
