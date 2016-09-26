package ShoseWebsite;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import PageObjects.BrowserLoader;
import PageObjects.CommonActions;
import PageObjects.ElementsRepositoryAction;
import PageObjects.TestOperations;
import PageObjects.Wait;
import junit.framework.Assert;


/**   
* @Title: EPT Automation Test on Shoes.ca 
* @Package ShoseWebsite 
* @Description: EPT Automation Test on Shoes.ca 
* @author: Howard
* @compay: PQA     
* @date 09/19/2016 
* @version V1.0   
*/



public class TestSignInUS {
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
	public void testSignInWithCorrectInfo() throws Exception {

	    testOperation.doSignInUSsite("hiend@yeah.net", "10011001");
	    wait.threadWait(3000);
	    Assert.assertTrue(testOperation.checkUSSignInStatus());
		testOperation.doSignOutUS();
//		wait.threadWait(1000);
	}

	@Test(dependsOnMethods = { "testSignInWithCorrectInfo" })
	public void testSignInWithBadInfo() throws Exception {

		testOperation.doSignInUSsiteAgain("aa", "111");
		Assert.assertTrue(!testOperation.checkUSSignInStatus());
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		driver.close();
		driver.quit();
	}

}
