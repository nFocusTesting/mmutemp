package org.mmu.g4sm.qa.at.selenium.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LoginPage extends Page {
	
    public LoginPage( WebDriver driver ) {
        super( driver );
        //driver.get(u4smURL); // Go to the u4sm login / default page. 
        
        if(runConfigTest) {
        	driver.get(benchmarkU4smURL);
        } else {
        	driver.get(u4smURL);
        }
    }
    
    @FindBy( how = How.XPATH, using = "//input[@name='UserName']" )
    private WebElement userName_textField;
    
    @FindBy( how = How.XPATH, using = "//input[@name='Password']" )
    private WebElement password_textField;
    
    @FindBy( how = How.XPATH, using = "//div[@class='localeSelection']/select[@id='cultureSelection']" )
    private WebElement locale_Selection;
    
    @FindBy( how = How.XPATH, using = "//input[@class='loginButton']" )
    private WebElement login_button;
    
    @FindBy( how = How.XPATH, using = "//div[@class='loginBox']//p[@class='errorMessage']" )
    private WebElement login_Error;
    
    
    public boolean isu4smLoginPagePresentAndDisplayed() {
    	boolean isPresentAndDisplayed = isElementPresentAndDisplayed(userName_textField);
    	info("u4sm login page present and displayed state is: " + isPresentAndDisplayed);
    	return isPresentAndDisplayed;
    }
    
//    public boolean isLoginErrorTextDisplayed() {
//    	boolean loginErrorTextResult = false;	
//    	try {
//        	loginErrorTextResult = isElementPresentAndDisplayed(login_Error);
//        	info("Login error text displayed? : " + loginErrorTextResult);	
//    	} catch(Exception e) {
//    		
//    	}
//    	return loginErrorTextResult;	
//    }
    
  public boolean isLoginErrorTextDisplayed() {
	boolean loginErrorTextResult = false;	
	try {
    	loginErrorTextResult = login_Error.isDisplayed() ;
    	info("Login error text displayed? : " + loginErrorTextResult);	
	} catch(Exception e) {
		
	}
	return loginErrorTextResult;	
}
    
    
    public String getLoginErrorMessage() {
    	return login_Error.getAttribute("textContent");
    }
    
    public void setUsernameTextField(String text) {
    	try {
    		waitForVisibility(userName_textField);
    		userName_textField.click();
    		userName_textField.clear();
    		((JavascriptExecutor) getDriver()).executeScript("document.getElementsByName('" 
    	+ userName_textField.getAttribute("name") 
    	+ "').item(0).value = '" 
    	+ text 
    	+ "';");
    		info("Inserted, \"" + text + "\" into the username text field.");
    	} catch (Exception e) {
    		error(e);
    	} 
    }
    
    public void setPasswordTextField(String text) {
    	try {
    		password_textField.click();
    		password_textField.clear();
    		((JavascriptExecutor) getDriver()).executeScript("document.getElementsByName('" 
    		    	+ password_textField.getAttribute("name") 
    		    	+ "').item(0).value = '" 
    		    	+ text 
    		    	+ "';");
    		password_textField.click();
//    		((JavascriptExecutor) getDriver()).executeScript("document.getElementsByName('" 
//    		    	+ login_btn.getAttribute("name") 
//    		    	+ "').item(0).removeAttribute('disabled');");
    		info("Inserted, \"" + text + "\" into the password text field.");
    	} catch (Exception e) {
    		error(e);
    	}
    }
    
    public void clickLoginButton() {
    	//explicitWait(200);
    	try {
    		clickElementWithRetry(login_button);
    		info("Clicked the Login button.");
    	} catch (Exception e) {
    		error(e);
    	} 
    }
    
    public void loginTou4smWithUserNameAndPassword(String userName, String password) {
    	setUsernameTextField(userName);
    	setPasswordTextField(password);
    	String localeString="en-GB";
    	Page.selectDropDownValuesbyVisibleText(locale_Selection, localeString);
    	clickLoginButton();
    }
    
    public void setLocaleInLoginPage(String locale) {
    	String localeString=locale;
    	Page.selectDropDownValuesbyVisibleText(locale_Selection, localeString);  	
    }
    

}
