package RvNLearn.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import RvNLearn.utilityObjects.UtilityObjects;

public class LandingPage extends UtilityObjects {
	
	WebDriver driver;
	
	public LandingPage(WebDriver driver){
		
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="userEmail")
	WebElement userEmail;
	
	@FindBy(id="userPassword")
	WebElement userPassword;
	
	@FindBy(id="login")
	WebElement submit;
	
	@FindBy(css="[class*='flyInOut']")
	WebElement errorMessage;
	
	public ShopFront loginApplication(String email, String pwd) {
		
		userEmail.sendKeys(email);
		userPassword.sendKeys(pwd);
		submit.click();
		ShopFront store = new ShopFront(driver);
		return store;
	}
	
	public String getLoginErrorMessage() {
		
		waitForWebElement(errorMessage);
		return errorMessage.getText();
	}
	
	public void goToPage(String url) {

		driver.get(url);
	}

}
