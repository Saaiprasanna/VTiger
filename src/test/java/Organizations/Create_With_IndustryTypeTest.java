package Organizations;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
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
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import Baseclass_Utility.Baseclass;
import Generic_Utilies.Excel_Utility;
import Generic_Utilies.Java_Utility;
import Generic_Utilies.Property_Utility;
import Generic_Utilies.WebDriver_Utility;
import POM_Pages.CreateNewOrganization;
import POM_Pages.HomePomPage;
import POM_Pages.LoginPomPage;
import POM_Pages.OrgDetailPomPage;
import POM_Pages.OrganizationPomPage;

public class Create_With_IndustryTypeTest extends Baseclass{

	//@Parameters("browser")
	@Test (groups="regression", retryAnalyzer= Listeners_Utilies.IRetry_Analyzer_Utility.class)

	public void Create_With_IndustryTest() throws EncryptedDocumentException, IOException, InterruptedException {

		
		
	

		// Fetch data from excel
       
		Excel_Utility ex_util= new Excel_Utility();
		Java_Utility  j_util= new Java_Utility(); 
		int random = j_util.getRandomNumber();
		
		String orgname = ex_util.FetchdatafromExcel("Organization", 7, 3)+random;
		String Industry = ex_util.FetchdatafromExcel("Organization", 7, 5);
		String type = ex_util.FetchdatafromExcel("Organization", 7, 6);

		
		WebDriver_Utility w_util= new WebDriver_Utility();
		
		


		// identify Organisation tab in homepage
		HomePomPage home = new HomePomPage(driver);
		home.getOrg_tab();

		// identify plus button and click on it
		OrganizationPomPage org = new OrganizationPomPage(driver);
		org.getPlusicon();

		// Enter org name in create new org name page and save
				CreateNewOrganization neworg=new CreateNewOrganization(driver);
				neworg.getOrgname_TF(orgname);
				
				WebElement ind_dd=neworg.getOrgIndustryDD();
				WebElement type_dd=neworg.getOrgTypeDD();
				w_util.HndleDropdownUsingValue(ind_dd, Industry);
				w_util.HndleDropdownUsingValue(type_dd, type);
				neworg.getSaveBtn();
		
		// Verify actual industry with expected industry
				OrgDetailPomPage orgdetail=new OrgDetailPomPage(driver);
				

		// click on org tab and delete the created org
		home.getOrg_tab();
		//driver.findElement(By.linkText("Organizations")).click();
		driver.findElement(
				By.xpath("//a[text()='" + orgname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();

		Thread.sleep(3000);
		w_util.HandleAlertAndAccept(driver);


		

	}
}
