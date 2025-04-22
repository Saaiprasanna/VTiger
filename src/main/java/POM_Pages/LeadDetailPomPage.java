package POM_Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LeadDetailPomPage {

	//declare
		@FindBy(xpath="//span[contains(text(),' Lead Information')]")
		private WebElement header;
		
		@FindBy(id="mouseArea_Last Name")
		private WebElement Verifylastname;
		
		@FindBy(id="mouseArea_Company")
		private WebElement Verifycompanyname;
		
		@FindBy(linkText=" Administrator")
		private WebElement Verifyassignedto;
		
		//Initialization
				public LeadDetailPomPage(WebDriver driver) {
					PageFactory.initElements(driver, this);	
				}
		// Utilization

				public String getHeader() {
					return header.getText();
				}

				public String getVerifylastname() {
					return Verifylastname.getText();
				}

				public String getVerifycompanyname() {
					return Verifycompanyname.getText();
				}

				public String getVerifyassignedto() {
					return Verifyassignedto.getText();
				}
				
				
				
				
		
	
}
