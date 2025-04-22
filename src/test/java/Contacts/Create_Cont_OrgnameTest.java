package Contacts;

import java.io.FileInputStream;

import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.Set;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import Baseclass_Utility.Baseclass;
import Generic_Utilies.ClassObject_Utility;
import Generic_Utilies.Excel_Utility;
import Generic_Utilies.Java_Utility;
import Generic_Utilies.Property_Utility;
import Generic_Utilies.WebDriver_Utility;
import POM_Pages.ContactDetailPomPage;
import POM_Pages.ContactPomPage;
import POM_Pages.CreateNewContactPomPage;
import POM_Pages.CreateNewOrganization;
import POM_Pages.HomePomPage;
import POM_Pages.LoginPomPage;
import POM_Pages.OrgDetailPomPage;
import POM_Pages.OrganizationPomPage;

public class Create_Cont_OrgnameTest extends Baseclass{
     
	//@Parameters("browser")
	@Test(groups= "regression" , retryAnalyzer= Listeners_Utilies.IRetry_Analyzer_Utility.class)
	public void Create_Con_Orgname() throws IOException, InterruptedException {
        
		ClassObject_Utility.getTest().log(Status.INFO, "Fetch data from Excel");
		
		// fetch data from excel
		Excel_Utility ex_util = new Excel_Utility();
		Java_Utility j_util = new Java_Utility();
		int random = j_util.getRandomNumber();

		String contname = ex_util.FetchdatafromExcel("Contacts", 7, 3) + random;
		String orgname = ex_util.FetchdatafromExcel("Contacts", 7, 4) + random;

		
		

		WebDriver_Utility w_util = new WebDriver_Utility();

		

	
				// Identify the Contacts tab in home and click on it
				HomePomPage home = new HomePomPage(driver);
				home.getOrg_tab();		
				// Identify plus symbol and click it
				OrganizationPomPage org = new OrganizationPomPage(driver);
				org.getPlusicon();

		
		// enter orgname in create new org page save
				CreateNewOrganization neworg = new CreateNewOrganization(driver);
				  neworg.getOrgname_TF(orgname);
				  neworg.getSaveBtn();

		//driver.findElement(By.name("accountname")).sendKeys(orgname);

		// click on save
		//driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();

		// Verify actual org name with expected orgname
		//WebElement header = driver.findElement(By.xpath("//span[contains(text(),'Organization Information')]"));
	     OrgDetailPomPage orgdetail = new OrgDetailPomPage(driver);
		 String header = orgdetail.getHeader();

		if (header.contains(orgname)) {
			System.out.println(orgname + "Test Pass");
		} else {

			System.out.println("org not created");
		}
		
		// Identify the Contacts tab in home and click on it
				
				home.getCont_tab();
	   			
				ContactPomPage con = new ContactPomPage(driver);
				con.getPlusicon();
		
				CreateNewContactPomPage newcon = new CreateNewContactPomPage(driver);
				newcon.getLastname_TF(contname);
				

		// Select org name
		String pwid = driver.getWindowHandle();
		//driver.findElement(By.xpath("//img[@alt='Select']")).click();
		newcon.getOrgplusicon();

		w_util.SwitchToTabUsingUrl(driver, "module=Accounts&action");
		newcon.getOrgsearchTF(orgname);
		newcon.getOrgsearchBtn();
		//Set<String> wids = driver.getWindowHandles();
		//for (String s : wids) {
			//driver.switchTo().window(s);

		//	if (driver.getCurrentUrl().contains("module=Accounts&action")) {
			//	driver.findElement(By.id("search_txt")).sendKeys(orgname);
			//	driver.findElement(By.name("search")).click();
				driver.findElement(By.xpath("//a[text()='" + orgname + "']")).click();
			//	break;
		//	}
	//	}
		
		w_util.SwitchBackToParentWindow(driver, pwid);

		//driver.switchTo().window(pwid);
		//driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		newcon.getSaveBtn();
		
		// Verify actual org name with expected org name
		ContactDetailPomPage condetail = new ContactDetailPomPage(driver);
		boolean exp_res_con = condetail.getHeader().contains(contname);
		Assert.assertEquals(exp_res_con, true);
		// click on org tab and delete the created org
		home.getCont_tab();
		//driver.findElement(By.linkText("Contacts")).click();

		driver.findElement(
				By.xpath("//a[text()='" + contname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();

		// handle the popup
		// driver.switchTo().alert().accept();
		Thread.sleep(3000);
		w_util.HandleAlertAndAccept(driver);

		
	}

}
