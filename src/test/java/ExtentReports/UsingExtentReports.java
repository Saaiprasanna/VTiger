package ExtentReports;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class UsingExtentReports {
	@Test
	public void demo() {

		// Configure the report
		ExtentSparkReporter spark = new ExtentSparkReporter("./AdvancedReport/demo.html");
		spark.config().setDocumentTitle("Demo test");
		spark.config().setReportName("Demo REPORT");
		spark.config().setTheme(Theme.DARK);

		// Set Environment configurations
		ExtentReports report = new ExtentReports();
		report.attachReporter(spark);
		report.setSystemInfo("OS version", "Windows-11");
		report.setSystemInfo("Browser version", "Chrome-135");
		ExtentTest test = report.createTest("Demo TEST Created");

		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.get("https://www.facebook.com");
		test.log(Status.INFO, "Navigated to Facebook appln");

		WebElement header = driver.findElement(By.xpath("//h2"));
		if (header.getText().contains("Facebook")) {
			test.log(Status.PASS, "Header verified true");
			driver.findElement(By.id("email")).sendKeys("selenium");
		} else {
			test.log(Status.FAIL, "Header verified false");
			System.out.println("test fail");
		}

		report.flush(); 
	}
}
