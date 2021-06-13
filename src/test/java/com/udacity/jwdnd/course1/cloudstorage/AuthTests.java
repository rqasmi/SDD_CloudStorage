package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthTests {

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
}
