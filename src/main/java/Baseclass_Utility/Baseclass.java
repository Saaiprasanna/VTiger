package Baseclass_Utility;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import Generic_Utilies.DataBase_Utility;
import Generic_Utilies.Property_Utility;
import Generic_Utilies.WebDriver_Utility;
import POM_Pages.HomePomPage;
import POM_Pages.LoginPomPage;

public class Baseclass {
	DataBase_Utility db = new DataBase_Utility();
	Property_Utility pro = new Property_Utility();
	WebDriver_Utility w_util = new WebDriver_Utility();
	public WebDriver driver = null;
	public static WebDriver sdriver = null;
	
	@BeforeSuite(groups = {"smoke","regression"})
	public void beforeSuite() {
		Reporter.log("Configure the DB:Connect", true);
		db.getDatabaseConnection();
	}

	@BeforeTest(groups = {"smoke","regression"})
	public void beforeTest() {
		Reporter.log("BT:Parallel Exe", true);
	}

	@BeforeClass(groups = {"smoke","regression"})
	public void beforeClass() throws IOException {
		
		Reporter.log("Launch the browser", true);
		
		//String Brow = pro.FetchdatafromPropfile("Browser");
		String Browser = System.getProperty("Browser",pro.FetchdatafromPropfile("Browser"));
		
		if (Browser.equals("chrome")) {
			driver = new ChromeDriver();
		}
		else if(Browser.equals("edge")) {
			driver = new EdgeDriver();
		}
		sdriver=driver;
	}

	@BeforeMethod(groups = {"smoke","regression"})
	public void beforeMethod() throws IOException {
		Reporter.log("Login to the appln", true);
		
		
		String url = System.getProperty("url",pro.FetchdatafromPropfile("url"));
		String username = System.getProperty("username",pro.FetchdatafromPropfile("username"));
		String pwd = System.getProperty("password", pro.FetchdatafromPropfile("password"));
		LoginPomPage l = new LoginPomPage(driver);
		w_util.navigateToAnAppl(driver, url);
		w_util.maximizeTheWindow(driver);
		l.login(username, pwd);
		String timeouts = (pro.FetchdatafromPropfile("timeouts"));
		w_util.waitTillElementFound(timeouts, driver);
		
	}

	@AfterMethod(groups = {"smoke","regression"})
	public void afterMethod() {
		Reporter.log("Logout to the appln", true);
		HomePomPage home = new HomePomPage(driver);
		home.logout(driver);
	}

	@AfterClass(groups = {"smoke","regression"})
	public void afterClass() {
		Reporter.log("close the browser", true);
		WebDriver_Utility wb = new WebDriver_Utility();
		wb.QuitTheBrowser(driver);
	}

	@AfterTest(groups = {"smoke","regression"})
	public void afterTest() {
		Reporter.log("AT:Parallel Exe", true);
	}

	@AfterSuite(groups = {"smoke","regression"})
	public void afterSuite() {
		Reporter.log("Close the DB Connection", true);
		db.closeDatabaseConnection();
	}

}
