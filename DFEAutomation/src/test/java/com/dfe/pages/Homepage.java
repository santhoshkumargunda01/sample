package com.dfe.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import com.dfe.stepdefs.Hooks;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class Homepage extends BasePage {

	AppiumDriver driver;
	
	

	public Homepage(AppiumDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(how = How.ID, using = "com.experitest.ExperiBank:id/passwordTextField")
	public WebElement txtPassword;

	@FindBy(how = How.ID, using = "com.experitest.ExperiBank:id/usernameTextField")
	public WebElement txtUserName;

	@FindBy(how = How.ID, using = "com.experitest.ExperiBank:id/loginButton")
	public WebElement btnLogin;

	@FindBy(how = How.ID, using = "android:id/button1")
	public WebElement btnOK;

	public void login()
	{		
		txtUserName.sendKeys("Company");
		txtPassword.sendKeys("Company");
	}

	public void clickLogin()
	{
		btnLogin.click();
		
		
	}


}