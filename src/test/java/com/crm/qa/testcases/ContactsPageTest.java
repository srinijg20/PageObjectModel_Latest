/*
 * @author Naveen Khunteta
 *
 */

package com.crm.qa.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.crm.qa.base.TestBase;
import com.crm.qa.pages.ContactsPage;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;
import com.crm.qa.util.TestUtil;

public class ContactsPageTest extends TestBase {

	LoginPage loginPage;
	HomePage homePage;
	TestUtil testUtil;
	ContactsPage contactsPage;
	WebDriver driver;
	String sheetName = "contacts";

	public ContactsPageTest() {
		super();

	}

	@Parameters("browser")
	@BeforeMethod
	public void setUp(String browser) throws InterruptedException {

		initialization(browser);
		// this.driver = BrowserFactory.GetBrowser(browser);
		this.testUtil = new TestUtil();
		this.contactsPage = new ContactsPage();
		this.loginPage = new LoginPage();
		this.homePage = this.loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
		TestUtil.runTimeInfo("error", "login successful");
		this.testUtil.switchToFrame();
		this.contactsPage = this.homePage.clickOnContactsLink();
	}

	@Test(priority = 1, groups = "sanity")
	public void verifyContactsPageLabel() {
		Assert.assertTrue(this.contactsPage.verifyContactsLabel(), "contacts label is missing on the page");
	}

	@Test(priority = 2)
	public void selectSingleContactsTest() {
		this.contactsPage.selectContactsByName("test2 test2");
	}

	@Test(priority = 3)
	public void selectMultipleContactsTest() {
		this.contactsPage.selectContactsByName("test2 test2");
		this.contactsPage.selectContactsByName("ui uiii");

	}

	@DataProvider
	public Object[][] getCRMTestData() {
		Object data[][] = TestUtil.getTestData(this.sheetName);
		return data;
	}

	@Test(priority = 4, dataProvider = "getCRMTestData")
	public void validateCreateNewContact(String title, String firstName, String lastName, String company) {
		this.homePage.clickOnNewContactLink();
		// contactsPage.createNewContact("Mr.", "Tom", "Peter", "Google");
		this.contactsPage.createNewContact(title, firstName, lastName, company);

	}

	@AfterMethod
	public void tearDown() {
		this.driver.quit();
	}

}
