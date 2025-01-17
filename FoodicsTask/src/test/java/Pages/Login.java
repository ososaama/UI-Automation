package Pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import Utilities.BasePage;

public class Login extends BasePage {
	
	private By EmailField = By.id("ap_email");
	private By continueButton = By.id("continue");
	private By PasswordField = By.id("ap_password");
	private By signInButton = By.id("signInSubmit");
	
	public Login(WebDriver driver)
	{
		super(driver);
	}
	
	public void login(String email, String password)
	{
		driver.findElement(By.cssSelector("div[id='nav-signin-tooltip'] span[class='nav-action-inner']")).click();
		driver.findElement(EmailField).sendKeys(email);
		driver.findElement(continueButton).click();
		driver.findElement(PasswordField).sendKeys(password);
		driver.findElement(signInButton).click();
	}
}
