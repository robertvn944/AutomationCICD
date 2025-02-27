package RvNLearn.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import RvNLearn.utilityObjects.UtilityObjects;

public class ConfirmationPage extends UtilityObjects {

	WebDriver driver;
	
	public ConfirmationPage(WebDriver driver) {
		
		super(driver);
		this.driver = driver;
	}
	
	By successMessage = By.cssSelector("h1.hero-primary");
	By orderNumberCSS = By.cssSelector("label[class='ng-star-inserted']");
	
	public String getCheckoutSuccess() {
		
		String done = driver.findElement(successMessage).getText();
		return done;
	}
	
	public String getOrderNumber() {
		
		waitForElement(orderNumberCSS);
		String[] on = driver.findElement(orderNumberCSS).getText().split(" ");
		String orderNumber = on[1];
		return orderNumber;
	}
}
