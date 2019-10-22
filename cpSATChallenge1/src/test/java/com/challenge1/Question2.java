package com.challenge1;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Question2 {

	private WebDriver driver;
	private String baseUrl;

	@Before
	public void setUp() throws Exception {		
		baseUrl = "https://www.cii.in/OnlineRegistration.aspx";
		// Firefox version 40
		System.setProperty("webdriver.gecko.driver", ".\\lib\\geckodriver.exe");
		FirefoxOptions capabilities =  new FirefoxOptions();
		capabilities.setCapability("marionette", false);
		driver = new FirefoxDriver(capabilities);

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		System.out.println("Open " + baseUrl + ", in Firefox");
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	/*
	 * 1. Select “Number of Attendees” as 3 (selectByValue)
	 * 2. Assert the row count is 3 
	 * 3. Select 1st-row title as ‘Admiral’ (selectByVisibleText) 
	 * 4. Select 2nd-row title as ‘CA’ (selectByValue)
	 * 5. Select 3rd-row title as ‘CS’(selectByIndex) 
	 * 6. Print all the options that are available in the title
	 */
	@Test
	public void testDropDownSelection() {
		driver.get(baseUrl);
		WebDriverWait wait = new WebDriverWait(driver, 5);
		String sAttendee = "3";
		int iAttendeeRows = 0;
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"drpAttendee\"]/option")));
		// select the first operator using "select by value"
		Select selectByValue = new Select(driver.findElement(By.id("drpAttendee")));
		selectByValue.selectByValue(sAttendee);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"Gridview1\"]/tbody/tr")));
		WebElement webTableAttendee = driver.findElement(By.xpath("//*[@id=\"Gridview1\"]/tbody"));
		List<WebElement> numberOfAttendeeRows = webTableAttendee.findElements(By.xpath("./tr"));

		iAttendeeRows = numberOfAttendeeRows.size() - 1; // subtract header row
		Assert.assertTrue("The row count is " + (iAttendeeRows), Integer.parseInt(sAttendee) == (iAttendeeRows));

		// 3. Select 1st-row title as ‘Admiral’(Use selectByVisibleText method)
		String sExpectedTitleByText = "Admiral";
		Select selectTitleByVisibleText = new Select(driver.findElement(By.id("Gridview1_ctl02_drpTitle")));
		selectTitleByVisibleText.selectByVisibleText(sExpectedTitleByText);

		// 4. Select 2nd-row title as ‘CA’ (Use selectByValue method)
		String sExpectedTitleByValue = "CA";
		Select selectTitleByValue = new Select(driver.findElement(By.id("Gridview1_ctl03_drpTitle")));
		selectTitleByValue.selectByValue(sExpectedTitleByValue);

		// 5. Select 3rd-row title as ‘CS’(Use selectByIndex method)
		String sExpectedTitleByIndex = "CS";
		int iTitleIndex = 0;
		Select selectTitleByIndex = new Select(driver.findElement(By.id("Gridview1_ctl04_drpTitle")));
		List<WebElement> listTitleOptions = selectTitleByIndex.getOptions();
		for (int i = 0; i < selectTitleByIndex.getOptions().size(); i++) {
			if (sExpectedTitleByIndex.equalsIgnoreCase(listTitleOptions.get(i).getText())) {
				iTitleIndex = i;
				break;
			}
		}
		selectTitleByIndex.selectByIndex(iTitleIndex);
		// 6. Print all the options that are available in the title
		System.out.println("Print all the options that are available in the title :");
		for (int i = 1; i < selectTitleByIndex.getOptions().size(); i++) {
			System.out.println(listTitleOptions.get(i).getText());
		}

	}
}