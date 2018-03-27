package org.mmu.g4sm.qa.at.selenium.pages;

import java.io.FileReader;
import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.mmu.g4sm.qa.at.selenium.utils.BaseTest;

import com.google.common.base.Function;

public abstract class Page extends BaseTest {

	private static WebDriver _driver = null;
	private static WebDriverWait webdriverWait;
	private static long fluentWaitTimeout ;
	
	public String u4smURL = getProp().getProperty("testTarget");
	public String benchmarkU4smURL = getProp().getProperty("benchmarkEnvironment");
	public boolean runConfigTest = Boolean.parseBoolean(getProp().getProperty("runConfigTest"));
	
	
	public Page(WebDriver driver) {
		_driver = driver;
		webdriverWait = new WebDriverWait(_driver, Integer.parseInt(getProp().getProperty("webDriverWaitTimeout")));
		fluentWaitTimeout = Long.parseLong(getProp().getProperty("fluentWaitTimeout"));
	}

	public WebDriverWait getWebDriverWait() {
		return webdriverWait;
	}
	
	public void WaitForPageToLoad() {
		getWebDriverWait().until(
				webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
	}
	
	
	public boolean waitForJSandJQueryToLoad() {

	    WebDriverWait wait = getWebDriverWait();

	    // wait for jQuery to load
	    ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
	      @Override
	      public Boolean apply(WebDriver driver) {
	        try {
	          return ((Long)((JavascriptExecutor)getDriver()).executeScript("return jQuery.active") == 0);
	        }
	        catch (Exception e) {
	          // no jQuery present
	          return true;
	        }
	      }
	    };

	    // wait for Javascript to load
	    ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
	      @Override
	      public Boolean apply(WebDriver driver) {
	        return ((JavascriptExecutor)getDriver()).executeScript("return document.readyState")
	        .toString().equals("complete");
	      }
	    };

	  return wait.until(jQueryLoad) && wait.until(jsLoad);
	}

	
	public  String handlePopUpMessagesOnSave() {
		String popUpMessageDisplayed = null;
		try {
			String xpathString = "//div[contains(@class, 'informationalMessages')]";
			
			if(isElementPresent(By.xpath(xpathString))) {
				info("Informational message is displayed, clicking ok to continue");
				WebElement buttonInpopUpMessageElement = _driver.findElement(By.xpath(xpathString + "/..//button"));
				buttonInpopUpMessageElement.click();
			}
			
		} catch (Exception e) {
			info("No Informational Message displayed, Continuing with the test");
		}
		
		return popUpMessageDisplayed;
	}
	
	
	public int getRowCountFromTable(WebElement table) {
		List<WebElement> rows = table.findElements(By.xpath("./tbody/tr"));
		return rows.size();
	}
	public void clickOkOnConfirmPopup() {
		clickOkOrCancelInConfirmationDialog("ok");
	}
	
	public void clickCancelOnConfirmPopup() {
		clickOkOrCancelInConfirmationDialog("cancel");
		
	}
	
	public void clickOkOrCancelInConfirmationDialog(String okOrCancel) {
		if (okOrCancel.equals("ok")) {
			waitForClickable(_driver.findElement(By.xpath("//div[@role='dialog']//button[./span[contains(text(),'OK')]]")));
			_driver.findElement(By.xpath("//div[@role='dialog']//button[./span[contains(text(),'OK')]]")).click();
			info("clicked OK on confirmation popup");
		} else {
			waitForClickable(_driver.findElement(By.xpath("//div[@role='dialog']//button[./span[contains(text(),'Cancel')]]")));
			_driver.findElement(By.xpath("//div[@role='dialog']//button[./span[contains(text(),'Cancel')]]")).click();
			info("clicked Cancel on confirmation popup");
		}
		
	}
	

	
	public static void selectDropDownValuesbyVisibleText(WebElement elementToSelect, String dropDownVisibleText) {
		try {
			Select select = new Select(elementToSelect);
			select.selectByVisibleText(dropDownVisibleText);
			explicitWait(200);
			info("Selected dropdown value " + getElementTextWithRetry(select.getFirstSelectedOption()));
		} catch (Exception e) {
			error(e);
		}
	}

	public boolean isElementPresentAndEnabled(WebElement element) {
		try {
			waitForVisibility(element);
			return element.isEnabled();
		} catch (NoSuchElementException e) {
			return false;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public boolean isElementPresentAndDisplayed(WebElement element) {
		try {
			waitForVisibility(element);
			return element.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		} catch (TimeoutException e) {
			return false;
		} 
	}



	public static void selectDropDownValuesbyIndex(WebElement elementToSelect, int index) {
		Select select = new Select(elementToSelect);
		select.selectByIndex(index);
	}
	
	public static void selectDropDownValuesbyValueText(WebElement elementToSelect, String optionText) {
		Select select = new Select(elementToSelect);
		WebElement option = select.getFirstSelectedOption();
		info(option.getText());
		select.selectByVisibleText(optionText);
	}
	
	
	public static String selectRandomnValueFromDropdown(WebElement dropDown, String defaultSelection) {
		String randomnSelectedOption = "";
		Select select = new Select(dropDown);
		List<WebElement> optionsList = select.getOptions();
		List<String> optionsTextList1 = new ArrayList<String>();
		List<String> optionsTextList2 = new ArrayList<String>();
		
		for(WebElement option: optionsList) {
			optionsTextList1.add(option.getText());
		}
		
		for(WebElement option: optionsList) {
			optionsTextList2.add(option.getText());
		}
		
		List<String> tempList = optionsTextList1;
		
		tempList.remove(0);
		tempList.remove(defaultSelection);
		int listSize = tempList.size();
		Random rand = new Random();
		int randomnIndex = rand.nextInt(listSize-1);
		randomnSelectedOption = tempList.get(randomnIndex);
		
		int indexForSelection = optionsTextList2.indexOf(randomnSelectedOption);
		selectDropDownValuesbyIndex(dropDown,indexForSelection);

		return randomnSelectedOption;
		
	}
	
	public String getCurrentDateAndTime() {
		String dateAndTime = null;
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public static String getRandomnString(int stringSize) {
		String randomString = "";
		String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder builder = new StringBuilder();
        Random rnd = new Random();
        while (builder.length() < stringSize) { 
            int index = (int) (rnd.nextFloat() * allowedChars.length());
            builder.append(allowedChars.charAt(index));
        }
        randomString = builder.toString();
		return randomString;
	}
	
	public static String getDefaultOptionSelectedFromDropdown(WebElement dropDown) {
		explicitWait(200);
		Select select = new Select(dropDown);		
		return select.getFirstSelectedOption().getText();
		
	}
	
	public static String getDefaultTextFromTextField(WebElement element) {
		explicitWait(200);
		String defaultText = null;
		if(element.getTagName().equals("input")) {
			defaultText = element.getAttribute("value");
		} else if(element.getTagName().equals("textarea")) {
			defaultText = element.getAttribute("textContent");
		}
		return defaultText;
	}

	
	public WebElement getDataTableRowByRowNumber(WebElement cellBody, int rowNumber) {
		WebElement row = null;
		try {
			String xpathString = ".//table/tbody/tr["+ rowNumber + "]";
			row = getDriver().findElement(By.xpath(xpathString));
		} catch (Exception e) {
			error(e);
		}
		return row;
	}


	public void setCheckBoxByState(WebElement element, boolean setCheckBoxStateTo) {
		try {
			if (setCheckBoxStateTo && !element.isSelected()) {
				element.click();
			}
			if (!setCheckBoxStateTo && element.isSelected()) {
				element.click();
			}
		} catch (Exception e) {
			error(e);
		}
	}



	public void waitForVisibility(WebElement element) throws Error {
		getWebDriverWait().until(ExpectedConditions.visibilityOf(element));
	}
	

	public void waitForClickable(WebElement element) throws Error {
		getWebDriverWait().until(ExpectedConditions.elementToBeClickable(element));
	}


	public void clickElementWithRetry(final WebElement element) {
		FluentWait<WebElement> customWait = new FluentWait<WebElement>(element)
				.withTimeout(Integer.parseInt(getProp().getProperty("webDriverWaitTimeout")), TimeUnit.SECONDS)
				.pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(StaleElementReferenceException.class).ignoring(TimeoutException.class);

		customWait.until(new Function<WebElement, Boolean>() {
			public Boolean apply(WebElement element) {
				try {
					element.click();
					return true;
				} catch (Exception e) {
					return false;
				}
			}
		});
	}


	public static String getElementTextWithRetry(final WebElement element) {
		FluentWait<WebElement> customWait = new FluentWait<WebElement>(element).withTimeout(15, TimeUnit.SECONDS)
				.pollingEvery(500, TimeUnit.MILLISECONDS).ignoring(StaleElementReferenceException.class);

		return customWait.until(new Function<WebElement, String>() {
			public String apply(WebElement element) {
				return element.getText();
			}
		});
	}

	public boolean isElementPresent(By locator) {
		try {
			getDriver().manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
			getDriver().findElement(locator);
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			getDriver().manage().timeouts().implicitlyWait(Long.parseLong(getProp().getProperty("implicit_time")),
					TimeUnit.SECONDS);
		}
	}


	public boolean getCheckBoxSelectedState(WebElement element) {
		boolean checkBoxSelectedState = false;
		try {
			checkBoxSelectedState = element.isSelected();
			info("CheckBox selected state is: " + checkBoxSelectedState);
		} catch (Exception e) {
			error(e);
		}
		return checkBoxSelectedState;
	}

}
