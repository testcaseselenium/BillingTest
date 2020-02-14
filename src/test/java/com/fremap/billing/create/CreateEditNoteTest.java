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
 * Test ID : 8371 - VWI-17244-CP218-SIT_Account_200004_TC1_Create Edit and Search Note on Account Summary
 * @author CMONTE5
 *
 */
public class CreateEditNoteTest {
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
	public void createTroubleTickets() throws InterruptedException, IOException {
		
		String filepath = "src\\test\\resources\\excel\\BankTransferBilling.xlsx";
		
		
		Actions build = new Actions(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;
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
		
		String accountNumber = "1000001145";
		
		// Account arrow
		build.moveToElement(driver.findElement(By.xpath("//span[contains(@id, 'TabBar:AccountsTab-btnWrap')]"))).moveByOffset(30, 0).click().build().perform();
		// Textbox SearchAccount
		build.moveToElement(driver.findElement(By.xpath("//input[contains(@id, 'TabBar:AccountsTab:AccountNumberSearchItem-inputEl')]"))).moveByOffset(30, 0).click().sendKeys(accountNumber).build().perform();
		// Lens button
		WebElement searchButton = driver.findElement(By.id("TabBar:AccountsTab:AccountNumberSearchItem_Button"));
		searchButton.click();
		Thread.sleep(2000);
		
	    //Click on '+' symbol Notes
	    By plus = By.xpath("//a[contains(@id, 'NotesAddIcon')]");
	    WebElement plusIcon = driver.findElement(plus);
		js.executeScript("arguments[0].scrollIntoView();", plusIcon);
		plusIcon.click();
	    
		//Verify Default fields
	    By date =By.xpath("//div[contains(@id, 'AuthoringDate')]");
	    By labelDate =By.xpath("//label[contains(@id, 'AuthoringDate')]");
	    verifyTextBox(date);
	    verifyTextBox(labelDate);
	    
	    By superUser =By.xpath("//div[contains(@id, 'Author')]");
	    By author =By.xpath("//label[contains(@id, 'Author')]");
	    verifyTextBox(superUser);
	    verifyTextBox(author);
	    
	    By english =By.xpath("//input[contains(@id, 'Language')]");
	    By language =By.xpath("//label[contains(@id, 'Language')]");
	    String languageDefault = readFile.getCellValue(filepath, "Sheet8", 1, 1);
	    verifyTextBox(language);
	    verifyEqualsTextBox(english, languageDefault);
	    
	    By general =By.xpath("//input[contains(@id, 'Topic')]");
	    By topic =By.xpath("//label[contains(@id, 'Topic')]");
	    String topicDefault = readFile.getCellValue(filepath, "Sheet8", 2, 1);
	    verifyTextBox(topic);
	    verifyEqualsTextBox(general, topicDefault);
	    
	    By subjectDefault =By.xpath("//input[contains(@id, 'Subject')]");
	    By subject =By.xpath("//label[contains(@id, 'Subject')]");

	    verifyTextBox(subject);
	    verifyEqualsTextBox(subjectDefault, "");
	    
	    By none =By.xpath("//input[contains(@id, 'RelatedTo')]");
	    By relatedTo =By.xpath("//label[contains(@id, 'RelatedTo')]");
	    String relatedDefault = readFile.getCellValue(filepath, "Sheet8", 4, 1);
	    verifyTextBox(relatedTo);
	    verifyEqualsTextBox(none, relatedDefault);
	    
	    By textBlank =By.xpath("//textarea[contains(@id, ':Text-inputEl')]");
	    By text =By.xpath("//label[contains(@id, ':Text-labelEl')]");

	    verifyTextBox(text);
	    verifyEqualsTextBox(textBlank, "");
	    
	    By updateLocator = By.xpath("//span[contains(@id, 'Update-btnInnerEl')]");
		By errorMessage = By.xpath("//div[@class = 'message']");
		buildClick(updateLocator);
		getIncorrectMessage(errorMessage);

	    //Fill Mandatory Fields
        String subjectText = readFile.getCellValue(filepath, "Sheet8", 3, 1);
		writeTextBoxByKeys(subjectDefault, subjectText);
		
		buildClick(updateLocator);
		getIncorrectMessage(errorMessage);
		
        String textDefault = readFile.getCellValue(filepath, "Sheet8", 6, 1);
		writeTextBoxByKeys(textBlank, textDefault);
		
		buildClick(updateLocator);
		Thread.sleep(1000);
		
		//Verify in Account Summary
		  By subjectSummary =By.xpath("//div[contains(@id, ':NoteSubject-inputEl')]");
		  WebElement elementSubject = driver.findElement(subjectSummary);
		  String subjectScreen = elementSubject.getText();
		  assertEquals(subjectText,subjectScreen);
		  
		  By textNote =By.xpath("//textarea[contains(@id, 'NoteText-inputEl')]");
		  WebElement elementText = driver.findElement(textNote);
		  String textScreen = elementText.getText();
		  assertEquals(textScreen, textDefault);
		  Thread.sleep(2000);
		  
		//Click on 'pencil Edit' and Fill Mandatory Fields
	    By pencil = By.xpath("//a[contains(@id, 'NotesEditIcon')]");
	    WebElement pencilEdit = driver.findElement(pencil);
		js.executeScript("arguments[0].scrollIntoView();", pencilEdit);
		pencilEdit.click();
		
		//Edit Subject
		 String subjectEditedText = readFile.getCellValue(filepath, "Sheet8", 3, 2);
        writeTextBoxByKeys(subjectDefault, subjectEditedText);
		buildClick(updateLocator);
		Thread.sleep(1000);
		
		//Account Note Screen
		By accountNote = By.id("AccountGroup:MenuLinks:AccountGroup_AccountDetailNotes");
		 WebElement noteScreen = driver.findElement(accountNote);
		js.executeScript("arguments[0].scrollIntoView();", noteScreen);
		buildClick(accountNote);
		
		//Edit Text
		By searchNote = By.xpath("//a[contains(@id,'SearchAndResetInputSet:SearchLinksInputSet:Search')]");
		buildClick(searchNote);		
		By editSearch = By.xpath("//a[contains(@id,':0:Edit')]");
		buildClick(editSearch);

		String textEditedText = readFile.getCellValue(filepath, "Sheet8", 6, 2);
        writeTextBoxByKeys(textBlank, textEditedText);
		buildClick(updateLocator);
		Thread.sleep(2000);
		
		//Search purpose 
		By findText = By.xpath("//input[contains(@id,':TextSearch-inputEl')]");
		writeTextBoxByKeys(findText, "purpose");
		
		buildClick(searchNote);
		By confirmPurpose = By.xpath("//div[contains(@id, 'NotesLV:0:Subject')]");
		verifyTextBox(confirmPurpose);
		
		
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
			Thread.sleep(2000);
			driver.findElement(locator).sendKeys(Keys.TAB);
			Thread.sleep(1000);

		} catch (Exception e) {
			System.out.println("Not found: " + locator);
		}
	}
}
