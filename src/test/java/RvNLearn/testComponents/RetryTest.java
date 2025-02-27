package RvNLearn.testComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryTest implements IRetryAnalyzer {

	int count = 0;
	int maxTry = 1;
	
	@Override
	public boolean retry(ITestResult result) {

		System.out.println(count);
		if(count < maxTry) {
			
			count++;
			return true;
		}
		return false;
	}

}
