package Pages;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utilities.BasePage;

public class VideoGames extends BasePage {
	
	 @FindBy(xpath = "//label[@for='apb-browse-refinements-checkbox_0']//i[@class='a-icon a-icon-checkbox']")
	 private WebElement freeShippingCheckbox;
	 
	 @FindBy(xpath = "//span[@class='a-size-base a-color-base'][normalize-space()='New']")
	 private WebElement conditionNewFilter;
	
	 @FindBy(id = "s-result-sort-select")
	 private WebElement sortDropdown;
	 
	 @FindBy(xpath = "//option[@value='price-desc-rank']")
	 private WebElement highToLowOption;
	 
	 @FindBy(xpath = "//span[@class='a-price-whole']")
	 private List<WebElement> productPrices;

	 @FindBy(xpath = "//input[@aria-labelledby='add-to-cart-button-announce']")
	 private List<WebElement> addToCartButtons;

	 @FindBy(xpath = "//a[contains(@class, 's-pagination-next')]")
	 private WebElement nextPageButton;

	 @FindBy(xpath = "//div[@data-component-type='s-search-result']")
	 private List<WebElement> productContainers;

	 @FindBy(xpath = "//h2[contains(text(),'Results')]")
	 private WebElement resultsHeading;
	 
   
    public VideoGames(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    public void applyFilters() {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	
        WebElement freeShipping = wait.until(ExpectedConditions.visibilityOf(freeShippingCheckbox));
    	scrollToElement(freeShipping);
        clickElement(freeShipping);
        
        WebElement newCondition = wait.until(ExpectedConditions.visibilityOf(conditionNewFilter));
        scrollToElement(newCondition);
        clickElement(newCondition);
    }
    
    public void sortByHighToLow() {
    	//clickElement(sortDropdown);
    	
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Step 1: Click the overlapping element (dropdown prompt)
        WebElement dropdownPrompt = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//span[@class='a-dropdown-prompt']")
        ));
        dropdownPrompt.click();

        // Step 2: Wait for the "High to Low" option to be visible and clickable
        wait.until(ExpectedConditions.elementToBeClickable(highToLowOption)).click();
    	 System.out.println("Dropdown clicked");
    	 System.out.println("High to Low option clicked");
    }
    
  
    public void addProductsUnder15k() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        while (true) {
            try {
                // Scroll down the page incrementally to load all products
                long lastHeight = (long) js.executeScript("return document.body.scrollHeight");
                boolean isScrollingComplete = false;

                while (!isScrollingComplete) {
                    js.executeScript("window.scrollBy(0, 1000);");
                    Thread.sleep(1000); // Wait for the page to load
                    long newHeight = (long) js.executeScript("return document.body.scrollHeight");
                    if (newHeight == lastHeight) {
                        isScrollingComplete = true;
                    }
                    lastHeight = newHeight;
                }

                // Find all product containers
                List<WebElement> products = driver.findElements(By.xpath("//div[@data-component-type='s-search-result']"));
                int totalProducts = products.size();
                System.out.println("Total products found: " + totalProducts);

                for (int i = 0; i < totalProducts; i++) {
                    try {
                        // Re-locate the products list in each iteration to avoid stale elements
                        products = driver.findElements(By.xpath("//div[@data-component-type='s-search-result']"));
                        WebElement product = products.get(i);

                        // Wait for the product to be visible
                        wait.until(ExpectedConditions.visibilityOf(product));

                        // Find the price element inside the product card
                        List<WebElement> priceElements = product.findElements(By.xpath(".//span[@class='a-price-whole']"));
                        if (priceElements.isEmpty()) {
                            System.out.println("Price not found for product " + (i + 1));
                            continue;
                        }

                        // Extract and parse the price
                        String priceText = priceElements.get(0).getText().replace(",", "").trim();
                        int price = Integer.parseInt(priceText);

                        // Check if the price is below 15k
                        if (price < 15000) {
                            System.out.println("Product " + (i + 1) + " is under 15k: EGP " + price);

                            // Find the "Add to Cart" button inside the specific product container
                            List<WebElement> addToCartButtons = product.findElements(By.name("submit.addToCart"));
                            if (!addToCartButtons.isEmpty()) {
                                // Click the "Add to Cart" button for this specific product
                                WebElement addToCartButton = addToCartButtons.get(0);
                                wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
                                System.out.println("Clicked 'Add to Cart' for product " + (i + 1));
                                Thread.sleep(2000); // Wait for the cart to update
                            } else {
                                System.out.println("'Add to Cart' button not found for product " + (i + 1));
                            }
                        } else {
                            System.out.println("Product " + (i + 1) + " is over 15k: EGP " + price);
                        }
                    } catch (NoSuchElementException e) {
                        System.out.println("Error locating element for product: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Unexpected error: " + e.getMessage());
                    }
                }

                // Check if the "Next" button exists and navigate to the next page
                try {
                    WebElement nextPageButton = driver.findElement(By.xpath("//a[normalize-space()='Next']"));
                    wait.until(ExpectedConditions.elementToBeClickable(nextPageButton)).click();
                    System.out.println("Navigating to the next page...");
                    Thread.sleep(3000); // Wait for the next page to load
                } catch (NoSuchElementException e) {
                    System.out.println("Next page button not found. No more pages to navigate.");
                    break; // Exit the loop if no "Next" button is present
                }
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
                break;
            }
        }
    }
}


