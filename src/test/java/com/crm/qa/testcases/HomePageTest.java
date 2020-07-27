package com.crm.qa.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.crm.qa.base.TestBase;
import com.crm.qa.pages.ContactsPage;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;
import com.crm.qa.util.TestUtil;

public class HomePageTest extends TestBase {
	LoginPage loginPage;
	HomePage homePage;
	TestUtil testUtil;
	ContactsPage contactsPage;
	WebDriver driver;

	public HomePageTest() {
		super();
	}

	// test cases should be separated -- independent with each other
	// before each test case -- launch the browser and login
	// @test -- execute test case
	// after each test case -- close the browser
	@Parameters("browser")
	@BeforeMethod
	public void setUp(String browser) {
		initialization(browser);
		// this.driver = BrowserFactory.GetBrowser(browser);
		this.testUtil = new TestUtil();
		this.contactsPage = new ContactsPage();
		this.loginPage = new LoginPage();
		// this.homePage = this.loginPage.login(prop.getProperty("username"),
		// prop.getProperty("password"));
	}

	@Test(priority = 1)
	public void verifyHomePageTitleTest() {
		String homePageTitle = this.homePage.verifyHomePageTitle();
		Assert.assertEquals(homePageTitle, "CRMPRO", "Home page title not matched");
	}

	@Test(priority = 2)
	public void verifyUserNameTest() {
		this.testUtil.switchToFrame();
		Assert.assertTrue(this.homePage.verifyCorrectUserName());
	}

	@Test(priority = 3)
	public void verifyContactsLinkTest() {
		this.testUtil.switchToFrame();
		this.contactsPage = this.homePage.clickOnContactsLink();
	}

	@AfterMethod
	public void tearDown() {
		this.driver.quit();
	}

}
