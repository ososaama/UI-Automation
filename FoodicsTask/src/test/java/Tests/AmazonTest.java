package Tests;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import Pages.Home;
import Pages.Login;
import Pages.VideoGames;

public class AmazonTest {
	
	WebDriver driver;
	Login login;
	Home home;
	VideoGames vidgames;
	
	@BeforeMethod
	public void setup()
	{
		System.setProperty("webdriver.chrome.driver", "C:/Users/Osama Rabie/eclipse-workspace/FoodicsTask/drivers/chromedriver.exe");
		driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.amazon.eg/");
        
        login = new Login(driver);
        home = new Home(driver);
        vidgames = new VideoGames(driver);
	}
	
	@Test
	public void testvidGamesPurchase()
	{
		login.login("email", "password"); //enter your email & password
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		
		home.openAllMenu();
		
		vidgames.applyFilters();
		vidgames.sortByHighToLow();
		vidgames.addProductsUnder15k();
	}
	
	 @AfterMethod
	   public void teardown() {
	        driver.quit();
	    }

}
