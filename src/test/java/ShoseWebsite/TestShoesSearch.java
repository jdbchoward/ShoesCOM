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
* @date 09/24/2016 
* @version V1.0   
*/
import org.testng.annotations.Test;

import PageObjects.BrowserLoader;
import PageObjects.CommonActions;
import PageObjects.ElementsRepositoryAction;
import PageObjects.TestOperations;
import PageObjects.Wait;
import junit.framework.Assert;

public class TestShoesSearch {
	private WebDriver driver;
	private Wait wait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	TestOperations testOperation;
	static Logger log = Logger.getLogger(TestSignIn.class.getName());
	int waitTimes = 5000;

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
	public void testAddShoesToWishList() throws Exception {

		boolean searchResult = false;
		boolean urlRef = false;
		testOperation.doSignIn("hiend@yeah.net", "10011001");
		wait.threadWait(waitTimes);
		searchResult = testOperation.searchShoe();
		wait.threadWait(waitTimes);
		urlRef = driver.getCurrentUrl().contains("Men%27s+Moab+Ventilator&type=product");

		if (searchResult && urlRef)
			Assert.assertTrue(true);
		else
			Assert.assertTrue(false);

	}

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		driver.close();
		driver.quit();
	}

}
