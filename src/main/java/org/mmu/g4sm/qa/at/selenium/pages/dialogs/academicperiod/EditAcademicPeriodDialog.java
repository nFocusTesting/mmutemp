package org.mmu.g4sm.qa.at.selenium.pages.dialogs.academicperiod;

import org.mmu.g4sm.qa.at.selenium.pages.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class EditAcademicPeriodDialog extends Page {
	
	@FindBy( how = How.XPATH, using = "//label[text()='Allow Registration']/input[@type='checkbox']")
    private WebElement allowRegistrationCheckBox;
	
	@FindBy( how = How.XPATH, using = "//div[@class='ui-dialog-buttonset']/button[.//span[contains(text(), 'OK')]]")
    private WebElement okButton;
	
	@FindBy( how = How.XPATH, using = "//div[@class='ui-dialog-buttonset']/button[.//span[contains(text(), 'Cancel')]]")
    private WebElement cancelButton;
	
	
	public EditAcademicPeriodDialog( WebDriver driver ) {
        super( driver );
	}
	
	public void setEnableAllowRegistration() {
		setCheckBoxByState(allowRegistrationCheckBox, true);
		
	}
	
	public void setDisableAllowRegistration() {
		setCheckBoxByState(allowRegistrationCheckBox, false);
	}
	
	public boolean isAllowRegistrationEnabled() {
		boolean isAllowRegEnabled = false ;
		isAllowRegEnabled = getCheckBoxSelectedState(allowRegistrationCheckBox);
		return isAllowRegEnabled;
	}

}
