import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebPageScanner2 {

    public static void main(String[] args) {
        // Set the path to your WebDriver executable
        System.setProperty("webdriver.chrome.driver", "chromedriver1.exe");
        // Initialize the WebDriver
        WebDriver driver = new ChromeDriver();
        
        // Use FirefoxDriver
        // System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
        // WebDriver driver = new FirefoxDriver();

        // URL to capture locators from
    //   String url = "https://webapps.tekstac.com/SeleniumApp2/Library/LibraryCard.html";
        
        String url =    "https://capgemini.tekstac.com/login/index.php";

        // Navigate to the URL
        driver.get(url);

        // Capture locators
        List<Map<String, String>> locators = captureLocators(driver);

        // Print captured locators
        for (Map<String, String> locator : locators) {
            System.out.println(locator);
        }

        // Send value to the "First Name" text box
     ///   sendValueToField(driver, locators, "first", "John");

        // Send value to the "Last Name" text box
        sendValueToField(driver, locators, "Username", "Doe");

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
            locator.put("type", element.getAttribute("type"));
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

    public static void sendValueToField(WebDriver driver, List<Map<String, String>> locators, String fieldName, String value) {
        // Compile the regex pattern
        Pattern pattern = Pattern.compile(fieldName, Pattern.CASE_INSENSITIVE);

        for (Map<String, String> locator : locators) {
            boolean matchFound = false;
            // Check if the tag is "input" and type is "text"
            if ("input".equals(locator.get("tag")) && "text".equals(locator.get("type"))) {
                // Check if the id attribute matches the given field name pattern
                String id = locator.get("id");
                if (id != null) {
                    Matcher matcher = pattern.matcher(id);
                    if (matcher.find()) {
                        matchFound = true;
                    }
                }

                // If id didn't match, check other attributes
                if (!matchFound) {
                    for (Map.Entry<String, String> entry : locator.entrySet()) {
                        String attributeValue = entry.getValue();
                        if (attributeValue != null) {
                            Matcher matcher = pattern.matcher(attributeValue);
                            if (matcher.find()) {
                                matchFound = true;
                                break;
                            }
                        }
                    }
                }
            }

            if (matchFound) {
                String xpath = locator.get("xpath");
                if (xpath != null) {
                    WebElement field = driver.findElement(By.xpath(xpath));
                    field.clear(); // Clear the field before entering new value
                    field.sendKeys(value);
                    return; // Exit after setting the value
                }
            }
        }

        // If no matching locator is found, print a message
        System.out.println("Locator for the field matching '" + fieldName + "' not found.");
    }
}
