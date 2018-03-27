package org.mmu.g4sm.qa.at.selenium.utils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

//import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.mmu.g4sm.qa.at.selenium.pages.mainpages.MainPage;


public class BaseTest extends SeleniumUtils {
	
	private static String screenshotSourcePath;
	private static String currentTestName;
	private static String mainBrowserWindow;
	
	
	public BaseTest() {
		setScreenshotSourcePath(getProp().getProperty( "screenshotResourcePath" ));
	}
	
    public static String getCurrentTestName() {
		return currentTestName;
	}

	protected void setCurrentTestName(Method currentTestName) {
		BaseTest.currentTestName = currentTestName.getName();
	}
	
	protected void setCurrentTestName(String appendedString) {
		BaseTest.currentTestName = BaseTest.currentTestName + appendedString;
	}

	protected static String getScreenshotSourcePath() {
		return BaseTest.screenshotSourcePath;
	}

	private static void setScreenshotSourcePath(String screenshotSourcePath) {
		BaseTest.screenshotSourcePath = screenshotSourcePath;
	}

	public void setup() {
        setDriver();
        WebDriver driver = getDriver();
        driver.manage().timeouts().implicitlyWait( Long.parseLong( getProp().getProperty( "implicit_time" ) ), TimeUnit.SECONDS );
        maximiseScreen();
        mainBrowserWindow = getDriver().getWindowHandle();
        if (getProp().getProperty( "incognitoMode" ).equals("TRUE") && getProp().getProperty("browser").contains("CHROME")) {
        	enableIncognitoChromeAutomationExtension();
        }
    }
    
	public static void teardown() {
        if ( getDriver() != null ) {
        	getDriver().quit();
    	}
    }
	
	public static void cleanUpBrowsers() {
		try {
			ArrayList<String> tabs = new ArrayList<String>(getDriver().getWindowHandles());
			for (String tab : tabs) {
				if (!tab.equals(mainBrowserWindow)) {
					getDriver().switchTo().window(tab);
					explicitWait(500);
					getDriver().close();
					explicitWait(1000);
				}
			}
			getDriver().switchTo().window(mainBrowserWindow);
		} catch (Exception e) {
			error(e);
		}
	}
	
	public static void cleanUpTestSession() {
		try {
			WebElement logout_btn = getDriver().findElement(By.xpath("//div[@id='switcher']//input[@id='Logout']"));
			logout_btn.click();
			explicitWait(1000);
		} catch (Exception e) {
			info("In cleanUpTestSession() - Logout button not found, user has already logged out.");
		}
	}
	
    
    private void maximiseScreen() {
    	((JavascriptExecutor) getDriver()).executeScript("window.moveTo(0, 0)");
    	((JavascriptExecutor) getDriver()).executeScript("window.resizeTo(screen.availWidth, screen.availHeight);");
    }
    
}
