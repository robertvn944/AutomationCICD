package RvNLearn.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import RvNLearn.utilityObjects.UtilityObjects;

public class ShoppingCartPage extends UtilityObjects {
	
	WebDriver driver;
	
	public ShoppingCartPage(WebDriver driver) {
		
		super(driver);
		this.driver = driver;
	}
	
	By gotoCheckout = By.cssSelector(".totalRow button");
	By cartItems = By.cssSelector(".cartSection h3");
	//By goToCart = By.cssSelector("[routerlink*='cart']");
	By cartPageHeader = By.cssSelector(".heading h1");
	By pageHeader = By.cssSelector(".heading h1");
	By orderItems = By.cssSelector(".cartSection h3");
	
	public String getCartPageHeader() {
		
		String actualPageHeader = driver.findElement(pageHeader).getText();
		return actualPageHeader;
	}
	
	public List<WebElement> getCartItems() {
		
		List<WebElement> customerItemsOrdered = driver.findElements(orderItems);
		return customerItemsOrdered;
	}
	
	public CheckOut gotoCheckout() {
		
		driver.findElement(gotoCheckout).click();
		CheckOut checkout = new CheckOut(driver);
		return checkout;
	}

}
