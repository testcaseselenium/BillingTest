package com.fremap.billing.create;

import static org.junit.Assert.*;

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
 * Test ID : 8365 - VWI-17244-CP216-SIT_Account_200001_TC1_Create Account_TC1_Create Account from Step 23 to Step 38
 *  Here check Colaterall Fund option it's hidden, and Start Delinquency from Account Summary
 *  
 * @author CMONTE5
 *
 */

public class CreateAccountCollateralFundsTest {
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
		
		By accountNameInput = By.xpath("//input[contains(@id,'AccountName')]");
		String accountNameText = readFile.getCellValue(filepath, "Sheet1", 2, 1);
        writeTextBoxByKeys(accountNameInput, accountNameText);
		
		By sendInvoices =By.xpath("//input[contains(@id,'SendInvoicesBy')]");
        writeTextBoxByKeys(sendInvoices, "Mail");
		
        // Error with Incorrectly fields
        By invoiceDayOfMonth =By.xpath("//input[contains(@id,'InvoiceDayOfMonth')]");
		writeTextBoxByKeys(invoiceDayOfMonth, "a");
		Thread.sleep(3000);
	     
		By updateLocator = By.xpath("//span[contains(@id, 'Update-btnInnerEl')]");
		buildArrowClick(updateLocator);
	    Thread.sleep(2000);
	    
	    By errorMessage = By.xpath("//div[@class = 'message']");
	    getIncorrectMessage(errorMessage);
	
	    //Correct data
	    writeTextBoxByKeys(invoiceDayOfMonth, "1");
	    
	    //Add Existing Contact
	    By addExistingContact = By.xpath("//span[contains(@id,'addExistingContact-btnInnerEl')]");
	    buildClick(addExistingContact);
	    
	    By typeContact = By.xpath("//input[contains(@id,'ContactType')]");
	    writeTextBoxByKeys(typeContact, "Person");
	    
	    By firstName = By.xpath("//input[contains(@id,'FirstName')]");
	    writeTextBoxByKeys(firstName, "Caro");
	    
	    By lastName = By.xpath("//input[contains(@id,'LastName')]");
	    writeTextBoxByKeys(lastName, "mc");
	    
	    By selectContact = By.xpath("//a[contains(@id,':0:_Select')]");
	    driver.findElement(selectContact).click();
	    
	    By editButton = By.xpath("//a[contains(@id,':EditLink')]");
	    WebElement edit = driver.findElement(editButton);
	    edit.click();
	    
	    By yesPayer = By.xpath("//input[contains(@id, 'PrimaryPayer_true')]");
	    driver.findElement(yesPayer).click();
	    
	    buildClick(updateLocator);
	    Thread.sleep(2000);
	    buildArrowClick(updateLocator);
	    
		//In Action > New Transaction the option of collateral funds should be hidden. 
//		WebElement actions2 = driver.findElement(By.xpath("//span[contains(@id, 'AccountsMenuActions-btnWrap')]"));
//		actions2.click();
//		
//		WebElement newTransaction = driver.findElement(By.xpath("//span[contains(@id, 'MenuActions_NewTransaction-textEl')]"));
//		newTransaction.click();
//
//		WebElement collateralFunds = driver.findElement(By.xpath("//span[contains(@id, 'CollateralFunds')]"));
//	    assertFalse(collateralFunds.isEnabled());
	    
	    //The system should update the value and bring you back to the Account Details screen. 
	    By detail = By.xpath("//td[contains(@id, 'Detail')]");
	    buildClick(detail);
		
	    By editLocator = By.xpath("//span[contains(@id, 'Edit')]");
	    buildClick(editLocator);
	    
	    //Get number Account to verify later on Policy Center
	    By numberAccount = By.xpath("//div[contains(@id, 'AccountNumber-inputEl')]");
	    WebElement numberAccountElement = driver.findElement(numberAccount);
	    String getNumberAccount = numberAccountElement.getText();
	    System.out.println("Number New Account is : " + getNumberAccount);
	    
	    By parentAccount = By.xpath("//input[contains(@id, 'ParentAccount')]");
	    writeTextBoxByKeys(parentAccount, accountNameText);
	    buildClick(parentAccount);
	    getIncorrectMessage(errorMessage);
	    
	    By lensParentAccount = By.xpath("//a[contains(@id, 'AccountPicker')]");
	    driver.findElement(lensParentAccount).click();
	    
	    writeTextBoxByKeys(accountNameInput, "caro mc");
	    
	    By selectAccountName = By.xpath(" //div[contains(text(),'caro mc')]/../../..//a[contains(@id, 'Select')]");
	    driver.findElement(selectAccountName).click();
	    
	    By emailInvoice = By.xpath("//input[contains(@id, 'SendInvoicesBy')]");
	    writeTextBoxByKeys(emailInvoice, "Email");
	    
	    buildArrowClick(updateLocator);
	    Thread.sleep(2000);
	    
	    //Start Delinquency
	    By startDelinquency = By.xpath("//span[contains(@id, 'StartDelinquency')]");
	    buildArrowClick(startDelinquency);
	    
	    By checkcolumn = By.xpath("//img[contains(@class, 'checkcolumn')]");
	    buildClick(checkcolumn);
	    
	    By execute = By.xpath("//span[contains(@id, 'Execute')]");
	    buildClick(execute);
	    
//	    By delinquencies = By.xpath("//td[contains(@id, 'Delinquencies')]");
//	    buildClick(delinquencies);
//	    
	    By openDelincuency = By.xpath("//div[contains(text(), 'Open')]");
	    WebElement openElement = driver.findElement(openDelincuency);
	    assertTrue(openElement.isEnabled());
	    
	    //Go to Policy Center and Confirm
	    driver.get("https://10.229.211.136/pc/PolicyCenter.do");
	    driver.findElement(By.id("Login:LoginScreen:LoginDV:username-inputEl")).sendKeys("su");
		driver.findElement(By.id("Login:LoginScreen:LoginDV:password-inputEl")).sendKeys("gw");
		driver.findElement(By.id("Login:LoginScreen:LoginDV:submit-btnInnerEl")).click();
		Thread.sleep(2000);
		build.moveToElement(driver.findElement(By.id("TabBar:AccountTab-btnWrap"))).moveByOffset(30, 0).click().build().perform();
		By accountTextBox = By.id("TabBar:AccountTab:AccountTab_AccountNumberSearchItem-inputEl");
		writeTextBoxByKeys(accountTextBox, getNumberAccount);
		WebElement searchButton = driver.findElement(By.id("TabBar:AccountTab:AccountNumberSearchItem_Button"));
		searchButton.click();
        Thread.sleep(2000);
		
	}
	
	public static void buildArrowClick(By locator){
		Actions build = new Actions(driver);
	    build.moveToElement(driver.findElement(locator)).moveByOffset(30, 0).click().build().perform();
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
