package automation;

import java.util.List;

public interface AutomationTool {
    void open();

    void close();

    List<Object> findElements(String locatorType, String locatorValue);

    List<Object> findElements(Object parentElement, String locatorType, String locatorValue);

    void navigateTo(String url);

    String getAttribute(Object element, String attribute);

    String getText(Object element);

    void scroll(int x, int y);

    Object waitForElement(String locatorType, String locatorValue);

    List<Object> waitForElements(String locatorType, String locatorValue);

    String getCurrentUrl();

    Object executeScript(String script);

    void sendKeys(Object element, String text);

    void sendEnter(Object element);

    int getTimeout();
}