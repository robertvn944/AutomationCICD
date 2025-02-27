package RvNLearn.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import RvNLearn.utilityObjects.UtilityObjects;

public class CheckOut extends UtilityObjects {
	
	WebDriver driver;
	
	public CheckOut(WebDriver driver) {
		
		super(driver);
		this.driver = driver;
	}
	
	private By checkedOutItems = By.cssSelector(".item__title");
	private By payTypes = By.cssSelector(".payment__types .payment__type");
	private By ccForm = By.cssSelector("div.payment__cc div.form__cc");
	private By cvvField = By.cssSelector("div.row:nth-child(2) input");
	private By cardName = By.cssSelector("div.row:nth-child(3) div.field input");
	private By userCheckoutMail = By.cssSelector("div.details__user div.user__name label");
	private By countryID = By.cssSelector("input[placeholder='Select Country']");
	private By countryOptions = By.cssSelector("button.ta-item");
	private By submitButton = By.cssSelector("div.actions a.action__submit");
	@FindBy(css="a.action__submit")
	private WebElement submitBtn;
	
	public List<WebElement> getCheckoutList(){
		
		List<WebElement> checkoutItems = driver.findElements(checkedOutItems);
		return checkoutItems;
	}
	
	public List<WebElement> getPaymentTypes(){
		
		List<WebElement> paymentTypes = driver.findElements(payTypes);
		return paymentTypes;
	}
	
	public WebElement getCCForm() {
		
		return driver.findElement(ccForm);
	}
	
	public void cCardForm(WebElement editCCForm, String cvv, String nameOnCard) {
		
		editCCForm.findElement(cvvField).sendKeys(cvv);
		editCCForm.findElement(cardName).sendKeys(nameOnCard);
	}
	
	public String getEmail() {
		
		String checkEmail = driver.findElement(userCheckoutMail).getText();
		return checkEmail;
	}
	
	public void setCountry(String cntry, String country) {
		
		driver.findElement(countryID).sendKeys(cntry);
		
		// Wait for the Ajax list to refresh with matching results
		//wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.ta-item")));
		
		// Create a list of the returned results
		List<WebElement> options = driver.findElements(countryOptions);
		
		// Iterate through the list to find the country name and select it and exit from the loop
		for(WebElement option:options) {
			if (option.getText().equalsIgnoreCase(country)) {
				option.click();
				break;
			}
		}
	}
	
	public ConfirmationPage submitOrder() {
		
		driver.findElement(submitButton).click();
		ConfirmationPage confirmationPage = new ConfirmationPage(driver);
		return confirmationPage;
	}
}
