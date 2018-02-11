package com.selenium.test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.selenium.page.PageClass;

public class TestClass {
	
	@BeforeClass
	@Parameters({"appUrl"})
	public void launchBrowserTest(String appUrl) throws Exception{
		PageClass.launchBrowser(appUrl);
	}
	
	@Test(alwaysRun=true)
	@Parameters({"searchText"})
	public void enterTextTest(String searchText) throws Exception{
		PageClass.enterText(searchText);
	}
	
	@Test(dependsOnMethods="enterTextTest")
	public void selectListItemTest() throws Exception{
		PageClass.selectListItem();
	}

}
