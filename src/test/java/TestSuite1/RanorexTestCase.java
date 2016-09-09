package TestSuite1;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;

import PageObjects.BrowserLoader;
import PageObjects.CommonActions;
import PageObjects.ElementsRepositoryAction;
import PageObjects.Wait;

import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class RanorexTestCase {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	private Wait wait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;

	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {

		common = PageFactory.initElements(driver, CommonActions.class);
		String browserType = common.getSettings().getValue("browserType");
		BrowserLoader brower = new BrowserLoader(browserType);
		driver = brower.driver;
//		elementsRepositoryAction = ElementsRepositoryAction.getInstance(driver);
		elementsRepositoryAction = new ElementsRepositoryAction(driver);
		
		baseUrl = "http://www.ranorex.com";
		wait = new Wait(driver);
		
	}

	@Test
	public void testRanorexTestCase() throws Exception {
		driver.get(baseUrl + "/support/user-guide-20/lesson-9-ranorex-spy.html");
		wait.WaitUntilPageLoaded();
		driver.findElement(By.xpath("//*[@id='search-icon']")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		elementsRepositoryAction.getElement("Ranorex_btn_Search").clear();
		elementsRepositoryAction.getElement("Ranorex_btn_Search").sendKeys("repository");
		WebElement wwe = driver.findElement(By.xpath("//input[@class='gsc-search-button' and @value='Search']"));

		// those jQuery elements which webdriver can't click(through element not
		// display error) then use this.
		// ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
		// wwe);
		common.javascriptClick(driver, wwe);

		// still in jQuery, elements always get displayed false, then try to use
		// enable
		WebElement wwe1 = driver
				.findElement(By.xpath("(//a[@class='gs-title' and contains(text(), 'Test Automation Tools')])"));
		common.javascriptOpenLink(driver, wwe1);
		String currWinHandle = driver.getWindowHandle();
		String pageTitleSwitchTo = "Test Automation Tools | Ranorex - Automated Testing for Everyone";
		common.switchWindowHandles(driver, pageTitleSwitchTo);
		wait.waitFor(10);
		common.javascriptClick(driver, elementsRepositoryAction.getElementNoWait("Ranorex_btn_Img"));
		common.javascriptClick(driver, driver.findElement(By.xpath("//img [@src='images/playbutton-56.png' ]")));
		driver.close();
		driver.switchTo().window(currWinHandle);
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}
