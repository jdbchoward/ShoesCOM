package example;		

import org.openqa.selenium.By;		
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;		
import org.testng.annotations.Test;

import PageObjects.BrowserLoader;
import PageObjects.BrowserTypes;
import PageObjects.CommonActions;
import PageObjects.PageLogin;
import PageObjects.Wait;

import org.testng.annotations.BeforeTest;	
import org.testng.annotations.AfterTest;		
public class NewTest {		
	    private WebDriver driver;
	    private String ipaddress="http://www.guru99.com/selenium-tutorial.html";
	    private String expectTitle= "Free Selenium Tutorials";
	    private Wait wait;
		@Test				
		public void testEasy() {	
			
			CommonActions common = PageFactory.initElements(driver, CommonActions.class);

			PageLogin pageLogin = PageFactory.initElements(driver, PageLogin.class);
			wait = new Wait(driver);
			String logoname = pageLogin.getLogoName(ipaddress);  
			pageLogin.checkPageTitle(ipaddress,expectTitle); 		
		}	
		@BeforeTest
		public void beforeTest() {	
			System.setProperty("webdriver.chrome.driver","src\\test\\resources\\chromedriver.exe");
		    ChromeOptions options=new ChromeOptions();
		    options.addArguments("no-sandbox");
			driver = new ChromeDriver();
			
//			BrowserLoader brower = new BrowserLoader(BrowserTypes.chrome);
//			driver = brower.driver; 
			//driver.manage().window().maximize();
			
		}		
		@AfterTest
		public void afterTest() {
			driver.quit();			
		}		
}	