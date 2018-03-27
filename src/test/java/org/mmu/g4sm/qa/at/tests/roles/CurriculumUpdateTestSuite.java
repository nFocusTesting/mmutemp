package org.mmu.g4sm.qa.at.tests.roles;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.mmu.g4sm.qa.at.selenium.pages.GenericSearchPage;
import org.mmu.g4sm.qa.at.selenium.pages.LoginPage;
import org.mmu.g4sm.qa.at.selenium.pages.Page;
import org.mmu.g4sm.qa.at.selenium.pages.dialogs.academicperiod.EditAcademicPeriodDialog;
import org.mmu.g4sm.qa.at.selenium.pages.mainpages.MainPage;
import org.mmu.g4sm.qa.at.selenium.utils.BaseTest;
import org.mmu.g4sm.qa.at.selenium.utils.DataFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(org.mmu.g4sm.qa.at.selenium.customReports.roles.CustomTestNgReporter1.class)
public class CurriculumUpdateTestSuite extends BaseTest {
	
	
	@DataProvider(name = "smoketest")
	public Iterator<Object[]> getTestDataSet1(Method method) {
		setCurrentTestName(method);
		DataFactory dataFactory = new DataFactory();
		return dataFactory.getTestDataSetSmokeTest(method);
	}
	
	@BeforeSuite
	public void testSetUp() {
		setup();
	}

	@AfterSuite
	public void testTearDown() {
		teardown();
	}
	
	private LoginPage loginPage;
	private MainPage mainPage;

	private GenericSearchPage genericSearchPage;
	private EditAcademicPeriodDialog editAcademicPeriodDialog;
	
	@Test(dataProvider = "smoketest", alwaysRun = true)
	public void CurricUpdateSmokeTest_roles_CurriculumUpdateTestSuite(String userName, String password, String userGroup, String rolesFile, String sheetName, String menuItem, String optionOrSetting, String managementGroup, String mgtGroupItem, String runMode, String isSearchPage, String updatingFieldsString) {
		
		info("TESTING " + menuItem + "->" + optionOrSetting + "->" + managementGroup + "->" + mgtGroupItem);
		int searchResultsCount = 0;
		explicitWait(200);			
		info("RUNNING: CurricUpdateSmokeTest_roles_CurriculumUpdateTestSuite1");
		loginToU4smWithUserNameAndPassword(userName, password);		
		try {
			
			if(runMode.equalsIgnoreCase("Y")) {
				
				mainPage = PageFactory.initElements(getDriver(), MainPage.class);
				mainPage.waitForJSandJQueryToLoad();
				mainPage.clickOnMenuItem(menuItem);
				mainPage.goToOptionsOrSettings(menuItem, optionOrSetting);
				
				
				info("Assert Check: Verifying if managementGroupItem \"" + mgtGroupItem + "\" is Visible under " + "\"" + menuItem + "->" + optionOrSetting + "->" + managementGroup  +"\"");
				Boolean isItemVisible = mainPage.isItemUnderManagementGroupVisible(managementGroup, mgtGroupItem);
				Assert.assertTrue(isItemVisible, "Visibility of item " + mgtGroupItem + " is " + isItemVisible.toString() + " :");
				info("Assert Check PASSED: managementGroupItem \"" + mgtGroupItem + "\" is Visible");			
				
				info("clicking managementItem " + mgtGroupItem + "under " + menuItem + "->" + optionOrSetting + "->" + managementGroup );
				mainPage.clickItemUnderManagementGroup(managementGroup, mgtGroupItem);
				mainPage.waitForJSandJQueryToLoad();
				explicitWait(200);
				
				genericSearchPage = PageFactory.initElements(getDriver(), GenericSearchPage.class);
				
				info("Assert Check: Verifying if Search page is displayed");
				boolean isExpectedPage = genericSearchPage.isExpectedSearchPageDisplayed(mgtGroupItem);
				Assert.assertTrue(true, "Expecting " + mgtGroupItem + "Search Page" + " :");
				info("Assert Check PASSED: Search page is displayed");
				
				info("Clicking Simple search");
				genericSearchPage.clickSimpleSearch();
				genericSearchPage.waitForJSandJQueryToLoad();
				
				try {
					info("Getting the number of records from the search results");
					searchResultsCount = genericSearchPage.getCountfromSearchResultsTable();
				} catch(Exception e) {
					info("FAIL: Issue with Searching. Maybe an Accplication error. See Assertion logs");
					error(e);
					Assert.fail("Search results is not displayed");
				}
				
				info("Assert Check: Verifying if search returns some data");
				Assert.assertNotEquals(searchResultsCount, 0, "Clicking on search returns no data");
				info("Assert Check PASSED: Search results returned non zero rows. The row count is:" + searchResultsCount);
								
				String firstColumnFirstRowValue=genericSearchPage.getCellDataFromSearchResultsTableByRowNUmberAndColumnIndex(1,1);
				genericSearchPage.clickRowFromSearchResultsTableByRowNUmber(1);
				genericSearchPage.waitForJSandJQueryToLoad();
				
				try {
					genericSearchPage.waitForDetailsPageToAppear(mgtGroupItem);
				} catch(Exception e) {
					info("FAIL: Details page not displayed. Possibly an Accplication error. See Assertion logs");
					error(e);
					Assert.fail("Details page not displayed. Possibly an Accplication error");
				}
				
				
				boolean isEditButtonVisible = genericSearchPage.isEditButtonDisplayedInDetailsPage(mgtGroupItem);
				explicitWait(200);
				info("Assert Check: Verifying if the Edit button is visible in the details page");
				Assert.assertTrue(isEditButtonVisible, "Expecting the Edit button to be visible");
				info("Assert Check PASSED: The Edit button is visible in the details page");
				
				genericSearchPage.clickEditButtonInDetailsPage(mgtGroupItem);
				genericSearchPage.waitForJSandJQueryToLoad();
				
				info("Assert Check: Verifying if the Edit dialog popup is visible");
				boolean isEditDialogVisible = genericSearchPage.isEditDialogDisplayed(mgtGroupItem);
				Assert.assertTrue(isEditDialogVisible, "Expecting the Edit Dialog to be visible");
				info("Assert Check PASSED: Edit Dialog popup is visible in the Search Page");
				explicitWait(200);
				
				info("Starting to modify data of field:" + updatingFieldsString + "in " + mgtGroupItem);
				List<String> oldAndNew = new ArrayList<String>();
				oldAndNew = modifyFieldsInEditDialog(mgtGroupItem, updatingFieldsString);
				String currentValue = oldAndNew.get(0);
				String updatedValue = oldAndNew.get(1);
				//info("previous value in field \"" + updatingFieldsString +  "\" before update = " + currentValue);
				//info("New value in field \"" + updatingFieldsString + "\" after update = " + updatedValue);
				info("Completed modifying data of field:" + updatingFieldsString + " in " + mgtGroupItem);
				
				
				info("Clicking OK button in Edit dialog popUp");
				genericSearchPage.clickOkButtonInEditDialog(mgtGroupItem);
				genericSearchPage.waitForJSandJQueryToLoad();
				
				info("Clicking Save button in details page");
				genericSearchPage.clickSaveButtonInDetailsPage(mgtGroupItem);
				
				mainPage.handlePopUpMessagesOnSave();
				
				info("Starting checks to verify of the updated value persists");
				mainPage.clickOnMenuItem(menuItem);
				mainPage.goToOptionsOrSettings(menuItem, optionOrSetting);
				isItemVisible = mainPage.isItemUnderManagementGroupVisible(managementGroup, mgtGroupItem);
				Assert.assertTrue(isItemVisible, "Visibility of item " + mgtGroupItem + " is " + isItemVisible.toString() + " :");
				
				
				mainPage.clickItemUnderManagementGroup(managementGroup, mgtGroupItem);
				mainPage.waitForJSandJQueryToLoad();
				info("clicked link via " + menuItem + "->" + optionOrSetting + "->" + managementGroup + "->" + mgtGroupItem);
				explicitWait(200);
				
				info("Assert Check: Verifying if Search page is displayed");
				isExpectedPage = genericSearchPage.isExpectedSearchPageDisplayed(mgtGroupItem);
				Assert.assertTrue(true, "Expecting " + mgtGroupItem + "Search Page" + " :");
				info("Assert Check PASSED: Search page is displayed");

				
				genericSearchPage.setSearchTextValue(firstColumnFirstRowValue);
				genericSearchPage.clickSimpleSearch();
				genericSearchPage.waitForJSandJQueryToLoad();
				explicitWait(200);
				
				genericSearchPage.clickRowFromSearchResultsTableByRowNUmber(1);
				genericSearchPage.waitForDetailsPageToAppear(mgtGroupItem);
				
				isEditButtonVisible = genericSearchPage.isEditButtonDisplayedInDetailsPage(mgtGroupItem);
				explicitWait(200);
				Assert.assertTrue(isEditButtonVisible, "Expecting the Edit button to be visible");
				info("Edit button is visible in the Search Page");
				
				genericSearchPage.clickEditButtonInDetailsPage(mgtGroupItem);
				isEditDialogVisible = genericSearchPage.isEditDialogDisplayed(mgtGroupItem);
				Assert.assertTrue(isEditDialogVisible, "Expecting the Edit Dialog to be visible");
				info("Edit Dialog is visible in the Search Page");
				explicitWait(200);
				verifyUpdatedFieldsinEditDialog(mgtGroupItem, updatingFieldsString, updatedValue);
				genericSearchPage.clickCancelButtonInEditDialog(mgtGroupItem);
				info("Clicked Cancel on the edit dialog");
				genericSearchPage.waitForJSandJQueryToLoad();
				info("TEST PASSED " + menuItem + "->" + optionOrSetting + "->" + managementGroup + "->" + mgtGroupItem);
				
			} 
							
		} finally {
			explicitWait(1000);
			mainPage.waitForJSandJQueryToLoad();
			explicitWait(1000);
			mainPage = PageFactory.initElements(getDriver(), MainPage.class);
			explicitWait(1000);
			mainPage.waitForJSandJQueryToLoad();
			explicitWait(1000);
			mainPage.logout();
		}
	}	
	
		
	private void loginToU4smWithUserNameAndPassword(String username, String password) {
		loginPage = PageFactory.initElements(getDriver(), LoginPage.class);
		loginPage.WaitForPageToLoad();
		Assert.assertTrue(loginPage.isu4smLoginPagePresentAndDisplayed());
		loginPage.setLocaleInLoginPage(getProp().getProperty("locale"));
		loginPage = PageFactory.initElements(getDriver(), LoginPage.class);
		loginPage.WaitForPageToLoad();
		loginPage.setUsernameTextField(username);
		loginPage.setPasswordTextField(password);
		loginPage.clickLoginButton();
		loginPage.waitForJSandJQueryToLoad();
	}
	
	private List<String> modifyFieldsInEditDialog(String mgtGroupItem, String fieldToModify) {
		// TODO Auto-generated method stub
		List<String> currentAndModifiedValuesList = new ArrayList<String>();
		String currentValue;
		String modifiedValue;
		
		String mgtGroupItemWithoutSpaces  = mgtGroupItem.replaceAll("\\s+","");
		//String xpathString = "//div[@id='edit" + mgtGroupItemWithoutSpaces + "']//*[@data-binding='" + fieldToModify + "']";
		
		
		String xpathString = "//div[contains(@id,'edit')]//*[@data-binding='" + fieldToModify + "']";
		
		WebElement elementToUpdate = getDriver().findElement(By.xpath(xpathString));
		String elementType = elementToUpdate.getTagName();
		info("field element \"" + fieldToModify + "\" is of type " + elementToUpdate.getTagName());	
		
		switch (elementType) {
		case "select":
			currentValue = Page.getDefaultOptionSelectedFromDropdown(elementToUpdate);
			info("Current Value in " + fieldToModify + " is " + currentValue);
			currentAndModifiedValuesList.add(currentValue);
			modifiedValue = Page.selectRandomnValueFromDropdown(elementToUpdate, currentValue);
			info("Updated Value in " + fieldToModify + " is " + modifiedValue);
			currentAndModifiedValuesList.add(modifiedValue);
			explicitWait(200);
			break;
		case "input":
			currentValue = Page.getDefaultTextFromTextField(elementToUpdate);
			info("Current Value in " + fieldToModify + " is " + currentValue);
			currentAndModifiedValuesList.add(currentValue);
			modifiedValue = "AUTOMATION_" + mainPage.getCurrentDateAndTime() + "_"+ Page.getRandomnString(6);
			genericSearchPage.setValueInTextField(elementToUpdate, modifiedValue);
			currentAndModifiedValuesList.add(modifiedValue);
			info("Updated Value in " + fieldToModify + " is " + modifiedValue);
			explicitWait(200);
			break;
		case "textarea":
			currentValue = Page.getDefaultTextFromTextField(elementToUpdate);
			info("Current Value in " + fieldToModify + " is " + currentValue);
			currentAndModifiedValuesList.add(currentValue);
			modifiedValue = "AUTOMATION_" + mainPage.getCurrentDateAndTime() + "_"+ Page.getRandomnString(6);
			//modifiedValue = "RandomnString" + Page.getRandomnString(12);
			genericSearchPage.setValueInTextField(elementToUpdate, modifiedValue);
			currentAndModifiedValuesList.add(modifiedValue);
			info("Updated Value in " + fieldToModify + " is " + modifiedValue);
			explicitWait(200);
			break;
		default:
			break;
		}

		return currentAndModifiedValuesList;
		
	}
	
	private void verifyUpdatedFieldsinEditDialog(String mgtGroupItem, String fieldToVerify, String updatedValueToVerify) {
		String mgtGroupItemWithoutSpaces  = mgtGroupItem.replaceAll("\\s+","");
		//String xpathString1 = "//div[@id='edit" + mgtGroupItemWithoutSpaces + "']//*[@data-binding='" + fieldToVerify + "']";
		
		//String xpathString1 = "//div[contains(@id,'edit')]//*[@data-binding='" + fieldToModify + "']";
		
		String xpathString1 = "//div[contains(@id,'edit')]//*[@data-binding='" + fieldToVerify + "']";
		
		WebElement elementToVerify = getDriver().findElement(By.xpath(xpathString1));
		String elementType = elementToVerify.getTagName();
		info("element is of type " + elementToVerify.getTagName());
		String currentValue;
		switch (elementType) {
		case "select":
			currentValue = Page.getDefaultOptionSelectedFromDropdown(elementToVerify);
			info("Current selected value is " + currentValue);
			info("value to verify is " + updatedValueToVerify);
			Assert.assertEquals(currentValue, updatedValueToVerify, "Updated value and expected value do not match");
			info("Current selected value in the dropdown for field " +  fieldToVerify + " is the updated value" + updatedValueToVerify);
			break;
		case "input":
			currentValue = Page.getDefaultTextFromTextField(elementToVerify);
			info("Current Value in " + fieldToVerify + " is " + currentValue);
			info("value to verify is " + updatedValueToVerify);
			Assert.assertEquals(currentValue, updatedValueToVerify, "Updated value and expected value do not match");
			info("Current selected value in the dropdown for field " +  fieldToVerify + " is the updated value" + updatedValueToVerify);
			break;
		case "textarea":
			currentValue = Page.getDefaultTextFromTextField(elementToVerify);
			info("Current Value in " + fieldToVerify + " is " + currentValue);
			info("value to verify is " + updatedValueToVerify);
			Assert.assertEquals(currentValue, updatedValueToVerify, "Updated value and expected value do not match");
			info("Current selected value in the dropdown for field " +  fieldToVerify + " is the updated value" + updatedValueToVerify);
			break;
		default:
			break;
		}
	}

}
