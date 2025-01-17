package Utilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class BasePage {
	
	public WebDriver driver;
	public WebDriverWait wait;
	private static final Duration TIMEOUT = Duration.ofSeconds(10);
	
	public BasePage(WebDriver driver)
	{
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	protected void clickElement(WebElement element)
	{
		//wait.until(ExpectedConditions.elementToBeClickable(element)).click();
		 try {
		        wait.until(ExpectedConditions.elementToBeClickable(element));
		        element.click();
		    } catch (Exception e) {
		        // Use JavaScript to click as a fallback
		        JavascriptExecutor js = (JavascriptExecutor) driver;
		        js.executeScript("arguments[0].click();", element);
		    }
	}
	
	public void enterText(WebElement element, String text)
	{
		wait.until(ExpectedConditions.visibilityOf(element)).sendKeys(text);
	}
	
	public String getText(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element)).getText();
    }
	
	public void scrollToElement(WebElement element) {
	    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	protected boolean isElementPresent(WebElement element) {
        try {
            new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
	
    protected void waitForPageToLoad() {
        new WebDriverWait(driver, TIMEOUT).until(
            webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete")
        );
    }

}
