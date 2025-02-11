package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.ElementNotInteractableException;

public class LoginPage {

    private WebDriver driver;
    private static final Logger logger = LogManager.getLogger(LoginPage.class);
    
	public LoginPage() {
	}

    // Using @FindBy annotations for locating elements
    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "signIn")
    private WebElement signInButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void enterUsername(String username) {
        try {
        //    logger.info("Entering username: {}", username);
            usernameField.sendKeys(username);
        } catch (NoSuchElementException | ElementNotInteractableException e) {
          //  logger.error("Error entering username '{}': {}", username, e.getMessage());
        }
    }

    public void enterPassword(String password) {
        try {
         //   logger.info("Entering password.");
            passwordField.sendKeys(password);
        } catch (NoSuchElementException | ElementNotInteractableException e) {
         //   logger.error("Error entering password: {}", e.getMessage());
        }
    }

    public void clickSigin() {
        try {
         //   logger.info("Clicking on 'Sign In' button.");
            signInButton.click();
        } catch (NoSuchElementException | ElementNotInteractableException e) {
          //  logger.error("Error clicking 'Sign In' button: {}", e.getMessage());
        }
    }
}

