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
 * Here all fields are verified in the Create Account process with one Existent Contact
 * @author CMONTE5
 *
 */

public class CreateAccountExistentContactTest {

	String filepath = "src\\test\\resources\\excel\\NewPerson.xlsx";
	private static WebDriver driver;
	private static ReadExcel readFile;
	
	Actions build = new Actions(driver);
	JavascriptExecutor js = (JavascriptExecutor) driver;
	
	By updateLocator = By.xpath("//span[contains(@id, 'Update-btnInnerEl')]");
	By errorMessage = By.xpath("//div[1][@class = 'message']");
	
	@BeforeClass
	public static void setUp() {
		System.setProperty("webdriver.chrome.driver","src\\test\\resources\\drivers\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--verbose");
        options.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
       
        driver = new ChromeDriver(options);
		readFile = new ReadExcel();
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
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

		By accountNumber =By.xpath("//label[contains(@id,'AccountNumber')]");
	    verifyTextBox(accountNumber);
	
	    By accountName =By.xpath("//label[contains(@id,'AccountName')]");
	    verifyTextBox(accountName);
	    
	    By accountParent =By.xpath("//label[contains(@id,'ParentAccount')]");
	    verifyTextBox(accountParent);
	    
	    By currency =By.xpath("//label[contains(@id,'Currency')]");
	    verifyTextBox(currency);
	    
	    By accountType =By.xpath("//label[contains(@id,'AccountType')]");
	    verifyTextBox(accountType);
	    
		By serviceTier =By.xpath("//label[contains(@id,'ServiceTier')]");
	    verifyTextBox(serviceTier);
	
	    By securityZone =By.xpath("//label[contains(@id,'SecurityZone')]");
	    verifyTextBox(securityZone);
	    
	    By doingBusinessAs =By.xpath("//label[contains(@id,'DBA')]");
	    verifyTextBox(doingBusinessAs);
	    
	    By segment =By.xpath("//label[contains(@id,'Segment')]");
	    verifyTextBox(segment);
	    
	    By billingPlan =By.xpath("//label[contains(@id,'BillingPlan')]");
	    verifyTextBox(billingPlan);
	    
		By delincuencyPlan =By.xpath("//label[contains(@id,'DelinquencyPlan')]");
	    verifyTextBox(delincuencyPlan);
	
	    By paymentAllocationPlan =By.xpath("//label[contains(@id,'PaymentAllocationPlan')]");
	    verifyTextBox(paymentAllocationPlan);
	    
	    By fixDueDate =By.xpath("//label[contains(@id,'FixDueDate')]");
	    verifyTextBox(fixDueDate);
	    
	    //Invoices Fixed On
	    By invoiceDayOfMonth =By.xpath("//input[contains(@id,'InvoiceDayOfMonth')]");
	    verifyEqualsTextBox(invoiceDayOfMonth, "1");
	    
	    By firstTwicePerMonthInvoiceDayOfMonth =By.xpath("//input[contains(@id,'FirstTwicePerMonthInvoiceDayOfMonth')]");
	    verifyEqualsTextBox(firstTwicePerMonthInvoiceDayOfMonth, "1");
	    
	    By secondTwicePerMonthInvoiceDayOfMonth =By.xpath("//input[contains(@id,'SecondTwicePerMonthInvoiceDayOfMonth')]");
	    verifyEqualsTextBox(secondTwicePerMonthInvoiceDayOfMonth, "15");
	    
	    By invoiceDayOfWeek =By.xpath("//input[contains(@id,'InvoiceDayOfWeek')]");
	    verifyEqualsTextBox(invoiceDayOfWeek, "Friday");
	    
	    By everyOtherWeekInvoiceAnchorDate =By.xpath("//input[contains(@id,'EveryOtherWeekInvoiceAnchorDate')]");
	    verifyEqualsTextBox(everyOtherWeekInvoiceAnchorDate, "01/01/2009");
	    
	    //Invoices, verify Default text
		By sendInvoices =By.xpath("//input[contains(@id,'SendInvoicesBy')]");
	    verifyEqualsTextBox(sendInvoices, "<none>");
	
	    //With this radioButton I verify the last radioButton
	    By separateIncoming =By.xpath("//input[contains(@id,'BillingLevelSeparateIncomingFundsByAccount_false')]");
	    verifyTextBox(separateIncoming);
	    
	    By defaultPaymentInstrument =By.xpath("//input[contains(@id,'PaymentInstrument')]");
	    verifyEqualsTextBox(defaultPaymentInstrument, "Responsive");
	    
	    //Contact Info
	    By name =By.xpath("//label[contains(@id,'PrimaryContactName')]");
	    verifyTextBox(name);
	    
	    By address =By.xpath("//label[contains(@id,'Address')]");
	    verifyTextBox(address);
	    
	    By emailAddress =By.xpath("//label[contains(@id,'PrimaryContactEmail')]");
	    verifyTextBox(emailAddress);
	    
	    //Company Type
	    By organizationType =By.xpath("//label[contains(@id,'OrganizationType')]");
	    verifyTextBox(organizationType);
	    
	    By FEIN =By.xpath("//label[contains(@id,'FEIN')]");
	    verifyTextBox(FEIN);
	
	    //Error with Blank fields
	    buildClick(updateLocator);
	    getIncorrectMessage(errorMessage);

		writeTextBoxByKeys(sendInvoices, "Email");
		
		By accountNameInput = By.xpath("//input[contains(@id,'AccountName')]");
		String accountNameText = readFile.getCellValue(filepath, "Sheet1", 2, 1);
        writeTextBoxByKeys(accountNameInput, accountNameText);
        
        // Error with Incorrectly fields
		writeTextBoxByKeys(firstTwicePerMonthInvoiceDayOfMonth, "a");
		Thread.sleep(3000);
	     
	    buildClick(updateLocator);
	    getIncorrectMessage(errorMessage);
	   
	    //Correct data
	    writeTextBoxByKeys(firstTwicePerMonthInvoiceDayOfMonth, "1");
	    
	    //Add Existing Contact
	    By addExistingContact = By.xpath("//span[contains(@id,'addExistingContact-btnInnerEl')]");
	    buildClick(addExistingContact);
	    
	    By typeContact = By.xpath("//input[contains(@id,'ContactType')]");
	    verifyTextBox(typeContact);
	    writeTextBoxByKeys(typeContact, "Person");
	    
	    By firstName = By.xpath("//input[contains(@id,'FirstName')]");
	    verifyTextBox(firstName);
	    writeTextBoxByKeys(firstName, "Caro");
	    
	    By lastName = By.xpath("//input[contains(@id,'LastName')]");
	    verifyTextBox(lastName);
	    writeTextBoxByKeys(lastName, "mc");
	    
	    By taxID = By.xpath("//input[contains(@id, 'TaxID')]");
	    verifyTextBox(taxID);
	    Thread.sleep(2000);
	    
	    By selectContact = By.xpath("//a[contains(@id,':0:_Select')]");
	    verifyTextBox(selectContact);
	    driver.findElement(selectContact).click();
	    
	    By editButton = By.xpath("//a[contains(@id,':EditLink')]");
	    WebElement edit = driver.findElement(editButton);
	    edit.click();
	    
	    By yesPayer = By.xpath("//input[contains(@id, 'PrimaryPayer_true')]");
	    driver.findElement(yesPayer).click();
	    
		buildClick(updateLocator);
		Thread.sleep(1000);
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
//			Thread.sleep(1000);
			
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
			driver.findElement(locator).sendKeys(Keys.TAB);
//			Thread.sleep(1000);

		} catch (Exception e) {
			System.out.println("Not found: " + locator);
		}
	}
}
