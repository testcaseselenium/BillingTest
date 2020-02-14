package com.fremap.billing.billing;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;

import com.fremap.prueba.excel.ReadExcel;



public class intAddaPersonContactTest {

		private static WebDriver driver;
		private static ReadExcel readFile;

		//Locator Contact Info
		private By persons = By.xpath("//input[contains(@id,'ContactType-inputEl')]");
		private By name = By.xpath("//input[contains(@id,'FirstName-inputEl')]");
		
		
@BeforeClass
public static void setUp() {
System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\drivers\\chromedriver.exe");
ChromeOptions options = new ChromeOptions();
options.addArguments("--ignore-certificate-errors");
options.addArguments("--verbose");
options.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);

driver = new ChromeDriver(options);
readFile = new ReadExcel();
}


@Test
public void billingPayCase() throws InterruptedException, IOException {

String filepath = "src\\test\\resources\\excel\\Billing.xlsx";
JavascriptExecutor js = (JavascriptExecutor) driver;
driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
driver.manage().window().maximize();
//driver.get("https://10.231.193.245/bc/BillingCenter.do");
driver.get("https://10.229.211.136/bc/BillingCenter.do");
// Loggin
WebElement username = driver.findElement(By.id("Login:LoginScreen:LoginDV:username-inputEl"));
username.sendKeys("su");

WebElement password = driver.findElement(By.id("Login:LoginScreen:LoginDV:password-inputEl"));
password.sendKeys("gw");

WebElement login = driver.findElement(By.id("Login:LoginScreen:LoginDV:submit-btnInnerEl"));
login.click();

Actions build = new Actions(driver);
//Textbox SearchPayment---
build.moveToElement(driver.findElement(By.id("TabBar:SearchTab-btnWrap"))).moveByOffset(30, 0).click().build().perform();
Thread.sleep(3000);
build.moveToElement(driver.findElement(By.id("TabBar:SearchTab:SearchGroup_ContactSearch-textEl"))).click().build().perform();
Thread.sleep(3000);
WebElement person = driver.findElement(By.id("ContactSearch:ContactSearchScreen:ContactSearchDV:ContactType-inputEl"));
js.executeScript("arguments[0].scrollIntoView();", person);
String personText = readFile.getCellValue(filepath, "Sheet6", 1, 1);
writeTextBoxByKeys(persons, personText);
Thread.sleep(3000); 

String nameText = readFile.getCellValue(filepath, "Sheet6", 2, 1);
writeTextBoxByKeys(name, nameText);
Thread.sleep(4000); 
By elementKey = By.tagName("html");
driver.findElement(elementKey).sendKeys(Keys.ALT + "s");

//Search ACCOUNT
build.moveToElement(driver.findElement(By.id("TabBar:AccountsTab-btnWrap"))).moveByOffset(30, 0).click().build().perform();
Thread.sleep(2000);
build.moveToElement(driver.findElement(By.id("TabBar:AccountsTab:AccountNumberSearchItem-inputEl"))).click().sendKeys(Keys.ENTER).build().perform();
Thread.sleep(4000); 

//Clear BUTTON
WebElement clear = driver.findElement(By.id("WebMessageWorksheet:WebMessageWorksheetScreen:WebMessageWorksheet_ClearButton-btnInnerEl"));
js.executeScript("arguments[0].scrollIntoView();", clear);
clear.click();
Thread.sleep(3000); 
//Actions
build.moveToElement(driver.findElement(By.id("AccountsGroup:AccountsMenuActions-btnInnerEl"))).moveByOffset(30, 0).click().build().perform();
Thread.sleep(3000); 
build.moveToElement(driver.findElement(By.id("AccountsGroup:AccountsMenuActions:AccountsMenuActions_NewAccount-textEl"))).click().build().perform();
Thread.sleep(4000); 
//Add an Existing Contact BUTTON
WebElement addExistingContact = driver.findElement(By.id("NewAccount:NewAccountScreen:NewAccountDV:NewAccountContactsLV_tb:addExistingContact-btnInnerEl"));
js.executeScript("arguments[0].scrollIntoView();", addExistingContact);
addExistingContact.click();

//type
String personsText = readFile.getCellValue(filepath, "Sheet6", 1, 1);
writeTextBoxByKeys(persons, personsText);
Thread.sleep(3000); 
//name 
String namesText = readFile.getCellValue(filepath, "Sheet6", 2, 1);
writeTextBoxByKeys(name, namesText);
Thread.sleep(4000); 
By elementKeys = By.tagName("html");
driver.findElement(elementKeys).sendKeys(Keys.ALT + "s");
Thread.sleep(5000); 
//Select BUTTON
By select = By.xpath("//a[contains(@id,':0:_Select')]");

WebElement selectBtn = driver.findElement(select);
js.executeScript("arguments[0].scrollIntoView();", selectBtn);
selectBtn.click();
Thread.sleep(4000); 

WebElement e = driver.findElement(By.id("NewAccount:NewAccountScreen:NewAccountDV:OrganizationType-inputEl"));
js.executeScript("arguments[0].scrollIntoView();", e);
e.click();
//Edit BUTTON
By edit = By.id("NewAccount:NewAccountScreen:NewAccountDV:NewAccountContactsLV:0:EditLink");
WebElement editBtn = driver.findElement(edit);
js.executeScript("arguments[0].scrollIntoView();", editBtn);
editBtn.click();

}

public static void writeTextBoxByKeys(By locator, String excelText) throws InterruptedException{
    driver.findElement(locator).sendKeys(Keys.CONTROL + "a");
    driver.findElement(locator).sendKeys(Keys.BACK_SPACE);
	Thread.sleep(2000);
    driver.findElement(locator).sendKeys(excelText);
    //driver.findElement(locator).sendKeys(Keys.ENTER);
    driver.findElement(locator).sendKeys(Keys.TAB);
    Thread.sleep(1000);   


}

}

