package example;	


import org.testng.annotations.*;

import PageObjects.Wait;
import PageObjects.BrowserLoader;
import PageObjects.BrowserTypes;

import static org.testng.Assert.*;
import org.openqa.selenium.*;

public class Firefoxcase {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  private Wait wait;

  @BeforeClass(alwaysRun = true)
  public void setUp() throws Exception {

		BrowserLoader brower = new BrowserLoader(BrowserTypes.firefox);
		driver = brower.driver; 
		//driver.manage().window().maximize();
		wait = new Wait(driver);
	  
    baseUrl = "https://www.google.ca/";
//    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    wait.WaitUntilPageLoaded();
  }

  @Test
  public void testFirefoxcase() throws Exception {
    driver.get(baseUrl + "/");
    driver.findElement(By.id("lst-ib")).clear();
    driver.findElement(By.id("lst-ib")).sendKeys("chrome");
//    driver.findElement(By.cssSelector("div.sbqs_c")).click();
//    driver.findElement(By.linkText("Chrome - Google")).click();
  }

  @AfterClass(alwaysRun = true)
  public void tearDown() throws Exception {
	driver.close();
    driver.quit();
//    String verificationErrorString = verificationErrors.toString();
//    if (!"".equals(verificationErrorString)) {
//      fail(verificationErrorString);
//    }
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
