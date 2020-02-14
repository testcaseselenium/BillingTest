package com.fremap.billing.create;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

/**
 * Test ID : 8365 - VWI-17244-CP216-SIT_Account_200001_TC1_Create Account_TC1_Create Account 
 * Here  New Company fields are verified in the Create Account process
 * @author CMONTE5
 *
 */

public class CreateAccountNewCompanyTest {
	String filepath = "src\\test\\resources\\excel\\NewPerson.xlsx";
	
	private static WebDriver driver;
	private static ReadExcel readFile;
	
	Actions build = new Actions(driver);
	JavascriptExecutor js = (JavascriptExecutor) driver;
	
	@BeforeClass
	public static void setUp() {
		System.setProperty("webdriver.chrome.driver","src\\test\\resources\\drivers\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--verbose");
        options.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
       
        driver = new ChromeDriver(options);
		readFile = new ReadExcel();
		
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://10.229.211.136/bc/BillingCenter.do");

	}
		
	@Test
	public void createAccount() throws InterruptedException, IOException {
		
		//Login
		WebElement username = driver.findElement(By.id("Login:LoginScreen:LoginDV:username-inputEl"));
		username.sendKeys("su");

		WebElement password = driver.findElement(By.id("Login:LoginScreen:LoginDV:password-inputEl"));
		password.sendKeys("gw");

		WebElement login = driver.findElement(By.id("Login:LoginScreen:LoginDV:submit-btnInnerEl"));
		login.click();
		Thread.sleep(2000);
		
		// Account arrow
		build.moveToElement(driver.findElement(By.xpath("//span[contains(@id, 'TabBar:AccountsTab-btnWrap')]"))).moveByOffset(0, 0).click().build().perform();
	
		WebElement actions = driver.findElement(By.xpath("//span[contains(@id, 'AccountsMenuActions-btnWrap')]"));
		actions.click();

		WebElement newAccount = driver.findElement(By.xpath("//span[contains(@id, 'MenuActions_NewAccount-textEl')]"));
		newAccount.click();
	
	    //Add new Company
	    By addButton = By.xpath("//span[contains(@id,'addNewContact-btnInnerEl')]");
	    WebElement addButtonElement = driver.findElement(addButton);
	    js.executeScript("arguments[0].scrollIntoView();", addButtonElement);
	    addButtonElement.click();
	    
	    By addNewCompany = By.xpath("//span[contains(@id,'addNewCompany-textEl')]");
	    driver.findElement(addNewCompany).click();
	    
	    //Verify and fill all with requirements suggested
	    
	    //Contact info
	    String companyText = readFile.getCellValue(filepath, "Sheet0", 1, 1);
	    By company =By.xpath("//input[contains(@id,'Name')]");
	    verifyEqualsTextBox(company, "");
	    writeTextBoxByKeys(company, companyText);
	    
	    By primaryPhone =By.xpath("//input[contains(@id,'PrimaryPhone')]");
	    verifyEqualsTextBox(primaryPhone, "<none>");
	  
	    By homePhone =By.xpath("//input[contains(@id,'HomePhone')]");
	    verifyEqualsTextBox(homePhone, "");
	    
	    By mobilePhone =By.xpath("//input[contains(@id,'MobilePhone')]");
	    verifyEqualsTextBox(mobilePhone, "");
	    
	    By officePhone =By.xpath("//input[contains(@id,'OfficePhone')]");
	    verifyEqualsTextBox(officePhone, "");
	    
	    By fax =By.xpath("//input[contains(@id,'NationalSubscriberNumber')]");
	    verifyEqualsTextBox(fax, "");
	    
	    //Address
	    String toponimoText = readFile.getCellValue(filepath, "Sheet0", 12, 1);
	    By toponimo =By.xpath("//input[contains(@id,'Toponimo')]");
	    verifyEqualsTextBox(toponimo, "Via ");
	    writeTextBoxByKeys(toponimo, toponimoText);
	    
	    String addressNameText = readFile.getCellValue(filepath, "Sheet0", 13, 1);
	    By addressName =By.xpath("//input[contains(@id,'AddressLine2')]");
	    verifyEqualsTextBox(addressName, "");
	    writeTextBoxByKeys(addressName, addressNameText);
	    
	    String houseNumberText = readFile.getCellValue(filepath, "Sheet0", 14, 1);
	    By houseNumber =By.xpath("//input[contains(@id,'AddressLine3')]");
	    verifyEqualsTextBox(houseNumber, "");
	    writeTextBoxByKeys(houseNumber, houseNumberText);
	    
	    By postalCode =By.xpath("//input[contains(@id,'PostalCode')]");
	    verifyTextBox(postalCode);
	    
	    By city =By.xpath("//input[contains(@id,'City')]");
	    verifyEqualsTextBox(city, "");
	    
	    //Emails
	    By officialIDDV =By.xpath("//input[contains(@id,'OfficialIDDV')]");
	    verifyTextBox(officialIDDV);

	    String primaryEmailText = readFile.getCellValue(filepath, "Sheet0", 8, 1);
	    By primaryEmail =By.xpath("//input[contains(@id,'Email')]");
	    verifyEqualsTextBox(primaryEmail, "");
	    verifyTextBox(primaryEmail);
	    writeTextBoxByKeys(primaryEmail, primaryEmailText);
	    
	    By secondaryEmail =By.xpath("//input[contains(@id,'SecondaryEmail')]");
	    verifyEqualsTextBox(secondaryEmail, "");
	    verifyTextBox(secondaryEmail);
	    
	    By primaryPayer =By.xpath("//input[contains(@id,'PrimaryPayer_false')]");
	    verifyTextBox(primaryPayer);

	    By yesPayer = By.xpath("//input[contains(@id, 'PrimaryPayer_true')]");
	    driver.findElement(yesPayer).click();
	    
	    //Roles
	    By addRoles =By.xpath("//span[contains(@id,'RolesLV_tb:Add')]");
	    WebElement addRolesElement = driver.findElement(addRoles);
	    js.executeScript("arguments[0].scrollIntoView();", addRolesElement);
	    addRolesElement.click();
	    
	    By role = By.xpath("//div[@class = 'x-grid-cell-inner ']");
	    build.moveToElement(driver.findElement(role)).moveByOffset(30, 0).click().build().perform();
	    Thread.sleep(1000);
	    By insured = By.name("Role");
	    writeTextBoxByKeys(insured, "Insured");
	        
	    By postalCodeLens =By.xpath("//div[contains(@id,'SelectPostalCode')]");
	    driver.findElement(postalCodeLens).click();
	    Thread.sleep(3000);
	    
	    //Write correct City
	    String cityCapparioText = readFile.getCellValue(filepath, "Sheet0", 16, 1);
	    By cityLocator =By.xpath("//input[contains(@id,'City')]");
	    writeTextBoxByKeys(cityLocator, cityCapparioText);
	    
	    By selectContact = By.xpath("//a[contains(@id,':0:_Select')]");
	    verifyTextBox(selectContact);
	    driver.findElement(selectContact).click();
	    
	    //OK
	    By updateLocator = By.xpath("//span[contains(@id, 'Update-btnInnerEl')]");
	    WebElement updateElement = driver.findElement(updateLocator);
	    js.executeScript("arguments[0].scrollIntoView();", updateElement);
	    updateElement.click();
	    
	    //Error with Incorrectly fields
	     By firstTwicePerMonthInvoiceDayOfMonth =By.xpath("//input[contains(@id,'FirstTwicePerMonthInvoiceDayOfMonth')]");
	     writeTextBoxByKeys(firstTwicePerMonthInvoiceDayOfMonth, "a");
	     
	    build.moveToElement(driver.findElement(updateLocator)).moveByOffset(30, 0).click().build().perform();
	
	    By error = By.xpath("//div[@class = 'message']");
	    WebElement errorElement = driver.findElement(error);
	    assertTrue(errorElement.isEnabled());
	    System.out.println("Incorrect Fields " + errorElement.getText()); 
	    
	    //Correct data
		By accountNameInput = By.xpath("//input[contains(@id,'AccountName')]");
		String accountNameText = readFile.getCellValue(filepath, "Sheet1", 2, 1);
        writeTextBoxByKeys(accountNameInput, accountNameText);
        
	    writeTextBoxByKeys(firstTwicePerMonthInvoiceDayOfMonth, "1");
	    
	    By sendInvoices =By.xpath("//input[contains(@id,'SendInvoicesBy')]");
	    writeTextBoxByKeys(sendInvoices, "Email");
		
	    build.moveToElement(driver.findElement(updateLocator)).moveByOffset(30, 0).click().build().perform();
		
	}
	

	public static void verifyEqualsTextBox(By locator, String expected)
			throws InterruptedException {
		try {
			Thread.sleep(1000);
			WebElement element = driver.findElement(locator);
			System.out.println("Verified: " + element.getAttribute("value"));
			assertTrue(element.isEnabled());	
			assertEquals(expected, element.getAttribute("value"));
			Thread.sleep(2000);
			
		} catch (Exception e) {
			System.out.println("Not found: " + locator);
		}
	}
	
	public static void verifyTextBox(By locator)
			throws InterruptedException {
		try {
			
			WebElement element = driver.findElement(locator);
			System.out.println(element.getText());
			assertTrue(element.isEnabled());		
			Thread.sleep(2000);
			
		} catch (Exception e) {
			System.out.println("Not found: " + locator);
		}
	}
	
	public static void writeTextBoxByKeys(By locator, String excelText)
			throws InterruptedException {
		try {
			Thread.sleep(1000);
			driver.findElement(locator).sendKeys(Keys.CONTROL + "a");
			driver.findElement(locator).sendKeys(Keys.BACK_SPACE);
			driver.findElement(locator).sendKeys(excelText);
			Thread.sleep(1000);
			driver.findElement(locator).sendKeys(Keys.ENTER);
			Thread.sleep(2000);
			driver.findElement(locator).sendKeys(Keys.TAB);
			Thread.sleep(1000);

		} catch (Exception e) {
			System.out.println("Not found: " + locator);
		}
	}
}
