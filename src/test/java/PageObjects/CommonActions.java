package PageObjects;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CommonActions {

	private ParseProperties settings;
	private String parsePath = "src\\test\\resources\\Setting.properties";

	public ParseProperties getSettings() {
		return new ParseProperties(parsePath);
	}

	/**
	 * When webdrive can't controlling click etc, (through element not display
	 * etc) then use this. )
	 */
	public void javascriptClick(WebDriver driver, WebElement webElement) {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", webElement);
	}

	public void javascriptOpenLink(WebDriver driver, WebElement webElement) {
		String href = webElement.getAttribute("href");
		((JavascriptExecutor) driver).executeScript("window.open('" + href + "')");
	}
	
	
	public void javascriptInputToTextField(WebDriver driver,WebElement ele,String content)
	{//this element could be input's iframe
		String js = "arguments[0].contentWindow.document.body.innerHTML='"
				+ content + "'";
		((JavascriptExecutor) driver).executeScript(js, ele);
	}

	/**
	 * Switch WindowHandles,pageName is the title of page that u want to
	 * switched to
	 */

	public void switchWindowHandles(WebDriver driver, String pageName) {
		for (String winHandle : driver.getWindowHandles()) {

			driver.switchTo().window(winHandle);
			if (driver.getTitle().equalsIgnoreCase(pageName)) {
				driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
				break;
			}

		}
	}
}
