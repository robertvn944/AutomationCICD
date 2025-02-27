package RvNLearn.pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import RvNLearn.utilityObjects.UtilityObjects;

public class OrderHistoryPage extends UtilityObjects {

	WebDriver driver;
	
	@FindBy(css="td:nth-child(3)")
	List<WebElement> orderHistoryDescription;
	@FindBy(css="th:nth-child(1)")
	List<WebElement> orderHistoryNumber;
	@FindBy(css="tbody")
	WebElement orderTable;
	
	public OrderHistoryPage(WebDriver driver) {
		
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public boolean verifyOrderHistory(String shoppingItem) {
		
		waitForWebElement(orderTable);
		boolean match = orderHistoryDescription.stream().anyMatch(product->product.getText().equalsIgnoreCase(shoppingItem));
		return match;
	}
	
	public boolean verifyOrderNumberHistory(String ordNo) {
		
		boolean matchON = orderHistoryNumber.stream().anyMatch(product->product.getText().equalsIgnoreCase(ordNo));
		return matchON;
	}
}
