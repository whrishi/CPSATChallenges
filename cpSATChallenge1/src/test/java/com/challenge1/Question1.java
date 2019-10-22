package com.challenge1;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class Question1 {

	private WebDriver driver;
	private String baseUrl;

	@BeforeTest
	public void beforeTest() {
		baseUrl = "https://www.meripustak.com/";
		// Chrome version 77.0.*
		System.setProperty("webdriver.chrome.driver", ".\\lib\\chromedriver.exe");
		driver = new ChromeDriver();		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// Navigate to "https://www.meripustak.com/"
		System.out.println("Open " + baseUrl + " in Google Chrome");
		driver.get("https://www.meripustak.com/");
	}

	@AfterTest
	public void afterTest() {
		driver.quit();
	}

	@Test
	public void testQuestion1() {
		// 1 Print the width and height of the logo
		WebElement elementLogo = driver.findElement(By.xpath("/html/body/form/div[4]/nav/div[1]/div[1]/a/img"));
		int width = elementLogo.getSize().getWidth();
		int height = elementLogo.getSize().getHeight();
		System.out.println("1. Print the width and height of the logo");
		System.out.println("Width : " + width + " and Height : " + height + " of the logo.");

		// 2 Under Follow us section on the bottom, extract and print the href of
		// ‘twitter’ social media icon
		// (Right a script in such a way, if the position of ‘twitter’ icon is
		// changed tomorrow in the social media icons, our script should work)
		WebElement elementTwitterURL = driver
				.findElement(By.xpath("/html/body/form/footer/div/div[6]/div/a[contains(@href,'twitter')]"));
		System.out.println("2. Print href of ‘twitter’ social media icon");
		System.out.println("href of ‘twitter’ social media icon: " + elementTwitterURL.getAttribute("href"));

		// 3 Click on the shopping cart when an item in the cart is 0
		WebElement elementCart = driver.findElement(By.xpath("/html/body/form/div[4]/nav/div[1]/div[2]/div[2]/a[2]"));
		WebElement elementtCartItem = elementCart.findElement(By.xpath("./span/span"));
		if (elementtCartItem.getText().startsWith("0")) {
			System.out.println("3. Clicking on the shopping cart when an item in the cart is 0");
			elementCart.click();
		}

		// 4 Assert the message in the shopping cart table
		// “No Item is Added In Cart yet.Cart is Empty!!!”
		// Read Table data
		String sTextToFind = "No Item is Added In Cart yet.Cart is Empty!!!";
		String sActualText = "";
		WebElement orderTable = driver
				.findElement(By.xpath("/html/body/form/div[5]/div[3]/table/tbody/tr[2]/td/div/table/tbody"));

		// Identify there is only column in the order table
		int colCount = orderTable.findElements(By.xpath("./tr/td")).size();
		if (colCount == 1) {
			sActualText = orderTable.findElement(By.xpath("./tr/td/h4")).getText();
		}
		System.out.println(
				"4. Assert the message in the shopping cart table. \"No Item is Added In Cart yet.Cart is Empty!!!\"");
		Assert.assertEquals(sActualText, sTextToFind, "Text not found!");

		// 5. Add anyone java book in cart
		// Search JAVA book, Select Java book from the list, click Add to Cart
		WebElement elementSearchBox = driver
				.findElement(By.xpath("/html/body/form/div[4]/nav/div[1]/div[2]/div[1]/input"));
		elementSearchBox.sendKeys("Java Book");
		elementSearchBox.sendKeys(Keys.ENTER);
		// Iterate thru list and read the text contains JAVA
		WebElement elementBookName = driver
				.findElement(By.xpath("/html/body/form/div[5]/div[4]/div/ul/li[1]/div[3]/a"));
		elementBookName.getText().contains("Java");
		System.out.println("5. Add anyone java book in cart");
		elementBookName.click();
		WebElement elementAddToCart = driver
				.findElement(By.xpath("/html/body/form/div[5]/div/div/div/div[2]/div[3]/div[1]/ul/li[2]/input[1]"));
		elementAddToCart.click();

		// 6. Verify if this message exists in the shopping cart table
		// “No Item is Added In Cart yet.Cart is Empty!!!”
		orderTable = driver.findElement(By.xpath("/html/body/form/div[5]/div[3]/table/tbody/tr[2]/td/div/table/tbody"));
		// Read a web table column count
		colCount = 0;
		colCount = orderTable
				.findElements(By.xpath("/html/body/form/div[5]/div[3]/table/tbody/tr[2]/td/div/table/tbody/tr/td"))
				.size();
		if (colCount > 1) {
			sActualText = orderTable
					.findElement(
							By.xpath("/html/body/form/div[5]/div[3]/table/tbody/tr[2]/td/div/table/tbody/tr/td[1]/a"))
					.getText();
		}
		System.out.println(
				"6. Verifying whether message exists in the shopping cart table \"No Item is Added In Cart yet.Cart is Empty!!!\"");
		Assert.assertNotEquals(sActualText, sTextToFind, "Text found!");
	}

}
