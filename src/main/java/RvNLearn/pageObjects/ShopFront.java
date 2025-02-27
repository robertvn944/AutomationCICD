package RvNLearn.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import RvNLearn.utilityObjects.UtilityObjects;

public class ShopFront extends UtilityObjects {
	
	WebDriver driver;
	
	public ShopFront(WebDriver driver){
		
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	By goToCart = By.cssSelector("[routerlink*='cart']");
	By productsLocator = By.cssSelector(".ng-star-inserted .card");
	By itemLocator = By.cssSelector(".card-body button:last-of-type");
	By h5 = By.tagName("h5");
	
	@FindBy(css=".ng-star-inserted .card")
	List<WebElement> productCards;
	
	public List<WebElement> getProductList() {
		
		waitForElement(productsLocator);
		return productCards;
	}
	
	public WebElement getItemOfChoice(String itemName){
		
		WebElement product = getProductList().stream()
		    .filter(productCard->productCard.findElement(h5).getText().equals(itemName))
			.findFirst()
			.orElse(null);
		
		return product;
	}
	
	public void addItemToCart(String itemName) {
		
		// Add to cart
		getItemOfChoice(itemName).findElement(itemLocator).click();
	}
	
	public ShoppingCartPage goToShoppingCart() {

		// Click to navigate to 'My Cart'
		driver.findElement(goToCart).click();
		ShoppingCartPage cart = new ShoppingCartPage(driver);
		return cart;
	}
}
