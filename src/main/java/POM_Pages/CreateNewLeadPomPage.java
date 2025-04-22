package POM_Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateNewLeadPomPage {

	@FindBy(xpath="//span[text()='Creating New Lead']")
	private WebElement header;
	
	@FindBy(name="lastname")
	private WebElement lastname_TF;
	
	@FindBy(name="company")
	private WebElement company_TF;
	
	@FindBy(xpath="(//input[@type='radio'])[1]")
	private WebElement radio_BTN;
	
	@FindBy(xpath="(//input[@title='Save [Alt+S]'])[1]")
	private WebElement save_BTN;
	
	//Initialization
		 public CreateNewLeadPomPage(WebDriver driver) 
		 {
		  PageFactory.initElements(driver, this);
		 }
		//Utilization
		public String getHeader() {
			return header.getText();
		}

		public void getLastname_TF(String Lastname) {
			 lastname_TF.sendKeys(Lastname);
		}

		public void getCompany_TF(String company) {
			company_TF.sendKeys(company);
		}

		public void getRadio_BTN() {
			 radio_BTN.click();
		}

		public void getSave_BTN() {
			 save_BTN.click();
		}
		 
	
		 
}
