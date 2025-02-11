package tests;

import base.*;
import io.restassured.response.Response;
import pages.*;
import utils.*;

import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.testng.TestNG;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.testng.TestNG;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.testng.TestNG;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StudentPortalTest {

    private static final Logger logger = LogManager.getLogger(StudentPortalTest.class);

    public static WebDriver driver;
    public LoginPage loginPage;
    public StudentPage studentPage;

    public static String studentRollNo = null;
    public static String studentRegistrationResult = null;
    public static List<String> viewByEmailID;
    public static Response updateRecord;
    public static Response deleteRecord;
    public static Response verifyRecord;
    
	public StudentPortalTest() {
	}

	public StudentPortalTest(WebDriver driver) {
		this.driver = driver;
	}
    
    
    @BeforeClass
    public void setUpTest() {
        try {
         //   logger.info("Initializing the WebDriver...");
            driver = BaseTest.setUp();
            driver.get("http://webapps.tekstac.com:2121/");
         //   logger.info("Navigated to Student Portal.");

            loginPage = new LoginPage(driver);
            studentPage = new StudentPage(driver);
        } catch (Exception e) {
         //   logger.error("Error during WebDriver setup: ", e);
        }
    }

    @Test
    public void testAddStudent() throws Throwable {
        try {
         //   logger.info("Starting test: Add Student");

            loginPage.enterUsername(ConfigReader.getPropertyValue("username"));
            loginPage.enterPassword(ConfigReader.getPropertyValue("password"));
            loginPage.clickSigin();
        //    logger.info("User logged in successfully.");

            String[] excelData = null;
            try {
                excelData = ExcelReader.readExcelData("TestData.xlsx", "Sheet1");
            } catch (Exception e) {
         //       logger.error("Error reading data from Excel: ", e);
                return;
            }

            studentPage.clickStudent();
            studentPage.clickRegister();
            studentPage.fillStudentDetails(excelData[0], excelData[1], excelData[2], excelData[3], excelData[4], excelData[5]);
            studentPage.submitRegistrationForm();
       //     logger.info("Student details submitted successfully.");

            studentRegistrationResult = studentPage.getResult();
            System.out.println("studentRegistrationResult#############"+studentRegistrationResult);
            
        //    logger.info("Registration result: " + studentRegistrationResult);

            if (studentRegistrationResult.contains("Registration was successful"))
          //      logger.info("Student details are added successfully");
        //    else
          //      logger.error("Student Registration failed. Expected 'John Smith' in result.");
            
            try {
                int lastSpaceIndex = studentRegistrationResult.lastIndexOf(" ");
                studentRollNo = studentRegistrationResult.substring(lastSpaceIndex + 1).replace(".", "");
           //     logger.info("Extracted Student RollNO: " + studentRollNo);
            } catch (Exception e) {
           //     logger.error("Error extracting Student RollNo from result: ", e);
            }

            studentPage.clickViewAllStudent();
            List<String> studentDetails = studentPage.getStudentDetails(studentRollNo);
            for (String s : studentDetails) {
               // logger.info("Student Details: " + s);
            }
         //   logger.info("Studet table is verified successfully");
            studentPage.clickViewByEmailId();
            studentPage.searchEmailId(excelData[2]);
            studentPage.clickSearchButton();

            viewByEmailID = studentPage.getResultUsingEmailId();
            for (String stud : viewByEmailID) {
        //        logger.info("Student Record by Email: " + stud);
            }

         //   logger.info("Test case 'testAddStudent' completed.");
        } catch (Exception e) {
        //    logger.error("Error during 'testAddStudent': ", e);
        }
    }

    @Test
    public void testUpdateDeleteStudent() {
        try {
         //   logger.info("Starting test: Update and Delete Student");

            try {
                BaseAPI.getToken();
            //    logger.info("Token generated successfully.");
            } catch (Exception e) {
           //     logger.error("Error generating API token: ", e);
                return;
            }

            try {
            	String backlogCount="10";
            	updateRecord=BaseAPI.updateBacklog(studentRollNo, backlogCount);
                if(updateRecord.asPrettyString().contains(studentRollNo)&&updateRecord.asPrettyString().contains(backlogCount))
             //   	logger.info("Student backlogCount updation is successful");
            //    else
               // 	logger.error("Student backlogCount updation is not successful");
                logger.info("PUT response has been successfully verified");
            } catch (Exception e) {
            //    logger.error("Error updating student details: ", e);
                
            }

            try {
            	deleteRecord=BaseAPI.deleteRecord(studentRollNo);
           //     logger.info("Deleted student Roll No: " + studentRollNo);
                
            } catch (Exception e) {
           //     logger.error("Error deleting student: ", e);
            }

        try {
            verifyRecord=BaseAPI.verifyRecord();
          //  logger.info("Checking Deleted student Roll No: " + studentRollNo);
      //      if(!verifyRecord.asPrettyString().contains(studentRollNo))
           // 	logger.info("Deletion is Successful");
          //  else
           // 	logger.info("Deletion is not Successful");
        } catch (Exception e) {
          //  logger.error("Error deleting student: ", e);
        }

    } catch (Exception e) {
       // logger.error("Unexpected error in 'testUpdateDeleteStudent': ", e);
    }
    }

    @AfterClass
    public void tearDownTest() {
        try {
       //     logger.info("Closing the WebDriver...");
            BaseTest.tearDown();
       //     logger.info("Test execution completed.");
        } catch (Exception e) {
         //   logger.error("Error during WebDriver teardown: ", e);
        }
    }
    
    public static void main(String[] args) {
    	TestNG testng = new TestNG();
		testng.setTestSuites(Arrays.asList("testng.xml"));
		testng.run();
		
        if(studentRegistrationResult!=null&&studentRegistrationResult.contains("Registration was successful"))
		System.out.println("Student Registration Result" + studentRegistrationResult);
        else
        System.out.println("Student Registration Result is null. Check Student details are Registered successfully");
       
        if(studentRollNo!=null)
    	System.out.println("Student Roll Number" + studentRollNo);
        else
        System.out.println("Student Roll Number. Check Student Roll Number is extracted successfully from the result");
        if(viewByEmailID!=null)
        	System.out.println("Extracted Student Details by Email Id" + viewByEmailID);
        else
        System.out.println("Student details are not retrieved successfully using Email Id");
		
        if(updateRecord!=null&&updateRecord.getStatusCode()==200)
		System.out.println("Put Response" + updateRecord.asPrettyString());
		else
		System.out.println("PUT response is null. Check that the student backlog has been updated successfully.");
		
        if(deleteRecord!=null&&deleteRecord.getStatusCode()==200)
		System.out.println("Delete Response" + deleteRecord.asPrettyString());
		else
		System.out.println("Delete response is null. Check that the student details have been successfully deleted.");
		
        if(verifyRecord!=null&&studentRollNo!=null&&verifyRecord.getStatusCode()==200)
		{
		System.out.println("GET Response" + verifyRecord.asPrettyString());
		if(!verifyRecord.asPrettyString().contains(studentRollNo))
		System.out.println("GET response is correct. Student details have been deleted and verified successfully.");
		else
		System.out.println("GET response is not correct. Check that the student details have been deleted and verified successfully.");
		}
		else
		System.out.println("GET Response is null. Check student details are retrived successfully.");
    }
}
