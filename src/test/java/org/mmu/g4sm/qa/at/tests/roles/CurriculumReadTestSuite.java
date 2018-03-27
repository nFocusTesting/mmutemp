package org.mmu.g4sm.qa.at.tests.roles;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils.Null;
import org.mmu.g4sm.qa.at.selenium.customReports.roles.CustomTestNGReporter;

import org.mmu.g4sm.qa.at.selenium.pages.GenericSearchPage;
import org.mmu.g4sm.qa.at.selenium.pages.LoginPage;
import org.mmu.g4sm.qa.at.selenium.pages.Page;
import org.mmu.g4sm.qa.at.selenium.pages.mainpages.MainPage;
import org.mmu.g4sm.qa.at.selenium.utils.BaseTest;
import org.mmu.g4sm.qa.at.selenium.utils.DataFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.TestNG;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(org.mmu.g4sm.qa.at.selenium.customReports.roles.CustomTestNgReporter1.class)
//@Listeners(org.mmu.g4sm.qa.at.selenium.customReports.roles.CustomTestNGReporter.class)
public class CurriculumReadTestSuite extends BaseTest {
	
		
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
	
	@Test(dataProvider = "smoketest", alwaysRun = true)
	public void CurricReadSmokeTest_roles_CurriculumReadTestSuite(String userName, String password, String userGroup, String rolesFile, String sheetName, String menuItem, String optionOrSetting, String managementGroup, String mgtGroupItem, String runMode, String isSearchPage, String testDataInMandatoryFields, String mandatoryFieldsString) {
		info("TESTING " + menuItem + "->" + optionOrSetting + "->" + managementGroup + "->" + mgtGroupItem);
		//info("user group is " + userGroup);
		int searchResultsCount = 0;
		explicitWait(200);			
		info("RUNNING: CurricReadSmokeTest_roles_CurriculumReadTestSuite1");
		loginToU4smWithUserNameAndPassword(userName, password);	
				
		try {
			mainPage = PageFactory.initElements(getDriver(), MainPage.class);
			mainPage.waitForJSandJQueryToLoad();
			//mainPage.changeCultureForLoggedInUser(getProp().getProperty("locale"));
			mainPage.clickOnMenuItem(menuItem);
			mainPage.goToOptionsOrSettings(menuItem, optionOrSetting);
				
			info("Assert Check: Verifying if managementGroupItem \"" + mgtGroupItem + "\" is Visible under " + "\"" + menuItem + "->" + optionOrSetting + "->" + managementGroup  +"\"");
			Boolean isItemVisible = mainPage.isItemUnderManagementGroupVisible(managementGroup, mgtGroupItem);
			Assert.assertTrue(isItemVisible, "Visibility of item " + mgtGroupItem + " is " + isItemVisible.toString() + " :");
			info("Assert Check PASSED: managementGroupItem \"" + mgtGroupItem + "\" is Visible");
			
			info("clicking managementItem " + mgtGroupItem + "under " + menuItem + "->" + optionOrSetting + "->" + managementGroup );
			mainPage.clickItemUnderManagementGroup(managementGroup, mgtGroupItem);
			//mainPage.WaitForPageToLoad();
			mainPage.waitForJSandJQueryToLoad();
			explicitWait(200);
			genericSearchPage = PageFactory.initElements(getDriver(), GenericSearchPage.class);
			
			info("Assert Check: Verifying if Search page is displayed");
			boolean isExpectedPage = genericSearchPage.isExpectedSearchPageDisplayed(mgtGroupItem);
			Assert.assertTrue(true, "Expecting " + mgtGroupItem + "Search Page" + " :");
			info("Assert Check PASSED: Search page is displayed");
			
			info("Clicking Simple search");
			genericSearchPage.clickSimpleSearch();
			//genericSearchPage.WaitForPageToLoad();
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
			//Assert.assertNotNull(searchResultsCount, "Expecting search results to display content  :");
			info("Assert Check PASSED: Search results returned non zero rows. The row count is:" + searchResultsCount);
			
			info("Clicking on the first record");
			genericSearchPage.clickRowFromSearchResultsTableByRowNUmber(1);
			//genericSearchPage.WaitForPageToLoad();
			genericSearchPage.waitForJSandJQueryToLoad();
			
			try {
				genericSearchPage.waitForDetailsPageToAppear(mgtGroupItem);
			} catch(Exception e) {
				info("FAIL: Details page not displayed. Possibly an Accplication error. See Assertion logs");
				error(e);
				Assert.fail("Details page not displayed. Possibly an Accplication error");
			}
			
			if (userGroup.equals("View Curriculum")) {
				boolean isViewButtonVisible = genericSearchPage.isViewButtonDisplayedInDetailsPage(mgtGroupItem);
				explicitWait(200);
				info("Assert Check: Verifying if the View button is visible in the details page");
				Assert.assertTrue(isViewButtonVisible, "Expecting the View button to be visible");
				info("Assert Check PASSED: The View button is visible in the details page"); 
					
				info("Clicking the View button in the details page");	
				genericSearchPage.clickViewButtonInDetailsPage(mgtGroupItem);	
			} else {
				boolean isEditButtonVisible = genericSearchPage.isEditButtonDisplayedInDetailsPage(mgtGroupItem);
				explicitWait(200);
				info("Assert Check: Verifying if the Edit button is visible in the details page");
				Assert.assertTrue(isEditButtonVisible, "Expecting the Edit button to be visible");
				info("Assert Check PASSED: The Edit button is visible in the details page"); 
				genericSearchPage.clickEditButtonInDetailsPage(mgtGroupItem);
			}
			
			//genericSearchPage.WaitForPageToLoad();
			genericSearchPage.waitForJSandJQueryToLoad();
				
			info("Assert Check: Verifying if the Edit dialog popup is visible");
			boolean isEditDialogVisible = genericSearchPage.isEditDialogDisplayed(mgtGroupItem);
			Assert.assertTrue(isEditDialogVisible, "Expecting the Edit Dialog to be visible");
			info("Assert Check PASSED: Edit Dialog popup is visible in the Search Page");
					
			if(testDataInMandatoryFields.equalsIgnoreCase("Y")) {
				if(userGroup.equals("View Curriculum")) {
					verifyIfFieldsAreGreyedOutInPopUpDialog(mandatoryFieldsString, mgtGroupItem);
				} else {
					verifyIfFieldsAreNotGreyedOutInPopUpDialog(mandatoryFieldsString, mgtGroupItem);
				}
				verifyIfMandatoryFieldsContainDataInPopUpDialog(mandatoryFieldsString, mgtGroupItem);					
			}	
			
			if(userGroup.equals("View Curriculum")) {
				genericSearchPage.clickDoneButtonInViewDialog(mgtGroupItem);
			} else {
				genericSearchPage.clickCancelButtonInEditDialog(mgtGroupItem);
			}
			
			
			//genericSearchPage.WaitForPageToLoad();
			genericSearchPage.waitForJSandJQueryToLoad();

			//mainPage = PageFactory.initElements(getDriver(), MainPage.class);
			//mainPage.logout();
			info("TEST PASSED " + menuItem + "->" + optionOrSetting + "->" + managementGroup + "->" + mgtGroupItem);
//			mainPage = PageFactory.initElements(getDriver(), MainPage.class);
//			mainPage.waitForJSandJQueryToLoad();
//			mainPage.logout();
							
		}
		finally {
			explicitWait(1000);
			mainPage.waitForJSandJQueryToLoad();
			explicitWait(1000);
			mainPage = PageFactory.initElements(getDriver(), MainPage.class);
			explicitWait(1000);
			mainPage.waitForJSandJQueryToLoad();
			explicitWait(1000);
			mainPage.logout();
			//cleanUpBrowsers();
			
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
	
	private void verifyIfFieldsAreNotGreyedOutInPopUpDialog(String mandatoryFieldsString, String managementGroupItem) {
		verifyIfFieldsAreDisabledInPopUpDialog(mandatoryFieldsString, managementGroupItem, false);
	}
	
	private void verifyIfFieldsAreGreyedOutInPopUpDialog(String mandatoryFieldsString, String managementGroupItem) {
		verifyIfFieldsAreDisabledInPopUpDialog(mandatoryFieldsString, managementGroupItem, true);
	}

	
	private void verifyIfFieldsAreDisabledInPopUpDialog(String mandatoryFieldsString, String managementGroupItem, boolean disabled) {
		
		String xpathString = null;
		
		if(managementGroupItem.equals("Course Equivalence")) {
			xpathString =  "//div[contains(@class,'ui-dialog')]//div[@class='fieldset']//div[@class='fieldGroup']/div[contains(@class, 'field')]/label[@for='" + mandatoryFieldsString + "']/../descendant::*[2]";
		} else {
			xpathString =  "//div[contains(@class,'ui-dialog')]//div[@class='fieldset']//div[@class='fieldGroup']/div[contains(@class, 'field')]/label[@for='" + mandatoryFieldsString + "']/../descendant::*[@data-binding]";
		}
		
		String readOnlyAttributeFromElement = getDriver().findElement(By.xpath(xpathString)).getAttribute("readonly");
		String disabledAttributeFromElement = getDriver().findElement(By.xpath(xpathString)).getAttribute("disabled");
		
		if(disabled) {
			info("Assert Check: Verifying if mandatory field \"" + mandatoryFieldsString + "\" is disabled");
			Assert.assertEquals(disabledAttributeFromElement, "true", "mandatory field \"" + mandatoryFieldsString + "\" is Enabled");
			info("Assert Check PASSED: mandatory field \"" + mandatoryFieldsString + "\" is disabled");
		} else {
			info("Assert Check: Verifying if mandatory field \"" + mandatoryFieldsString + "\" is not disabled");
			//Assert.assertEquals(disabledAttributeFromElement, "null", "mandatory field \"" + mandatoryFieldsString + "\" is Disabled");
			Assert.assertNull(disabledAttributeFromElement, "mandatory field \"" + mandatoryFieldsString + "\" is Disabled");
			info("Assert Check PASSED: mandatory field \"" + mandatoryFieldsString + "\" is not disabled");
		}

	}
		
		
	private void verifyIfMandatoryFieldsContainDataInPopUpDialog(String mandatoryFieldsString, String managementGroupItem) {
		
		String xpathString = null;
		
		if(managementGroupItem.equals("Course Equivalence")) {
			xpathString =  "//div[contains(@class,'ui-dialog')]//div[@class='fieldset']//div[@class='fieldGroup']/div[contains(@class, 'field')]/label[@for='" + mandatoryFieldsString + "']/../descendant::*[2]";
		} else {
			xpathString =  "//div[contains(@class,'ui-dialog')]//div[@class='fieldset']//div[@class='fieldGroup']/div[contains(@class, 'field')]/label[@for='" + mandatoryFieldsString + "']/../descendant::*[@data-binding]";
		}
		
		WebElement element = getDriver().findElement(By.xpath(xpathString));
		String tagOfElement = element.getTagName();
		int lengthOfText=0;
		String dataInMandatoryField=null;
		if (tagOfElement.equals("input")) {
			dataInMandatoryField = element.getAttribute("value");
			lengthOfText = element.getAttribute("value").length();
		} else if(tagOfElement.equals("textarea")) {
			dataInMandatoryField = element.getAttribute("textContent");
			lengthOfText = element.getAttribute("textContent").length();
		} else if(tagOfElement.equals("select")) {
			dataInMandatoryField = Page.getDefaultOptionSelectedFromDropdown(element);
			Assert.assertNotEquals(dataInMandatoryField, "<Select one>");
			lengthOfText = dataInMandatoryField.length();
		}
		
		info("Data in mandatory field \"" + mandatoryFieldsString + "\" is: " + dataInMandatoryField);
		info("Assert Check: Verifying if length of data in mandatory field \"" + mandatoryFieldsString + "\" is > 0");
		Assert.assertNotEquals(lengthOfText, 0, "Mandatory field \"" + mandatoryFieldsString + "\" does not have data" );
		info("Assert Check PASSED: mandatory field \"" + mandatoryFieldsString + "\" has some data");
		
	}

}
