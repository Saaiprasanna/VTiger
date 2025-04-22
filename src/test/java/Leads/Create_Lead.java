package Leads;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.Test;

import Generic_Utilies.Excel_Utility;
import Generic_Utilies.Java_Utility;
import Generic_Utilies.Property_Utility;
import Generic_Utilies.WebDriver_Utility;
import POM_Pages.CreateNewLeadPomPage;
import POM_Pages.HomePomPage;
import POM_Pages.LeadDetailPomPage;
import POM_Pages.LeadPomPage;
import POM_Pages.LoginPomPage;

public class Create_Lead {
     
	@Test
	public void CreateLead_Test() throws IOException, InterruptedException {
		
	   //Fetch data from property	
		Property_Utility pro = new Property_Utility();

		String Browser = pro.FetchdatafromPropfile("browser");
		String url = pro.FetchdatafromPropfile("url");
		String username = pro.FetchdatafromPropfile("username");
		String password = pro.FetchdatafromPropfile("password");
		String timeouts = pro.FetchdatafromPropfile("timeouts").toString();
		long time = Long.parseLong(timeouts);
		
		// fetch data from excel
				Excel_Utility ex_util = new Excel_Utility();
				Java_Utility j_util = new Java_Utility();
				int random = j_util.getRandomNumber();
				String Lastname = ex_util.FetchdatafromExcel("Leads", 1, 3) + random;
				String Company = ex_util.FetchdatafromExcel("Leads", 1, 4);
				
				// Launch the browser
				WebDriver driver = null;
				if (Browser.equals("chrome")) {
					driver = new ChromeDriver();
				}
				if (Browser.equals("edge")) {
					driver = new EdgeDriver();
				} else {
					driver = new ChromeDriver();
				}
				
				WebDriver_Utility w_util = new WebDriver_Utility();

				w_util.maximizeTheWindow(driver);

				w_util.waitTillElementFound(timeouts, driver);

				w_util.navigateToAnAppl(driver, url);
				
				// Login to Vtiger appln
				LoginPomPage l = new LoginPomPage(driver);
				l.login(username, password);
				
				// Identify the Contacts tab in home and click on it
				HomePomPage home = new HomePomPage(driver);
                 home.getLead_tab();
                 
              // Identify plus symbol and click it
                 LeadPomPage le = new LeadPomPage(driver);
				 le.getPlusicon();
				 
				// Enter the lastname in LN textfield and click on savebtn
				 CreateNewLeadPomPage newlead = new CreateNewLeadPomPage(driver);
				 newlead.getLastname_TF(Lastname);
				 newlead.getCompany_TF(Company);
				 newlead.getSave_BTN();
				 
				 LeadDetailPomPage leaddetail = new LeadDetailPomPage(driver); 
				 String header = leaddetail.getHeader();
					if (header.contains(Lastname)) {
						System.out.println(Lastname + ": TestPass");
					} else {
						System.out.println("lastname not matching");
					}
				 
				 
					// Identify the Contacts button and click on it
					home.getLead_tab();
				driver.findElement(By.xpath(
						"//a[text()='" + Lastname + "']/ancestor::tr[@bgcolor='white']/descendant::a[contains(text(),'del')]"))
						.click();
				
				// handle the popup
				
				Thread.sleep(3000);
				w_util.HandleAlertAndAccept(driver);

				
				// close the browser
				// driver.quit();
				w_util.QuitTheBrowser(driver);
				

	}

}
