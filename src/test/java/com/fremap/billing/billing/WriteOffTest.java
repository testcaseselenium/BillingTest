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


public class WriteOffTest {

		private static WebDriver driver;
		private static ReadExcel readFile;

		//Locator Account Info
		private By accountNumber = By.id("TabBar:AccountsTab:AccountNumberSearchItem-inputEl");
	
		private By next = By.xpath("//span[contains(@id,'Next-btnInnerEl')]");
		private By finished = By.xpath("//span[contains(@id,'Finish-btnInnerEl')]");
		
		
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

build.moveToElement(driver.findElement(By.id("TabBar:AccountsTab-btnWrap"))).moveByOffset(30, 0).click().build().perform();
//Textbox SearchAccount
Thread.sleep(2000);
WebElement account = driver.findElement(By.id("TabBar:AccountsTab:AccountNumberSearchItem-inputEl"));
js.executeScript("arguments[0].scrollIntoView();", account);
Thread.sleep(1000);
String accountNumberText = readFile.getCellValue(filepath, "Sheet3", 1, 1);
driver.findElement(accountNumber).click();
Thread.sleep(2000);
driver.findElement(accountNumber).sendKeys(accountNumberText);

Thread.sleep(2000);
WebElement searchButton = driver.findElement(By.id("TabBar:AccountsTab:AccountNumberSearchItem_Button"));
searchButton.click();


Thread.sleep(2000);
build.moveToElement(driver.findElement(By.id("AccountGroup:AccountDetailMenuActions-btnEl"))).moveByOffset(30, 0).click().build().perform();
Thread.sleep(2000);
build.moveToElement(driver.findElement(By.id("AccountGroup:AccountDetailMenuActions:AccountDetailMenuActions_NewTransaction-textEl"))).click().build().perform();										 
Thread.sleep(2000);
build.moveToElement(driver.findElement(By.id("AccountGroup:AccountDetailMenuActions:AccountDetailMenuActions_NewTransaction:AccountDetailMenuActions_Writeoff-textEl"))).click().build().perform();										 
Thread.sleep(2000);


Thread.sleep(1000);
//Select BUTTON
By select = By.xpath("//a[contains(@id,':2:Select')]");
WebElement selectBtn = driver.findElement(select);
selectBtn.click();


Thread.sleep(3000);
WebElement nextB = driver.findElement(next);
js.executeScript("arguments[0].scrollIntoView();", nextB);
nextB.click();

Thread.sleep(3000);
String chargePText = readFile.getCellValue(filepath, "Sheet3", 2, 1);
By chargeP = By.xpath("//input[contains(@id, 'ChargePattern')]");
writeTextBoxByKeys(chargeP, chargePText);
Thread.sleep(3000);


String reasonText = readFile.getCellValue(filepath, "Sheet3", 4, 1);
By reason = By.xpath("//input[contains(@id, 'Reason')]");
writeTextBoxByKeys(reason, reasonText);
Thread.sleep(3000);

WebElement nextBu = driver.findElement(next);
js.executeScript("arguments[0].scrollIntoView();", nextBu);
nextBu.click();

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
