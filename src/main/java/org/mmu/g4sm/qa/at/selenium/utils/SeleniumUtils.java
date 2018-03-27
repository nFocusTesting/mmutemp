package org.mmu.g4sm.qa.at.selenium.utils;

import java.io.File;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


public class SeleniumUtils extends LoggingFactory {

	private static String binaryResourcePath;
	private static WebDriver driver;

	public enum Browsers {
		FIREFOX,CHROME_REMOTE,CHROME_MAC,CHROME_LINUX_X64,LOCAL,IE_REMOTE,IE_LOCAL, CHROME_LOCAL
	}
	
	
	public SeleniumUtils() {
		binaryResourcePath = getProp().getProperty( "binaryResourcePath" );
	}
	
	
	protected void setDriver() {
		if ( null == driver ) {
            String browser = getProp().getProperty( "browser" );
            driver = getDriverInstance(Boolean.parseBoolean(getProp().getProperty( "incognitoMode" )), browser );
            if (getProp().getProperty("browser").contains("REMOTE")) {
            	driver = new Augmenter().augment(driver);
            }
        }
	}
	
	public static WebDriver getDriver() {
        return driver;
    }    
	    
	private static WebDriver getDriverInstance( boolean incognitoMode, String browser ) {
		Browsers browsers = Browsers.valueOf(browser);
        DesiredCapabilities capability;
        WebDriver newDriver = null;
        //ChromeOptions options = new ChromeOptions();
        ChromeOptions options;

        switch ( browsers ) {
            case CHROME_REMOTE:
            	capability = getChromeCapabilities(incognitoMode, true);
            	newDriver = getRemoteWebDriver( getProp().getProperty("Chrome_remoteIP"), getProp().getProperty("Chrome_remotePort"), capability );
                break;
            case CHROME_LOCAL:
            	capability = getChromeCapabilities(incognitoMode, false);
            	options = getChromeOptions(incognitoMode);
            	ChromeDriverService service = new ChromeDriverService.Builder()
                        .usingDriverExecutable(new File(System.getProperty("user.dir") + binaryResourcePath + "chromeDriver/chromedriver.exe"))
                        .usingAnyFreePort()
                        .build();
            	//System.setProperty( "webdriver.chrome.driver", System.getProperty( "user.dir" ) + binaryResourcePath + "chromeDriver/chromedriver.exe" );
            	newDriver = new ChromeDriver(service, options);
                break;
            case IE_REMOTE:
            	capability = getIECapabilities();
            	newDriver = getRemoteWebDriver( getProp().getProperty("IE_remoteIP"), getProp().getProperty("IE_remotePort"), capability);
                break;
            case IE_LOCAL:
            	capability = getIECapabilities();
                System.setProperty( "webdriver.ie.driver", System.getProperty( "user.dir" ) + binaryResourcePath + "ieDriver/IEDriverServer.exe" );
                newDriver = new InternetExplorerDriver( capability );
                break;
            default:
                error( "No browser provided. Shutting down the test." );
                System.exit( 1 );
                break;
        }
        return newDriver;
    }
	
	public void enableIncognitoChromeAutomationExtension() {
		try {
			getDriver().get("chrome://extensions-frame");
			getDriver().findElement(By.xpath("//*[@focus-type='incognito']")).click();
			info("Enabling the automation extension for Chrome via incognito.");
		} catch (Exception e) {}
	}

	public static void explicitWait(long millis) {
		try {
			Thread.sleep(millis);
			info("Explicitly waiting for " + millis + " milliseconds");	
		} catch (InterruptedException e) {
			error(e);
		}
	}
	
	private static ChromeOptions getChromeOptions(boolean incognito) {
		ChromeOptions options = new ChromeOptions();
		if (incognito) 
			{ options.addArguments( "--incognito" ); }
		options.addArguments( "test-type" );
        options.addArguments( "--start-maximized" );
        options.addArguments("--no-sandbox");
        return options;
	}
	
	private static DesiredCapabilities getChromeCapabilities(boolean incognito, boolean remote) {
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability( "nativeEvents", false );
        //capabilities.setCapability( ChromeOptions.CAPABILITY, getChromeOptions(incognito) );
        capabilities.setPlatform( Platform.WINDOWS );
        return capabilities;
	}
	
	private static DesiredCapabilities getIECapabilities() {
		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setBrowserName("internet explorer");
        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
        capabilities.setCapability( "nativeEvents", false );
        capabilities.setCapability("ie.ensureCleanSession", true);
        capabilities.setPlatform( Platform.WINDOWS );
        return capabilities;
	}
	
	private static RemoteWebDriver getRemoteWebDriver(String hostName, String portNumber, DesiredCapabilities capabilities) {
		RemoteWebDriver remoteDriver = null;
		try {
			remoteDriver = new RemoteWebDriver(new URL( "http://" 
												+ hostName 
												+ ":" 
												+ portNumber 
												+ "/wd/hub" ), capabilities);
		} catch (Exception e) {
			error(e);
		}
		return remoteDriver;
	}
}

	
        
	