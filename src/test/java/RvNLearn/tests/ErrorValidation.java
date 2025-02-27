package RvNLearn.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import RvNLearn.testComponents.BaseTest;
import RvNLearn.testComponents.RetryTest;

public class ErrorValidation extends BaseTest {

	ExtentReports report;
	
	String email = "you@here.com";
	String pwd = "sLW_U7jz'4vBPGv";

	@Test(groups= {"ErrorHandling"})
	public void validateLoginError() throws InterruptedException {

		String reportsPath = System.getProperty("user.dir")+"\\reports\\index.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(reportsPath);
		reporter.config().setReportName("Web Automation Results");
		reporter.config().setDocumentTitle("Test Results");
		
		report = new ExtentReports();
		report.attachReporter(reporter);
		report.setSystemInfo("Tester", "Bob");
		
		landing.loginApplication(email, pwd);
		landing.getLoginErrorMessage();
		Assert.assertEquals("Incorrect email or password.", landing.getLoginErrorMessage());
	}

}