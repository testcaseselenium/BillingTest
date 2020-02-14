package com.fremap.billing.billing;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
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



public class invoiceSearchTest {

		private static WebDriver driver;
		private static ReadExcel readFile;

		//Locator Account Info
		private By accountNumber = By.name("InvoiceSearch:InvoiceSearchScreen:InvoiceSearchDV:AccountNumberCriterion");

		
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
driver.get("https://10.231.193.245/bc/BillingCenter.do");

// Loggin
WebElement username = driver.findElement(By.id("Login:LoginScreen:LoginDV:username-inputEl"));
username.sendKeys("su");

WebElement password = driver.findElement(By.id("Login:LoginScreen:LoginDV:password-inputEl"));
password.sendKeys("gw");

WebElement login = driver.findElement(By.id("Login:LoginScreen:LoginDV:submit-btnInnerEl"));
login.click();

Actions build = new Actions(driver);

build.moveToElement(driver.findElement(By.id("TabBar:SearchTab-btnWrap"))).moveByOffset(30, 0).click().build().perform();
Thread.sleep(1000);
build.moveToElement(driver.findElement(By.id("TabBar:SearchTab:SearchGroup_InvoiceSearch-itemEl"))).click().build().perform();

Thread.sleep(2000);

WebElement account = driver.findElement(By.id("InvoiceSearch:InvoiceSearchScreen:InvoiceSearchDV:AccountNumberCriterion-inputEl"));
js.executeScript("arguments[0].scrollIntoView();", account);
String accountNumberText = readFile.getCellValue(filepath, "Sheet1", 1, 1);
driver.findElement(accountNumber).click();
Thread.sleep(2000);
driver.findElement(accountNumber).sendKeys(accountNumberText);
driver.findElement(accountNumber).click();

By elementKey = By.tagName("html");
driver.findElement(elementKey).sendKeys(Keys.ALT + "s");

Thread.sleep(1000);

By invoiceElement = By.xpath("//div[@class = 'x-grid-cell-inner ']/../../../../..//table[1]//td[3]/div/a");
WebElement invoice = driver.findElement(invoiceElement);
js.executeScript("arguments[0].scrollIntoView();", invoice);
String numberInvoice = invoice.getText();
System.out.println(numberInvoice);

Thread.sleep(3000);
build.moveToElement(driver.findElement(By.id("TabBar:SearchTab-btnWrap"))).moveByOffset(30, 0).click().build().perform();
Thread.sleep(1000);
build.moveToElement(driver.findElement(By.id("TabBar:SearchTab:SearchGroup_InvoiceSearch-itemEl"))).click().build().perform();
Thread.sleep(3000);
WebElement invoices = driver.findElement(By.name("InvoiceSearch:InvoiceSearchScreen:InvoiceSearchDV:InvoiceNumberCriterion"));
js.executeScript("arguments[0].scrollIntoView();", invoices);
invoices.click();
invoices.sendKeys(numberInvoice);
invoices.click();
Thread.sleep(2000);
By elementKeys = By.tagName("html");
driver.findElement(elementKeys).sendKeys(Keys.ALT + "s");

}


@AfterClass
public static void tearDown() {

	// driver.close();
	// driver.quit();
}

}
