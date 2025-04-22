package Organizations;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import Baseclass_Utility.Baseclass;
import Generic_Utilies.ClassObject_Utility;
import Generic_Utilies.Excel_Utility;
import Generic_Utilies.Java_Utility;
import Generic_Utilies.WebDriver_Utility;
import POM_Pages.CreateNewOrganization;
import POM_Pages.HomePomPage;
import POM_Pages.OrgDetailPomPage;
import POM_Pages.OrganizationPomPage;

@Listeners(Listeners_Utilies.Listener.class)

public class Organization_ScenarioTest extends Baseclass {

	@Test(groups = "smoke")
	public void Create_organisation_Test() throws IOException, InterruptedException {

		WebDriver_Utility w_util = new WebDriver_Utility();
		
		ClassObject_Utility.getTest().log(Status.INFO, "Fetching data from Excel File");
		// Fetch data from excel
        Excel_Utility ex_util = new Excel_Utility();
		Java_Utility j_util = new Java_Utility();
		int random = j_util.getRandomNumber();
        String orgname = ex_util.FetchdatafromExcel("Organization", 1, 3) + random;

		// identify Organisation tab in homepage
		ClassObject_Utility.getTest().log(Status.INFO, "Verifying  homepage");
		HomePomPage home = new HomePomPage(driver);
		boolean exp_res = home.getHeader().contains("Home");
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(exp_res, true);
		

		// identify plus button and click on it
		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to org page");
		home.getOrg_tab();
		
		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to create new  org page");
		OrganizationPomPage org = new OrganizationPomPage(driver);
		org.getPlusicon();

		// enter orgname in create new org page save
		ClassObject_Utility.getTest().log(Status.INFO, "create new org ");
		CreateNewOrganization neworg = new CreateNewOrganization(driver);
		neworg.getOrgname_TF(orgname);
		neworg.getSaveBtn();
		// driver.findElement(By.name("accountname")).sendKeys(orgname);
		// click on save
		// driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();

		// Verify actual org name with expected orgname
		ClassObject_Utility.getTest().log(Status.INFO, "Verify org name ");
		OrgDetailPomPage orgdetail = new OrgDetailPomPage(driver);
		boolean exp_res1 = orgdetail.getHeader().contains(orgname);
		Assert.assertEquals(exp_res1, true);
		// WebElement header =
		// driver.findElement(By.xpath("//span[contains(text(),'Organization
		// Information')]"));

		// driver.findElement(By.linkText("Organizations")).click();
		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to org tab and delete ");
		home.getOrg_tab();
		// click on org tab and delete the created org
		driver.findElement(
				By.xpath("//a[text()='"+ orgname+"']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();

		// handle the popup
		Thread.sleep(3000);
		ClassObject_Utility.getTest().log(Status.INFO, "Delete popup handled");
		w_util.HandleAlertAndAccept(driver);
		
		soft.assertAll();

	}

	@Test(groups = "regression" )//retryAnalyzer = Listeners_Utilies.IRetry_Analyzer_Utility.class)

	public void Create_With_IndustryTest() throws EncryptedDocumentException, IOException, InterruptedException {

		// Fetch data from excel

		Excel_Utility ex_util = new Excel_Utility();
		Java_Utility j_util = new Java_Utility();
		int random = j_util.getRandomNumber();

		String orgname = ex_util.FetchdatafromExcel("Organization", 7, 3) + random;
		String Industry = ex_util.FetchdatafromExcel("Organization", 7, 5);
		String type = ex_util.FetchdatafromExcel("Organization", 7, 6);

		WebDriver_Utility w_util = new WebDriver_Utility();

		ClassObject_Utility.getTest().log(Status.INFO, "Fetching data from Excel File");
		// identify Organisation tab in homepage
		ClassObject_Utility.getTest().log(Status.INFO, "Verify homepage");
		HomePomPage home = new HomePomPage(driver);
		boolean exp_res = home.getHeader().contains("Home");
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(exp_res, true);
		home.getOrg_tab();

		// identify plus button and click on it
		ClassObject_Utility.getTest().log(Status.INFO, "navigate to org page");
		home.getOrg_tab();
		
		ClassObject_Utility.getTest().log(Status.INFO, "navigate to create new org page");
		OrganizationPomPage org = new OrganizationPomPage(driver);
		org.getPlusicon();

		// Enter org name in create new org name page and save
		ClassObject_Utility.getTest().log(Status.INFO, "Create new org with industry and type");
		CreateNewOrganization neworg = new CreateNewOrganization(driver);
		neworg.getOrgname_TF(orgname);

		WebElement ind_dd = neworg.getOrgIndustryDD();
		WebElement type_dd = neworg.getOrgTypeDD();
		w_util.HndleDropdownUsingValue(ind_dd, Industry);
		w_util.HndleDropdownUsingValue(type_dd, type);
		neworg.getSaveBtn();
		
		//verify actual org name with expected Org
		ClassObject_Utility.getTest().log(Status.INFO, "Verify org name");
		OrgDetailPomPage orgdetail = new OrgDetailPomPage(driver);
		boolean exp_res1 = orgdetail.getHeader().contains(orgname);
		Assert.assertEquals(exp_res1, true);
		
		//verify actual industry with expected org
		ClassObject_Utility.getTest().log(Status.INFO, "Verify Industry");
		boolean exp_actIndustry = orgdetail.getVerifyIndustry().contains(Industry);
		Assert.assertEquals(exp_actIndustry, true);


		// Verify actual type with expected type
		ClassObject_Utility.getTest().log(Status.INFO, "Verify type");
		boolean exp_acttype = orgdetail.getVerifyType().contains(type);
		Assert.assertEquals(exp_acttype, true);

		// click on org tab and delete the created org
		ClassObject_Utility.getTest().log(Status.INFO, "navigate to org tab and delete");
		home.getOrg_tab();
		// driver.findElement(By.linkText("Organizations")).click();
		driver.findElement(
				By.xpath("//a[text()='"+ orgname +"']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();

		Thread.sleep(3000);
		ClassObject_Utility.getTest().log(Status.INFO, "delete popup handled");
		w_util.HandleAlertAndAccept(driver);
		soft.assertAll();

	}

	@Test(groups = "regression")//retryAnalyzer = Listeners_Utilies.IRetry_Analyzer_Utility.class)
	public void Create_withMobileno_Test() throws IOException, InterruptedException {

		ClassObject_Utility.getTest().log(Status.INFO, "Fetching data from Excel File");
		// Fetch data from excel

		Excel_Utility ex_Utility = new Excel_Utility();
		Java_Utility j_util = new Java_Utility();
		int random = j_util.getRandomNumber();
		String orgname = ex_Utility.FetchdatafromExcel("Organization", 4, 3) + random;
		String phone = ex_Utility.FetchdatafromExcel("Organization", 4, 4);

		WebDriver_Utility w_util = new WebDriver_Utility();

		// identify Organisation tab in homepage
		ClassObject_Utility.getTest().log(Status.INFO, "Navigating to home page");
		HomePomPage home = new HomePomPage(driver);
		boolean exp_res = home.getHeader().contains("Home");
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(exp_res, true);
		
		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to org page");
		home.getOrg_tab();

		// identify plus button and click on it
		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to create new org page");
		OrganizationPomPage org = new OrganizationPomPage(driver);
		org.getPlusicon();

		// enter orgname in create new org page save
		ClassObject_Utility.getTest().log(Status.INFO, "creating org and phn no");
		CreateNewOrganization neworg = new CreateNewOrganization(driver);
		neworg.getOrgname_TF(orgname);
		neworg.getOrgphone_TF(phone);
		neworg.getSaveBtn();

		// Verify actual org name with expected orgname
		ClassObject_Utility.getTest().log(Status.INFO, "Verify org name and phn no");
		OrgDetailPomPage orgdetail = new OrgDetailPomPage(driver);
		boolean exp_org = orgdetail.getHeader().contains(orgname);
		Assert.assertEquals(exp_org, true);

		// Verify actual ph no with expected ph no
		boolean exp_phno = orgdetail.getVerigyOrgPhno().contains(phone);
		Assert.assertEquals(exp_phno, true);
		// click on org tab and delete the created org
		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to org tab and delete");
		home.getOrg_tab(); // driver.findElement(By.linkText("Organizations")).click();
		driver.findElement(
				By.xpath("//a[text()='"+ orgname +"']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();

		// handle the popup
		// driver.switchTo().alert().accept();
		Thread.sleep(3000);
		ClassObject_Utility.getTest().log(Status.INFO, "delete popup handled");
		w_util.HandleAlertAndAccept(driver);
		
		soft.assertAll();

	}

}
