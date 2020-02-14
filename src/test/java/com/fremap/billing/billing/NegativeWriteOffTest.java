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


public class NegativeWriteOffTest {

		private static WebDriver driver;
		private static ReadExcel readFile;

		//Locator Account Info
		private By accountNumber = By.id("TabBar:AccountsTab:AccountNumberSearchItem-inputEl");
		private By next = By.xpath ("//span[contains(@id,'Next-btnInnerEl')]");
			
		private By unappliedFunds = By.id("AccountNewNegativeWriteoffWizard:NewNegativeWriteoffWizardDetailsStepScreen:AccountDesignatedUnappliedInputs:UnappliedFund-inputEl");
		private By unappliedFundo = By.id("NewDirectBillPayment:EditDBPaymentScreen:PaymentDetailsDV:UnappliedFunds-inputEl");
		private By amounts = By.xpath("//input[contains(@id,'Amount-inputEl')]");
				
		private By executeb = By.id("NewDirectBillPayment:EditDBPaymentScreen:ExecuteWithoutDistribution-btnEl");			
		private By reasons = By.id("AccountNewNegativeWriteoffWizard:NewNegativeWriteoffWizardDetailsStepScreen:NewNegativeWriteoffDetailsDV:NegativeReason-inputEl");
		private By finished = By.xpath("//span[contains(@id,'Finish-btnInnerEl')]");
		
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

build.moveToElement(driver.findElement(By.id("TabBar:AccountsTab-btnWrap"))).moveByOffset(30, 0).click().build().perform();
//Textbox SearchAccount
Thread.sleep(2000);
WebElement account = driver.findElement(By.id("TabBar:AccountsTab:AccountNumberSearchItem-inputEl"));
js.executeScript("arguments[0].scrollIntoView();", account);
Thread.sleep(1000);
String accountNumberText = readFile.getCellValue(filepath, "Sheet2", 1, 1);
driver.findElement(accountNumber).click();
Thread.sleep(2000);
driver.findElement(accountNumber).sendKeys(accountNumberText);

Thread.sleep(2000);
WebElement searchButton = driver.findElement(By.id("TabBar:AccountsTab:AccountNumberSearchItem_Button"));
searchButton.click();



//DIRECT PAYMENT

	WebElement actions = driver.findElement(By.id("AccountGroup:AccountDetailMenuActions-btnEl"));
    actions.click();
    Thread.sleep(2000);
    WebElement newPayment = driver.findElement(By.id("AccountGroup:AccountDetailMenuActions:AccountDetailMenuActions_Payments-textEl"));
    newPayment.click();
    Thread.sleep(2000);
    WebElement newBillPayment = driver.findElement(By.id("AccountGroup:AccountDetailMenuActions:AccountDetailMenuActions_Payments:AccountDetailMenuActions_NewDirectBillPayment-textEl"));
    newBillPayment.click();
 	Thread.sleep(1000);
	
 	WebElement date = driver.findElement(By.id("NewDirectBillPayment:EditDBPaymentScreen:PaymentDetailsDV:RealPaymentDate-trigger-picker"));
 	js.executeScript("arguments[0].scrollIntoView();", date);	
 	date.click();
 	Thread.sleep(3000);
 	WebElement dates = driver.findElement(By.id("NewDirectBillPayment:EditDBPaymentScreen:PaymentDetailsDV:RealPaymentDate-inputEl"));
 	dates.sendKeys(Keys.SPACE);
 	dates.sendKeys(Keys.TAB); 
	
 	Thread.sleep(1000);
 	String amountText = readFile.getCellValue(filepath, "Sheet2", 3, 1);
 	writeTextBoxByKeys(amounts, amountText);
 	Thread.sleep(1000);
 	
 	String unappliedFundsText = readFile.getCellValue(filepath, "Sheet2", 5, 1);
 	writeTextBoxByKeys(unappliedFundo, unappliedFundsText);
 	Thread.sleep(1000);
 	
 	WebElement executeWD = driver.findElement(executeb);
 	js.executeScript("arguments[0].scrollIntoView();", executeWD);
 	executeWD.click();
 	Thread.sleep(4000);
 	//Negative Write Off
 	Thread.sleep(2000);
 	build.moveToElement(driver.findElement(By.id("AccountGroup:AccountDetailMenuActions-btnEl"))).moveByOffset(30, 0).click().build().perform();
 	Thread.sleep(2000);
 	build.moveToElement(driver.findElement(By.id("AccountGroup:AccountDetailMenuActions:AccountDetailMenuActions_NewTransaction-textEl"))).click().build().perform();										 
 	Thread.sleep(2000);
 	build.moveToElement(driver.findElement(By.id("AccountGroup:AccountDetailMenuActions:AccountDetailMenuActions_NewTransaction:AccountDetailMenuActions_NegativeWriteoff-textEl"))).click().build().perform();										 
 	Thread.sleep(2000);
 	
 	//Next Wizard
 	WebElement nextBtn = driver.findElement(next);
 	js.executeScript("arguments[0].scrollIntoView();", nextBtn);
 	nextBtn.click();

 	Thread.sleep(2000);
 	String unappliedFundText = readFile.getCellValue(filepath, "Sheet2", 2, 1);
 	writeTextBoxByKeys(unappliedFunds, unappliedFundText);
 	Thread.sleep(1000);

 	String reasonText = readFile.getCellValue(filepath, "Sheet2", 4, 1);
 	writeTextBoxByKeys(reasons, reasonText);
 	Thread.sleep(2000);

 	WebElement nextB = driver.findElement(next);
 	js.executeScript("arguments[0].scrollIntoView();", nextB);
 	nextB.click();

 	Thread.sleep(2000);

 	WebElement finish = driver.findElement(finished);
 	js.executeScript("arguments[0].scrollIntoView();", finish);
 	finish.click();


}

public static void writeTextBoxByKeys(By locator, String excelText) throws InterruptedException{
    driver.findElement(locator).sendKeys(Keys.CONTROL + "a");
    driver.findElement(locator).sendKeys(Keys.BACK_SPACE);
	Thread.sleep(2000);
    driver.findElement(locator).sendKeys(excelText);
    driver.findElement(locator).sendKeys(Keys.ENTER);
    driver.findElement(locator).sendKeys(Keys.TAB);
    Thread.sleep(1000);
}

}
