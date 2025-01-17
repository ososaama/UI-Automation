package Pages;

//import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import Utilities.BasePage;

public class Home extends BasePage {

	@FindBy(id = "nav-hamburger-menu")
	private WebElement AllMenuBtn;
	
	@FindBy(xpath = "//a[contains(@class, 'hmenu-item') and contains(@class, 'hmenu-compressed-btn')]")
	private WebElement allCategoriesBtn;
	
	@FindBy(linkText = "Video Games")
    private WebElement videoGamesLink;
	
	@FindBy(linkText = "All Video Games")
    private WebElement allvideoGamesLink;
	
	
	public Home(WebDriver driver)
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void openAllMenu()
	{
		clickElement(AllMenuBtn);
		clickElement(allCategoriesBtn);
		clickElement(videoGamesLink);
		clickElement(allvideoGamesLink);
	}
}
