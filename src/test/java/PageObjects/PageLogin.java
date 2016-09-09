package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class PageLogin {
	
	@FindBy(xpath = "//a[contains(text(),'免费注册')]")
	private WebElement reg; 
	
	@FindBy(xpath = "//a[@id='rt-logo']")
	private WebElement logo; 
	
	 private WebDriver driver;
  
	public PageLogin(WebDriver driver)
	{
		this.driver=driver;
	}
  
    public void checkPageTitle(String ipaddress, String expectTitle) { 
    	
		driver.get(ipaddress);  
		String realTitle = driver.getTitle();				 
		Assert.assertTrue(realTitle.contains(expectTitle)); 
  
    }  
    
    public String getRegName(String ipaddress)
    {
    	 
    	driver.get(ipaddress); 
    	return reg.getTagName();
    }
    
    public String getLogoName(String ipaddress)
    {
    	 
    	driver.get(ipaddress); 
    	return logo.getTagName();
    }

}
