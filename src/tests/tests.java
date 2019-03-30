package tests;

import db.DB;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import model.Handler;
import model.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.assertEquals;

public class tests {
    private static final String url = "http://localhost:8080/";
    private WebDriver driver;
    private Handler handler;
    private User user;
    @Before
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "/home/karel/Downloads/geckodriver-v0.24.0-linux64/geckodriver");
        driver = new FirefoxDriver();
        handler = new Handler();
        user = new User(this.handler,"test" );
    }

    @Test
    public void test_succesful_log_gives_register_page(){
        driver.get(url+"get?r=index&loginId="+user.getId());
        String antwoord = driver.findElement(By.className("lead")).getText();
        assertEquals("Your ID is: "+user.getId(),antwoord);
    }

    //@After
    public void cleanUp(){
        driver.quit();
    }
}
