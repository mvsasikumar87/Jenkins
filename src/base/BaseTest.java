package base;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BaseTest {

	public static WebDriver driver;
	public static WebDriver setUp() {

		System.setProperty("webdriver.chrome.driver", "chromedriver2.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
//		options.addArguments("--disable-gpu");
//		options.addArguments("--window-size=1920,1080");
		WebDriver driver = new ChromeDriver(options);
		return driver;
	}


	public static void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

}

