package com.crm.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.crm.qa.util.TestUtil;
import com.crm.qa.util.WebEventListener;

import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserFactory {
	public static WebDriver driver;
	public static Properties prop;
	public static EventFiringWebDriver e_driver;
	public static WebEventListener eventListener;

	public BrowserFactory() {
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\main\\java\\com\\crm" + "\\qa\\config\\config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static WebDriver GetBrowser(String browserName) {
		browserName = browserName.toLowerCase();

		if (browserName.equals("chrome")) {
			return getChromeInstance();
		}

		if (browserName.equals("edge")) {
			return getEdgeInstance();
		}

		if (browserName.equals("ie")) {
			return getIEInstance();
		}
//		if (browserName.equals("phantomjs")) {
//			return getPhantomJSInstance();
//		}
//
//		if (browserName.equals("opera")) {
//			return getOperaInstance();
//		}
		else {
			return getFFInstance();
		}

	}

	public static void init() {
		e_driver = new EventFiringWebDriver(driver);
		// Now create object of EventListerHandler to register it with
		// EventFiringWebDriver
		eventListener = new WebEventListener();
		e_driver.register(eventListener);
		driver = e_driver;

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);

		driver.get(prop.getProperty("url"));

	}

	private static FirefoxDriver getFFInstance() {

		WebDriverManager.getInstance(DriverManagerType.FIREFOX).setup();
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		return (FirefoxDriver) driver;
	}

	private static WebDriver getChromeInstance() {

		WebDriverManager.getInstance(DriverManagerType.CHROME).setup();

		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.default_content_setting_values.notifications", 2);
		prefs.put("credentials_enable_service", false);
		prefs.put("password_manager_enabled", false);

		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);

		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		return driver;
	}

	private static InternetExplorerDriver getIEInstance() {

		WebDriverManager.getInstance(DriverManagerType.IEXPLORER).setup();

		InternetExplorerDriver driver = new InternetExplorerDriver();
		return driver;
	}

//	private static PhantomJSDriver getPhantomJSInstance() {
//
//		PhantomJsDriverManager.getInstance().setup();
//		PhantomJSDriver driver = new PhantomJSDriver();
//		return driver;
//	}

	public static EdgeDriver getEdgeInstance() {
		WebDriverManager.getInstance(DriverManagerType.EDGE).setup();
		EdgeDriver driver = new EdgeDriver();
		return driver;
	}

//	public static OperaDriver getOperaInstance() {
//		OperaDriverManager.getInstance().setup();
//		OperaDriver driver = new OperaDriver();
//		return driver;
//	}

}
