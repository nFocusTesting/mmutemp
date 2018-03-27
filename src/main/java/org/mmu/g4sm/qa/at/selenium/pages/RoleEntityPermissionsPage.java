package org.mmu.g4sm.qa.at.selenium.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class RoleEntityPermissionsPage extends Page {
	
	@FindBy( how = How.XPATH, using = "//table[@id='permissionTable']" )
    private WebElement permissionsTable;
	
	
	public RoleEntityPermissionsPage( WebDriver driver ) {
        super( driver );
	}

	public List<String[]> getRoleEntityPermissionsForRole() {
		List<String[]> entityPermissionsList = new ArrayList<>();
		entityPermissionsList.add("entityTitle1,entityTitle2,FieldsOrRelationships,FieldOrRelationshipName,CREATE,READ,MODIFY,DELETE,READ_SECURED".split(","));
		List<WebElement> entityBlockTitles1 = new ArrayList<>();
		entityBlockTitles1 = permissionsTable.findElements(By.xpath("./tbody/tr"));	
		String entityBlockTitle1Name = null;
		String entityPermissions1 = null;
		String permissionsRow1 = null;
		int i = 1;
		for(WebElement entityBlockTitle1: entityBlockTitles1) {
			waitForJSandJQueryToLoad();
			entityBlockTitle1Name = entityBlockTitle1.findElement(By.xpath("./td[1]/div/span[@class='entityBlockTitle']")).getText();
			entityPermissions1 = getPermissionsforEntity(entityBlockTitle1);
			permissionsRow1 = entityBlockTitle1Name + "," + "" + "," + "" + "," + "" + "," + entityPermissions1;
			String[] array1 = permissionsRow1.split(",");
			//entityPermissionsList.add(permissionsRow1);
			entityPermissionsList.add(array1);
			//explicitWait(1);
			info("inserted " + permissionsRow1);
			entityBlockTitle1.findElement(By.xpath("./td[1]/div/span[@class='icon expandIcon']")).click();
			waitForJSandJQueryToLoad();
			if (i<2) {
				List<WebElement> entityBlockTitles2 = entityBlockTitle1.findElements(By.xpath("./following-sibling::tr[contains(@class,'entityRow')]"));
				String entityBlockTitle2Name = null;
				String entityPermissions2 = null;
				String permissionsRow2 = null;	
			
				for(WebElement entityBlockTitle2:entityBlockTitles2) {
					entityBlockTitle2Name = entityBlockTitle2.findElement(By.xpath("./td[1]/div/span[@class='entityBlockTitle']")).getText();
					entityPermissions2 = getPermissionsforEntity(entityBlockTitle2);
					permissionsRow2 = entityBlockTitle1Name + "," + entityBlockTitle2Name + "," + "" + "," + "" + "," + entityPermissions2;
					String[] array2 = permissionsRow2.split(",");
					entityPermissionsList.add(array2);
					info("inserted " + permissionsRow2);
					entityBlockTitle2.findElement(By.xpath("./td[1]/div/span[@class='icon expandIcon']")).click();
					waitForJSandJQueryToLoad();
					List<WebElement> fieldOrRelationships = entityBlockTitle2.findElements(By.xpath("./following-sibling::tr[contains(@class,'fieldRow')][@data-object-type='field' or @data-object-type='relationship']"));
					String fieldsOrRelationshipsConstant = null;
					String fieldOrRelationshipName = null;
					String entityPermissions3 = null;
					String permissionsRow3 = null;
					for(WebElement fieldOrRelationship:fieldOrRelationships) {
						fieldsOrRelationshipsConstant = fieldOrRelationship.getAttribute("data-object-type");
						fieldOrRelationshipName = fieldOrRelationship.findElement(By.xpath("./td[1]/div/span[@class='entityBlockTitle']")).getText();
						entityPermissions3 = getPermissionsforEntity(fieldOrRelationship);
						permissionsRow3 = entityBlockTitle1Name + "," + entityBlockTitle2Name + "," + fieldsOrRelationshipsConstant + "," + fieldOrRelationshipName + "," + entityPermissions3;
						String[] array3 = permissionsRow3.split(",");
						entityPermissionsList.add(array3);
						info("inserted " + permissionsRow3);
						fieldsOrRelationshipsConstant = null;
						fieldOrRelationshipName = null;
						entityPermissions3 = null;
						permissionsRow3 = null;
					}
					entityBlockTitle2.findElement(By.xpath("./td[1]/div/span[@class='icon collapseIcon']")).click();
					waitForJSandJQueryToLoad();
					
					entityBlockTitle2Name = null;
					entityPermissions2 = null;
					permissionsRow2 = null;
				}
				i++;
			}
			entityBlockTitle1.findElement(By.xpath("./td[1]/div/span[@class='icon collapseIcon']")).click();
			waitForJSandJQueryToLoad();
			entityBlockTitle1Name = null;
			entityPermissions1 = null;
			permissionsRow1 = null;
		}

		return entityPermissionsList ;
	}	
	
	private String getPermissionsforEntity(WebElement entityBlockTitle1) {
		StringBuilder comaSeperatedPermissions = new StringBuilder();
		//sb.append(filename).append("/");
		//String comaSeperatedPermissions = null;
		String permissionValue = null;
		String className = null;
		List<WebElement> permissionElements = entityBlockTitle1.findElements(By.xpath("./td[@class='crudMatch']"));
		for(WebElement permissionElement: permissionElements) {
			int index = permissionElements.indexOf(permissionElement);			
			try {
				getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
				className = permissionElement.findElement(By.xpath("./span")).getAttribute("class");
				if(className.equals("icon rpNegativeIcon")) {
					permissionValue = "OFF";
				} else if(className.equals("icon rpAffirmativeIcon")) {
					permissionValue = "ON";
				} else if(className.equals("icon rpMaybeIcon") ) {
					permissionValue = "PARTIAL";
				}
			} catch (Exception e) {
				permissionValue = "N/A";
				getDriver().manage().timeouts().implicitlyWait(Long.parseLong(getProp().getProperty("implicit_time")),TimeUnit.SECONDS);
			}
			
			comaSeperatedPermissions.append(permissionValue);
			if(index < 4) {
				comaSeperatedPermissions.append(",");
			}
			permissionValue = null;				
		}
		return comaSeperatedPermissions.toString();
	}

}
