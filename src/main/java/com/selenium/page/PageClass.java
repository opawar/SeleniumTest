package com.selenium.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageClass {
	
	public static WebDriver driver;
	static WebDriverWait wait;
	static boolean flag = false;
	
	public static void launchBrowser(String appUrl) throws Exception{
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		driver = new ChromeDriver(options);
		wait = new WebDriverWait(driver, 10);
		
		driver.get(appUrl);
		System.out.println("--------Launched browser");
		System.out.println("URL of the page is -> " + driver.getCurrentUrl());
		Thread.sleep(2000);
	}
	
	public static void enterText(String searchText) throws Exception{
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("lst-ib")));
		WebElement textbox = driver.findElement(By.id("lst-ib"));
		textbox.sendKeys(searchText);
		System.out.println("--------Text entered");
	}
	
	public static void selectListItem() throws Exception{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul[role='listbox']")));
		WebElement dropdown = driver.findElement(By.cssSelector("ul[role='listbox']"));
		dropdown.findElement(By.tagName("li")).click();
		System.out.println("--------List item selected");
	}
	
	public static void clickOnTutorialLink(String linkText) throws Exception{
		wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(linkText)));
		WebElement tutorialLink = driver.findElement(By.partialLinkText(linkText));
		tutorialLink.click();
		System.out.println("--------Tutorial link clicked");
	}
	
	public static void validateTutorialPage() throws Exception{
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("g-page-surround")));
		//wait.until(ExpectedConditions.presenceOfElementLocated(By.id("g-page-surroundd")));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(), ' Free Selenium Tutorials ')]")));
		System.out.println("--------Test successful");
	}
	
	public static void quitBrowser() throws Exception{
		driver.quit();
	}
	
	public static void makeFlagTrue() throws Exception{
		flag = true;
	}

}
