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


public class mantainEditContactsTest {

		private static WebDriver driver;
		private static ReadExcel readFile;

		//Locator Contact Info
		
		private By persons = By.xpath("//input[contains(@id,'ContactType-inputEl')]");
		private By name = By.xpath("//input[contains(@id,'FirstName-inputEl')]");
		private By namec = By.xpath("//input[contains(@id,'Name-inputEl')]");
		private By accountNumber = By.id("TabBar:AccountsTab:AccountNumberSearchItem-inputEl");
		private By accountName = By.xpath("//input[contains(@id,'AccountNameCriterion-inputEl')]");
		
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
//Search ACCOUNT
build.moveToElement(driver.findElement(By.id("TabBar:AccountsTab-btnWrap"))).moveByOffset(30, 0).click().build().perform();
Thread.sleep(1000);
build.moveToElement(driver.findElement(By.id("TabBar:AccountsTab:AccountNumberSearchItem-inputEl"))).moveByOffset(30, 0).click().sendKeys("2086057816").click().build().perform();

Thread.sleep(1000);
WebElement searchButton = driver.findElement(By.id("TabBar:AccountsTab:AccountNumberSearchItem-inputEl"));
searchButton.sendKeys(Keys.ENTER);

Thread.sleep(2000);
//Contacts
WebElement contacts = driver.findElement(By.id("AccountGroup:MenuLinks:AccountGroup_AccountDetailContacts"));
js.executeScript("arguments[0].scrollIntoView();", contacts);
contacts.click();
Thread.sleep(3000); 
//Edit BUTTON
By edit = By.id("AccountDetailContacts:AccountDetailContactsScreen:Edit-btnInnerEl");
WebElement editBtn = driver.findElement(edit);
js.executeScript("arguments[0].scrollIntoView();", editBtn);
editBtn.click();
Thread.sleep(2000); 

//Add a COMPANY
By addperson1 = By.id("AccountDetailContacts:AccountDetailContactsScreen:addNewContact");
WebElement addpersonBtn1 = driver.findElement(addperson1);
js.executeScript("arguments[0].scrollIntoView();", addpersonBtn1);
addpersonBtn1.click();
Thread.sleep(2000); 
By addnewcompany = By.id("AccountDetailContacts:AccountDetailContactsScreen:addNewContact:addNewCompany-textEl");
WebElement addnewcompanyBtn = driver.findElement(addnewcompany);
js.executeScript("arguments[0].scrollIntoView();", addnewcompanyBtn);
addnewcompanyBtn.click();
Thread.sleep(3000);

By company = By.xpath("//input[contains(@id,'Name-inputEl')]");
String companyText = readFile.getCellValue(filepath, "Sheet8", 13, 1);
writeTextBoxByKeys(company, companyText);
Thread.sleep(3000); 
By addressc = By.xpath("//input[contains(@id,'AddressLine2-inputEl')]");
String addresscText = readFile.getCellValue(filepath, "Sheet8", 14, 1);
writeTextBoxByKeys(addressc, addresscText);
Thread.sleep(3000); 
By addresscc = By.xpath("//input[contains(@id,'AddressLine3-inputEl')]");
String addressccText = readFile.getCellValue(filepath, "Sheet8", 15, 1);
writeTextBoxByKeys(addresscc, addressccText);
Thread.sleep(3000); 
By postalCodeLenss =By.xpath("//div[contains(@id,'SelectPostalCode')]");
driver.findElement(postalCodeLenss).click();
Thread.sleep(3000); 
By selectContacts = By.id("SearchPostalCode_VrtPopup:PostalCodeSearch_VrtDV:PostalCode-inputEl");
String cityCappariosText = readFile.getCellValue(filepath, "Sheet8", 16, 1);
writeTextBoxByKeys(selectContacts, cityCappariosText);
Thread.sleep(3000); 
By elementKeyo = By.tagName("html");
driver.findElement(elementKeyo).sendKeys(Keys.ALT + "s");
Thread.sleep(3000); 
//button select
By selectbto = By.xpath("//a[contains(@id,':0:_Select')]");
driver.findElement(selectbto).click();
Thread.sleep(4000); 
By email = By.xpath("//input[contains(@id,'Email-inputEl')]");
String emailText = readFile.getCellValue(filepath, "Sheet8", 17, 1);
writeTextBoxByKeys(email, emailText);
Thread.sleep(3000); 
//okbutton
By oks = By.id("NewAccountContactPopup:NewAccountContactScreen:AccountContactCV:AccountContactDetailDV:RolesLV_tb:Update-btnInnerEl");
WebElement okisa = driver.findElement(oks);
js.executeScript("arguments[0].scrollIntoView();", okisa);
okisa.click();
Thread.sleep(2000);
//ADD A PERSON
By addperson = By.id("AccountDetailContacts:AccountDetailContactsScreen:addNewContact");
WebElement addpersonBtn = driver.findElement(addperson);
js.executeScript("arguments[0].scrollIntoView();", addpersonBtn);
addpersonBtn.click();
Thread.sleep(2000);  
By addnewperson = By.id("AccountDetailContacts:AccountDetailContactsScreen:addNewContact:addNewPerson-textEl");
WebElement addnewpersonBtn = driver.findElement(addnewperson);
js.executeScript("arguments[0].scrollIntoView();", addnewpersonBtn);
addnewpersonBtn.click();
Thread.sleep(2000); 
By title = By.xpath("//input[contains(@id,'Title-inputEl')]");

String titleText = readFile.getCellValue(filepath, "Sheet8", 2, 1);
writeTextBoxByKeys(title, titleText);
Thread.sleep(3000); 

By firstname = By.xpath("//input[contains(@id,'FirstName-inputEl')]");
String firstnameText = readFile.getCellValue(filepath, "Sheet8", 3, 1);
writeTextBoxByKeys(firstname, firstnameText);
Thread.sleep(3000); 

By lastname = By.xpath("//input[contains(@id,'LastName-inputEl')]");
String lastnameText = readFile.getCellValue(filepath, "Sheet8", 4, 1);
writeTextBoxByKeys(lastname, lastnameText);
Thread.sleep(3000); 

By date = By.xpath("//input[contains(@id,'DateOfBirth-inputEl')]");
String dateText = readFile.getCellValue(filepath, "Sheet8", 5, 1);
writeTextBoxByKeys(date, dateText);
Thread.sleep(3000); 

By marital = By.xpath("//input[contains(@id,'MaritalStatus-inputEl')]");
String maritalText = readFile.getCellValue(filepath, "Sheet8", 6, 1);
writeTextBoxByKeys(marital, maritalText);
Thread.sleep(3000); 

By profesion = By.xpath("//input[contains(@id,'Profession-inputEl')]");
String profesionText = readFile.getCellValue(filepath, "Sheet8", 7, 1);
writeTextBoxByKeys(profesion, profesionText);
Thread.sleep(3000); 

WebElement addressf = driver.findElement(By.name("NewAccountContactPopup:NewAccountContactScreen:AccountContactCV:AccountContactDetailDV:globalAddressContainer:GlobalAddressInputSet:AddressLine2"));
js.executeScript("arguments[0].scrollIntoView();", addressf);
Thread.sleep(2000); 
By address = By.xpath("//input[contains(@id,'AddressLine2-inputEl')]");
String addressText = readFile.getCellValue(filepath, "Sheet8", 8, 1);
writeTextBoxByKeys(address, addressText);
Thread.sleep(3000); 

By addressn = By.xpath("//input[contains(@id,'AddressLine3-inputEl')]");
String addressnText = readFile.getCellValue(filepath, "Sheet8", 9, 1);
writeTextBoxByKeys(addressn, addressnText);
Thread.sleep(3000); 

By postalCodeLens =By.xpath("//div[contains(@id,'SelectPostalCode')]");
driver.findElement(postalCodeLens).click();
Thread.sleep(3000); 

By selectContact = By.id("SearchPostalCode_VrtPopup:PostalCodeSearch_VrtDV:PostalCode-inputEl");
String cityCapparioText = readFile.getCellValue(filepath, "Sheet8", 10, 1);
writeTextBoxByKeys(selectContact, cityCapparioText);
Thread.sleep(3000); 
By elementKey = By.tagName("html");
driver.findElement(elementKey).sendKeys(Keys.ALT + "s");
Thread.sleep(3000); 

By selectbt = By.xpath("//a[contains(@id,':0:_Select')]");
driver.findElement(selectbt).click();
Thread.sleep(4000); 

By codfiscale = By.id("NewAccountContactPopup:NewAccountContactScreen:AccountContactCV:AccountContactDetailDV:OfficialIDInputSet:OfficialIDDV_Input-inputEl");
WebElement codf = driver.findElement(codfiscale);
js.executeScript("arguments[0].scrollIntoView();", codf);
String codfiscaleText = readFile.getCellValue(filepath, "Sheet8", 11, 1);
writeTextBoxByKeys(codfiscale, codfiscaleText);

By mail = By.xpath("//input[contains(@id,'Email-inputEl')]");
String mailText = readFile.getCellValue(filepath, "Sheet8", 12, 1);
writeTextBoxByKeys(mail, mailText);
Thread.sleep(3000); 

By ok = By.id("NewAccountContactPopup:NewAccountContactScreen:AccountContactCV:AccountContactDetailDV:RolesLV_tb:Update-btnInnerEl");
WebElement okis = driver.findElement(ok);
js.executeScript("arguments[0].scrollIntoView();", okis);
okis.click();

Thread.sleep(3000); 

//add existing new contact
By addexisting = By.id("AccountDetailContacts:AccountDetailContactsScreen:addExistingContact-btnInnerEl");
WebElement addexistingc = driver.findElement(addexisting);
js.executeScript("arguments[0].scrollIntoView();", addexistingc);
addexistingc.click();
Thread.sleep(4000); 

String personText = readFile.getCellValue(filepath, "Sheet8", 18, 1);
writeTextBoxByKeys(persons, personText);
Thread.sleep(3000); 

String nameText = readFile.getCellValue(filepath, "Sheet8", 19, 1);
writeTextBoxByKeys(name, nameText);
Thread.sleep(3000); 
 
By elementKeya = By.tagName("html");
driver.findElement(elementKeya).sendKeys(Keys.ALT + "s");
Thread.sleep(4000); 

By select = By.xpath("//a[contains(@id,':0:_Select')]");
WebElement selectBtno = driver.findElement(select);
js.executeScript("arguments[0].scrollIntoView();", selectBtno);
selectBtno.click();
Thread.sleep(4000); 

By update3 = By.xpath("//span[contains(@id,'Update-btnInnerEl')]");
WebElement upd = driver.findElement(update3);
js.executeScript("arguments[0].scrollIntoView();", upd);
upd.click();

By edit2 = By.xpath("//span[contains(@id,'Edit-btnInnerEl')]");
WebElement editos = driver.findElement(edit2);
js.executeScript("arguments[0].scrollIntoView();", editos);
editos.click();
Thread.sleep(4000);

String personaText = readFile.getCellValue(filepath, "Sheet8", 20, 1);
writeTextBoxByKeys(namec, personaText);  
Thread.sleep(3000); 

By update4 = By.xpath("//span[contains(@id,'Update-btnInnerEl')]");
WebElement updo = driver.findElement(update4);
js.executeScript("arguments[0].scrollIntoView();", updo);
updo.click();

By edit3 = By.xpath("//span[contains(@id,'Edit-btnInnerEl')]");
buildClick(edit3);
Thread.sleep(1000);

By checkcharlotte = By.xpath("//div[contains(text(), '"+firstnameText+"')]/../../..//img[contains(@class,'x-grid-checkcolumn ')]");
WebElement check = driver.findElement(checkcharlotte);
js.executeScript("arguments[0].scrollIntoView();", check);
check.click();
Thread.sleep(4000);

By remove = By.xpath("//span[contains(@id,'Remove-btnInnerEl')]");
WebElement removeb = driver.findElement(remove);
js.executeScript("arguments[0].scrollIntoView();",  removeb );
removeb.click();


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

public static void buildClick(By locator){
	 try    {
   Actions build = new Actions(driver);
   build.moveToElement(driver.findElement(locator)).moveByOffset(0, 0).click().build().perform();
	 }catch (Exception e)      {
        System.out.println(e.getMessage());} 
   }

}

