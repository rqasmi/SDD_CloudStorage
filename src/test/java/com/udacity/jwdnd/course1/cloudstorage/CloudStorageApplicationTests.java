package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	private static final String FIRST_NAME = "Ragheed";
	private static final String LAST_NAME = "Qasmieh";
	private static final String USERNAME = "ragheed";
	private static final String PASSWORD = "12345";
	private static final String NOTE_TITLE = "To do list";
	private static final String NOTE_DESCRIPTION = "Review code";

	private LoginPage loginPage;
	private SignupPage signupPage;
	private HomePage homePage;
	private ResultPage resultPage;

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private String baseURL;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		baseURL = "http://localhost:" + this.port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get(baseURL + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testUnauthorizedLoginAndSignupAccess() {
		String signupURL = baseURL + "/signup";
		String loginURL = baseURL + "/login";
		String homeURL = baseURL + "/home";

		driver.get(signupURL);
		assertEquals(signupURL, driver.getCurrentUrl());
		driver.get(loginURL);
		assertEquals(loginURL, driver.getCurrentUrl());
		driver.get(baseURL);
		assertEquals(loginURL, driver.getCurrentUrl());
		driver.get(homeURL);
		assertEquals(loginURL, driver.getCurrentUrl());
	}

	@Test
	public void testSignupAndLogin(){
		driver.get(baseURL + "/signup");
		signupPage = new SignupPage(driver);

		signupPage.signup(FIRST_NAME, LAST_NAME, USERNAME, PASSWORD);

		driver.get(baseURL + "/login");
		loginPage = new LoginPage(driver);

		loginPage.login(USERNAME, PASSWORD);

		assertEquals(baseURL + "/home", driver.getCurrentUrl());
		homePage = new HomePage(driver);
		assertNotNull(homePage.getLogoutBtn());
		assertNotNull(homePage.getNavCredentialsTab());
		assertNotNull(homePage.getNavFilesTab());
		assertNotNull(homePage.getNavNotesTab());

		homePage.logout();
		assertNotEquals(baseURL + "/home", driver.getCurrentUrl());
	}

	@Test
	public void testCreateNote() {
		driver.get(baseURL + "/signup");
		signupPage = new SignupPage(driver);
		signupPage.signup(FIRST_NAME, LAST_NAME, USERNAME, PASSWORD);
		signupPage.navigateToLogin();

		loginPage = new LoginPage(driver);
		loginPage.login(USERNAME, PASSWORD);

		homePage = new HomePage(driver);
		homePage.createNote(NOTE_TITLE, NOTE_DESCRIPTION);

		resultPage = new ResultPage(driver);
		try { Thread.sleep(2000); } catch (InterruptedException e) {}
		assertEquals("Successfully posted note.", resultPage.getSuccessMessage());
		resultPage.navigateToHomePage();
		try { Thread.sleep(2000); } catch (InterruptedException e) {}
		homePage.navigateToNotes();
		try { Thread.sleep(2000); } catch (InterruptedException e) {}

		assertEquals(NOTE_TITLE, homePage.getNoteTitleText());
		assertEquals(NOTE_DESCRIPTION, homePage.getNoteDescriptionText());
	}

}
