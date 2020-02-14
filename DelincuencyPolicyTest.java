package com.fremap.billing.delincuency;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;

/**
 *Test ID : 8124 - VWI-17041_CP001_Delinquency Process, policy
 * Start Delinquency from  a policy Number
 * @author CMONTE5
 *
 */
public class DelincuencyPolicyTest {
	private static WebDriver driver;
	
		
	@BeforeClass
	public static void setUp() {
		System.setProperty("webdriver.chrome.driver","src\\test\\resources\\drivers\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--verbose");
        options.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
       
        driver = new ChromeDriver(options);
	}
		
	@Test
	public void accountCharges() throws InterruptedException, IOException {
		Actions build = new Actions(driver);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://10.231.193.245/bc/BillingCenter.do");
		
		//Login
		WebElement username = driver.findElement(By.id("Login:LoginScreen:LoginDV:username-inputEl"));
		username.sendKeys("su");

		WebElement password = driver.findElement(By.id("Login:LoginScreen:LoginDV:password-inputEl"));
		password.sendKeys("gw");

		WebElement login = driver.findElement(By.id("Login:LoginScreen:LoginDV:submit-btnInnerEl"));
		login.click();
		
		Thread.sleep(2000);
		
		String accountPolicy = "VRT000000002893664-2";//From account Cancelled cancelled
		
		Thread.sleep(2000);
		// Account arrow
		build.moveToElement(driver.findElement(By.xpath("//span[contains(@id, 'TabBar:PoliciesTab-btnWrap')]"))).moveByOffset(30, 0).click().build().perform();
        //Textbox SearchAccount
		build.moveToElement(driver.findElement(By.xpath("//input[contains(@id, 'TabBar:PoliciesTab:PolicyNumberSearchItem-inputEl')]"))).moveByOffset(30, 0).click().sendKeys(accountPolicy).build().perform();
		// Lens button
		WebElement searchButton = driver.findElement(By.id("TabBar:PoliciesTab:PolicyNumberSearchItem_Button"));
		searchButton.click();
		
        Thread.sleep(2000);
        
        //Start Delinquency
	    By startDelinquency = By.xpath("//span[contains(@id, 'StartDelinquency')]");
	    build.moveToElement(driver.findElement(startDelinquency)).moveByOffset(30, 0).click().build().perform();
	    
	    By checkcolumn = By.xpath("//a[contains(text(), '" + accountPolicy +"')]/../../..//img[contains(@class, 'checkcolumn')]");
	    build.moveToElement(driver.findElement(checkcolumn)).moveByOffset(0, 0).click().build().perform();
	    
	    By execute = By.xpath("//span[contains(@id, 'Execute')]");
	    build.moveToElement(driver.findElement(execute)).moveByOffset(0, 0).click().build().perform();
	    
	    By delinquencies = By.xpath("//td[contains(@id, 'Delinquencies')]");
	    buildClick(delinquencies);
	    
	    By openDelincuency = By.xpath("//div[contains(text(), 'Open')]");
	    WebElement openElement = driver.findElement(openDelincuency);
	    assertTrue(openElement.isEnabled());
  
     
	}
	
	public static void buildClick(By locator){
		Actions build = new Actions(driver);
	    build.moveToElement(driver.findElement(locator)).moveByOffset(0, 0).click().build().perform();
		}
}
