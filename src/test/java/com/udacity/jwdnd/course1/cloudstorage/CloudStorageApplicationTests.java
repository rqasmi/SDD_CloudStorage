package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.NoSuchElementException;
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
	private static final String NOTE_TITLE_EDITED = "To do list for cloud";
	private static final String NOTE_DESCRIPTION = "Review code";
	private static final String NOTE_DESCRIPTION_EDITED = "Review code and merge pull request";
	private static final String URL = "https://amazon.com/login";
	private static final String CRED_USERNAME = "ragheed";
	private static final String CRED_PASSWORD = "amz2312";
	private static final String URL_EDITED = "https://facebook.com/login";
	private static final String CRED_USERNAME_EDITED = "rag";
	private static final String CRED_PASSWORD_EDITED = "fcb3412";

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
		signupPage.navigateToLogin();

		loginPage = new LoginPage(driver);
		loginPage.login(USERNAME, PASSWORD);

		homePage = new HomePage(driver);
		assertEquals(baseURL + "/home", driver.getCurrentUrl());
		assertNotNull(homePage.getLogoutBtn());
		assertNotNull(homePage.getNavCredentialsTab());
		assertNotNull(homePage.getNavFilesTab());
		assertNotNull(homePage.getNavNotesTab());

		homePage.logout();
		assertNotEquals(baseURL + "/home", driver.getCurrentUrl());
	}

	@Test
	public void testCreateNote() {
		try {
			driver.get(baseURL + "/signup");
			signupPage = new SignupPage(driver);
			signupPage.signup(FIRST_NAME, LAST_NAME, USERNAME, PASSWORD);
			signupPage.navigateToLogin();

			loginPage = new LoginPage(driver);
			loginPage.login(USERNAME, PASSWORD);

			homePage = new HomePage(driver);
			homePage.createNote(NOTE_TITLE, NOTE_DESCRIPTION);

			resultPage = new ResultPage(driver);
			Thread.sleep(2000);
			assertEquals("Successfully posted note.", resultPage.getSuccessMessage());
			resultPage.navigateToHomePage();
			Thread.sleep(2000);
			homePage.navigateToNotes();
			Thread.sleep(2000);

			assertEquals(NOTE_TITLE, homePage.getNoteTitleText());
			assertEquals(NOTE_DESCRIPTION, homePage.getNoteDescriptionText());

		} catch (InterruptedException e) {}
	}

	@Test
	public void testEditNote() {
		try {
			driver.get(baseURL + "/signup");
			signupPage = new SignupPage(driver);
			signupPage.signup(FIRST_NAME, LAST_NAME, USERNAME, PASSWORD);
			signupPage.navigateToLogin();

			loginPage = new LoginPage(driver);
			loginPage.login(USERNAME, PASSWORD);

			homePage = new HomePage(driver);
			homePage.createNote(NOTE_TITLE, NOTE_DESCRIPTION);

			resultPage = new ResultPage(driver);
			resultPage.navigateToHomePage();
			Thread.sleep(2000);
			homePage.editNote(NOTE_TITLE_EDITED, NOTE_DESCRIPTION_EDITED);
			Thread.sleep(2000);

			assertEquals("Successfully updated note.", resultPage.getSuccessMessage());
			resultPage.navigateToHomePage();
			Thread.sleep(2000);
			homePage.navigateToNotes();
			Thread.sleep(2000);

			assertEquals(NOTE_TITLE_EDITED, homePage.getNoteTitleText());
			assertEquals(NOTE_DESCRIPTION_EDITED, homePage.getNoteDescriptionText());

		} catch (InterruptedException e) {}
	}

	@Test
	public void testDeleteNote() {
		try {
			driver.get(baseURL + "/signup");
			signupPage = new SignupPage(driver);
			signupPage.signup(FIRST_NAME, LAST_NAME, USERNAME, PASSWORD);
			signupPage.navigateToLogin();

			loginPage = new LoginPage(driver);
			loginPage.login(USERNAME, PASSWORD);

			homePage = new HomePage(driver);
			homePage.createNote(NOTE_TITLE, NOTE_DESCRIPTION);

			resultPage = new ResultPage(driver);
			resultPage.navigateToHomePage();
			Thread.sleep(2000);

			homePage.deleteNote();

			assertEquals("Successfully deleted note: " + NOTE_TITLE, resultPage.getSuccessMessage());
			Thread.sleep(2000);
			resultPage.navigateToHomePage();
			homePage = new HomePage(driver);
			Thread.sleep(2000);
			homePage.navigateToNotes();
			Thread.sleep(2000);

			assertThrows(NoSuchElementException.class, () -> homePage.getNoteTitleText());
			assertThrows(NoSuchElementException.class, () -> homePage.getNoteDescriptionText());

		} catch (InterruptedException e) {}
	}

	@Test
	public void testAddCredential() {
		try {
			driver.get(baseURL + "/signup");
			signupPage = new SignupPage(driver);
			signupPage.signup(FIRST_NAME, LAST_NAME, USERNAME, PASSWORD);
			signupPage.navigateToLogin();

			loginPage = new LoginPage(driver);
			loginPage.login(USERNAME, PASSWORD);

			homePage = new HomePage(driver);
			homePage.addCredential(URL, CRED_USERNAME, CRED_PASSWORD);

			resultPage = new ResultPage(driver);
			Thread.sleep(2000);
			assertEquals("Successfully added credential.", resultPage.getSuccessMessage());
			resultPage.navigateToHomePage();
			Thread.sleep(2000);
			homePage.navigateToCredentials();
			Thread.sleep(2000);

			assertEquals(URL, homePage.getCredentialUrlText());
			assertEquals(CRED_USERNAME, homePage.getCredentialUsernameText());
			assertFalse(homePage.getCredentialPasswordText().isEmpty());
			assertNotEquals(CRED_PASSWORD, homePage.getCredentialPasswordText());

		} catch (InterruptedException e) {}
	}

	@Test
	public void testViewAndEditCredential() {
		try {
			driver.get(baseURL + "/signup");
			signupPage = new SignupPage(driver);
			signupPage.signup(FIRST_NAME, LAST_NAME, USERNAME, PASSWORD);
			signupPage.navigateToLogin();

			loginPage = new LoginPage(driver);
			loginPage.login(USERNAME, PASSWORD);

			homePage = new HomePage(driver);
			homePage.addCredential(URL, CRED_USERNAME, CRED_PASSWORD);

			resultPage = new ResultPage(driver);
			resultPage.navigateToHomePage();
			Thread.sleep(2000);

			homePage.viewEditCredentialModal();
			assertEquals(CRED_PASSWORD, homePage.getCredentialPasswordInputText());
			homePage.editCredential(URL_EDITED, CRED_USERNAME_EDITED, CRED_PASSWORD_EDITED);
			Thread.sleep(2000);

			assertEquals("Successfully updated credential.", resultPage.getSuccessMessage());
			resultPage.navigateToHomePage();
			Thread.sleep(2000);

			homePage.navigateToCredentials();
			Thread.sleep(2000);

			assertEquals(URL_EDITED, homePage.getCredentialUrlText());
			assertEquals(CRED_USERNAME_EDITED, homePage.getCredentialUsernameText());

		} catch (InterruptedException e) {}
	}

}
