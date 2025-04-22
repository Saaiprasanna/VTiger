package Contacts;

import java.io.FileInputStream;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
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
import org.testng.asserts.SoftAssert;

import Baseclass_Utility.Baseclass;
import Generic_Utilies.Excel_Utility;
import Generic_Utilies.Java_Utility;
import Generic_Utilies.Property_Utility;
import Generic_Utilies.WebDriver_Utility;
import POM_Pages.ContactDetailPomPage;
import POM_Pages.ContactPomPage;
import POM_Pages.CreateNewContactPomPage;
import POM_Pages.HomePomPage;
import POM_Pages.LoginPomPage;

public class Create_ContactwithSD_EDTest extends Baseclass{
    
	@Parameters("browser")
	@Test (groups= "regression", retryAnalyzer= Listeners_Utilies.IRetry_Analyzer_Utility.class)
	public void Create_ContSD_ED() throws IOException, InterruptedException {
		

		// FETCH data from Excel
		Excel_Utility ex_util= new Excel_Utility();
		Java_Utility  j_util= new Java_Utility(); 
		int random = j_util.getRandomNumber();
		
		String lastname = ex_util.FetchdatafromExcel("Contacts", 4, 3)+random;
		WebDriver_Utility w_util= new WebDriver_Utility();
		// Identify contacts tab in home page and click on it
				HomePomPage home = new HomePomPage(driver);
				boolean exp_res = home.getHeader().contains("Home");
				SoftAssert soft = new SoftAssert();
				soft.assertEquals(exp_res, true);
				home.getCont_tab();
		//driver.findElement(By.linkText("Contacts")).click();

		// Identify plus button and click on it
				ContactPomPage con = new ContactPomPage(driver);
				con.getPlusicon();		
		//driver.findElement(By.xpath("//img[@src='themes/softed/images/btnL3Add.gif']")).click();
         
	    // Lastname
				CreateNewContactPomPage newcon = new CreateNewContactPomPage(driver);
				newcon.getLastname_TF(lastname);	
		//driver.findElement(By.name("lastname")).sendKeys(lastname);

				// specify start and end support date
				WebElement start_dateTF = newcon.getConStartDate_TF();
				start_dateTF.clear();
				String startdate = j_util.getCurrentDate();
				start_dateTF.sendKeys(startdate);
				
				WebElement end_dateTF = newcon.getConEndDate_TF();
				end_dateTF.clear();
				String enddate = j_util.getDateAftergivendays(30);
				end_dateTF.sendKeys(enddate);
              newcon.getSaveBtn();
		// verify actual org name with expected org name
      //WebElement header = driver.findElement(By.xpath("//span[contains(text(),' Contact Information')]"));
              ContactDetailPomPage condetail = new ContactDetailPomPage(driver);
              boolean exp_res_orgname=condetail.getHeader().contains(lastname);
              Assert.assertEquals(exp_res_orgname, true);

		// Verify start sup date and end support date

		//WebElement actstartdate = driver.findElement(By.id("dtlview_Support Start Date"));
              
              boolean  exp_actstartdate = condetail.getVerifyStartdate().contains(startdate);
              Assert.assertEquals(exp_actstartdate, true);
              
        
		//WebElement actenddate = driver.findElement(By.id("dtlview_Support End Date"));
              boolean exp_actenddate = condetail.getVerifyEnddate().contains(enddate);
      		Assert.assertEquals(exp_actenddate, true);

		// click on org tab and delete the created org
		driver.findElement(By.linkText("Contacts")).click();
		driver.findElement(
				By.xpath("//a[text()='" + lastname + "']/ancestor::tr[@bgcolor='white']/descendant::a[contains(text(), 'del')]"))
				.click();

		// Handle the popup
		//driver.switchTo().alert().accept();
		Thread.sleep(3000);
		w_util.HandleAlertAndAccept(driver);

		
		
	}
}
