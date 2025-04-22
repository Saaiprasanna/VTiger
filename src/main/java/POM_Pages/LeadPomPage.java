package POM_Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LeadPomPage {

	//declare
	@FindBy(linkText="Leads")
	private WebElement header;
	
	@FindBy(xpath="//img[@src='themes/softed/images/btnL3Add.gif']")
	private WebElement plusicon;
	
	//initalilzation
	
	public LeadPomPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
    
	//utilization
	public String getHeader() {
		return header.getText();
	}

	public void getPlusicon() {
		 plusicon.click();
	}
	
	
	
}
