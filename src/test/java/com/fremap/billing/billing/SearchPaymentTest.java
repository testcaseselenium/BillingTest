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
import org.openqa.selenium.interactions.Actions;

import com.fremap.prueba.excel.ReadExcel;


public class SearchPaymentTest {

		private static WebDriver driver;
		private static ReadExcel readFile;

		//Locator Account Info
		private By accountNumber = By.name("PaymentSearch:PaymentSearchScreen:PaymentSearchDV:AccountNumberCriterion");
		private By payMethod = By.id("PaymentSearch:PaymentSearchScreen:PaymentSearchDV:MethodCriterion-inputEl");
		private By accountNumbers = By.id("TabBar:AccountsTab:AccountNumberSearchItem-inputEl");
		private By unappliedFundo = By.id("NewDirectBillPayment:EditDBPaymentScreen:PaymentDetailsDV:UnappliedFunds-inputEl");
		private By amounts = By.xpath("//input[contains(@id,'Amount-inputEl')]"); 
		private By executeb = By.id("NewDirectBillPayment:EditDBPaymentScreen:ExecuteWithoutDistribution-btnInnerEl");	
		
@BeforeClass
public static void setUp() {
System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\drivers\\chromedriver.exe");
driver = new ChromeDriver();
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
build.moveToElement(driver.findElement(By.id("TabBar:SearchTab:SearchGroup_PaymentSearch-itemEl"))).click().build().perform();
Thread.sleep(3000);
WebElement account = driver.findElement(By.id("PaymentSearch:PaymentSearchScreen:PaymentSearchDV:AccountNumberCriterion-inputEl"));
js.executeScript("arguments[0].scrollIntoView();", account);
String accountNumberText = readFile.getCellValue(filepath, "Sheet4", 1, 1);
writeTextBoxByKeys(accountNumber, accountNumberText);
Thread.sleep(3000);

String payMethodText = readFile.getCellValue(filepath, "Sheet4", 2, 1);
writeTextBoxByKeys(payMethod, payMethodText);
Thread.sleep(5000);
//By elementKey = By.tagName("html");
//driver.findElement(elementKey).sendKeys(Keys.ALT + "s");
WebElement searchB = driver.findElement(By.id("PaymentSearch:PaymentSearchScreen:PaymentSearchDV:SearchAndResetInputSet:SearchLinksInputSet:Search"));
js.executeScript("arguments[0].scrollIntoView();", searchB);
searchB.click();
Thread.sleep(2000);
build.moveToElement(driver.findElement(By.id("TabBar:AccountsTab-btnWrap"))).moveByOffset(30, 0).click().build().perform();
//Textbox SearchAccount
Thread.sleep(2000);
WebElement accounts = driver.findElement(By.id("TabBar:AccountsTab:AccountNumberSearchItem-inputEl"));
js.executeScript("arguments[0].scrollIntoView();", accounts);
Thread.sleep(1000);
String accountNumbersText = readFile.getCellValue(filepath, "Sheet4", 1, 1);
driver.findElement(accountNumbers).click();
Thread.sleep(2000);
driver.findElement(accountNumbers).sendKeys(accountNumbersText);

Thread.sleep(2000);
WebElement searchButton = driver.findElement(By.id("TabBar:AccountsTab:AccountNumberSearchItem_Button"));
searchButton.click();

Thread.sleep(4000);
//Direct Bill Payments 
WebElement actions = driver.findElement(By.xpath("//span[contains(@id,'AccountDetailMenuActions-btnEl')]"));
actions.click();
Thread.sleep(2000);      
WebElement newPayment = driver.findElement(By.xpath("//span[contains(@id,'AccountDetailMenuActions_Payments-textEl')]"));
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
	String amountText = readFile.getCellValue(filepath, "Sheet4", 3, 1);
	writeTextBoxByKeys(amounts, amountText);
	Thread.sleep(1000);
	
	String unappliedFundsText = readFile.getCellValue(filepath, "Sheet4", 4, 1);
	writeTextBoxByKeys(unappliedFundo, unappliedFundsText);
	Thread.sleep(1000);
	
	WebElement executeWD = driver.findElement(executeb);
	js.executeScript("arguments[0].scrollIntoView();", executeWD);
	executeWD.click();
	Thread.sleep(5000);
	//Textbox SearchPayment
	build.moveToElement(driver.findElement(By.id("TabBar:SearchTab-btnInnerEl"))).moveByOffset(30, 0).click().build().perform();
	Thread.sleep(1000);
	build.moveToElement(driver.findElement(By.id("TabBar:SearchTab:SearchGroup_PaymentSearch-textEl"))).click().build().perform();
	Thread.sleep(3000);
	
	WebElement searchButtons = driver.findElement(By.id("PaymentSearch:PaymentSearchScreen:PaymentSearchDV:SearchAndResetInputSet:SearchLinksInputSet:Search"));
	js.executeScript("arguments[0].scrollIntoView();", searchButtons);
	searchButtons.click();
	
	Thread.sleep(5000);
	//Textbox SearchPayment
	build.moveToElement(driver.findElement(By.id("TabBar:SearchTab-btnInnerEl"))).moveByOffset(30, 0).click().build().perform();
	
	Thread.sleep(3000);
	build.moveToElement(driver.findElement(By.id("TabBar:SearchTab:SearchGroup_DirectBillSuspenseItemSearch-textEl"))).click().build().perform();

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

