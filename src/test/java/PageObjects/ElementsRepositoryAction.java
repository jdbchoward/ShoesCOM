package PageObjects;

import PageObjects.Wait;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.ho.yaml.Yaml;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/*
 * never try to Singleton this Class,when running testsuite altogether every case need their own driver.
 */
public class ElementsRepositoryAction {
	private static ElementsRepositoryAction elementsRepositoryInstance;
	private String yamlfile;
	private WebDriver driver;
	private Map<String, Map<String, String>> elementRepository;
	private Wait wait;

//	private ElementsRepositoryAction(WebDriver driver) {
//		yamlfile = "ElementsPath";
//		this.getYamlFile();
//		this.driver = driver;
//		wait = new Wait(driver);
//	}
//	
	
	public ElementsRepositoryAction(WebDriver driver) {
		yamlfile = "ElementsPath";
		this.getYamlFile();
		this.driver = driver;
		wait = new Wait(driver);
	}

//	public static synchronized ElementsRepositoryAction getInstance(WebDriver driver) {
//		if (elementsRepositoryInstance == null) {
//			elementsRepositoryInstance = new ElementsRepositoryAction(driver);
//		}
//		return elementsRepositoryInstance;
//	}
//	
	

	public void getYamlFile() {

		String path = System.getProperty("user.dir");
		File f = new File(path + "\\src\\test\\resources\\ElementsPath.yaml");

		try {
			// elementRepository = Yaml.loadType(new
			// FileInputStream(f.getAbsolutePath()), HashMap.class);
			elementRepository = Yaml.loadType(new InputStreamReader(new FileInputStream(f.getAbsolutePath()), "UTF-8"),
					HashMap.class);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private By getBy(String type, String value) {
		By by = null;
		if (type.equals("id")) {
			by = By.id(value);
		}
		if (type.equals("xpath")) {
			by = By.xpath(value);
		}
		if (type.equals("name")) {
			by = By.name(value);
		}
		if (type.equals("className")) {
			by = By.className(value);
		}
		if (type.equals("linkTest")) {
			by = By.linkText(value);
		}
		if (type.equals("cssSelector")) {
			by = By.cssSelector(value);
		}
		if (type.equals("tagName")) {
			by = By.tagName(value);
		}
		return by;
	}

	public WebElement getElement(String key) {
		return this.getLocator(key, null, true);
	}
	
	
	public By getByObjectFromKey(String key,String[] replace)
	{
		Map<String, String> m = elementRepository.get(key);
		String type = m.get("type");
		String value = m.get("value");
		if (replace != null) {
			value = this.replaceLocatorString(value, replace);
		}
		return this.getBy(type, value);
		
	}

	public WebElement getElement(String key, String[] replace) {

		return this.getLocator(key, replace, false);
	}

	public WebElement getElementNoWait(String key) {
		return this.getLocator(key, null, false);
	}

	// 多态形式，字符串替换
	public WebElement getElementNoWait(String key, String[] replace) {

		return this.getLocator(key, replace, false);
	}

	// 由于getElement和getElementNoWait非常相似，所以重构一下这两个方法
	private WebElement getLocator(String key, String[] replace, boolean needWait) {
		WebElement element = null;
		if (elementRepository.containsKey(key)) {
//			Map<String, String> m = elementRepository.get(key);
//			String type = m.get("type");
//			String value = m.get("value");
//			if (replace != null) {
//				value = this.replaceLocatorString(value, replace);
//			}
//			By by = this.getBy(type, value);
		By by=getByObjectFromKey(key,replace);
			if (!needWait)
				return element = driver.findElement(by);
			if (needWait && wait.waitElementToBeDisplayed(by))
				return element = driver.findElement(by);

		} else {
			System.out.println("ElementsPepository.class : " + key + " is not exists in " + yamlfile + ".yaml");
		}
		return element;
	}

	private String replaceLocatorString(String locatorString, String[] ss) {
		for (String s : ss) {
			locatorString = locatorString.replaceFirst("%s", s);
		}
		return locatorString;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		WebDriver driver;
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\hzhang\\workspace\\WebdriverTest\\src\\test\\resources\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("no-sandbox");
		options.addArguments("chrome.switches", "--disable-extensions");

		driver = new ChromeDriver(options);
//		ElementsRepositoryAction el = ElementsRepositoryAction.getInstance(driver);
		ElementsRepositoryAction el  = new ElementsRepositoryAction(driver);
		// TODO Auto-generated method stub

		// el.driver.navigate().to("http://www.baidu.com");
		el.driver.navigate().to("http://www.jd.com");
		WebElement element;
		element = el.getElement("JD_button1");
		// String[] replace = new String[] { "kw" };// element =
		// l.getElement("baidu_input", replace);
		// element.sendKeys("aa");
		element.click();
		driver.close();
		driver.quit();
	}

}