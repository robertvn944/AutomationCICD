package RvNLearn.testComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import RvNLearn.pageObjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	public WebDriver driver;
	public Properties props;
	public LandingPage landing;
	
	public WebDriver initialiseDriver() throws IOException {
		
		// Properties class
		props = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
				+"\\src\\main\\"
		 		+ "java\\RvNLearn\\resources\\GlobalData.properties");
		props.load(fis);
		String browserName = System.getProperty("browser") != null
				? System.getProperty("browser")
				: props.getProperty("browser");
		
		//String browserName = props.getProperty("browser");
		
		if(browserName.contains("firefox")) {
			
			FirefoxOptions fo = new FirefoxOptions();
			if(browserName.contains("headless")) {
				fo.addArguments("--headless");
			}
			
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver(fo);
			
			driver.manage().window().setSize(new Dimension(1440, 900));
			
		}else if(browserName.contains("chrome")) {
			
			ChromeOptions co = new ChromeOptions();
			if(browserName.contains("headless")) {
				co.addArguments("headless");
			}
			
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(co);
			
			driver.manage().window().setSize(new Dimension(1440, 900));
			
		}else if(browserName.equalsIgnoreCase("edge")) {
			
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			
		}else if(browserName.equalsIgnoreCase("brave")) {
			
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		return driver;
	}
	
	public WebDriver getDriver() {
		
		return driver;
	}
	
	public List<HashMap<String, String>> getJsonToMap(String filePath) throws IOException {
		
		String jsonFileContent = FileUtils.readFileToString(new File(filePath),
				StandardCharsets.UTF_8);
		
		// String to HashMap util Jackson DataBind
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonFileContent,
				new TypeReference<List<HashMap<String, String>>>(){
			
		});
		return data;
	}
	
	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
		
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		
		File file = new File(System.getProperty("user.dir")+"\\reports\\" + testCaseName + ".png");
		FileUtils.copyFile(source, file);
		
		return System.getProperty("user.dir")+"\\reports\\" + testCaseName + ".png";
	}
	
	@BeforeMethod(alwaysRun=true)
	public LandingPage launchApplication() throws IOException {
		
		driver = initialiseDriver();
		
		landing = new LandingPage(driver);
		landing.goToPage(props.getProperty("url"));
				
		return landing;
	}
	
	@AfterMethod(alwaysRun=true)
	public void tearDown() throws InterruptedException {
				
		// Close the browser
		driver.close();
	}
}
