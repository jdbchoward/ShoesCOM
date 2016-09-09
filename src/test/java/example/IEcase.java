package example;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;		
import org.testng.annotations.Test;

import PageObjects.BrowserLoader;
import PageObjects.BrowserTypes;
import PageObjects.Wait;

import org.testng.annotations.BeforeTest;	
import org.testng.annotations.AfterTest;		
public class IEcase {		
	    private WebDriver driver;	    
	    private Wait wait;
	    static Logger log = Logger.getLogger(IEcase.class.getName());
		@Test				
		public void testEasy() {	
			driver.get("http://www.guru99.com/selenium-tutorial.html");  			
			wait.WaitUntilPageLoaded();
	
		    System.out.println(driver.getTitle());
			String title = driver.getTitle();	
//			Assert.assertTrue(title.contains("Free Selenium Tutorials")); 
			driver.findElement(By.id("rt-logo"));
		}
		@BeforeTest
		public void beforeTest() {	
			BrowserLoader brower = new BrowserLoader(BrowserTypes.ie);
			driver = brower.driver; 
			//driver.manage().window().maximize();
			wait = new Wait(driver);
		}	
				
		
		@AfterTest
		public void afterTest() {
			driver.quit();			
		}		
		
		
}	