package RvNLearn.testComponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import RvNLearn.resources.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener {

	ExtentTest test;
	ExtentReports report = ExtentReporterNG.getReportObject();
		
	// Thread config
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
	
	private String getTestName(ITestResult result) {
		
		return result.getMethod().getConstructorOrMethod().getName();
	}
	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		test = report.createTest(getTestName(result));
		extentTest.set(test);
		ITestListener.super.onTestStart(result);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		test.log(Status.PASS, getTestName(result) + "Success! Test passed.");
		ITestListener.super.onTestSuccess(result);
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		// Replaced test.fail(...); with
		//test.fail(result.getThrowable());
		extentTest.get().fail(result.getThrowable());
		
		//Get driver from BaseTest and assign to local webdriver variable.
        //Object testClass = result.getInstance();
        //WebDriver driver = ((BaseTest) testClass).getDriver();
        
        try {
			driver = (WebDriver) result.getTestClass()
					.getRealClass()
					.getField("driver")
					.get(result.getInstance());
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String screenshotFilePath = null;
		try {
			screenshotFilePath = getScreenshot(getTestName(result), driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		test.addScreenCaptureFromPath(screenshotFilePath, getTestName(result));
		//ITestListener.super.onTestFailure(result);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		test.log(Status.SKIP, "Test not run.");
		ITestListener.super.onTestSkipped(result);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedWithTimeout(result);
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onStart(context);
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		report.flush();
		ITestListener.super.onFinish(context);
	}

}
