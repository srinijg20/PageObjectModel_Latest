package com.crm.qa.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.crm.qa.base.BrowserFactory;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;

public class LoginPageTest extends BrowserFactory {
	LoginPage loginPage;
	HomePage homePage;
	WebDriver driver;

	public LoginPageTest() {
		super();
	}

	@Parameters("browser")
	@BeforeMethod
	public void setUp(String browser) {
		// initialization(browser);
		this.driver = BrowserFactory.GetBrowser(browser);

		init();
		this.loginPage = new LoginPage();
	}

	@Test(priority = 1)
	public void loginPageTitleTest() {
		// System.out.println("inside");
		String title = this.loginPage.validateLoginPageTitle();
		System.out.println(title);
		Assert.assertEquals(title, "CRMPRO - CRM software for customer relationship management, sales, and support.");
	}

	@Test(priority = 2)
	public void crmLogoImageTest() {
		boolean flag = this.loginPage.validateCRMImage();
		Assert.assertTrue(flag);
	}

	@Test(priority = 3)
	public void loginTest() throws InterruptedException {
		this.homePage = this.loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
		// this.homePage = this.loginPage.login("naveenk", "test@123");
	}

	@AfterMethod
	public void tearDown() {
		System.out.println("quit");
		this.driver.quit();
	}

}
