package br.com.automation4j.config;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.Scenario;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

/**
 * @author Caio Cobacho
 */
public class Settings {

	public static AndroidDriver<WebElement> driver;
	public static AppiumDriver<WebElement> _driver;
	public WebDriverWait wait;
	public JavascriptExecutor executor;

	public String getScenario(Scenario name) {
		String cenario = name.getName();
		return cenario;

	}

	public void setUp() {

		System.out.println("Your session its being created");
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability("platformName", "ANDROID");
		cap.setCapability("deviceName", "POCOPHONE");
		cap.setCapability("app", System.getProperty("user.dir") + "/app/<PUT_YOUR_APK>");
		cap.setCapability("noReset", "true");
		cap.setCapability("automationName", "uiautomator2");
		cap.setCapability("autoWebview", "true");
		try {
			driver = new AndroidDriver<WebElement>(new URL("http://0.0.0.0:4723/wd/hub"), cap);
			Set<String> contextNames = driver.getContextHandles();
			for (String contextName : contextNames) {
				System.out.println(contextName);
				if (contextName.contains("WEBVIEW")) {
					driver.context(contextName);
				} else if (contextName.contains("NATIVE_APP")) {
					driver.context(contextName);

				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		System.out.println("Your session has been created, have fun :D");
	}

	public void fillField(WebElement field, String value) {
		field.clear();
		field.sendKeys(value);
	}

	public void eraseField(WebElement field) {
		field.clear();
	}

	public void click(WebElement element) {
		try {
			element.click();
		} catch (Exception e) {
			try {
				executor = driver;
				executor.executeScript("arguments[0].click();", element);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	public void selectByText(WebElement select, String text) {
		new org.openqa.selenium.support.ui.Select(select).selectByVisibleText(text);
	}

	public WebElement grabID(WebDriver driver, String id, Boolean hold) {
		if (hold == false)
			return driver.findElement(By.id(id));
		else {
			wait = new WebDriverWait(driver, 10);
			return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
		}
	}

	public WebElement searchByClass(WebDriver driver, String classe, Boolean hold) {
		if (hold == false)
			return driver.findElement(By.className(classe));
		else {
			wait = new WebDriverWait(driver, 10);
			return wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(classe)));
		}
	}

	public WebElement searchByName(WebDriver driver, String name, Boolean hold) {
		if (hold == false)
			return driver.findElement(By.name(name));
		else {
			wait = new WebDriverWait(driver, 10);
			return wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(name)));
		}
	}

	public WebElement grabXPATH(WebDriver driver, String xpath, Boolean hold) {
		if (hold == false)
			return driver.findElement(By.xpath(xpath));
		else {
			wait = new WebDriverWait(driver, 20);
			return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		}
	}

	public boolean elementVisible(WebElement element) {
		return element.isDisplayed();
	}

	public void waitUntilShows(WebElement element) {
		while (!elementVisible(element)) {
		}
	}

	public String grabText(WebElement element) {

		String text = element.getText();
		return text;

	}

	public static String captureScreenShot(AndroidDriver<WebElement> driver) throws IOException {
		TakesScreenshot screen = driver;
		File src = screen.getScreenshotAs(OutputType.FILE);
		String dest = System.getProperty("user.dir") + "/screenshots/" + getcurrentdateandtime() + ".png";
		File target = new File(dest);
		FileUtils.copyFile(src, target);
		return dest;
	}

	// time pattern according to Brazil, because I'm Brazilian, change according to
	// your region :D
	private static String getcurrentdateandtime() {
		String str = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SSS");
			Date date = new Date();
			str = dateFormat.format(date);
			str = str.replace(" ", "").replaceAll("/", "").replaceAll(":", "");
		} catch (Exception e) {
		}
		return str;
	}

}
