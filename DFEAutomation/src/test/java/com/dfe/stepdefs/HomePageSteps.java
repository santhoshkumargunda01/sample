package com.dfe.stepdefs;

import com.aventstack.extentreports.gherkin.model.Feature;
import com.dfe.common.ExtentReportListener;
import com.dfe.pages.Homepage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class HomePageSteps extends ExtentReportListener{		
	Homepage homePage = new Homepage(driver);

	@Given("^user is in login screen$")
	public void user_is_in_login_screen() throws Throwable {
		test = extent.createTest(Feature.class, "Home Page Verifications");		
	}

	@When("^user enters credentails$")
	public void user_enters_credentails() throws Throwable {
		homePage.login();
		test.pass("Enter credentials successfully");
	}

	@Then("^user clicks login$")
	public void user_clicks_login() throws Throwable {
		new Homepage(driver).clickLogin();
		test.pass("Clicked on login button successfully");
	
	}

}
