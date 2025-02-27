package RvNLearn.utilityObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import RvNLearn.pageObjects.HomePage;
import RvNLearn.pageObjects.OrderHistoryPage;
import RvNLearn.pageObjects.ShoppingCartPage;

public class UtilityObjects {
	
	WebDriver driver;
	
	public UtilityObjects(WebDriver driver) {
		
		this.driver = driver;
	}
	
	By cartIcon = By.cssSelector(".ng-star-inserted .card");
	By background = By.cssSelector(".ng-animating");
	
	@FindBy(css="[routerlink='/dashboard/']")
	WebElement myHomeMenuItem;	
	@FindBy(css="[routerlink='/dashboard/myorders']")
	WebElement myOrdersMenuItem;
	@FindBy(css="[routerlink='/dashboard/cart']")
	WebElement myCartMenuItem;	
	@FindBy(css="li:last-of-type")
	WebElement signOutItem;
	
	
	
	public void waitForElement(By findBy) {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}
	
	public void waitForIcon(){
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(cartIcon));
	}
	
	public void waitForBackground() {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(background)));
	}
	
	public void waitForWebElement(WebElement findBy) {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(findBy));
	}
	
	/*
	 * 
	 * Menu links
	 * 
	 */
	
	public void gotoHomePage() {
		
		myHomeMenuItem.click();
		HomePage hp = new HomePage(driver);
	}
	
	public OrderHistoryPage gotoOrderHistoryPage() {
		
		myOrdersMenuItem.click();
		OrderHistoryPage ohp = new OrderHistoryPage(driver);
		return ohp;
	}
	
	public ShoppingCartPage gotoCartPage() {
		
		myCartMenuItem.click();
		ShoppingCartPage ohp = new ShoppingCartPage(driver);
		return ohp;
	}

	public void gotoSignOut() {
	
		signOutItem.click();
	}
}
