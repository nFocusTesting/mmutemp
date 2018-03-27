package org.mmu.g4sm.qa.at.selenium.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class GenericSearchPage extends Page {
	
	
	@FindBy( how = How.XPATH, using = "//input[@id='KeywordSearch']" )
    private WebElement searchTextinput;
	
	@FindBy( how = How.XPATH, using = "//div[@class='superSearch']//div[@class='optionsButton']/button[1]" )
    private WebElement searchButton;
	
	@FindBy( how = How.XPATH, using = "//div[@id='searchResults']/table" )
    private WebElement searchResultsTable;
	
	@FindBy( how = How.XPATH, using = "//div[@class='paneContent']//div[@id='searchResults']" )
    private WebElement searchResultsCell;
	
	@FindBy( how = How.XPATH, using = "//div[@id='pageContent']/span[@id='AcademicPeriodDetails']" )
    private WebElement academicPeriodDetails;
	
	@FindBy( how = How.XPATH, using = "//div[contains(@class, 'ui-dialog')]/div[.//span[contains(text(), 'Edit Academic Period')]]" )
    private WebElement editAcademicPeriodDialog;
	
	@FindBy( how = How.XPATH, using = "//div[contains(@class,'ui-dialog')]//button[./span[contains(text(),'Done')]]" )
    private WebElement doneButtonInPopUpDialog;
	
	@FindBy( how = How.XPATH, using = "//div[@class='blockUI']" )
    private WebElement blockUI;
	
	@FindBy( how = How.XPATH, using = "//div[@class='blockUI blockOverlay']" )
    private WebElement blockUIOverlay;
	
	@FindBy( how = How.XPATH, using = "//div[@class='blockUI blockMsg blockPage']" )
    private WebElement blockUIBlockMsg;
	
	
	
	
	public GenericSearchPage( WebDriver driver ) {
        super( driver );
	}
	
	public boolean isExpectedSearchPageDisplayed(String managementGroupItem) {
		boolean isexpectedPage = false;
		
		//07-03/2018 commented. Uncomment if this does not work 
		//String xpathString = "//div[@class='contentPane']//div[@class='headerBar'][./h3[text()='" + managementGroupItem + " " + "Search']]";
		
		
		String xpathString = "//div[@class='contentPane']//div[@class='headerBar'][./h3[contains(text(),'Search')]]";
		isexpectedPage = isElementPresentAndDisplayed(getDriver().findElement(By.xpath(xpathString)));
		return isexpectedPage;
	}
	
    public void setSearchTextValue(String text) {
    	try {
    		waitForVisibility(searchTextinput);
    		searchTextinput.click();
    		searchTextinput.clear();
    		((JavascriptExecutor) getDriver()).executeScript("document.getElementsByName('" 
    	+ searchTextinput.getAttribute("name") 
    	+ "').item(0).value = '" 
    	+ text 
    	+ "';");
    		info("Inserted, \"" + text + "\" into the search text field.");
    	} catch (Exception e) {
    		error(e);
    	} 
    }
    
    public void setValueInTextField(WebElement elementToUpdate, String modifiedValue) {
    	try {
    		waitForVisibility(elementToUpdate);
    		elementToUpdate.click();
    		elementToUpdate.clear();
    		elementToUpdate.sendKeys(modifiedValue);
//    		explicitWait(200);
//    		((JavascriptExecutor) getDriver()).executeScript("document.getElementsByName('" 
//    	+ elementToUpdate.getAttribute("name") 
//    	+ "').item(0).value = '" 
//    	+ modifiedValue 
//    	+ "';");
    		info("Inserted, \"" + modifiedValue + "\" into the text field.");
    	} catch (Exception e) {
    		error(e);
    	} 
    }
    
    public void clickSimpleSearch() {
    	searchButton.click();
    	info("clicked search button on the academic period search page");
    }
    
    public int getCountfromSearchResultsTable() {
    	return getRowCountFromTable(searchResultsTable);		
    }
    
    public void clickRowFromSearchResultsTableByRowNUmber(int rowNumber) { 	
    	explicitWait(200);
    	WebElement row = getDataTableRowByRowNumber(searchResultsCell, 1);
    	WebElement cell = row.findElement(By.tagName("td"));
    	info(cell.getText());
    	clickElementWithRetry(cell);
    }
    
    public void clickLastButtonFromResultsTableByRowNUmber(int rowNumber) { 	
    	explicitWait(200);
    	WebElement row = getDataTableRowByRowNumber(searchResultsCell, 1);
    	List<WebElement> cells = row.findElements(By.tagName("td"));
    	int count = cells.size();
    	WebElement buttonElementTd = cells.get(count-1);
    	WebElement buttonElement = buttonElementTd.findElement(By.xpath("./span[contains(@title,'Record')]"));
    	//info(cell.getText());
    	clickElementWithRetry(buttonElement);
    }
    
    public void waitForDetailsPageToAppear(String mgtGroupItem) {
    	info("waiting for details page to appear");
    	explicitWait(200);
    	String mgtGroupItemWithoutSpaces = null;
    	String xpathString = null;
    	//BlockRegistrationDetails
//    	if(mgtGroupItem.equals("Degree Audit Setup")) {
//    		mgtGroupItemWithoutSpaces  = "DegreeAudit";
//    	} else if(mgtGroupItem.equals("Module Block Setup")) {
//    		mgtGroupItemWithoutSpaces  = "BlockRegistration";
//    	} else if(mgtGroupItem.equals("Programme Offering Item") || mgtGroupItem.equals("Admissions Program Offering Item")) {
//    		mgtGroupItemWithoutSpaces="AdmissionsProgramOfferingItem";
//    	} else {
//    		mgtGroupItemWithoutSpaces  = mgtGroupItem.replaceAll("\\s+","");
//    	}
    	
    	//String mgtGroupItemWithoutSpaces  = mgtGroupItem.replaceAll("\\s+","");
    	
    	mgtGroupItemWithoutSpaces = getManagementGroupWithoutSpaces(mgtGroupItem);
    	
    	//07/03/2018 commented below line. uncomment if not working
    	//xpathString = "//span[@id='" + mgtGroupItemWithoutSpaces + "Details']";
    	
    	xpathString = "//span[contains(@id,'Details')]";
    	waitForVisibility(getDriver().findElement(By.xpath(xpathString)));
    }
    
    public boolean isViewButtonDisplayedInDetailsPage(String mgtGroupItem) {
    	String mgtGroupItemWithoutSpaces = null;
    	String xpathString = null;
    	
//    	if(mgtGroupItem.equals("Degree Audit Setup")) {
//    		mgtGroupItemWithoutSpaces  = "DegreeAudit";
//    	} else if(mgtGroupItem.equals("Module Block Setup")) {
//    		mgtGroupItemWithoutSpaces  = "BlockRegistration";
//    	} else if(mgtGroupItem.equals("Programme Offering Item") || mgtGroupItem.equals("Admissions Program Offering Item")) {
//    		mgtGroupItemWithoutSpaces="AdmissionsProgramOfferingItem"; 
//    	} else {
//    		mgtGroupItemWithoutSpaces  = mgtGroupItem.replaceAll("\\s+","");
//    	}   
    	
    	mgtGroupItemWithoutSpaces = getManagementGroupWithoutSpaces(mgtGroupItem);

//    	if(mgtGroupItem.equals("Degree Audit Setup")) {
//    		mgtGroupItemWithoutSpaces  = "DegreeAudit";
//    	} else {
//    		mgtGroupItemWithoutSpaces  = mgtGroupItem.replaceAll("\\s+","");
//    	}
    	
    	//String mgtGroupItemWithoutSpaces  = mgtGroupItem.replaceAll("\\s+","");
    	
    	//07/03/2018 commented below line. uncomment if not working
    	//xpathString = "//div[@id='" + mgtGroupItemWithoutSpaces + "']//button[text()='View']";
    	
    	xpathString = "//button[text()='View']";
    	return isElementPresentAndEnabled(getDriver().findElement(By.xpath(xpathString)));
    }
    
    public void clickViewButtonInDetailsPage(String mgtGroupItem) {
    	String mgtGroupItemWithoutSpaces = null;
    	String xpathString = null;
    	
//    	if(mgtGroupItem.equals("Degree Audit Setup")) {
//    		mgtGroupItemWithoutSpaces  = "DegreeAudit";
//    	} else if(mgtGroupItem.equals("Module Block Setup")) {
//    		mgtGroupItemWithoutSpaces  = "BlockRegistration";
//    	} else {
//    		mgtGroupItemWithoutSpaces  = mgtGroupItem.replaceAll("\\s+","");
//    	} 
    	
    	mgtGroupItemWithoutSpaces = getManagementGroupWithoutSpaces(mgtGroupItem);
    	
//    	if(mgtGroupItem.equals("Degree Audit Setup")) {
//    		mgtGroupItemWithoutSpaces  = "DegreeAudit";
//    	} else {
//    		mgtGroupItemWithoutSpaces  = mgtGroupItem.replaceAll("\\s+","");
//    	}
    	
    	//String mgtGroupItemWithoutSpaces  = mgtGroupItem.replaceAll("\\s+","");
    	
    	//07/03/2018 commented below line. uncomment if not working
    	//xpathString = "//div[@id='" + mgtGroupItemWithoutSpaces + "']//button[text()='View']";
    	
    	xpathString = "//button[text()='View']";
    	getDriver().findElement(By.xpath(xpathString)).click();
    }
    
    public boolean isEditButtonDisplayedInDetailsPage(String mgtGroupItem) {
    	String mgtGroupItemWithoutSpaces = null;
    	String xpathString = null;
    	
//    	if(mgtGroupItem.equals("Degree Audit Setup")) {
//    		mgtGroupItemWithoutSpaces  = "DegreeAudit";
//    	} else if(mgtGroupItem.equals("Module Block Setup")) {
//    		mgtGroupItemWithoutSpaces  = "BlockRegistration";
//    	} else if(mgtGroupItem.equals("Programme Offering Item") || mgtGroupItem.equals("Admissions Program Offering Item")) {
//    		mgtGroupItemWithoutSpaces="AdmissionsProgramOfferingItem";
//    	} else {
//    		mgtGroupItemWithoutSpaces  = mgtGroupItem.replaceAll("\\s+","");
//    	} 
    	
    	mgtGroupItemWithoutSpaces = getManagementGroupWithoutSpaces(mgtGroupItem);
    	//mgtGroupItemWithoutSpaces  = mgtGroupItem.replaceAll("\\s+","");
    	
    	//07/03/2018 commented below line. uncomment if not working
    	//xpathString = "//div[@id='" + mgtGroupItemWithoutSpaces + "']//button[text()='Edit']";
    	
    	xpathString = "//button[text()='Edit']";
    	return isElementPresentAndEnabled(getDriver().findElement(By.xpath(xpathString)));
    }
    
    
    
    public void clickEditButtonInDetailsPage(String mgtGroupItem) {
    	//String mgtGroupItemWithoutSpaces  = mgtGroupItem.replaceAll("\\s+","");
    	String mgtGroupItemWithoutSpaces = getManagementGroupWithoutSpaces(mgtGroupItem);
    	
    	//07/03/2018 commented below line. uncomment if not working
    	//String xpathString = "//div[@id='" + mgtGroupItemWithoutSpaces + "']//button[text()='Edit']";
    	
    	String xpathString = "//button[text()='Edit']";
    	getDriver().findElement(By.xpath(xpathString)).click();
    }
    
    public void clickSaveButtonInDetailsPage(String mgtGroupItem) {
    	explicitWait(1000);
    	String mgtGroupItemWithoutSpaces  = mgtGroupItem.replaceAll("\\s+","");
    	//String xpathString = "//div[@class='headerBar']//div[@class='fRight']/button[@data-entity-title='" + mgtGroupItemWithoutSpaces + "'][text()='Save']";
    	
    	String xpathString = "//div[@class='headerBar']//div[@class='fRight']/button[text()='Save']";
    	
    	waitForClickable(getDriver().findElement(By.xpath(xpathString)));
    	explicitWait(2000);
    	getDriver().findElement(By.xpath(xpathString)).click();	
    	info("Clicked on the Save button in details page");
    	
    	info("waiting for All blocks on page to dissapear");
    	waitForBlockUIToDisappear();
    	info("All blocks on page dissapeared");
    	
    	//getWebDriverWait().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='notificationBar']/div/div[@class='alertBody']")));
    	//getWebDriverWait().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='notificationBar']/div/div[@class='alertBody'][contains(text(),'succeeded')]")));
    	//getWebDriverWait().until(ExpectedConditions.not(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='notificationBar']/div"))));
    	getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='notificationBar']/div")));
    	
    	boolean isDisplayed = true;
    	info("waiting for Save Message to disappear");
    	StringBuilder builder = new StringBuilder();
    	while(isDisplayed) {
    		try {
    			getDriver().findElement(By.xpath("//div[@id='notificationBar']/div"));
    			System.out.print('.');
    		} catch(Exception e) {
    			System.out.println();
    			info("Save message has now disappeared");
    			isDisplayed = false;
    		}
    	}
    	//getWebDriverWait().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='notificationBar']/div")));
    	//div[@id='notificationBar']/div/div[@class='alertBody'][contains(text(),'succeeded')]
    	//waitForVisibility(element);
    }
    
    public void waitForBlockUIToDisappear() {
    	getWebDriverWait().until(ExpectedConditions.invisibilityOf(blockUI));
    	//getWebDriverWait().until(ExpectedConditions.invisibilityOf(blockUIOverlay));
    	//getWebDriverWait().until(ExpectedConditions.invisibilityOf(blockUIBlockMsg));	
    }
    

        
    public boolean isEditDialogDisplayed(String mgtGroupItem) {    	
    	//07/03/2018 commented below line. uncomment if not working
    	//String xpathString = "//div[contains(@class,'ui-dialog')]/div[./span[text()='Edit " + mgtGroupItem + "']]";
    	
    	String xpathString = "//div[contains(@class,'ui-dialog')]/div[./span[contains(text(), 'Edit')]]";
    	
    	return isElementPresentAndEnabled(getDriver().findElement(By.xpath(xpathString)));
    }
    
    public boolean isEditGlossaryDisplayed(String mgtGroupItem) {    	
    	//07/03/2018 commented below line. uncomment if not working
    	//String xpathString = "//div[contains(@class,'ui-dialog')]/div[./span[text()='Edit " + mgtGroupItem + "']]";
    	
    	String xpathString = "//div[contains(@class,'ui-dialog')][2]/div[./span[contains(text(), 'Edit')]]";
    	
    	return isElementPresentAndEnabled(getDriver().findElement(By.xpath(xpathString)));
    }
    
    public void clickDoneButtonInViewDialog(String mgtGroupItem) {
    	info("Clicking the Done button in the edit dialog popUp");
    	explicitWait(200);
    	
    	String mgtGroupItemWithoutSpaces = null;
    	String xpathString = null;
    	
    	//07/03/2018 commented below lines. uncomment if not working
//    	if(mgtGroupItem.equals("Degree Audit Setup")) {
//    		mgtGroupItemWithoutSpaces  = "degreerequirementEdit";
//    		xpathString = "//div[@id='" + mgtGroupItemWithoutSpaces + "']/..//button[./span[contains(text(),'Done')]]";
//    	} else if(mgtGroupItem.equals("Module Block Setup")) {
//    		mgtGroupItemWithoutSpaces  = "rBlockRegistration";
//    		xpathString = "//div[@id='" + mgtGroupItemWithoutSpaces + "']/..//button[./span[contains(text(),'Done')]]";
//    	} else {
//    		mgtGroupItemWithoutSpaces  = mgtGroupItem.replaceAll("\\s+","");
//    		xpathString = "//div[@id='r" + mgtGroupItemWithoutSpaces + "']/..//button[./span[contains(text(),'Done')]]";
//    	}
    	
    	xpathString = "//button[./span[contains(text(),'Done')]]";
    	
    	
    	List<WebElement> buttonElements =  getDriver().findElements(By.xpath(xpathString));
    	int numberOfButtons = buttonElements.size();
    	buttonElements.get(numberOfButtons-1).click();
    }
    
    public void clickOkButtonInEditDialog(String mgtGroupItem) {
    	explicitWait(200);
    	//doneButtonInPopUpDialog.click();
    	String mgtGroupItemWithoutSpaces  = mgtGroupItem.replaceAll("\\s+","");
    	
    	//07/03/2018 commented below line. uncomment if not working
    	//String xpathString = "//div[@id='r" + mgtGroupItemWithoutSpaces + "']/..//div[contains(@class,'ui-dialog-buttonset')]/button[.//span[contains(text(),'OK')]]";
    	
    	String xpathString = "//div[contains(@class,'ui-dialog-buttonset')]/button[.//span[contains(text(),'OK')]]";
    	
    	List<WebElement> buttonElements =  getDriver().findElements(By.xpath(xpathString));
    	int numberOfButtons = buttonElements.size();
    	buttonElements.get(numberOfButtons-1).click();
    	explicitWait(200);
    	info("Clicked OK button in Edit dialog popUp");
    	//waitForEditDialogToClose(mgtGroupItem);
    }
    
    public void clickCancelButtonInEditDialog(String mgtGroupItem) {
    	explicitWait(200);
    	//doneButtonInPopUpDialog.click();
    	String mgtGroupItemWithoutSpaces  = mgtGroupItem.replaceAll("\\s+","");
    	
    	//07/03/2018 commented below line. uncomment if not working
    	//String xpathStringCancel = "//div[@id='r" + mgtGroupItemWithoutSpaces + "']/..//div[contains(@class,'ui-dialog-buttonset')]/button[.//span[contains(text(),'Cancel')]]";
    	
    	String xpathStringCancel = "//div[contains(@class,'ui-dialog-buttonset')]/button[.//span[contains(text(),'Cancel')]]";
    	
    	List<WebElement> buttonElementsCancel =  getDriver().findElements(By.xpath(xpathStringCancel));
    	int numberOfButtons = buttonElementsCancel.size();
    	buttonElementsCancel.get(numberOfButtons-1).click();
    	explicitWait(200);
    	
    }
    
    public void waitForEditDialogToClose(String mgtGroupItem) {
    	explicitWait(200);
    	String mgtGroupItemWithoutSpaces  = mgtGroupItem.replaceAll("\\s+","");
    	String xpathString = "//div[@class='pane']/div[contains(@class,paneContent)][./div[@id='" + mgtGroupItemWithoutSpaces + "']]/div[@id='" + mgtGroupItemWithoutSpaces + "']";
    	
    	String  attributeValue = getDriver().findElement(By.xpath(xpathString)).getAttribute("data-form-dirty");
    	info("value of the attribute is " +  attributeValue);
    	getWebDriverWait().until(ExpectedConditions.attributeContains(By.xpath(xpathString), "data-form-dirty", null));
    }
    
    
    
    public void clickSaveOnDetailsPage(String mgtGroupItem) {
    	String mgtGroupItemWithoutSpaces  = mgtGroupItem.replaceAll("\\s+","");
    	String xpathString="//div[@class='headerBar']//div[@class='fRight']/button[@data-entity-title='" + mgtGroupItem + "'][text()='Save']";
    }
    
    public String getCellDataFromSearchResultsTableByRowNUmberAndColumnIndex(int rowNumber, int columnIndex) {
    	WebElement row = getDataTableRowByRowNumber(searchResultsCell, 1);
    	WebElement cell = row.findElements(By.tagName("td")).get(columnIndex-1);
    	info(cell.getText());
    	return cell.getText();
    }
    
	public String getManagementGroupWithoutSpaces(String mgtGroupItem) {
		String groupWithoutSpaces = null;
    	if(mgtGroupItem.equals("Degree Audit Setup")) {
    		groupWithoutSpaces  = "DegreeAudit";
    	} else if(mgtGroupItem.equals("Module Block Setup")) {
    		groupWithoutSpaces  = "BlockRegistration";
    	} else if(mgtGroupItem.equals("Programme Offering Item") || mgtGroupItem.equals("Admissions Program Offering Item")) {
    		groupWithoutSpaces = "AdmissionsProgramOfferingItem";
    	} else {
    		groupWithoutSpaces  = mgtGroupItem.replaceAll("\\s+","");
    	}
		return groupWithoutSpaces;
	}
    
}


    
//    public boolean verifyFieldsinEditDialogIsEditable(String mandatoryField) {
//    	
//    }
//    public boolean checkIfMandatoryFieldsContainData(String mandatoryField) {
//    	
//    }

