package com.dfe.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.AppiumDriver;
import io.cucumber.core.api.Scenario;

public class Driver {

	public static AppiumDriver driver=null;
	public  SessionId session=null;
	public static Properties prop = new Properties();
	WebDriverWait wait;

	public Driver(){
		try {
			prop.load( new FileInputStream("./config/application.properties") );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void setUpDriver() throws MalformedURLException{
		String platform = prop.getProperty("platform");
		if(platform.equalsIgnoreCase("android")) {

			try {
				DesiredCapabilities caps = new DesiredCapabilities();
				caps.setCapability("deviceName",prop.getProperty("deviceName"));
				caps.setCapability("platformVersion", prop.getProperty("platformVersion"));
				caps.setCapability("platformName", prop.getProperty("platform"));
				caps.setCapability("app", System.getProperty("user.dir") + "/lib/EriBank.apk");
				caps.setCapability("noReset", "false");
				driver = new AppiumDriver(new URL(prop.getProperty("url")), caps);
				wait = new WebDriverWait(Driver.driver, 10);				
			}
			catch (Exception e){
				e.printStackTrace();

			}
		}
		else {
			DesiredCapabilities iosCapabilites = new DesiredCapabilities();
			iosCapabilites.setCapability("deviceName", "iPad");
			iosCapabilites.setCapability("device", prop.getProperty("Device"));
			iosCapabilites.setCapability("udid", prop.getProperty("UDID"));
			iosCapabilites.setCapability("bundleid", prop.getProperty("BuldleID"));
			iosCapabilites.setCapability(CapabilityType.VERSION, prop.getProperty("DeviceVersion"));
			iosCapabilites.setCapability(CapabilityType.PLATFORM,prop.getProperty("Platform"));
			driver = new AppiumDriver(new URL(prop.getProperty("DeviceUrl")), iosCapabilites);
			System.out.println("###........CONNECTION SUCCESSFULLY ESTABLISHED WITH NODE DEVICE........###");
			JavascriptExecutor js1 = (JavascriptExecutor) driver;
			js1.executeScript("target.setDeviceOrientation(UIA_DEVICE_ORIENTATION_LANDSCAPELEFT);");
		}


	}

	public void closeDriver(Scenario scenario){
		if(scenario.isFailed()){
			saveScreenshotsForScenario(scenario);
		}
		if(driver!=null) {
			//driver.close();
		}
	}

	private void saveScreenshotsForScenario(final Scenario scenario) {
		final byte[] screenshot = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.BYTES);
		scenario.embed(screenshot, "image/png");
	}

	public void waitForPageLoad(int timeout){
		ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";");
	}



	public By getElementWithLocator(String WebElement) throws Exception {
		String locatorTypeAndValue = prop.getProperty(WebElement);
		String[] locatorTypeAndValueArray = locatorTypeAndValue.split(",");
		String locatorType = locatorTypeAndValueArray[0].trim();
		String locatorValue = locatorTypeAndValueArray[1].trim();
		switch (locatorType.toUpperCase()) {
		case "ID":
			return By.id(locatorValue);
		case "NAME":
			return By.name(locatorValue);
		case "TAGNAME":
			return By.tagName(locatorValue);
		case "LINKTEXT":
			return By.linkText(locatorValue);
		case "PARTIALLINKTEXT":
			return By.partialLinkText(locatorValue);
		case "XPATH":
			return By.xpath(locatorValue);
		case "CSS":
			return By.cssSelector(locatorValue);
		case "CLASSNAME":
			return By.className(locatorValue);
		default:
			return null;
		}
	}

	public WebElement FindAnElement(String WebElement) throws Exception{
		return driver.findElement(getElementWithLocator(WebElement));
	}

	public void PerformActionOnElement(String WebElement, String Action, String Text) throws Exception {
		switch (Action) {
		case "Click":
			FindAnElement(WebElement).click();
			break;
		case "Type":
			FindAnElement(WebElement).sendKeys(Text);
			break;
		case "Clear":
			FindAnElement(WebElement).clear();
			break;
		case "WaitForElementDisplay":
			waitForCondition("Presence",WebElement,60);
			break;
		case "WaitForElementClickable":
			waitForCondition("Clickable",WebElement,60);
			break;
		case "ElementNotDisplayed":
			waitForCondition("NotPresent",WebElement,60);
			break;
		default:
			throw new IllegalArgumentException("Action \"" + Action + "\" isn't supported.");
		}
	}

	public void waitForCondition(String TypeOfWait, String WebElement, int Time){
		try {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Time, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS).ignoring(Exception.class);
			switch (TypeOfWait)
			{
			case "PageLoad":
				wait.until( ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
				break;
			case "Clickable":
				wait.until(ExpectedConditions.elementToBeClickable(FindAnElement(WebElement)));
				break;
			case "Presence":
				wait.until(ExpectedConditions.presenceOfElementLocated(getElementWithLocator(WebElement)));
				break;
			case "Visibility":
				wait.until(ExpectedConditions.visibilityOfElementLocated(getElementWithLocator(WebElement)));
				break;
			case "NotPresent":
				wait.until(ExpectedConditions.invisibilityOfElementLocated(getElementWithLocator(WebElement)));
				break;
			default:
				Thread.sleep(Time*1000);
			}
		}
		catch(Exception e)
		{
			throw new IllegalArgumentException("wait For Condition \"" + TypeOfWait + "\" isn't supported.");
		}
	}
}
