package ShoseWebsite;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
/**   
* @Title: EPT Automation Test on Shoes.ca 
* @Package ShoseWebsite 
* @Description: EPT Automation Test on Shoes.ca 
* @author: Howard
* @compay: PQA     
* @date 09/20/2016 
* @version V1.0   
*/
import org.testng.annotations.Test;

import PageObjects.BrowserLoader;
import PageObjects.CommonActions;
import PageObjects.ElementsRepositoryAction;
import PageObjects.TestOperations;
import PageObjects.Wait;
import junit.framework.Assert;

public class TestNaviBarUS {
	private WebDriver driver;
	private Wait wait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	TestOperations testOperation;
	static Logger log = Logger.getLogger(TestSignIn.class.getName());

	@BeforeTest(alwaysRun = true)
	public void setUp() throws Exception {

		common = PageFactory.initElements(driver, CommonActions.class);
		String browserType = common.getSettings().getValue("browserType");
		BrowserLoader brower = new BrowserLoader(browserType);
		driver = brower.driver;
		wait = new Wait(driver);
		elementsRepositoryAction = new ElementsRepositoryAction(driver);
		testOperation = PageFactory.initElements(driver, TestOperations.class);

	}

	@Test
	public void testNavigationToManShoes() throws Exception {

	    testOperation.doSignInUSsite("hiend@yeah.net", "10011001");
	    wait.threadWait(10000);
        testOperation.navigationToManShoesUS();
        wait.threadWait(10000);
        log.debug(driver.getCurrentUrl());
        Assert.assertTrue(driver.getCurrentUrl().contains("womens-shoes-boots"));


	}


	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
//		testOperation.doSignOut();
//		wait.threadWait(1000);
		driver.close();
		driver.quit();
	}

}
