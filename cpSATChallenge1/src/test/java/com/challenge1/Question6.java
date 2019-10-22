package com.challenge1;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class Question6 {
	private WebDriver driver;
	private String baseUrl;
	private String dataFilePath = "C:\\Projects\\CPSAT-AUTOMATHON\\Project1\\src\\test\\java\\com\\cpsat\\SearchTestData.xls";

	@BeforeClass
	public void beforeClass() {
		baseUrl = "https://www.woodlandworldwide.com/";
		// Chrome version 77.0.*
		System.setProperty("webdriver.chrome.driver", ".\\lib\\chromedriver.exe");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		// Disabling Push Notification popup
		capabilities.setCapability("notification.feature.enabled", false);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		System.out.println("Open " + baseUrl + " , in Google Chrome");
	}

	@AfterTest
	public void afterTest() {
		driver.quit();
	}

	@Test(dataProvider = "testData1")
	public void testTCQ1(String searchProduct) throws Exception {
		testData(searchProduct);

	}

	@Test(dataProvider = "testData2")
	public void testTCQ2(String searchProduct) throws Exception {
		testData(searchProduct);
	}

	@Test(dataProvider = "testData3")
	public void testTCQ3(String searchProduct) throws Exception {
		testData(searchProduct);
	}

	public void testData(String searchProduct) {
		// To wait for element visible
		WebDriverWait wait = new WebDriverWait(driver, 5);
		driver.get(baseUrl);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id=\"searchForm\"]/div")).click();
		driver.findElement(By.id("searchkey")).sendKeys(searchProduct);
		driver.findElement(By.id("searchkey")).sendKeys(Keys.ENTER);
		wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath("/html/body/main/nav/span")),
				searchProduct));
		driver.findElement(By.xpath("//*[@id=\"sortByForm\"]/ul/li[3]/label")).click();
		WebElement allProducts = driver.findElement(By.xpath("//*[@id=\"categoryProductList\"]"));
		Double dCurrentProductPrice = 0.00;
		Double dPreviousProductPrice = 0.00;
		String sPrice = "";
		String sExpected = "First 8 products are in descending order of the price.";
		int iProductList = allProducts.findElements(By.xpath("//*[@id=\"categoryProductList\"]")).size();
		boolean bOrder = false;
		int iCount = 0;
		String sActual = "First 8 products are NOT in descending order of the price.";
		if (iProductList > 0) {
			bOrder = true;
			for (iCount = 1; iCount < 9; iCount++) {
				sPrice = allProducts
						.findElement(By.xpath("/html/body/main/div/div/div/section[" + iCount + "]/ul/li[1]/span"))
						.getText();
				// System.out.println(sPrice);
				// System.out.println(dPreviousProductPrice);
				dCurrentProductPrice = Double.valueOf(sPrice.substring(sPrice.indexOf("R") + 2));
				// System.out.println(dCurrentProductPrice);
				if ((dPreviousProductPrice < dCurrentProductPrice) && (iCount != 1)) {
					bOrder = false;
					sActual = iCount + "th Product price: \"" + dCurrentProductPrice
							+ "\" is greater than previous Product price: \"" + dPreviousProductPrice + "";
					break;
				} else {
					dPreviousProductPrice = dCurrentProductPrice;
				}

			}
		}
		if (bOrder) {
			Assert.assertEquals(bOrder, true, sActual);
		} else {
			System.out.println(sExpected);
		}
	}

	@DataProvider(name = "testData1")
	public Object[][] createData1() throws Exception {
		Object[][] retObjArr = getTableArray(dataFilePath, "TestData", "productName1");
		System.out.println("*****************  1 *************************");
		return (retObjArr);
	}

	@DataProvider(name = "testData2")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr = getTableArray(
				"C:\\Projects\\CPSAT-AUTOMATHON\\Project1\\src\\test\\java\\com\\cpsat\\SearchTestData.xls", "TestData",
				"productName2");
		System.out.println("*****************  2 *************************");
		return (retObjArr);
	}

	@DataProvider(name = "testData3")
	public Object[][] createData3() throws Exception {
		Object[][] retObjArr = getTableArray(
				"C:\\Projects\\CPSAT-AUTOMATHON\\Project1\\src\\test\\java\\com\\cpsat\\SearchTestData.xls", "TestData",
				"productName3");
		System.out.println("*****************  3 *************************");
		return (retObjArr);
	}

	public String[][] getTableArray(String xlFilePath, String sheetName, String tableName) {
		String[][] tabArray = null;
		try {
			Workbook workbook = Workbook.getWorkbook(new File(xlFilePath));
			Sheet sheet = workbook.getSheet(sheetName);

			int startRow, startCol, ci, cj;

			int totalNoOfCols = sheet.getColumns();
			// int totalNoOfRows = sheet.getRows();
			// System.out.println(totalNoOfCols);
			// System.out.println(totalNoOfRows);

			Cell tableStart = sheet.findCell(tableName);
			startRow = tableStart.getRow();
			startCol = tableStart.getColumn();

			// System.out.println("startRow=" + startRow + ", " + "startCol=" + startCol);
			tabArray = new String[1][1];
			ci = 0;

			for (int i = startRow; i <= startRow; i++, ci++) {
				cj = 0;
				for (int j = startCol + 1; j < totalNoOfCols; j++, cj++) {
					tabArray[ci][cj] = sheet.getCell(j, i).getContents();
					// System.out.println(tabArray[ci][cj]);
				}
			}
		} catch (Exception e) {
			System.out.println("error in getTableArray()");

		}

		return (tabArray);
	}

}
