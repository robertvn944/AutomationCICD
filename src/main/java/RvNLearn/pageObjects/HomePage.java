package RvNLearn.pageObjects;

import org.openqa.selenium.WebDriver;

import RvNLearn.utilityObjects.UtilityObjects;

public class HomePage extends UtilityObjects {
	
	WebDriver driver;
	
	public HomePage(WebDriver driver) {
		
		super(driver);
		this.driver = driver;
	}

}
