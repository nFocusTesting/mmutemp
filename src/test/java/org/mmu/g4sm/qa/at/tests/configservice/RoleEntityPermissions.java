package org.mmu.g4sm.qa.at.tests.configservice;

import java.util.Arrays;
import java.util.List;

import org.mmu.g4sm.qa.at.selenium.pages.GenericSearchPage;
import org.mmu.g4sm.qa.at.selenium.pages.LoginPage;
import org.mmu.g4sm.qa.at.selenium.pages.RoleActionPermissionsPage;
import org.mmu.g4sm.qa.at.selenium.pages.RoleEntityPermissionsPage;
import org.mmu.g4sm.qa.at.selenium.pages.mainpages.MainPage;
import org.mmu.g4sm.qa.at.selenium.utils.BaseTest;
import org.mmu.g4sm.qa.at.selenium.utils.CsvFactory;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class RoleEntityPermissions extends BaseTest {
	
	private LoginPage loginPage;
	private MainPage mainPage;
	private GenericSearchPage genericSearchPage;
	private RoleEntityPermissionsPage roleEntityPermissionsPage;
	private CsvFactory csvFactory;
	
	String user = getProp().getProperty("loginUser");
	String password = getProp().getProperty("loginUserPassword");
	String roles = getProp().getProperty("userRoles");
	List<String> u4smRoles = Arrays.asList(roles.split("\\s*,\\s*"));
	
	@BeforeSuite
	public void testSetUp() {
		setup();
	}

	@AfterSuite
	public void testTearDown() {
		teardown();
	}
	
	@Test
	public void getRoleEntityPermissions() {
		try {	
			loginToU4smWithUserNameAndPassword(user, password);
			mainPage = PageFactory.initElements(getDriver(), MainPage.class);
			mainPage.waitForJSandJQueryToLoad();
			for(String u4smRole:u4smRoles) {
				createCsvFileForRole(u4smRole);
				mainPage.clickOnMenuItem("mosaicManagerMenu");
				mainPage.goToOptionsOrSettings("mosaicManagerMenu", "Options");
				mainPage.clickItemUnderManagementGroup("Security", "Role Entity Permissions");
				String redirected_url = getDriver().getCurrentUrl();
				if(redirected_url.contains("/Details")) {
					redirected_url = redirected_url.replace("Details", "entitypermissions");
					getDriver().navigate().to(redirected_url);
				}
				mainPage.waitForJSandJQueryToLoad();
				genericSearchPage = PageFactory.initElements(getDriver(), GenericSearchPage.class);
				genericSearchPage.setSearchTextValue(u4smRole.trim());
				genericSearchPage.clickSimpleSearch();
				genericSearchPage.waitForJSandJQueryToLoad();
				explicitWait(200);
				genericSearchPage.clickRowFromSearchResultsTableByRowNUmber(1);
				genericSearchPage.waitForJSandJQueryToLoad();
				roleEntityPermissionsPage = PageFactory.initElements(getDriver(), RoleEntityPermissionsPage.class);
				List<String[]> permList = roleEntityPermissionsPage.getRoleEntityPermissionsForRole();
				csvFactory.InsertDataToCsvFromList(permList);
				
//				for(String[] permissionRow: permList) {
//					info(permissionRow.toString());
//				}
			}
			
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
		}
		
	}
	
	private void createCsvFileForRole(String u4smRole) {
		String filePath = getProp().getProperty("roleEntitiesPath") + u4smRole + "_RoleEntityPermissionsConfig.csv";
		csvFactory = new CsvFactory(filePath);
		
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
//		if(loginPage.isLoginErrorTextDisplayed()) {
//			info("Login Error Message displayed: " + loginPage.getLoginErrorMessage().trim());
//			info("FAIL: Unable to login to U4SM. Please see the Assertion Logs for more detail");
//			Assert.fail("Unable to login to U4SM");
//		}	
	}
}
