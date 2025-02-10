import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebPageScanner1 {

    private WebDriver driver;
    private Map<String, By> locators;

    public WebPageScanner1() {
        // Set up the WebDriver (assume ChromeDriver for this example)
        System.setProperty("webdriver.chrome.driver", "chromedriver1.exe");
        this.driver = new ChromeDriver();
        this.locators = new HashMap<>();
    }

    public void scanPage(String url) {
        driver.get(url);
        List<WebElement> labels = driver.findElements(By.tagName("label"));
        
        for (WebElement label : labels) {
            String labelText = label.getText().toLowerCase().replace(" ", "_");
            WebElement inputElement = findAssociatedInput(label);
            if (inputElement != null) {
                locators.put(labelText, By.id(inputElement.getAttribute("id")));
            }
        }
    }

    private WebElement findAssociatedInput(WebElement label) {
        // Assuming label has a 'for' attribute linking it to an input element
        String forAttribute = label.getAttribute("for");
        if (forAttribute != null && !forAttribute.isEmpty()) {
            return driver.findElement(By.id(forAttribute));
        }
        // Fallback: Check for sibling input elements
        WebElement parent = label.findElement(By.xpath(".."));
        List<WebElement> inputs = parent.findElements(By.tagName("input"));
        if (!inputs.isEmpty()) {
            return inputs.get(0);
        }
        return null;
    }

    public void enterValue(String label, String value) {
        By locator = locators.get(label.toLowerCase().replace(" ", "_"));
        if (locator != null) {
            WebElement inputField = driver.findElement(locator);
            inputField.sendKeys(value);
        } else {
            System.out.println("No locator found for label: " + label);
        }
    }

    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }

    public static void main(String[] args) {
        WebPageScanner1 scanner = new WebPageScanner1();
        scanner.scanPage("https://webapps.tekstac.com/SeleniumApp1/SmartUniversity/add_stud.html");

        scanner.enterValue("first name", "John");
        scanner.enterValue("last name", "Doe");

       // scanner.close();
    }
}
