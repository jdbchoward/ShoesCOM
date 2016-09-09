package PageObjects;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;

public class Wait {
	private WebDriver driver;
	private int timeout = 10;
	
	
	
	/*
	 * Coommon Wait methods
	 * 
	 * 
	 * 
	 */
	

	public Wait(WebDriver driver) {
		this.driver = driver;
		// PageFactory .initElements(driver, this);
	}

	
	public void threadWait(int ms)
	{
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public void waitForElementPresent(String locator) {
		try {
			(new WebDriverWait(driver, timeout)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
		} catch (Exception e) {

		}

	}

	public void waitForElementPresent(By by) {
		try {
			(new WebDriverWait(driver, timeout)).until(ExpectedConditions.presenceOfElementLocated(by));
		} catch (Exception e) {
            e.printStackTrace();
		}

	}
	
	
	public void waitForElementIsClickable(final By by) {
		(new WebDriverWait(driver, timeout)).until(ExpectedConditions.elementToBeClickable(by));
	}

	public void waitFor(long timeout) {
		try {
			//Thread.sleep(timeout);
			driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void WaitUntilPageLoaded() {

		JavascriptExecutor js = (JavascriptExecutor) driver;

		// Initially bellow given if condition will check ready state of page.
		if (js.executeScript("return document.readyState").toString().equals("complete")) {
			System.out.println("Page Is loaded.");
			return;
		}

		// This loop will rotate for 25 times to check If page Is ready after
		// every 1 second.
		// You can replace your value with 25 If you wants to Increase or
		// decrease wait time.
		for (int i = 0; i < 25; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			// To check page ready state.
			if (js.executeScript("return document.readyState").toString().equals("complete")) {
				break;
			}
		}

	}
	
	
	
	
	/*
	 * Wait methods for ElementsRepository class
	 * 
	 * 
	 * 
	 */
	
	public boolean waitElementToBeDisplayed(final By by) {
        boolean wait = false;
        if (by == null)
            return wait;
        try {
            wait = new WebDriverWait(driver, timeout)
                    .until(new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver d) {
                            return d.findElement(by).isDisplayed();
                        }
                    });
        } catch (Exception e) {
            System.out.println(driver.findElement(by).toString() + " is not displayed");
        }
        return wait;
    }

	public boolean waitElementToBeNonDisplayed(final By by) {
        boolean wait = false;  
        if (by == null)
            return wait;
        try {
            wait = new WebDriverWait(driver, timeout)
                    .until(new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver d) {
                            return !driver.findElement(by).isDisplayed();
                        }
                    });
        } catch (Exception e) {
            System.out.println(driver.findElement(by).toString() + " is still display");
        }
        return wait;
    }

	public WebElement getElementWithWait(final By by) {
        WebElement element = null;        
        try {
            element = new WebDriverWait(driver, timeout)
                    .until(new ExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver d) {
                            return d.findElement(by);
                        }
                    });
        } catch (Exception e) {
            System.out.println(by.toString() + " is not exist until "
                    + timeout);
        }
        return element;
    }

	
	
	public boolean waitElementToBeEnabled(final By by) {
        boolean wait = false;
        if (by == null)
            return wait;
        try {
            wait = new WebDriverWait(driver, timeout)
                    .until(new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver d) {
                            return d.findElement(by).isEnabled();
                        }
                    });
        } catch (Exception e) {
            System.out.println(driver.findElement(by).toString() + " is not enabled");
        }
        return wait;
    }

}
