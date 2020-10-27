package com.dfe.stepdefs;

import java.net.MalformedURLException;

import org.apache.commons.lang.StringUtils;

import com.dfe.common.ExtentReportListener;
import com.dfe.pages.Homepage;

import io.appium.java_client.AppiumDriver;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks extends ExtentReportListener{
	public static String featureName;
	
	public AppiumDriver driver;
	Homepage homepage;
	
	@Before
	public void before(Scenario scenario) throws MalformedURLException {
		String featureName= getFeatureFileNameFromScenarioId(scenario);
		startReport();  
		setUpDriver();		
	}

	private String getFeatureFileNameFromScenarioId(Scenario scenario) {
		featureName = "Feature ";
		String rawFeatureName = scenario.getId().split(";")[0].replace("-"," ");
		featureName = StringUtils.substringAfterLast(rawFeatureName, "/").split(".feature")[0];
		return featureName;
	}

	@After
	public void after(Scenario scenario){
		closeDriver(scenario);
		extent.flush();
	}


}
