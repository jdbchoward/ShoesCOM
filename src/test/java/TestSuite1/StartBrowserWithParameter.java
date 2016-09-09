package TestSuite1;

import org.testng.annotations.Test;

import DataProviderFiles.DataProviderSource;
import PageObjects.BrowserLoader;
import PageObjects.BrowserTypes;
import PageObjects.CommonActions;
import PageObjects.ElementsRepositoryAction;
import PageObjects.PageLogin;
import PageObjects.Wait;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class StartBrowserWithParameter {

	private WebDriver driver;
	private String ipaddress = "http://www.jd.com/";
	private Wait wait;
	
//	@FindBy(xpath = "//a[contains(text(),'企业采购')]")
	private WebElement market; 
	
	@Test
	public void testEasy() {

		//get webpage and wait4pageLoaded
		PageFactory.initElements(driver, this);
		wait = new Wait(driver);
		wait.WaitUntilPageLoaded();

		//do login, or menu chooser etc...
		PageLogin pageLogin = PageFactory.initElements(driver, PageLogin.class);
		String logoname = pageLogin.getRegName(ipaddress);
		initWebElements();
		//do other UI action
		market.click();
		//get data from dataprovider
		

	}
	
	@Test (dataProvider="input2baiduSearch", dataProviderClass=DataProviderSource.class)
	public void dataProviderTest(int index,String inputContent)
	{
		System.out.println(inputContent);
	}
	
//	@Test(dataProvider="TestType", dataProviderClass=DataProviderSource.class, enabled = false)
//	public void testNgDataProviderExample(String data)
//	{
//       System.out.println("Integration testing: Data(" + data + ")");
//
//	}
//	
//	 @Test(dataProvider="scenarioData", dataProviderClass=DataProviderSource.class,enabled = false)	 
//	     public void commonScenarios(String scenarioData) {
//	
//	         System.out.println("Common Scenarios testing: Data(" + scenarioData + ")");
//	
//	     }

	 

	@BeforeTest
	public void beforeTest() {

		CommonActions common = PageFactory.initElements(driver, CommonActions.class);
		String browserType=common.getSettings().getValue("browserType");
		BrowserLoader brower = new BrowserLoader(browserType);
		driver = brower.driver;
				
	}
	
	private void initWebElements()
	{
		//get elementsRepositoryAction class to read elements path from yaml config file
//		ElementsRepositoryAction elementsRepositoryAction = ElementsRepositoryAction.getInstance(driver);
		ElementsRepositoryAction elementsRepositoryAction = new ElementsRepositoryAction(driver);
		market=elementsRepositoryAction.getElement("JD_button1");
	}

	@AfterTest
	public void afterTest() {
		driver.close();
		driver.quit();
	}

}
