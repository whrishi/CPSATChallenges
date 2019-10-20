package com.challenge1;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Question3 {

	private WebDriver driver;
	private String baseUrl;

	@Before
	public void setUp() throws Exception {
		baseUrl = "https://www.premierleague.com/";
		// Firefox version 40
		System.setProperty("webdriver.gecko.driver", ".\\lib\\geckodriver.exe");
		FirefoxOptions capabilities =  new FirefoxOptions();
		capabilities.setCapability("marionette", false);
		//capabilities.setCapability("network.websocket.allowInsecureFromHTTPS",true);
		//capabilities.setCapability ("browser.link.open_newwindow", 2);		
		driver = new FirefoxDriver(capabilities);		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Open " + baseUrl + " in Firefox");
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	/*
	 * 1. Click on Tables from the header 
	 * 2. From the tables, open ‘Arsenal’ club in a new window via context-click 
	 * 3. Find official website URL on the page and
	 * print on the console from the newly opened window 
	 * 4. Print the page title of the newly opened window 
	 * 5. Go back to the main window
	 * 6. Print the page title again
	 */
	@Test
	public void testMultipleWindows() throws InterruptedException {
		driver.get(baseUrl);
		// Get current window handle
		String parentWinHandle = driver.getWindowHandle();
		WebDriverWait wait = new WebDriverWait(driver, 5);
		String sExpectedClub = "Arsenal";
		//sExpectedClub = "Brighton & Hove Albion";
		WebElement elementUL = driver.findElement(By.xpath("/html/body/header/nav/ul"));
		List<WebElement> listClubs = elementUL.findElements(By.xpath("./li"));
		Actions actions = new Actions(driver);
		for (int i = 0; i < listClubs.size(); i++) {
			if (sExpectedClub.equalsIgnoreCase(listClubs.get(i).getText())) {
				// 2. From the tables, open ‘Arsenal’ club in a new window via context-click
				String myURL = listClubs.get(i).findElement(By.xpath("./a")).getAttribute("href");
				System.out.println(myURL);
				// WebElement element =
				// listClubs.get(i).findElement(By.xpath("./a/span[\"+i+\"]/span"));
				WebElement element = listClubs.get(i).findElement(By.xpath("./a"));				
				actions.contextClick(element).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER)
						.build().perform();
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				Thread.sleep(1000);				
				// Get the window handles of all open windows
				Set<String> winHandles = driver.getWindowHandles();
				// Loop through all handles
				for (String handle : winHandles) {
					if (!handle.equals(parentWinHandle)) {						
						driver.switchTo().window(handle);
						//driver.navigate().to(myURL);
						// 3. Find official website URL on the page and 
						// print URL on the console from the newly opened window
						System.out.println("URL of the new window: " + driver.getCurrentUrl());
						//4. Print the page title of the newly opened window
						System.out.println("Title of the new window: " + driver.getTitle());
						break;
					}
				}
				break;
			}
		}
		// Switching the control back to parent window
		driver.switchTo().window(parentWinHandle);
		// Print the Title to the console
		System.out.println("Parent window Title: " + driver.getTitle());
	}
}