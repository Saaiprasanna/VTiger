package Contacts;

import java.io.IOException;

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
import POM_Pages.ContactDetailPomPage;
import POM_Pages.ContactPomPage;
import POM_Pages.CreateNewContactPomPage;
import POM_Pages.CreateNewOrganization;
import POM_Pages.HomePomPage;
import POM_Pages.OrgDetailPomPage;
import POM_Pages.OrganizationPomPage;

@Listeners(Listeners_Utilies.Listener.class)
public class Contact_ScenarioTest extends Baseclass {

	@Test(groups = "smoke", retryAnalyzer = Listeners_Utilies.IRetry_Analyzer_Utility.class)

	public void Create_Contact_Test() throws IOException, InterruptedException {

		WebDriver_Utility w_util = new WebDriver_Utility();

		ClassObject_Utility.getTest().log(Status.INFO, "fetching Data from Excel file");

		// fetch data from excel
		Excel_Utility ex_util = new Excel_Utility();
		Java_Utility j_util = new Java_Utility();
		int random = j_util.getRandomNumber();

		String Lastname = ex_util.FetchdatafromExcel("Contacts", 1, 3) + random;

		// Identify the Contacts tab in home and click on it
		ClassObject_Utility.getTest().log(Status.INFO, "Home page verification");

		HomePomPage home = new HomePomPage(driver);
		boolean exp_res = home.getHeader().contains("Home");
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(exp_res, true);

		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to Contacts tab");
		home.getCont_tab();

		// Identify plus symbol and click it
		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to create new contact page");
		ContactPomPage con = new ContactPomPage(driver);
		con.getPlusicon();
		// driver.findElement(By.xpath("//img[@src='themes/softed/images/btnL3Add.gif']")).click();

		ClassObject_Utility.getTest().log(Status.INFO, "Creating new contact");
		CreateNewContactPomPage newcon = new CreateNewContactPomPage(driver);
		newcon.getLastname_TF(Lastname);
		newcon.getSaveBtn();

		ClassObject_Utility.getTest().log(Status.INFO, "Verify the Contact");
		ContactDetailPomPage condetail = new ContactDetailPomPage(driver);
		String header = condetail.getHeader();
		boolean exp_res1 = header.contains(Lastname);
		Assert.assertEquals(exp_res1, true);
		// Identify the Contacts button and click on it
		// driver.findElement(By.linkText("Contacts")).click();

		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to contact tab and delete");
		home.getCont_tab();
		// delete the contact
		driver.findElement(By.xpath(
				"//a[text()='" + Lastname + "']/ancestor::tr[@bgcolor='white']/descendant::a[contains(text(),'del')]"))
				.click();

		// handle the popup
		// driver.switchTo().alert().accept();
		Thread.sleep(3000);

		ClassObject_Utility.getTest().log(Status.INFO, "Handling delete popup ");
		w_util.HandleAlertAndAccept(driver);
		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to org tab and delete ");
	soft.assertAll();

	}

	@Test(groups = "regression", retryAnalyzer = Listeners_Utilies.IRetry_Analyzer_Utility.class)
	public void Create_Con_Orgname() throws IOException, InterruptedException {

		WebDriver_Utility w_util = new WebDriver_Utility();

		ClassObject_Utility.getTest().log(Status.INFO, "fetching Data from Excel file");
		// fetch data from excel
		Excel_Utility ex_util = new Excel_Utility();
		Java_Utility j_util = new Java_Utility();
		int random = j_util.getRandomNumber();

		String contname = ex_util.FetchdatafromExcel("Contacts", 7, 3) + random;
		String orgname = ex_util.FetchdatafromExcel("Contacts", 7, 4) + random;

		ClassObject_Utility.getTest().log(Status.INFO, "Home page verification");
		// Identify the Contacts tab in home and click on it
		HomePomPage home = new HomePomPage(driver);
		boolean exp_res = home.getHeader().contains("Home");
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(exp_res, true);

		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to org tab ");
		home.getOrg_tab();
		// Identify plus symbol and click it
		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to new org  ");
		OrganizationPomPage org = new OrganizationPomPage(driver);
		org.getPlusicon();

		// enter orgname in create new org page save\
		ClassObject_Utility.getTest().log(Status.INFO, "Create new org  ");
		CreateNewOrganization neworg = new CreateNewOrganization(driver);
		neworg.getOrgname_TF(orgname);
		neworg.getSaveBtn();

		ClassObject_Utility.getTest().log(Status.INFO, "Verifying  org name ");
		OrgDetailPomPage orgdetail = new OrgDetailPomPage(driver);
		boolean exp_res_org = orgdetail.getHeader().contains(orgname);
		Assert.assertEquals(exp_res_org, true);

		// Identify the Contacts tab in home and click on it
		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to contact tab ");
		home.getCont_tab();

		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to create new contact page ");
		ContactPomPage con = new ContactPomPage(driver);
		con.getPlusicon();

		ClassObject_Utility.getTest().log(Status.INFO, "Create new Contact with org name ");
		CreateNewContactPomPage newcon = new CreateNewContactPomPage(driver);
		newcon.getLastname_TF(contname);

		// Select org name
		ClassObject_Utility.getTest().log(Status.INFO, "Select org name from child window ");
		String pwid = driver.getWindowHandle();
		// driver.findElement(By.xpath("//img[@alt='Select']")).click();
		newcon.getOrgplusicon();

		w_util.SwitchToTabUsingUrl(driver, "module=Accounts&action");
		newcon.getOrgsearchTF(orgname);
		newcon.getOrgsearchBtn();

		driver.findElement(By.xpath("//a[text()='" + orgname + "']")).click();

		w_util.SwitchBackToParentWindow(driver, pwid);

		// driver.switchTo().window(pwid);
		// driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		newcon.getSaveBtn();

		// Verify actual org name with expected org name
		ClassObject_Utility.getTest().log(Status.INFO, "Verifying contact name with organzation ");
		ContactDetailPomPage condetail = new ContactDetailPomPage(driver);
		boolean exp_res_con = condetail.getHeader().contains(contname);
		Assert.assertEquals(exp_res_con, true);

		// click on org tab and delete the created org
		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to contact tab and delete ");
		home.getCont_tab();
		// driver.findElement(By.linkText("Contacts")).click();

		driver.findElement(
				By.xpath("//a[text()='" + contname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();

		// handle the popup
		// driver.switchTo().alert().accept();
		Thread.sleep(3000);
		ClassObject_Utility.getTest().log(Status.INFO, "Handling delete popup ");
		w_util.HandleAlertAndAccept(driver);
		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to org tab and delete ");
		soft.assertAll();

	}

	@Test(groups = "regression")
	public void Create_ContSD_ED() throws IOException, InterruptedException {

		WebDriver_Utility w_util = new WebDriver_Utility();

		ClassObject_Utility.getTest().log(Status.INFO, "fetching Data from Excel file");

		// FETCH data from Excel
		Excel_Utility ex_util = new Excel_Utility();
		Java_Utility j_util = new Java_Utility();
		int random = j_util.getRandomNumber();

		String lastname = ex_util.FetchdatafromExcel("Contacts", 4, 3) + random;

		// Identify contacts tab in home page and click on it

		ClassObject_Utility.getTest().log(Status.INFO, "Home page verification");
		HomePomPage home = new HomePomPage(driver);
		boolean exp_res = home.getHeader().contains("Home");
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(exp_res, true);

		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to Contacts tab");
		home.getCont_tab();
		// driver.findElement(By.linkText("Contacts")).click();

		// Identify plus button and click on it
		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to create new contact page");
		ContactPomPage con = new ContactPomPage(driver);
		con.getPlusicon();
		// driver.findElement(By.xpath("//img[@src='themes/softed/images/btnL3Add.gif']")).click();

		// Lastname
		ClassObject_Utility.getTest().log(Status.INFO, "Creating new contact");
		CreateNewContactPomPage newcon = new CreateNewContactPomPage(driver);
		newcon.getLastname_TF(lastname);
		// driver.findElement(By.name("lastname")).sendKeys(lastname);
		ClassObject_Utility.getTest().log(Status.INFO, "Specify start date ");
		// specify start and end support date
		WebElement start_dateTF = newcon.getConStartDate_TF();
		start_dateTF.clear();
		String startdate = j_util.getCurrentDate();
		start_dateTF.sendKeys(startdate);

		ClassObject_Utility.getTest().log(Status.INFO, "Specify end date ");
		WebElement end_dateTF = newcon.getConEndDate_TF();
		end_dateTF.clear();
		String enddate = j_util.getDateAftergivendays(30);
		end_dateTF.sendKeys(enddate);
		newcon.getSaveBtn();
		// verify actual org name with expected org name
		// WebElement header = driver.findElement(By.xpath("//span[contains(text(),'
		// Contact Information')]"));
		ClassObject_Utility.getTest().log(Status.INFO, "Verify the Contact");
		ContactDetailPomPage condetail = new ContactDetailPomPage(driver);
		boolean exp_res_orgname = condetail.getHeader().contains(lastname);
		Assert.assertEquals(exp_res_orgname, true);

		// Verify start sup date and end support date

		// WebElement actstartdate = driver.findElement(By.id("dtlview_Support Start
		// Date"));
		boolean exp_actstartdate = condetail.getVerifyStartdate().contains(startdate);
		Assert.assertEquals(exp_actstartdate, true);

		// WebElement actenddate = driver.findElement(By.id("dtlview_Support End
		// Date"));
		boolean exp_actenddate = condetail.getVerifyEnddate().contains(enddate);
		Assert.assertEquals(exp_actenddate, true);

		// click on org tab and delete the created org
		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to contact tab and delete");
		home.getCont_tab();
		driver.findElement(By.xpath(
				"//a[text()='" + lastname + "']/ancestor::tr[@bgcolor='white']/descendant::a[contains(text(), 'del')]"))
				.click();

		// Handle the popup
		// driver.switchTo().alert().accept();

		Thread.sleep(3000);
		ClassObject_Utility.getTest().log(Status.INFO, "Handling delete popup ");
		w_util.HandleAlertAndAccept(driver);
		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to org tab and delete ");

		soft.assertAll();

	}

}
