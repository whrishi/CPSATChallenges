package com.challenge1;

import java.util.List;
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
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Question4 {

	private WebDriver driver;
	private String baseUrl;

	@Before
	public void setUp() throws Exception {
		baseUrl = "https://www.hometown.in/";

		System.setProperty("webdriver.gecko.driver", ".\\lib\\geckodriver.exe");
		FirefoxOptions capabilities =  new FirefoxOptions();
		capabilities.setCapability("marionette", false);
		driver = new FirefoxDriver(capabilities);

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Open " + baseUrl + "  in Firefox");
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	/*
	 * 1. Select Electronics from ‘More’ menu 
	 * 2. From Filter, section select the
	 * color as ‘Black’ 
	 * 3. When you go to the first product image, you will see
	 * Quick View option, click on that 
	 * 4. Assert that product name contains Black
	 * in a name. 
	 * 5. Close the Quick view window and verify if Black is also present
	 * in Applied filters
	 */
	@Test
	public void testFilterQuickView() throws InterruptedException {
		String sExpectedMenu = "Electronics";
		driver.get(baseUrl);
		WebDriverWait wait = new WebDriverWait(driver, 5);
		WebElement elementMore = driver
				.findElement(By.xpath("//*[@id=\"content\"]/div/main/section/div[1]/section/div[2]/div/div"));
		List<WebElement> listAnchors = elementMore.findElements(By.xpath("./a"));
		// System.out.println(listAnchors.size());
		Actions actionHover = new Actions(driver);
		for (WebElement anchors : listAnchors) {
			// System.out.println(anchors.getText());
			if (sExpectedMenu.equalsIgnoreCase(anchors.getText())) {
				anchors.click();
				actionHover.moveByOffset(0, 0);
				WebElement elementColour = driver.findElement(By.xpath(
						"//*[@id=\"content\"]/div/main/section/div[1]/div/div/section[3]/div/div/div/div[1]/div[5]/button/span"));
				actionHover.moveToElement(elementColour).build().perform();
				WebElement elementColor = driver.findElement(By.xpath(
						"//*[@id=\"content\"]/div/main/section/div[1]/div/div/section[3]/div/div/div/div[1]/div[5]/div/ul/li[1]"));
				//System.out.println(elementColor.getText());
				String sExpectedColor = "Black";
				if (elementColor.getText().equalsIgnoreCase(sExpectedColor)) {
					WebElement elementColourchek = driver.findElement(By.xpath(
							"//*[@id=\"content\"]/div/main/section/div[1]/div/div/section[3]/div/div/div/div[1]/div[5]/div/ul/li[1]/div/label/span"));
					elementColourchek.click();
					WebElement elementProduct = driver.findElement(By.xpath(
							"//*[@id=\"content\"]/div/main/section/div[1]/div/div/section[5]/div/div/div[1]/div[1]/a/div[1]/div/img"));
					actionHover.moveToElement(elementProduct).build().perform();
					WebElement elementQuickView = driver.findElement(By.xpath(
							"//*[@id=\"content\"]/div/main/section/div[1]/div/div/section[5]/div/div/div[1]/div[1]/button[2]"));
					// actionHover.click(elementQuickView);
					actionHover.moveToElement(elementQuickView).build().perform();
					elementQuickView.click();
					WebElement elementProductName = driver
							.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div[2]/div[1]/h1/a"));					
					Assert.assertTrue("Product Name does not contain " + sExpectedColor,
							elementProductName.getText().contains(sExpectedColor));
					actionHover.sendKeys(Keys.ESCAPE);
					elementColor = driver.findElement(By.xpath(
							"//*[@id=\"content\"]/div/main/section/div[1]/div/div/section[3]/div/div/div/div[1]/div[5]/button/span"));
					actionHover.moveToElement(elementColor).build().perform();
					elementColourchek = driver.findElement(By.xpath("//*[@id=\"checkbox\"]"));
					Assert.assertEquals(sExpectedColor + " Colour is not selected", true,
							elementColourchek.isSelected());

				}
				break;
			}
		}
	}

}
