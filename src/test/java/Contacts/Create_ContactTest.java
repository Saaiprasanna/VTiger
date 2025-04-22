package Contacts;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;

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
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Baseclass_Utility.Baseclass;
import Generic_Utilies.Excel_Utility;
import Generic_Utilies.Java_Utility;
import Generic_Utilies.Property_Utility;
import Generic_Utilies.WebDriver_Utility;
import Listeners_Utilies.IRetry_Analyzer_Utility;
import POM_Pages.ContactDetailPomPage;
import POM_Pages.ContactPomPage;
import POM_Pages.CreateNewContactPomPage;
import POM_Pages.HomePomPage;
import POM_Pages.LoginPomPage;
@Listeners(Listeners_Utilies.Listener.class)
public class Create_ContactTest extends Baseclass {
   // @Parameters("browser")
	@Test(groups= "smoke" ,retryAnalyzer= Listeners_Utilies.IRetry_Analyzer_Utility.class)

	public void Create_Contact_Test() throws IOException, InterruptedException {

		
        // fetch data from excel
		Excel_Utility ex_util = new Excel_Utility();
		Java_Utility j_util = new Java_Utility();
		int random = j_util.getRandomNumber();

		String Lastname = ex_util.FetchdatafromExcel("Contacts", 1, 3) + random;

		WebDriver_Utility w_util = new WebDriver_Utility();


		// Identify the Contacts tab in home and click on it
		HomePomPage home = new HomePomPage(driver);
		
		boolean exp_res = home.getHeader().contains("Home");
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(exp_res, true);
		home.getCont_tab();

		// Identify plus symbol and click it
		ContactPomPage con = new ContactPomPage(driver);
		con.getPlusicon();
		// driver.findElement(By.xpath("//img[@src='themes/softed/images/btnL3Add.gif']")).click();

		CreateNewContactPomPage newcon = new CreateNewContactPomPage(driver);
		newcon.getLastname_TF(Lastname);
		newcon.getSaveBtn();

		
		ContactDetailPomPage condetail = new ContactDetailPomPage(driver);
		String header = condetail.getHeader();
		boolean exp_res1 = header.contains(Lastname);
		Assert.assertEquals(exp_res1, true);
		
		// Identify the Contacts button and click on it
		// driver.findElement(By.linkText("Contacts")).click();
		home.getCont_tab();
		// delete the contact
		driver.findElement(By.xpath(
				"//a[text()='" + Lastname + "']/ancestor::tr[@bgcolor='white']/descendant::a[contains(text(),'del')]"))
				.click();

		// handle the popup
		// driver.switchTo().alert().accept();
		Thread.sleep(3000);
		w_util.HandleAlertAndAccept(driver);
		soft.assertAll();
		

		
	}

}
