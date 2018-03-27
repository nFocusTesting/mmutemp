package org.mmu.g4sm.qa.at.tests.configservice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.mmu.g4sm.qa.at.selenium.pages.GenericSearchPage;
import org.mmu.g4sm.qa.at.selenium.pages.LoginPage;
import org.mmu.g4sm.qa.at.selenium.pages.RoleActionPermissionsPage;
import org.mmu.g4sm.qa.at.selenium.pages.mainpages.MainPage;
import org.mmu.g4sm.qa.at.selenium.utils.BaseTest;
import org.mmu.g4sm.qa.at.selenium.utils.CsvFactory;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class RoleActions extends BaseTest {
	
	private LoginPage loginPage;
	private MainPage mainPage;
	private GenericSearchPage genericSearchPage;
	private RoleActionPermissionsPage roleActionPermissionsPage;
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
	public void getRoleActionPermissions() {
		try {
			loginToU4smWithUserNameAndPassword(user, password);
			mainPage = PageFactory.initElements(getDriver(), MainPage.class);
			mainPage.waitForJSandJQueryToLoad();
			for(String u4smRole:u4smRoles) {
				mainPage.clickOnMenuItem("mosaicManagerMenu");
				mainPage.goToOptionsOrSettings("mosaicManagerMenu", "Options");
				mainPage.clickItemUnderManagementGroup("Security", "Role Action Permissions");
				mainPage.waitForJSandJQueryToLoad();
				genericSearchPage = PageFactory.initElements(getDriver(), GenericSearchPage.class);
				genericSearchPage.setSearchTextValue(u4smRole.trim());
				genericSearchPage.clickSimpleSearch();
				genericSearchPage.waitForJSandJQueryToLoad();
				explicitWait(200);
				genericSearchPage.clickRowFromSearchResultsTableByRowNUmber(1);
				genericSearchPage.waitForJSandJQueryToLoad();
				roleActionPermissionsPage = PageFactory.initElements(getDriver(), RoleActionPermissionsPage.class);	
				List<String[]> actionPermissionsList   = roleActionPermissionsPage.getRoleActionPermissionsForRole();
				insertRoleActionPermissionsToCsv(u4smRole, actionPermissionsList);
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
	
	private void insertRoleActionPermissionsToCsv(String u4smRole, List<String[]> actionPermissionsList) {
		String filePath = getProp().getProperty("roleActionsPath") + u4smRole + "_RoleActionPermissionsConfig.csv";
		csvFactory = new CsvFactory(filePath);
		csvFactory.InsertDataToCsvFromList(actionPermissionsList);
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

}
