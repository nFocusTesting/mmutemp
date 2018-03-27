package org.mmu.g4sm.qa.at.selenium.pages.mainpages;

import java.util.List;

import org.mmu.g4sm.qa.at.selenium.pages.ManagePreferences;
import org.mmu.g4sm.qa.at.selenium.pages.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends Page{
	
	@FindBy( how = How.XPATH, using = "//div[@class='currentUser']/span[1]" )
    private WebElement userFirstNameLastName;
	
	@FindBy( how = How.XPATH, using = "//div[@class='currentUser']/span[2]" )
    private WebElement userName;
	
	@FindBy( how = How.XPATH, using = "//div[@class='profileMenu']/button" )
    private WebElement profileMenu;
	
	@FindBy( how = How.XPATH, using = "//div[@class='profileMenu']/ul[@class='active']/li/button[@id='logout']" )
	private WebElement logoutButton; 
	
//	@FindBy( how = How.XPATH, using = "//div[@class='profileMenu']/ul[@class='active']/li/button[@title='Manage Preferences']" )
//	private WebElement managerPreferencesButton;
	
	@FindBy( how = How.XPATH, using = "//div[@class='profileMenu']/ul[@class='active']/li/button[1]" )
	private WebElement managerPreferencesButton;
	
	
	
	@FindBy( how = How.XPATH, using = "//div[@class='ui-dialog ui-widget ui-widget-content ui-corner-all editPopup confirmDialog absoluteMiddle ui-draggable ui-dialog-buttons']" )
	private WebElement confirmationPopup;
	
	@FindBy( how = How.XPATH, using = "//div[@class='navFull visible']//div[@class='navMiddle']/button[@data-flyout='admissionsMenu']" )
	private WebElement admissionsMenu;
	
	@FindBy( how = How.XPATH, using = "//div[@class='navFull visible']//div[@class='navMiddle']/button[@data-flyout='academicMenu']" )
	private WebElement academicMenu;
	
	@FindBy( how = How.XPATH, using = "//div[@class='navFull visible']//div[@class='navMiddle']/button[@data-flyout='billingMenu']" )
	private WebElement billingMenu;	
	
	@FindBy( how = How.XPATH, using = "//div[@class='navFull visible']//div[@class='navMiddle']/button[@data-flyout='communityMenu']" )
	private WebElement communityMenu;
	
	@FindBy( how = How.XPATH, using = "//div[@class='navFull visible']//div[@class='navMiddle']/button[@data-flyout='financialAidMenu']" )
	private WebElement financialAidMenu;
	
	@FindBy( how = How.XPATH, using = "//div[@class='navFull visible']//div[@class='navMiddle']/button[@data-flyout='studentLifeMenu']" )
	private WebElement studentLifeMenu;
	
	@FindBy( how = How.XPATH, using = "//div[@class='navFlyout active']/div[@class='flyoutPane']//div[@class='fRight']/button[contains(@class, 'toggleManagementItems')]" )
	private WebElement menuToggleButton;
	
	@FindBy( how = How.XPATH, using = "//div[@class='navFlyout active']//div[@class='front']//ul/li" )
	private WebElement menuOptionsElement;
	
	@FindBy( how = How.XPATH, using = "//div[@class='navFlyout active']//div[@class='back']//ul/li" )
	private WebElement menuSettingsElement;
	
	@FindBy( how = How.XPATH, using = "//div[@class='contentPane']//div[@class='headerBar']" )
	private WebElement contentPaneHeaderBar;
	
	@FindBy( how = How.XPATH, using = "//div[contains(@class,'contentPane')]//div[@class='headerBar']" )
	private WebElement contentPaneHeaderBar1;
	
	public MainPage( WebDriver driver ) {
        super( driver );
	}
	
	public String getLoggedInUserFirstNameLastName() {
		return userFirstNameLastName.getText();
		
	}
	
	public String getLoggedINUserName() {
		return userName.getText();
		
	}
	
	public void goToAcademicMenuOptions_() {
		
	}
	
	public void goToAcademicPeriodFromAcademicSettings() {
		academicMenu.click();
		info("clicked on Academic menu item");
		menuToggleButton.click();
		info("toggled to Academic settings");
		clickOnMenuSettingsItemByLinkTextName("Academic Period");
		info("User is now on Academic Period Search Page");		
	}
	
	public void goToAcademicProgramFromAcademicSettings() {
		academicMenu.click();
		info("clicked on Academic menu item");
		menuToggleButton.click();
		info("toggled to Academic settings");
		clickOnMenuSettingsItemByLinkTextName("Academic Program");
		info("User is now on Academic Program Search Page");		
	}
	
	public void goToAdmissionsPeriodFromAdmissionsSettings() {
		admissionsMenu.click();
		info("clicked on Admissions menu item");
		menuToggleButton.click();
		info("toggled to Admissions settings");
		clickOnMenuSettingsItemByLinkTextName("Admissions Period");
		info("User is now on Admissions Period Search Page");		
	}
	
	public void goToAdmissionsProgramFromAdmissionsSettings() {
		admissionsMenu.click();
		info("clicked on Admissions menu item");
		menuToggleButton.click();
		info("toggled to Admissions settings");
		clickOnMenuSettingsItemByLinkTextName("Admissions Program");
		info("User is now on Admissions Program Search Page");
	}
	
	public void goToAdmissionsProgramOfferingFromAdmissionsSettings() {
		admissionsMenu.click();
		info("clicked on Admissions menu item");
		menuToggleButton.click();
		info("toggled to Admissions settings");
		clickOnMenuSettingsItemByLinkTextName("Admissions Program Offering");
		info("User is now on Admissions Program Offering Search Page");
	}
	
	public void goToBillingPeriodFromBillingOptions() {
		billingMenu.click();
		info("clicked on Billing menu item");
		clickOnMenuOptionsItemByLinkTextName("Billing Period");
		info("User is now on Billing Period Search Page");
	}
	
	public void clickOnMenuSettingsItemByLinkTextName(String linkTextName) {
		menuSettingsElement.findElement(By.xpath("//a[text()='" + linkTextName + "']")).click();
		info("clicked on the " + linkTextName + " link");
	}
	
	public void clickOnMenuOptionsItemByLinkTextName(String linkTextName) {
		menuOptionsElement.findElement(By.xpath("//a[text()='" + linkTextName + "']")).click();
		info("clicked on the " + linkTextName + " link"); 
		
	}
	
	public void clickOnMenuItem(String menuItem) {
		explicitWait(500);
		WebElement element = getDriver().findElement(By.xpath("//div[@class='navFull visible']//button[@data-flyout='"+ menuItem + "']"));
		JavascriptExecutor executor = (JavascriptExecutor)getDriver();
		executor.executeScript("arguments[0].click();", element);
		
		//getDriver().findElement(By.xpath("//div[@class='navFull visible']//button[@data-flyout='"+ menuItem + "']")).click();
		String xpathString = "//div[@class='navFlyout active'][@id='" + menuItem + "']" ;
		explicitWait(200);
		waitForVisibility(getDriver().findElement(By.xpath(xpathString)));
	}

	public void goToOptionsOrSettings(String menuItem, String optionsOrSettings) {
		 
		explicitWait(200);
		String xpathOfElement = "//div[@class='navFlyouts']//div[@id='" + menuItem + "']/div[@class='flyoutPane']//div[@class='header']/h2/span";
		String currentValueOfHeader = getDriver().findElement(By.xpath(xpathOfElement)).getAttribute("textContent");
		if(!optionsOrSettings.equalsIgnoreCase(currentValueOfHeader)) {
			toggleOptionsOrSettings();
		}
	}
	
	public void toggleOptionsOrSettings() {
		menuToggleButton.click();
	}
	
	public boolean isItemUnderManagementGroupVisible(String mgtGroup, String item) {
		explicitWait(200);
		boolean isItemVisible = false;
		String xpathString = "//div[@class='mgmtGroup'][./h3[text()='" + mgtGroup + "']]/ul/li/a[text()='" + item + "']";
		List<WebElement> elements = getDriver().findElements(By.xpath(xpathString));
		if(elements.size() == 1) {
			isItemVisible = true;
		}
		return isItemVisible;
	}
	

	
	public void clickItemUnderManagementGroup(String mgtGroup, String item) {
		explicitWait(200);
		String xpathString = "//div[@class='mgmtGroup'][./h3[text()='" + mgtGroup + "']]/ul/li/a[text()='" + item + "']";
		
		WebElement element = getDriver().findElement(By.xpath(xpathString));
		JavascriptExecutor executor = (JavascriptExecutor)getDriver();
		executor.executeScript("arguments[0].click();", element);
		
		//getDriver().findElement(By.xpath(xpathString)).click();
		waitForVisibility(contentPaneHeaderBar);
	}
	
	
	public void clickItemUnderManagementGroup1(String mgtGroup, String item) {
		explicitWait(200);
		String xpathString = "//div[@class='mgmtGroup'][./h3[text()='" + mgtGroup + "']]/ul/li/a[text()='" + item + "']";
		
		WebElement element = getDriver().findElement(By.xpath(xpathString));
		JavascriptExecutor executor = (JavascriptExecutor)getDriver();
		executor.executeScript("arguments[0].click();", element);
		
		//getDriver().findElement(By.xpath(xpathString)).click();
		waitForVisibility(contentPaneHeaderBar1);		
	}
	
	public void logout() {
		explicitWait(1000);
		profileMenu.click();
		waitForVisibility(logoutButton);
		logoutButton.click();
		waitForVisibility(confirmationPopup);
		info("confirmation popup displayed");
		clickOkOnLogoutPopup();
		//clickOkOnConfirmPopup(); uncomment this if the above does not work
		info("User logged out of u4sm sucessfully");
		//logoutButton =  profileMenu.findElement(By.xpath("ul[@class='active']/li/button[@id='logout']"));
		//logoutButton.click();
	}

	public void changeCultureForLoggedInUser(String locale) {
		profileMenu.click();
		waitForVisibility(managerPreferencesButton);
		managerPreferencesButton.click();
		ManagePreferences managePreferences = PageFactory.initElements(getDriver(), ManagePreferences.class);
		managePreferences.changeCulture(locale);
	}
	
	public void clickOkOnLogoutPopup() {
		clickOkOrCancelInLogoutDialog("ok");
	}
	
	public void clickCancelOnLogoutPopup() {
		clickOkOrCancelInLogoutDialog("cancel");
		
	}
	
	public void clickOkOrCancelInLogoutDialog(String okOrCancel) {
		//waitForConfirmationPopUp();
		//switchBrowserTabByIndex(1);
		//explicitWait(2000);
		//Alert alert = _driver.switchTo().alert();
		if (okOrCancel.equals("ok")) {
			waitForClickable(getDriver().findElement(By.xpath("//div[@role='dialog'][./div[@id='confirmDialog']]//button[./span[contains(text(),'OK')]]")));
			getDriver().findElement(By.xpath("//div[@role='dialog'][./div[@id='confirmDialog']]//button[./span[contains(text(),'OK')]]")).click();
			info("clicked OK on Logout popup");
		} else {
			waitForClickable(getDriver().findElement(By.xpath("//div[@role='dialog'][./div[@id='confirmDialog']]//button[./span[contains(text(),'Cancel')]]")));
			getDriver().findElement(By.xpath("//div[@role='dialog'][./div[@id='confirmDialog']]//button[./span[contains(text(),'Cancel')]]")).click();
			info("clicked Cancel on Logout popup");
		}
		
	}



}
