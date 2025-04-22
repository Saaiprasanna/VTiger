package Organizations;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

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
import org.testng.Assert;
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

public class Create_OrganisationTest extends Baseclass {

	// @Parameters("browser")
	@Test(groups = "smoke", retryAnalyzer = Listeners_Utilies.IRetry_Analyzer_Utility.class)

	public void Create_organisation_Test() throws IOException, InterruptedException {

		// Fetch data from excel

		Excel_Utility ex_util = new Excel_Utility();
		Java_Utility j_util = new Java_Utility();
		int random = j_util.getRandomNumber();

		String orgname = ex_util.FetchdatafromExcel("Organization", 1, 3) + random;

		WebDriver_Utility w_util = new WebDriver_Utility();

		// identify Organisation tab in homepage
		HomePomPage home = new HomePomPage(driver);
		home.getOrg_tab();

		// identify plus button and click on it
		OrganizationPomPage org = new OrganizationPomPage(driver);
		org.getPlusicon();

		// enter orgname in create new org page save
		CreateNewOrganization neworg = new CreateNewOrganization(driver);
		neworg.getOrgname_TF(orgname);
		neworg.getSaveBtn();
		// driver.findElement(By.name("accountname")).sendKeys(orgname);
		// click on save
		// driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();

		// Verify actual org name with expected orgname
		OrgDetailPomPage orgdetail = new OrgDetailPomPage(driver);
		boolean exp_res1 = orgdetail.getHeader().contains(orgname);
		Assert.assertEquals(exp_res1, true);

		// driver.findElement(By.linkText("Organizations")).click();
		home.getOrg_tab();
		// click on org tab and delete the created org
		driver.findElement(
				By.xpath("//a[text()='"+orgname+"']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();

		// handle the popup
		Thread.sleep(3000);
		w_util.HandleAlertAndAccept(driver);
		// driver.switchTo().alert().accept();

	}

}
