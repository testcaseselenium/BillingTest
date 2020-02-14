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
 *Test ID : 8365 - VWI-17244-CP216-SIT_Account_200001_TC1_Create Account_TC1_Create Account 
 * Here  New Person fields are verified in the Create Account process
 * @author CMONTE5
 *
 */

public class CreateAccountNewPersonTest {
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
	
	    //Add new Person
	    By addButton = By.xpath("//span[contains(@id,'addNewContact-btnInnerEl')]");
	    buildClick(addButton);
	    
	    By addNewPerson = By.xpath("//span[contains(@id,'addNewPerson-textEl')]");
	    driver.findElement(addNewPerson).click();
	    
	    //Contact info
	    String titleText = readFile.getCellValue(filepath, "Sheet1", 1, 1);
	    String firstnameText = readFile.getCellValue(filepath, "Sheet1", 2, 1);
	    String lastnameText = readFile.getCellValue(filepath, "Sheet1", 3, 1);
	    
	    String dateText = readFile.getCellValue(filepath, "Sheet1", 4, 1);
	    String professionText = readFile.getCellValue(filepath, "Sheet1", 5, 1);
	    String maritalText = readFile.getCellValue(filepath, "Sheet1", 6, 1);
	    String codiceFiscaleText = readFile.getCellValue(filepath, "Sheet1", 7, 1);
	    
	    String primaryEmailText = readFile.getCellValue(filepath, "Sheet1", 14, 1);	    
	    String placeNameText = readFile.getCellValue(filepath, "Sheet1", 18, 1);
	    String addressText = readFile.getCellValue(filepath, "Sheet1", 19, 1);
	    String addressNumberText = readFile.getCellValue(filepath, "Sheet1", 20, 1);
	    String cityCapparioText = readFile.getCellValue(filepath, "Sheet1", 22, 1);
	    
	    By title =By.xpath("//input[contains(@id,'Title')]");
	    verifyEqualsTextBox(title, "<none>");
	    writeTextBoxByKeys(title, titleText);
	    
	    By firstname =By.xpath("//input[contains(@id,'FirstName')]");
	    verifyEqualsTextBox(firstname, "");
	    writeTextBoxByKeys(firstname, firstnameText);
	    
	    By lastname =By.xpath("//input[contains(@id,'LastName')]");
	    verifyEqualsTextBox(lastname, "");
	    writeTextBoxByKeys(lastname, lastnameText);
	    
	    By dateOfBirth =By.xpath("//input[contains(@id,'DateOfBirth')]");
	    verifyEqualsTextBox(dateOfBirth, "");
	    writeTextBoxByKeys(dateOfBirth, dateText);
	    
	    By gender =By.xpath("//input[contains(@id,'Gender_option0')]");
	    verifyTextBox(gender);
	    driver.findElement(gender).click();
	    driver.findElement(gender).sendKeys(Keys.TAB);
	    
	    By maritalStatus =By.xpath("//input[contains(@id,'MaritalStatus')]");
	    verifyEqualsTextBox(maritalStatus, "<none>");
	    writeTextBoxByKeys(maritalStatus, maritalText);
	    
	    By profession =By.xpath("//input[contains(@id,'Profession')]");
	    verifyEqualsTextBox(profession, "<none>");
	    writeTextBoxByKeys(profession, professionText);
	    
	    By primaryPhone =By.xpath("//input[contains(@id,'PrimaryPhone')]");
	    verifyEqualsTextBox(primaryPhone, "<none>");
	  
	    By homePhone =By.xpath("//input[contains(@id,'HomePhone')]");
	    verifyEqualsTextBox(homePhone, "");
	    
	    By mobilePhone =By.xpath("//input[contains(@id,'CellPhone')]");
	    verifyEqualsTextBox(mobilePhone, "");
	    
	    By officePhone =By.xpath("//input[contains(@id,':Phone:')]");
	    verifyEqualsTextBox(officePhone, "");
	    
	    By fax =By.xpath("//input[contains(@id,'FaxPhone')]");
	    verifyEqualsTextBox(fax, "");
	    
	    //Address
	    By placeName =By.xpath("//input[contains(@id,'Toponimo')]");
	    verifyEqualsTextBox(placeName, "Via ");
	    writeTextBoxByKeys(placeName, placeNameText);
	    
	    By address =By.xpath("//input[contains(@id,'AddressLine2')]");
	    verifyEqualsTextBox(address, "");
	    writeTextBoxByKeys(address, addressText);
	    
	    By addressNumber = By.xpath("//input[contains(@id,'AddressLine3')]");
	    verifyEqualsTextBox(addressNumber, "");
	    writeTextBoxByKeys(addressNumber, addressNumberText);
	    
	    By postalCode =By.xpath("//input[contains(@id,'PostalCode')]");
	    verifyTextBox(postalCode);
	    
	    By city =By.xpath("//input[contains(@id,'City')]");
	    verifyEqualsTextBox(city, "");
	    
	    //Emails
	    By officialIDDV =By.xpath("//input[contains(@id,'OfficialIDDV')]");
	    verifyTextBox(officialIDDV);
        writeTextBoxByKeys(officialIDDV, codiceFiscaleText);
	    
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
	    
	    By postalCodeLens =By.xpath("//div[contains(@id,'SelectPostalCode')]");
	    driver.findElement(postalCodeLens).click();
	    Thread.sleep(3000);
	    
	    //Write correct City
	    By cityLocator =By.xpath("//input[contains(@id,'City')]");
	    writeTextBoxByKeys(cityLocator, cityCapparioText);
	    
	    By selectContact = By.xpath("//a[contains(@id,':0:_Select')]");
	    verifyTextBox(selectContact);
	    driver.findElement(selectContact).click();
	    
	    //OK
	    By updateLocator = By.xpath("//span[contains(@id, 'Update-btnInnerEl')]");
	    buildClick(updateLocator);
	    
	    //Error with Incorrectly fields
	     By firstTwicePerMonthInvoiceDayOfMonth =By.xpath("//input[contains(@id,'FirstTwicePerMonthInvoiceDayOfMonth')]");
	     writeTextBoxByKeys(firstTwicePerMonthInvoiceDayOfMonth, "a");
	     
	    buildClick(updateLocator);
	
	    By error = By.xpath("//div[@class = 'message']");
	    getIncorrectMessage(error);
	    
	    //Correct data
		By accountNameInput = By.xpath("//input[contains(@id,'AccountName')]");
		String accountNameText = readFile.getCellValue(filepath, "Sheet1", 2, 1);
        writeTextBoxByKeys(accountNameInput, accountNameText);
        
	    writeTextBoxByKeys(firstTwicePerMonthInvoiceDayOfMonth, "1");
	    
	    By sendInvoices =By.xpath("//input[contains(@id,'SendInvoicesBy')]");
	    writeTextBoxByKeys(sendInvoices, "Email");
		
	    buildClick(updateLocator);
		
	}
	
	public static void buildClick(By locator){
	Actions build = new Actions(driver);
    build.moveToElement(driver.findElement(locator)).moveByOffset(0, 0).click().build().perform();
	}

	public static void getIncorrectMessage(By errorMessage){
		
	    WebElement errorElemento = driver.findElement(errorMessage);
	    assertTrue(errorElemento.isEnabled());
	   System.out.println("Incorrect Fields :" + errorElemento.getText());
} 
	
	
	public static void verifyEqualsTextBox(By locator, String expected)
			throws InterruptedException {
		try {
			Thread.sleep(1000);
			WebElement element = driver.findElement(locator);
			System.out.println("Verified: " + element.getAttribute("value"));
			assertTrue(element.isEnabled());	
			assertEquals(expected, element.getAttribute("value"));
//			Thread.sleep(2000);
			
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
//			Thread.sleep(2000);
			
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
			Thread.sleep(1000);
			driver.findElement(locator).sendKeys(Keys.TAB);
			Thread.sleep(1000);

		} catch (Exception e) {
			System.out.println("Not found: " + locator);
		}
	}
}
