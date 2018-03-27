package org.mmu.g4sm.qa.at.selenium.pages;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

//role actions permissions page
public class RoleActionPermissionsPage extends Page {
	
	@FindBy( how = How.XPATH, using = "//div[@class='tabBar']/p[@data-activate-tab='mainMenuActions']" )
    private WebElement mainMenu;
	
	@FindBy( how = How.XPATH, using = "//div[@class='tabBar']/p[@data-activate-tab='mosaicManagerActions']" )
    private WebElement systemManagerMenu;
	
	@FindBy( how = How.XPATH, using = "//div[@class='tabBar']/p[@data-activate-tab='contextMenuActions']" )
    private WebElement contextMenu;
	
	public RoleActionPermissionsPage( WebDriver driver ) {
        super( driver );
	}
	
	public void clickMainMenu() {
		mainMenu.click();
	}
	
	public void clickSystemManagerMenu() {
		systemManagerMenu.click();
	}
	
	public void clickContextMenu() {
		contextMenu.click();
	}
	
	
	public List<String[]> getRoleActionPermissionsForRole() {
		List<String[]> actionPermissionsList = new ArrayList<>();
		actionPermissionsList.add("MenuAction,Menu,Group,Entity,ONorOFF".split(","));
		List<WebElement> roleActionTabs = getDriver().findElements(By.xpath("//div[@class='tabBar']/p[contains(@data-activate-tab,'Actions')]"));
		LinkedHashMap<String,String> menuItemAndOnOff =new LinkedHashMap<String,String>();
		for(WebElement roleActionTab: roleActionTabs) {
			String name = roleActionTab.getAttribute("data-activate-tab");
			String actionName = roleActionTab.getAttribute("innerText");
			String actionNameWithoutChars = actionName.replaceAll("^[^a-zA-Z0-9\\s]+|[^a-zA-Z0-9\\s]+$", "");
			roleActionTab.click();
			waitForJSandJQueryToLoad();
			String xpath1 = "//div[contains(@class,'tab')][@id='" + name + "']";
			String xpath2 = "//ul[@class='accessList']/li/div[starts-with(@class,'acl')]";
			List<WebElement> menuItems = getDriver().findElements(By.xpath(xpath1 + xpath2));
			info("number of elements is " + menuItems.size());
			String menuItemText = null;
			String menuItemTextIncludingActionName = null;
			String menuItemChecked = null;
			for(WebElement menuItem: menuItems) {
				menuItemText = menuItem.findElement(By.xpath("./span[@class='heading']")).getText();
				menuItemTextIncludingActionName = actionNameWithoutChars + "," + menuItemText + "," + "" + "," + "";
				menuItemChecked = menuItem.findElement(By.xpath("./div[@class='onoffswitch']/input[@type='checkbox']")).getAttribute("checked");
				if(menuItemChecked == null) {
					menuItemTextIncludingActionName = menuItemTextIncludingActionName + "," + "OFF";
				} else if(menuItemChecked.equals("true")) {
					menuItemTextIncludingActionName = menuItemTextIncludingActionName + "," + "ON";
				}	
				
				String[] array1 = menuItemTextIncludingActionName.split(",");
				actionPermissionsList.add(array1);
				info("inserted " + menuItemTextIncludingActionName);
				menuItem.findElement(By.xpath("./span[@data-indicator='true']")).click();
				waitForJSandJQueryToLoad();
				WebElement item = menuItem.findElement(By.xpath("./following-sibling::ul[1]"));
				List<WebElement> mgtGroups = item.findElements(By.xpath("./li[@class='separator']"));
				for(WebElement mgtGroup: mgtGroups) {
					int index = mgtGroups.indexOf(mgtGroup) + 1;
					String mgtGroupTextWithOptionsOrSettings = mgtGroup.findElement(By.xpath("./h3")).getAttribute("innerText");
					List<WebElement> entities = mgtGroup.findElements(By.xpath("./following-sibling::li[./div[contains(@class,'acl')] and count(preceding-sibling::li[@class='separator'])=" + index + "]"));
					String[] array2 = null;
					for(WebElement entity: entities) {
						String entityName = entity.findElement(By.xpath("./div/span[@class='heading']")).getAttribute("innerText");
						String fullEntityName = actionNameWithoutChars + "," + menuItemText + "," + mgtGroupTextWithOptionsOrSettings + "," + entityName;
						String entityOnOrOff = entity.findElement(By.xpath("./div/div[@class='onoffswitch']/input[@type='checkbox']")).getAttribute("checked");
						if(entityOnOrOff == null) {
							fullEntityName = fullEntityName + "," + "OFF";
						} else if(entityOnOrOff.equals("true")) {
							fullEntityName = fullEntityName + "," + "ON";
						}
						
						array2 = fullEntityName.split(",");
						actionPermissionsList.add(array2);
						info("inserted " + fullEntityName);
						array2 = null;
					}
					
				}
				menuItem.findElement(By.xpath("./span[@data-indicator='true']")).click();
				waitForJSandJQueryToLoad();
				
			}
			
			
		}	
		return actionPermissionsList;  
	}

}
