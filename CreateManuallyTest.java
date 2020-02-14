package com.fremap.billing.disbursement;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;

import com.fremap.prueba.excel.ReadExcel;

/**
 * Test ID : 8331 - VWI-17244-CP230-SIT_Payment_200005_TC2_Creating disbursement, manually 
 * 
 * @author CMONTE5
 *
 */
public class CreateManuallyTest {
	private static WebDriver driver;
	private static ReadExcel readFile;
		
	@BeforeClass
	public static void setUp() {
		System.setProperty("webdriver.chrome.driver","src\\test\\resources\\drivers\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--verbose");
        options.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
       
        driver = new ChromeDriver(options);
		readFile = new ReadExcel();
	}
		
	@Test
	public void accountCharges() throws InterruptedException, IOException {
		Actions build = new Actions(driver);
		String filepath = "src\\test\\resources\\excel\\BankTransferBilling.xlsx";
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://10.229.211.136/bc/BillingCenter.do");

		//Login
		WebElement username = driver.findElement(By.id("Login:LoginScreen:LoginDV:username-inputEl"));
		username.sendKeys("su");

		WebElement password = driver.findElement(By.id("Login:LoginScreen:LoginDV:password-inputEl"));
		password.sendKeys("gw");

		WebElement login = driver.findElement(By.id("Login:LoginScreen:LoginDV:submit-btnInnerEl"));
		login.click();
		Thread.sleep(2000);
		
		String accountNumber = readFile.getCellValue(filepath, "Sheet6", 7, 1);
		
		// Account arrow
		build.moveToElement(driver.findElement(By.xpath("//span[contains(@id, 'TabBar:AccountsTab-btnWrap')]"))).moveByOffset(30, 0).click().build().perform();
        //Textbox SearchAccount
		build.moveToElement(driver.findElement(By.xpath("//input[contains(@id, 'TabBar:AccountsTab:AccountNumberSearchItem-inputEl')]"))).moveByOffset(30, 0).click().sendKeys(accountNumber).build().perform();//Account RenewalTest

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// Lens button
		WebElement searchButton = driver.findElement(By.id("TabBar:AccountsTab:AccountNumberSearchItem_Button"));
		searchButton.click();
		
        Thread.sleep(2000);
        
		// Go to Disbursement
		WebElement actionsDisbursement = driver.findElement(By.xpath("//span[contains(@id, 'MenuActions')]"));
		actionsDisbursement.click();

		WebElement newTransaction = driver.findElement(By.xpath("//span[contains(@id, 'MenuActions_NewTransaction-textEl')]"));
		newTransaction.click();

		WebElement disbursement = driver.findElement(By.xpath("//span[contains(@id, 'MenuActions_Disbursement-textEl')]"));
		disbursement.click();

		//Disbursement Data
		String policyUnpaid = readFile.getCellValue(filepath, "Sheet6", 6, 1);
		By unapliedPolicy = By.xpath("//input[contains(@id, 'UnappliedFunds-inputEl')]");
		writeTextBoxByKeys(unapliedPolicy, policyUnpaid);
		
//		By unapliedamount = By.xpath("//div[contains(@id, 'UnappliedAmount-inputEl')]");
//		WebElement unapliedamountElement = driver.findElement(unapliedamount);
//		String unapliedamountText = unapliedamountElement.getText();
		String ammountNumber = readFile.getCellValue(filepath, "Sheet6", 2, 1);
		By amount = By.xpath("//input[contains(@id, 'amount-inputEl')]");
		writeTextBoxByKeys(amount, ammountNumber);
		driver.findElement(amount).sendKeys(Keys.BACK_SPACE);

		String reasonText = readFile.getCellValue(filepath, "Sheet6", 5, 1);
		By reason = By.xpath("//input[contains(@id, 'reason-inputEl')]");
		writeTextBoxByKeys(reason, reasonText);

		By nextButton = By.xpath("//span[contains(@id, 'Next')]");
		buildClick(nextButton);
		
		String paymentInstrumentText = readFile.getCellValue(filepath, "Sheet6", 3, 1);
		By paymentInstrument = By.xpath("//input[contains(@id, 'PaymentInstrument-inputEl')]");
		writeTextBoxByKeys(paymentInstrument, paymentInstrumentText);
		Thread.sleep(3000);
		
		buildClick(nextButton);
		
		By finishButton = By.xpath("//span[contains(@id, 'Finish')]");
		buildClick(finishButton);
	}
	
	public static void buildClick(By locator){
	Actions build = new Actions(driver);
    build.moveToElement(driver.findElement(locator)).moveByOffset(0, 0).click().build().perform();
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