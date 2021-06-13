package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	private static final String FIRST_NAME = "Ragheed";
	private static final String LAST_NAME = "Qasmieh";
	private static String USERNAME = "ragheed";
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
		Random random = new Random();

		// generate a 3 digit random number
		USERNAME += random.nextInt(900) + 100 + "";

		driver.get(baseURL + "/signup");
		signupPage = new SignupPage(driver);
		signupPage.signup(FIRST_NAME, LAST_NAME, USERNAME, PASSWORD);
		
		loginPage = new LoginPage(driver);
		loginPage.login(USERNAME, PASSWORD);

		homePage = new HomePage(driver);
	}

	@AfterEach
	public void afterEach() {
		USERNAME = "ragheed";
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	@Order(1)
	public void testSignupAndLogin(){
		assertEquals(baseURL + "/home", driver.getCurrentUrl());
		assertNotNull(homePage.getLogoutBtn());
		assertNotNull(homePage.getNavCredentialsTab());
		assertNotNull(homePage.getNavFilesTab());
		assertNotNull(homePage.getNavNotesTab());

		homePage.logout();
		assertNotEquals(baseURL + "/home", driver.getCurrentUrl());
	}

	@Test
	@Order(2)
	public void testCreateNote() {
		try {
			homePage.createNote(NOTE_TITLE, NOTE_DESCRIPTION);

			resultPage = new ResultPage(driver);
			Thread.sleep(1500);
			assertEquals("Successfully posted note.", resultPage.getSuccessMessage());
			resultPage.navigateToHomePage();
			Thread.sleep(1500);
			homePage.navigateToNotes();
			Thread.sleep(1500);

			assertEquals(NOTE_TITLE, homePage.getNoteTitleText());
			assertEquals(NOTE_DESCRIPTION, homePage.getNoteDescriptionText());

		} catch (InterruptedException e) {}
	}

	@Test
	@Order(3)
	public void testEditNote() {
		try {
			homePage.createNote(NOTE_TITLE, NOTE_DESCRIPTION);

			resultPage = new ResultPage(driver);
			resultPage.navigateToHomePage();
			Thread.sleep(1500);
			homePage.editNote(NOTE_TITLE_EDITED, NOTE_DESCRIPTION_EDITED);
			Thread.sleep(1500);

			assertEquals("Successfully updated note.", resultPage.getSuccessMessage());
			resultPage.navigateToHomePage();
			Thread.sleep(1500);
			homePage.navigateToNotes();
			Thread.sleep(1500);

			assertEquals(NOTE_TITLE_EDITED, homePage.getNoteTitleText());
			assertEquals(NOTE_DESCRIPTION_EDITED, homePage.getNoteDescriptionText());

		} catch (InterruptedException e) {}
	}

	@Test
	@Order(4)
	public void testDeleteNote() {
		try {
			homePage.createNote(NOTE_TITLE, NOTE_DESCRIPTION);

			resultPage = new ResultPage(driver);
			resultPage.navigateToHomePage();
			Thread.sleep(1500);

			homePage.deleteNote();

			assertEquals("Successfully deleted note: " + NOTE_TITLE, resultPage.getSuccessMessage());
			Thread.sleep(1500);
			resultPage.navigateToHomePage();
			homePage = new HomePage(driver);
			Thread.sleep(1500);

			homePage.navigateToNotes();
			Thread.sleep(1500);

			assertThrows(NoSuchElementException.class, () -> homePage.getNoteTitleText());
			assertThrows(NoSuchElementException.class, () -> homePage.getNoteDescriptionText());

		} catch (InterruptedException e) {}
	}

	@Test
	@Order(5)
	public void testAddCredential() {
		try {
			homePage.addCredential(URL, CRED_USERNAME, CRED_PASSWORD);

			resultPage = new ResultPage(driver);
			Thread.sleep(1500);
			assertEquals("Successfully added credential.", resultPage.getSuccessMessage());
			resultPage.navigateToHomePage();
			Thread.sleep(1500);
			homePage.navigateToCredentials();
			Thread.sleep(1500);

			assertEquals(URL, homePage.getCredentialUrlText());
			assertEquals(CRED_USERNAME, homePage.getCredentialUsernameText());
			assertFalse(homePage.getCredentialPasswordText().isEmpty());
			assertNotEquals(CRED_PASSWORD, homePage.getCredentialPasswordText());

		} catch (InterruptedException e) {}
	}

	@Test
	@Order(6)
	public void testViewAndEditCredential() {
		try {
			homePage.addCredential(URL, CRED_USERNAME, CRED_PASSWORD);

			resultPage = new ResultPage(driver);
			resultPage.navigateToHomePage();
			Thread.sleep(1500);

			homePage.viewEditCredentialModal();
			assertEquals(CRED_PASSWORD, homePage.getCredentialPasswordInputText());
			homePage.editCredential(URL_EDITED, CRED_USERNAME_EDITED, CRED_PASSWORD_EDITED);
			Thread.sleep(1500);

			assertEquals("Successfully updated credential.", resultPage.getSuccessMessage());
			resultPage.navigateToHomePage();
			Thread.sleep(1500);

			homePage.navigateToCredentials();
			Thread.sleep(1500);

			assertEquals(URL_EDITED, homePage.getCredentialUrlText());
			assertEquals(CRED_USERNAME_EDITED, homePage.getCredentialUsernameText());

		} catch (InterruptedException e) {}
	}

	@Test
	@Order(7)
	public void testDeleteCredential() {
		try {
			homePage.addCredential(URL, CRED_USERNAME, CRED_PASSWORD);

			resultPage = new ResultPage(driver);
			resultPage.navigateToHomePage();
			Thread.sleep(1500);

			homePage.deleteCredential();

			assertEquals("Successfully deleted credential for " + URL + " and username " + CRED_USERNAME, resultPage.getSuccessMessage());
			Thread.sleep(1500);
			resultPage.navigateToHomePage();
			homePage = new HomePage(driver);
			Thread.sleep(1500);

			homePage.navigateToNotes();
			Thread.sleep(1500);

			assertThrows(NoSuchElementException.class, () -> homePage.getCredentialUrlText());
			assertThrows(NoSuchElementException.class, () -> homePage.getCredentialUsernameText());

		} catch (InterruptedException e) {}
	}

}
