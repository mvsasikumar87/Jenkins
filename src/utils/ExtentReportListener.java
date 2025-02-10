package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentReportListener implements ITestListener {
    
    // Create global variables for ExtentReports and ExtentTest
    private ExtentReports extent;
    private ExtentTest test;
    
    public ExtentReportListener() {
        // Default constructor
    }

    @Override
    public void onStart(ITestContext context) {
        // Set up the ExtentHtmlReporter to specify the location of the report
    	ExtentSparkReporter  htmlReporter = new ExtentSparkReporter ("test-output/ExtentReport.html");
    	
        htmlReporter.config().setDocumentTitle("Customer Registration Test Report");
        htmlReporter.config().setReportName("Test Activity Report");
        htmlReporter.config().setTheme(Theme.STANDARD);
        
        // Initialize ExtentReports object
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @Override
    public void onTestStart(ITestResult result) {	 	  	      	 	    	   	 	       	 	
        // Create a test case in Extent Report
        test = extent.createTest(result.getMethod().getMethodName());
        test.log(Status.INFO, result.getMethod().getMethodName() + " started.");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // Log a passed test
        test.log(Status.PASS, result.getMethod().getMethodName() + " passed.");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // Log a failed test
        test.log(Status.FAIL, result.getMethod().getMethodName() + " failed.");
        test.log(Status.FAIL, result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // Log a skipped test
        test.log(Status.SKIP, result.getMethod().getMethodName() + " skipped.");
    }

    @Override
    public void onFinish(ITestContext context) {
        // Generate the report when all the tests are completed
        extent.flush();
    }

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}
}	 	  	      	 	    	   	 	       	 	

