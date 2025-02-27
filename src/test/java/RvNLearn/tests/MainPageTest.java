package RvNLearn.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import RvNLearn.pageObjects.CheckOut;
import RvNLearn.pageObjects.ConfirmationPage;
import RvNLearn.pageObjects.OrderHistoryPage;
import RvNLearn.pageObjects.ShopFront;
import RvNLearn.pageObjects.ShoppingCartPage;
import RvNLearn.testComponents.BaseTest;
import RvNLearn.testComponents.RetryTest;

public class MainPageTest extends BaseTest {

	String orderNumber;
	//String jsonFilePath = System.getProperty("user.dir") + "\\src\\test\\java\\RvNLearn\\data\\purchaseOrder.json";

	// 2025/01/29 public static void main(String[] args) throws InterruptedException
	// {
	@Test(dataProvider = "getData", groups = { "Purchase" })
	public void customerPurchase(HashMap<String, String> testVars) throws IOException {
		
		WebElement paymentForm;
		String shoppingItem = testVars.get("item");
		System.out.println(testVars.get("item"));

		// 2025/01/29 - LandingPage landing = new LandingPage(driver);
		// 2025/01/30 - Remove LandingPage landing = launchApplication(); now handled by
		// BaseTest.@BeforeTest
		// LandingPage landing = launchApplication();

		/*
		 * LOGIN
		 */
		ShopFront store = landing.loginApplication(testVars.get("email"), testVars.get("pwd"));

		/*
		 * SELECT Product(/s)
		 */
		store.addItemToCart(shoppingItem);

		// Wait for the cart icon to update
		store.waitForIcon();
		store.waitForBackground();

		/*
		 * Goto CART
		 */
		// Click on the cart icon
		ShoppingCartPage cart = store.goToShoppingCart();

		Assert.assertTrue(cart.getCartPageHeader().equalsIgnoreCase("My Cart"));

		boolean match = cart.getCartItems().stream().anyMatch(item -> item.getText().equalsIgnoreCase(shoppingItem));

		Assert.assertTrue(match);

		// Checkout
		CheckOut checkout = cart.gotoCheckout();

		// checkout.waitForElement(checkout.payTypes);

		boolean checkoutMatch = checkout.getCheckoutList().stream()
				.anyMatch(paym -> paym.getText().equalsIgnoreCase(shoppingItem));

		// Confirm the Shopping Cart itemt
		Assert.assertTrue(checkoutMatch);

		// Select payment method
		for (WebElement payment : checkout.getPaymentTypes()) {
			if (payment.getText().equalsIgnoreCase(testVars.get("paymentType"))) {
				payment.click();
			}
		}

		paymentForm = checkout.getCCForm();

		checkout.cCardForm(paymentForm, testVars.get("cvv"), testVars.get("cardName"));

		// Check that the email is correct
		Assert.assertEquals(checkout.getEmail(), testVars.get("email"));

		checkout.setCountry(testVars.get("cntry"), testVars.get("country"));

		// Submit the purchase
		ConfirmationPage confirmationPage = checkout.submitOrder();

		// Return the order number for further validation
		orderNumber = confirmationPage.getOrderNumber();

		// Confirm order was successful
		Assert.assertEquals(confirmationPage.getCheckoutSuccess(), testVars.get("success"));

		// 2025/01/30 removed driver.close to BaseTest()
		// driver.close();

	}

	// Confirm that the order was placed and now in the 'My Orders' tab
	@Test(dependsOnMethods = { "customerPurchase" },
			dataProvider = "getData",
			groups = { "Purchase" },
			retryAnalyzer = RetryTest.class)
	public void orderHistoryTest(HashMap<String, String> testVars) {

		ShopFront store = landing.loginApplication(testVars.get("email"), testVars.get("pwd"));
		OrderHistoryPage history = store.gotoOrderHistoryPage();
		Assert.assertTrue(history.verifyOrderHistory(testVars.get("item")));
		Assert.assertTrue(history.verifyOrderNumberHistory(orderNumber));
	}
	
	/*
	 * public String getScreenshot(String testCaseName) throws IOException {
	 * 
	 * TakesScreenshot ts = (TakesScreenshot) driver; File source =
	 * ts.getScreenshotAs(OutputType.FILE);
	 * 
	 * File file = new
	 * File(System.getProperty("user.dir")+"\\reports\\" + testCaseName + ".png");
	 * FileUtils.copyFile(source, file);
	 * 
	 * return System.getProperty("user.dir")+"\\reports\\" + testCaseName + ".png";
	 * }
	 */

	@DataProvider
	public Object[][] getData() throws IOException {

		List<HashMap<String, String>> data = getJsonToMap(System.getProperty("user.dir")
				+ "\\src\\test\\java\\RvNLearn\\data\\PurchaseOrder.json");
		return new Object[][] { { data.get(0) }, { data.get(1) } };
	}

	/*
	 * alternate dataProvider
	 * @DataProvider public Object[][] getData() {
	 * 
	 * HashMap<String, String> map = new HashMap<String, String>(); map.put("email",
	 * "me@here.com"); map.put("pwd", "sLW_U7jz'4vBPGv"); map.put("item", "QWERTY");
	 * map.put("paymentType", "Credit Card"); map.put("cvv", "123");
	 * map.put("cardName", "Joe  Soap"); map.put("success",
	 * "THANKYOU FOR THE ORDER."); map.put("cntry", "sou"); map.put("country",
	 * "south africa");
	 * 
	 * Object[][] testVars = new Object[][] { { map } }; return testVars;
	 * 
	 * }
	 */

}
