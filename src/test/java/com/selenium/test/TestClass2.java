package com.selenium.test;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.selenium.page.PageClass;

public class TestClass2 {
	
	@Test(alwaysRun=true)
	@Parameters({"linkText"})
	public void clickOnTutorialLinkTest(String linkText) throws Exception{
		PageClass.clickOnTutorialLink(linkText);
	}
	
	@Test(dependsOnMethods="clickOnTutorialLinkTest")
	public void validateTutorialPageTest() throws Exception{
		PageClass.validateTutorialPage();
		PageClass.makeFlagTrue();
	}
	
	@AfterClass
	public void quitBrowserTest() throws Exception{
		PageClass.quitBrowser();
	}

}
