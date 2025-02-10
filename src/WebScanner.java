import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebScanner {

    public static void main(String[] args) {
        // Set the path to your WebDriver executable
        System.setProperty("webdriver.chrome.driver", "chromedriver1.exe");
        // Initialize the WebDriver
        WebDriver driver = new ChromeDriver();
        
        // Use FirefoxDriver
        // System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
        // WebDriver driver = new FirefoxDriver();

        // URL to capture locators from
        String url = "https://webapps.tekstac.com/SeleniumApp2/Library/LibraryCard.html";

        // Navigate to the URL
        driver.get(url);

        // Capture locators
        List<Map<String, String>> locators = captureLocators(driver);

        // Print captured locators
        for (Map<String, String> locator : locators) {
            System.out.println(locator);
        }

        // Send value to the "First Name" text box
        sendValueToFirstName(driver, locators, "John");

        // Close the WebDriver
     //   driver.quit();
    }

    public static List<Map<String, String>> captureLocators(WebDriver driver) {
        List<Map<String, String>> locators = new ArrayList<>();

        // Find all elements
        List<WebElement> elements = driver.findElements(By.cssSelector("*"));

        for (WebElement element : elements) {
            Map<String, String> locator = new HashMap<>();
            locator.put("tag", element.getTagName());
            locator.put("id", element.getAttribute("id"));
            locator.put("class", element.getAttribute("class"));
            locator.put("name", element.getAttribute("name"));
            locator.put("xpath", generateXPath(element, driver));

            locators.add(locator);
        }

        return locators;
    }

    public static String generateXPath(WebElement element, WebDriver driver) {
        String xpath = "";
        while (element != null) {
            int index = getIndex(element);
            String tag = element.getTagName();
            xpath = "/" + tag + "[" + index + "]" + xpath;
            try {
                element = element.findElement(By.xpath(".."));
            } catch (Exception e) {
                element = null;
            }
        }
        return xpath;
    }

    private static int getIndex(WebElement element) {
        int index = 1;
        try {
            List<WebElement> siblings = element.findElements(By.xpath("preceding-sibling::" + element.getTagName()));
            index += siblings.size();
        } catch (Exception e) {
            // Do nothing, reached the start of the siblings
        }
        return index;
    }

    public static void sendValueToFirstName(WebDriver driver, List<Map<String, String>> locators, String value) {
        // Find the locator for the "First Name" text box
        String firstNameLocator = null;
        for (Map<String, String> locator : locators) {
            if ("input".equals(locator.get("tag")) && "field1".equals(locator.get("name"))) {
                firstNameLocator = locator.get("xpath");
                break;
            }
        }

        // If the locator is found, send the value to the text box
        if (firstNameLocator != null) {
            WebElement firstNameTextBox = driver.findElement(By.xpath(firstNameLocator));
            firstNameTextBox.sendKeys(value);
        } else {
            System.out.println("Locator for 'First Name' text box not found.");
        }
    }
}
