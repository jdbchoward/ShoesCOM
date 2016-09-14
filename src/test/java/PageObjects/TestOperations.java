package PageObjects;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import junit.framework.Assert;

public class TestOperations {

	private WebDriver driver;
	private String baseUrl;
	private Wait wait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	static Logger log = Logger.getLogger(TestOperations.class.getName());

	public TestOperations(WebDriver driver) {
		this.driver = driver;
		baseUrl = "https://demox.mmxreservations.com/";
		wait = new Wait(driver);
		// elementsRepositoryAction =
		// ElementsRepositoryAction.getInstance(driver);
		common=PageFactory.initElements(driver, CommonActions.class);
		elementsRepositoryAction = new ElementsRepositoryAction(driver);
	}

	public void searchRooms() {
		driver.get(baseUrl + "ui#/fe2?pageCode=UC2026");
		wait.WaitUntilPageLoaded();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		elementsRepositoryAction.getElementNoWait("TED_Search_StartDate").clear();
		elementsRepositoryAction.getElementNoWait("TED_Search_StartDate").sendKeys("Apr 23, 2018");
		elementsRepositoryAction.getElementNoWait("TED_Search_RoomCount").clear();
		elementsRepositoryAction.getElementNoWait("TED_Search_RoomCount").sendKeys("2");
		elementsRepositoryAction.getElement("TED_Search_btn").click();
	}

	public void searchRoomsWhenDateWrong() {
		driver.get(baseUrl + "ui#/fe2?pageCode=UC2026");
		wait.WaitUntilPageLoaded();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		wait.threadWait(10000);
		elementsRepositoryAction.getElementNoWait("TED_Search_StartDate").clear();
		elementsRepositoryAction.getElementNoWait("TED_Search_StartDate").sendKeys("11, 2018");
		elementsRepositoryAction.getElementNoWait("TED_Search_RoomCount").clear();
		elementsRepositoryAction.getElementNoWait("TED_Search_RoomCount").sendKeys("2");
		// elementsRepositoryAction.getElement("TED_Search_btn").click();
	}

	public String getToolTip(String element, String attribute) {
		Actions action = new Actions(driver);
		WebElement tooltip = elementsRepositoryAction.getElementNoWait(element);

		wait.threadWait(2000);

		action.clickAndHold(tooltip).perform();
		String ToolTipText = tooltip.getAttribute(attribute);

		return ToolTipText;
	}

	public String filterByRating() {
		// elementsRepositoryAction.getElementNoWait("TED_Filter_rating").click();

		List<WebElement> listWebElements = driver.findElements(By.xpath("//input[@type='checkbox']"));

		listWebElements.get(1).click();
		// for(WebElement w:listWebElements)
		// {
		// System.out.println(w.getAttribute(""));
		// System.out.println(w.getText());
		// }

		String rateText = elementsRepositoryAction.getElementNoWait("TED_Filter_lable").getText();
		// assertEquals(driver.findElement(By.cssSelector("em.ng-binding.firepath-matching-node")).getText(),
		// "2 Hotels");
		return rateText;
	}

	public int getHotelNumber(String hotels) {
		if (!hotels.equals("") || hotels != null) {
			String[] getHotelNumber = hotels.split(" ");
			return Integer.parseInt(getHotelNumber[0]);
		}
		return -1;
	}

	public WebElement getHotelOverViewInfo() {
		List<WebElement> listWebElements = driver.findElements(By.linkText("Hotel Overview"));
		if (listWebElements != null && listWebElements.size() > 0)
			return listWebElements.get(0);
		return null;

	}

	public int getHotelRoomViewInfo() {
		int count = 0;

		List<WebElement> listRoomButton = driver.findElements(By.linkText("Rooms"));
		if (listRoomButton != null && listRoomButton.size() > 0)
			listRoomButton.get(0).click();

		String hotelPriceText = elementsRepositoryAction.getElement("TED_HotelOverView_hotelPrice").getText();
		log.debug("hotelPriceText= " + hotelPriceText);
		List<WebElement> listWebElements = driver.findElements(By.xpath("//span[contains(text(),'$')]"));
		if (listWebElements != null && listWebElements.size() > 0) {
			for (WebElement w : listWebElements) {
				log.debug("w.getText()= " + w.getText());
				if (w.getText().equalsIgnoreCase(hotelPriceText))
					count++;
			}

		}
		log.debug("count= " + count);
		return count;
	}

	public void selectHotel() {
		List<WebElement> listRoomButton = driver.findElements(By.linkText("Select Hotel"));
		if (listRoomButton != null && listRoomButton.size() > 0)
			listRoomButton.get(0).click();
	}

	public void modifySelectedRoom() {
		// change days tooltip tool
		Actions action = new Actions(driver);
		WebElement tooltip = driver.findElement(By.xpath("//div[@id='day-dte']"));
		wait.threadWait(2000);
		action.clickAndHold(tooltip).perform();

		// change room number selector
		List<WebElement> listSelector = driver
				.findElements(By.xpath("//div[@class='col-sm-12 text-center']/div/select"));
		if (listSelector != null && listSelector.size() >= 2) {
			// change room quantity
			new Select(listSelector.get(0)).selectByVisibleText("2 Rooms");
			// change adults
			new Select(listSelector.get(1)).selectByVisibleText("1 Adult");
		}

	}

	public void addToBooking() {
		List<WebElement> listRoomButton = driver.findElements(By.linkText("Add to Booking"));
		if (listRoomButton != null && listRoomButton.size() > 0)
			listRoomButton.get(1).click();

	}

	public boolean verifyRoomInfor() {
		boolean verified = false;
		// get room quanlity choosen
		List<WebElement> listRoomCountLabel = driver
				.findElements(By.xpath("//div[@class='row summary-label larger-font']/div/b"));
		if (listRoomCountLabel != null && listRoomCountLabel.size() > 0) {
			if (Integer.parseInt(listRoomCountLabel.get(0).getText()) > 1)
				verified = true;
		}

		List<WebElement> roomPriceLabel = driver
				.findElements(By.xpath("//div[@class='row summary-label larger-font']/div/b"));
		if (roomPriceLabel != null && roomPriceLabel.size() > 0) {
			String totalPrice = roomPriceLabel.get(1).getText();
			String p = totalPrice.replaceAll(",", "").trim().substring(1, 4);
			if (Integer.parseInt(p) > 1000)
				verified = true;
		} else
			verified = false;

		// get room left
		List<WebElement> roomLeftLabel = driver.findElements(By.xpath("//span[contains(text(),'Only')]"));
		if (roomLeftLabel != null && roomLeftLabel.size() > 0) {
			String roomleft = roomLeftLabel.get(0).getText();
			String[] roomLeftNumber = roomleft.split(" ");
			if (Integer.parseInt(roomLeftNumber[1]) > 0)
				verified = true;
		} else
			verified = false;

		return verified;
	}

	public void checkoutReservation()
	{		
//		elementsRepositoryAction.getElement("TED_HotelCheckout_continueBtn").click();
		
		List<WebElement> continueButton = driver.findElements(By.linkText("Continue"));
		if (continueButton != null && continueButton.size() > 0)
			continueButton.get(0).click();
		//fill out the form
		elementsRepositoryAction.getElement("TED_HotelCheckout_Form_companyName").sendKeys("Personal");
		new Select(elementsRepositoryAction.getElement("TED_HotelCheckout_Form_salutation")).selectByVisibleText("Mr.");
		elementsRepositoryAction.getElement("TED_HotelCheckout_Form_firstName").sendKeys("Neil");
		elementsRepositoryAction.getElement("TED_HotelCheckout_Form_lastName").sendKeys("Jabi");
		elementsRepositoryAction.getElement("TED_HotelCheckout_Form_address").sendKeys("3575 Jason Ave");
		elementsRepositoryAction.getElement("TED_HotelCheckout_Form_city").sendKeys("Toronto");
		new Select(elementsRepositoryAction.getElement("TED_HotelCheckout_Form_country")).selectByVisibleText("Canada");
		new Select(elementsRepositoryAction.getElement("TED_HotelCheckout_Form_stateProvince")).selectByVisibleText("Ontario");
		elementsRepositoryAction.getElement("TED_HotelCheckout_Form_zipCode").sendKeys("L5A3Y3");
		elementsRepositoryAction.getElement("TED_HotelCheckout_Form_telephone").sendKeys("647123456");
		elementsRepositoryAction.getElement("TED_HotelCheckout_Form_email").sendKeys("fakemail1344455@gmail.com");
		elementsRepositoryAction.getElement("TED_HotelCheckout_Form_email2").sendKeys("fakemail1344455@gmail.com");
		new Select(elementsRepositoryAction.getElement("TED_HotelCheckout_Form_creditCardType")).selectByVisibleText("Visa");
		elementsRepositoryAction.getElement("TED_HotelCheckout_Form_creditCardNumber").sendKeys("4444333322221111");
		new Select(elementsRepositoryAction.getElement("TED_HotelCheckout_Form_creditCardExpirationMonth")).selectByVisibleText("12");
		new Select(elementsRepositoryAction.getElement("TED_HotelCheckout_Form_creditCardExpirationYear")).selectByVisibleText("2018");
		elementsRepositoryAction.getElement("TED_HotelCheckout_Form_creditCardName").sendKeys("Neil Jabi");
		
		elementsRepositoryAction.getElement("TED_HotelCheckout_Form_Guest1FirstName").sendKeys("Neil");
		elementsRepositoryAction.getElement("TED_HotelCheckout_Form_Guest1LastName").sendKeys("Guest");
		elementsRepositoryAction.getElement("TED_HotelCheckout_Form_Guest2FirstName").sendKeys("Neil2");
		elementsRepositoryAction.getElement("TED_HotelCheckout_Form_Guest2LastName").sendKeys("Guest2");
		elementsRepositoryAction.getElement("TED_HotelCheckout_Form_Guest1Email").sendKeys("fakeemail344455@gmail.com");
		new Select(elementsRepositoryAction.getElement("TED_HotelCheckout_Form_eta")).selectByVisibleText("10:30 AM");
		
		elementsRepositoryAction.getElement("TED_HotelCheckout_Form_ReviewBtn").click();
	}
	
	
	public void doSignIn(String username,String psw)
	{
		driver.manage().window().maximize();
		driver.get("http://www.shoeme.ca/");		
		wait.WaitUntilPageLoaded();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);			
//		WebElement w=driver.findElement(By.xpath("//button[@class='btn btn-default localize']"));
//	    common.javascriptClick(driver, w);
		
		
		driver.findElement(By.xpath("//a[@id='signin-link']/span")).click();
		wait.threadWait(10000);
//		driver.findElement(By.xpath("//a[@id='signin-link']/span")).click();
	
		elementsRepositoryAction.getElement("TED_ShoesCOM_signInBtn").click();
		wait.threadWait(10000);
		driver.findElement(By.id("customer_email")).clear();
		driver.findElement(By.id("customer_email")).sendKeys(username);
		driver.findElement(By.id("customer_password")).clear();
		driver.findElement(By.id("customer_password")).sendKeys(psw);
		driver.findElement(By.xpath("//input[@value='Log In']")).click();
		
	}
	
	
	
	public boolean doSignOut()
	{
		wait.threadWait(10000);
		common.javascriptClick(driver, driver.findElement(By.xpath("//a[@data-i18n='localize_log-out']")));

//		wait.waitElementToBeDisplayed(By.linkText("Sign in / Create Account"));
		return checkSignOutStatus();
	}
	
	public boolean checkSignOutStatus()
	{
//		driver.findElement(By.xpath("//a[@id='signin-link']/span")).click();
//		wait.threadWait(10000);
//		return (driver.findElement(By.xpath("//a[@data-i18n=\"localize_log-in\"]")).getText().equalsIgnoreCase("Sign in / Create Account")?true:false);
		return !driver.getCurrentUrl().contains("sid");
	}
	
	
	public void navigationToManShoes()
	{
		WebElement w=driver.findElement(By.xpath("//a[@data-i18n='localize_waterproof']"));
		common.javascriptClick(driver, w);

	}
	
	public void filterShoes()
	{
		driver.findElement(By.xpath("//span[@data-i18n='localize_nxt-8.5']")).click();
	}
	
	public void selectItem()
	{
		driver.findElement(By.xpath("//img[contains(@src,'https://cdn.shopify.com/s/files/1/0121/3322/products/176643_1000_45_3b288327-be1e-4090-a98a-933f96a98232_medium.jpeg?v=1458761530')]")).click();
		 wait.threadWait(10000); 
		driver.findElement(By.xpath("//div[@id='size-options']/div[4]")).click();
		driver.findElement(By.xpath("//div[@id='width-options']/div")).click();	
	}
	public void addItem()
	{
	
		driver.findElement(By.id("add")).click();
	}
	
	public void addToWishList()
	{
		driver.findElement(By.cssSelector("#add-to-wishlist > a > span")).click();
	}
	
	
	public boolean checkShoppingCart()
	{
		String bagCount=driver.findElement(By.xpath("//span[@id='bag-count']")).getText();
	    if(bagCount!=null&&!bagCount.equalsIgnoreCase("")) 
	    	return (Integer.parseInt(bagCount)>0?true:false);
	    else
	    	return false;
	}
	
	public boolean checkWishList()
	{
		String wishCount=driver.findElement(By.xpath("//span[@id='wishlist-count']")).getText();
	    if(wishCount!=null&&!wishCount.equalsIgnoreCase("")) 
	    	return (Integer.parseInt(wishCount)>0?true:false);
	    else
	    	return false;
	}
    
	public void checkShoppingCartContent()
	{
		driver.findElement(By.id("bag-count")).click();
		driver.findElement(By.xpath("//form[@id='cart-form']/div[2]/div/div[2]/div/div[2]/a/span")).click();

	}
	
	public void checkWishListContent()
	{
		driver.findElement(By.cssSelector("#wishlist-link > span.localize.util-text")).click();

	}
	
	
	public boolean searchShoe()
	{
		driver.get("http://www.shoeme.ca/");
		wait.threadWait(5000);
		driver.findElement(By.id("search-field")).clear();
		driver.findElement(By.id("search-field")).sendKeys("Men's Moab Ventilator");
		driver.findElement(By.xpath("//div[@id='search-submit']/i")).click();
		wait.threadWait(5000);
        String result=driver.findElement(By.cssSelector("b.nxt-result-total")).getText();
	    if(result!=null&&!result.equalsIgnoreCase("")) 
	    	return (Integer.parseInt(result)>0?true:false);
	    else
	    	return false;
	}
}