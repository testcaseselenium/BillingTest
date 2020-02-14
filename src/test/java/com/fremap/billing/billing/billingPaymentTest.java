package com.fremap.billing.billing;

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


public class billingPaymentTest {

		private static WebDriver driver;

@BeforeClass
public static void setUp() {
System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\drivers\\chromedriver.exe");
ChromeOptions options = new ChromeOptions();
options.addArguments("--ignore-certificate-errors");
options.addArguments("--verbose");
options.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);

driver = new ChromeDriver(options);

}


@Test
public void billingPayCase() throws InterruptedException, IOException {


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

build.moveToElement(driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div/div[1]/div/div/a[3]"))).moveByOffset(30, 0).click().build().perform();

By searchbtn = By.id("TabBar:PoliciesTab:PolicyNumberSearchItem-inputEl");
build.moveToElement(driver.findElement(By.id("TabBar:PoliciesTab:PolicyNumberSearchItem-inputEl"))).moveByOffset(30, 0).click().sendKeys("VRT000000007883246-1").build().perform();
driver.findElement(searchbtn).sendKeys(Keys.ENTER);


build.moveToElement(driver.findElement(By.id("PolicyGroup:MenuLinks:PolicyGroup_PolicyDetailPayments"))).click().build().perform();

build.moveToElement(driver.findElement(By.id("PolicyDetailPayments:PolicyDetailPaymentsScreen:InvoiceItemsLV:0:PaymentDate"))).click().build().perform();
}
}
