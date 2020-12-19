package scenarios;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.MalformedURLException;
import java.net.URL;

public class Flipkart {

	static WebDriver driver;

	public static final String USERNAME = "nikhilkakade1";
	public static final String AUTOMATE_KEY = "4ctp4oJPzT2oSox5DVjF";
	public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

	// Launch Browser
	@BeforeSuite
	public void launchAndNavigateToFlipkart() throws MalformedURLException {

		DesiredCapabilities caps = new DesiredCapabilities();

		caps.setCapability("os", "Windows");
		caps.setCapability("os_version", "10");
		caps.setCapability("browser", "Chrome");
		caps.setCapability("browser_version", "80");

		caps.setCapability("name", "nikhilkakade1's First Test");

		driver = new RemoteWebDriver(new URL(URL), caps);

		// navigate to flipkart
		driver.navigate().to("https://www.flipkart.com/");
	}

	@Test(priority = 0)
	public void handleLoginPoup() throws InterruptedException {

		driver.manage().timeouts().implicitlyWait(7000, TimeUnit.SECONDS);

		// handle login popup
		By loginPopupCloseButton = By.xpath("(//span[text()='Login']/ancestor::div/button)[1]");
		if (driver.findElement(loginPopupCloseButton).isDisplayed()) {
			driver.findElement(loginPopupCloseButton).click();
		}

		// search for the product
		searchProduct();

		// set the filters
		selectFilters();

		// sort the entries
		sortEntries();
	}

	@Test(priority = 1)
	public void fetchResults() throws InterruptedException {

		// print the mobile names
		printData(By.xpath("//div[@data-id]//a/div[2]/div[1]/div[1]"), false);
		Thread.sleep(2000);

		// print the price of mobiles
		printData(By.xpath("//div[@data-id]//a/div[2]/div[2]/div[1]//div[contains(text(),',')]"), false);

		// print the link for the mobile
		printData(By.xpath("//div[@data-id]//a"), true);

	}

	public static void printData(By locator, boolean getAttr) {

		String text;
		List<WebElement> valueList = driver.findElements(locator);

		for (WebElement value : valueList) {

			if (getAttr == true) {
				text = value.getAttribute("href");
			} else
				text = value.getText();

			System.out.println(text);
		}

	}

	public void searchProduct() {

		WebElement searchProduct = driver.findElement(By.xpath("//input[contains(@title,'Search for products')]"));

		searchProduct.click();
		searchProduct.sendKeys("Samsung Galaxy S10");

		WebElement searchBtn = driver.findElement(By.xpath("//button[@type='submit']"));
		searchBtn.click();
	}

	public void selectFilters() throws InterruptedException {

		WebElement brandFilter = driver.findElement(By.xpath("//div[text()='Samsung']/ancestor::label"));

		brandFilter.click();
		Thread.sleep(3000);

		WebElement fAssuredFilter = driver
				.findElement(By.xpath("//img[contains(@src,'linchpin')]/ancestor::label/div[2]"));

		fAssuredFilter.click();
		Thread.sleep(3000);
	}

	public void sortEntries() throws InterruptedException {

		WebElement sortHighToLow = driver.findElement(By.xpath("//div[text()='Price -- High to Low']"));

		sortHighToLow.click();
		Thread.sleep(3000);
	}
}
