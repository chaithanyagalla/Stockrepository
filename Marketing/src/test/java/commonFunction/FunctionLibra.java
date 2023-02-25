package commonFunction;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utility.PropertyFileUtili;

public class FunctionLibra {
	public static WebDriver driver;
	public static String Expected="";
	public static String Actual="";
	
	public static WebDriver startBrowser() throws Throwable {

		if(PropertyFileUtili.getValueForKey("Browser").equalsIgnoreCase("chrome")) {

			driver=new ChromeDriver();
			driver.manage().window().maximize();

		}else if(PropertyFileUtili.getValueForKey("Browser").equalsIgnoreCase("firefox")) {

			driver=new FirefoxDriver();
		}else {
			System.out.println("Keys are not matching");
		}
		return driver;
	}
	public static void openUrl(WebDriver driver) throws Throwable {
		driver.get(PropertyFileUtili.getValueForKey("url"));
	}
	public static void waitFotElement(String waitFotElement,String Locator_Type,
			String Locator_Value,String TestData) {
		WebDriverWait mywait= new WebDriverWait(driver, Integer.parseInt(TestData));
		if(Locator_Type.equalsIgnoreCase("name")) {
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(Locator_Value)));

		}else if(Locator_Type.equalsIgnoreCase("xpath")) {
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locator_Value)));

		}else if(Locator_Type.equalsIgnoreCase("id")) {
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Locator_Value)));
		}
	}
	public static void typeAction(WebDriver driver,String Locator_Type,String Locator_Value,String TestData) {

		if(Locator_Type.equalsIgnoreCase("name")) {
			driver.findElement(By.name(Locator_Value)).clear();
			driver.findElement(By.name(Locator_Value)).sendKeys(TestData);
		}else if(Locator_Type.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(Locator_Value)).clear();
			driver.findElement(By.xpath(Locator_Value)).sendKeys(TestData);
		}else if(Locator_Type.equalsIgnoreCase("id")) {
			driver.findElement(By.id(Locator_Value)).clear();
			driver.findElement(By.id(Locator_Value)).sendKeys(TestData);
		}
	}
	public static void clickAction(WebDriver driver,String Locator_Type,String Locator_Value ) {
		if(Locator_Type.equalsIgnoreCase("name")) {
			driver.findElement(By.name(Locator_Value)).click();
		}else if(Locator_Type.equalsIgnoreCase("id")) {
			driver.findElement(By.id(Locator_Value)).sendKeys(Keys.ENTER);
		}else if(Locator_Type.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(Locator_Value)).click();
		}
	}
	public static void validateHomeTitle(WebDriver driver,String TestData) {

		String actual=driver.getTitle();
		try {
			Assert.assertEquals(actual, TestData,"actual and Expected is not found");
		}catch(Throwable t) {
			System.out.println(t.getMessage());
		}
	}
	public static void closeBrowser() {

		driver.close();
	}
	public static void mouseClick(WebDriver driver) throws Throwable {

		Actions ac=new Actions(driver);
		ac.moveToElement(driver.findElement(By.xpath("//a[text()='Stock Items ']"))).perform();
		Thread.sleep(5000);
		ac.moveToElement(driver.findElement(By.xpath("(//a)[84]"))).click().perform();

	}
	public static void categoryTable(WebDriver driver,String TestData) throws Throwable{

		if(!driver.findElement(By.xpath(PropertyFileUtili.getValueForKey("search-textbox"))).isDisplayed());
		Thread.sleep(2000);
		driver.findElement(By.xpath(PropertyFileUtili.getValueForKey("search-panel"))).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(PropertyFileUtili.getValueForKey("search-textbox"))).sendKeys(TestData);
		Thread.sleep(2000);
		driver.findElement(By.xpath(PropertyFileUtili.getValueForKey("search-button"))).click();
		Thread.sleep(2000);
		String actualdata=driver.findElement(By.xpath("//table[@id='tbl_a_stock_categorieslist']/tbody/tr[1]/td[4]/div/span/span")).getText();
		System.out.println(actualdata+"  "+TestData);
		Assert.assertEquals(actualdata, TestData,"not equal");
	}
	public static void captureData(WebDriver driver,String Locator_Type,String Locator_Value) {
		Expected=driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
	}
	public static void supplierTable(WebDriver driver) throws Throwable {
		if(!driver.findElement(By.xpath(PropertyFileUtili.getValueForKey("search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(PropertyFileUtili.getValueForKey("search-panel"))).click();
		driver.findElement(By.xpath(PropertyFileUtili.getValueForKey("search-textbox"))).sendKeys(Expected);
		driver.findElement(By.xpath(PropertyFileUtili.getValueForKey("search-button"))).click();
		Actual=driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr[1]/td[6]/div/span/span")).getText();
		System.out.println(Expected+"   "+Actual);
		Assert.assertEquals(Actual, Expected,"Values Are Not Matched");
	}
	public static void validateTable(WebDriver driver,String Locator_Type,String Locator_Value) throws Throwable {
		if(!driver.findElement(By.xpath(PropertyFileUtili.getValueForKey("search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(PropertyFileUtili.getValueForKey("search-panel"))).click();
		driver.findElement(By.xpath(PropertyFileUtili.getValueForKey("search-textbox"))).sendKeys(Expected);
		driver.findElement(By.xpath(PropertyFileUtili.getValueForKey("search-button"))).click();
		Actual=  driver.findElement(By.xpath(Locator_Value)).getText();
		System.out.println(Expected+"   "+Actual);
		Assert.assertEquals(Actual, Expected,"Customer number not matched");
	}
	public static void selectList(WebDriver driver,String Locator_Value) {
		
		Select select=new Select(driver.findElement(By.xpath(Locator_Value)));
	  select.selectByIndex(4);
	}

	
	
	public static String getDate() {
		
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("YYYY_MM_DD");
		return format.format(date);
		
		
	}
	}


	







