package Listeners_Utilies;

import java.io.File;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import Baseclass_Utility.Baseclass;
import Generic_Utilies.ClassObject_Utility;
import Generic_Utilies.Java_Utility;

public class Listener implements ITestListener, ISuiteListener {

	public ExtentReports report = null;
	public ExtentTest test = null;

	public void onStart(ISuite suite) {
		Reporter.log("Report configuration", true);
		String time = new Date().toString().replace(" ", "").replace(":", "");
		// Configure the report
		ExtentSparkReporter spark = new ExtentSparkReporter("./AdvancedReport/Adv_Report"+ time +".html");
		spark.config().setDocumentTitle("Advance Report_VTiger");
		spark.config().setReportName("V-TigerScenario");
		spark.config().setTheme(Theme.DARK);

		// Set Environment configurations
		
		report = new ExtentReports();
		report.attachReporter(spark);
		report.setSystemInfo("OS version", "Windows-11");
		report.setSystemInfo("Browser version", "Chrome-135");
		test=report.createTest("VTiger RunTime Events");
		ClassObject_Utility.setTest(test);
	}

	public void onFinish(ISuite suite) {
		Reporter.log("Report BackUp", true);
		report.flush();
		test.log(Status.INFO, "Suite Execution Finished");
	}

	public void onTestStart(ITestResult result) {
		String testname = result.getMethod().getMethodName();
		Reporter.log(testname + "--Started--", true);
		test = report.createTest(result.getMethod().getMethodName());
		test.log(Status.INFO, result.getMethod().getMethodName()+ "--Started--");

	}

	public void onTestSuccess(ITestResult result) {
		
		Reporter.log(result.getMethod().getMethodName()+ "--Success--", true);
		test.log(Status.INFO, result.getMethod().getMethodName()+ "--success--");
	}

	public void onTestFailure(ITestResult result) {
		String testname = result.getMethod().getMethodName();
		Reporter.log(testname + "--Failed--", true);
		test.log(Status.FAIL, testname + "--Failed--");
		
		String time = new Date().toString().replace(" ", "").replace(":", "");
		TakesScreenshot ts = (TakesScreenshot) Baseclass.sdriver;
		String filepath = ts.getScreenshotAs(OutputType.BASE64);
	    test.addScreenCaptureFromBase64String(filepath,testname + "_" + time);

		
	}

	public void onTestSkipped(ITestResult result) {
		Reporter.log(result.getMethod().getMethodName() + "--Skipped--", true);
		test.log(Status.INFO, result.getMethod().getMethodName()+"--Skipped--");
	}

}
