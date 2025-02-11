package pages;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;

public class StudentPage {
    private WebDriver driver;
    private static final Logger logger = LogManager.getLogger(StudentPage.class);

    @FindBy(id = "student")
    private WebElement studentButton;

    @FindBy(xpath = "//span[text()=' Register ']")
    private WebElement registerButton;

    @FindBy(id = "studentName")
    private WebElement studentNameField;

    @FindBy(id = "mobileNumber")
    private WebElement mobileNumberField;

    @FindBy(id = "emailId")
    private WebElement emailField;

    @FindBy(id = "cgpa")
    private WebElement cgpaField;

    @FindBy(id = "deptName")
    private WebElement deptNameField;

    @FindBy(id = "backlogCount")
    private WebElement backlogCountField;

    @FindBy(xpath = "//span[text()='Register']")
    private WebElement submitButton;

    @FindBy(id = "result")
    private WebElement resultText;

    @FindBy(id = "viewAllStudentLink")
    private WebElement viewAllStudentLink;

    @FindBy(id = "viewByEmailIdLink")
    private WebElement viewByEmailId;

    @FindBy(id = "emailId")
    private WebElement searchEmail;

    @FindBy(xpath = "//span[text()='Search']")
    private WebElement searchButton;

    public StudentPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickStudent() {
        try {
       //     logger.info("Clicking on 'Student' button.");
            studentButton.click();
        } catch (NoSuchElementException | ElementNotInteractableException e) {
         //   logger.error("Error clicking 'Student' button: {}", e.getMessage());
        }
    }

    public void clickRegister() {
        try {
         //   logger.info("Clicking on 'Register' button.");
            registerButton.click();
        } catch (NoSuchElementException | ElementNotInteractableException e) {
         //   logger.error("Error clicking 'Register' button: {}", e.getMessage());
        }
    }

    public void fillStudentDetails(String name, String mobile, String email, String cgpa, String dept, String backlog) {
        try {
          
            studentNameField.sendKeys(name);
            mobileNumberField.sendKeys(mobile);
            emailField.sendKeys(email);
            cgpaField.sendKeys(cgpa);
            deptNameField.sendKeys(dept);
            backlogCountField.sendKeys(backlog);
        } catch (NoSuchElementException | ElementNotInteractableException e) {
         //   logger.error("Error filling student details: {}", e.getMessage());
        }
    }

    public void submitRegistrationForm() {
        try {
          //  logger.info("Submitting student registration form.");
            submitButton.click();
        } catch (NoSuchElementException | ElementNotInteractableException e) {
       //     logger.error("Error submitting student registration form: {}", e.getMessage());
        }
    }

    public String getResult() throws Throwable {
    
        try {
        	JavascriptExecutor js = (JavascriptExecutor) driver; 
        	js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        	Thread.sleep(3000);
            String result = resultText.getText();
        //    logger.info("Result text obtained: {}", result);
            return result;
        } catch (NoSuchElementException e) {
         //   logger.error("Error fetching result text: {}", e.getMessage());
            return "Error fetching result";
        }
    }

    public void clickViewAllStudent() {
        try {
         //   logger.info("Clicking on 'View All Students' link.");
            viewAllStudentLink.click();
        } catch (NoSuchElementException | ElementNotInteractableException e) {
         //   logger.error("Error clicking 'View All Students' link: {}", e.getMessage());
        }
    }

    public List<String> getStudentDetails(String studentId) {
       // logger.info("Retrieving details for student ID: {}", studentId);
        try {
            List<WebElement> rows = driver.findElements(By.tagName("tr"));

            for (WebElement row : rows) {
                List<WebElement> columns = row.findElements(By.tagName("td"));
                if (!columns.isEmpty()) {
                    String id = columns.get(0).getText();
                    if (id.equals(studentId)) {
                        List<String> rowData = new ArrayList<>();
                        for (WebElement column : columns) {
                            rowData.add(column.getText());
                        }
                      //  logger.info("Student details found: {}", rowData);
                        return rowData;
                    }
                }
            }
        } catch (NoSuchElementException | StaleElementReferenceException e) {
         //   logger.error("Error retrieving student details: {}", e.getMessage());
        }

        //logger.warn("No student found with ID: {}", studentId);
        return new ArrayList<>();
    }

    public void clickViewByEmailId() {
        try {
          //  logger.info("Clicking on 'View By Email ID' link.");
            viewByEmailId.click();
        } catch (NoSuchElementException | ElementNotInteractableException e) {
          //  logger.error("Error clicking 'View By Email ID' link: {}", e.getMessage());
        }
    }

    public void searchEmailId(String emailId) {
        try {
            //logger.info("Searching for student with Email ID: {}", emailId);
            Select s = new Select(searchEmail);
            s.selectByVisibleText(emailId);
        } catch (NoSuchElementException | ElementNotInteractableException e) {
           // logger.error("Error selecting email ID '{}': {}", emailId, e.getMessage());
        }
    }

    public void clickSearchButton() {
        try {
        //    logger.info("Clicking on 'Search' button.");
            searchButton.click();
        } catch (NoSuchElementException | ElementNotInteractableException e) {
         //   logger.error("Error clicking 'Search' button: {}", e.getMessage());
        }
    }

    public List<String> getResultUsingEmailId() {
       // logger.info("Fetching student details using Email ID search.");
        List<String> result = new ArrayList<>();
        try {
            List<WebElement> elements = driver.findElements(By.xpath("//table[@id='viewTable']/tr/td"));

            if (elements.isEmpty()) {
           //     logger.warn("No student records found for the given email.");
                return result;
            }

            for (WebElement ele : elements) {
                String text = ele.getText();
                result.add(text);
            }

          //  logger.info("Search result: {}", result);
        } catch (NoSuchElementException | StaleElementReferenceException e) {
          //  logger.error("Error fetching search results: {}", e.getMessage());
        }
        return result;
    }
}

