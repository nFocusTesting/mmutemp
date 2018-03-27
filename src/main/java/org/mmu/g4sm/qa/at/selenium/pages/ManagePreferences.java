package org.mmu.g4sm.qa.at.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ManagePreferences extends Page {
	
	@FindBy( how = How.XPATH, using = "//div[@data-edittitle='Manage Preferences']/form[@id='EditProfile']//select[@id='Culture']")
    private WebElement selectCulture;
	
	@FindBy( how = How.XPATH, using = "//div[@class='ui-dialog-buttonset']//button[./span[contains(text(),'OK')]]")
    private WebElement okButton;
	
	
	
	
	public ManagePreferences( WebDriver driver ) {
        super( driver );
	}
	
	public void changeCulture(String locale) {
		Page.selectDropDownValuesbyValueText(selectCulture, locale);
		okButton.click();
	}
	
	

}
