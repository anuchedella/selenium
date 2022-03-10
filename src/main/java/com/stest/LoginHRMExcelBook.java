package com.stest;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class LoginHRMExcelBook {
	public static int i = 0;
	public static WebDriver driver;
	public static ExtentReports report;
	public static ExtentTest test;

	@BeforeSuite
	public void begin() {
		report = new ExtentReports(".//reports//HrmAppReport.html", true);
		report.addSystemInfo("Author", "Anu");
		report.addSystemInfo("userStory", "US10234");
		test = report.startTest("To verify HRM login page working with valid credentials");
	}

	@Test(dataProvider = "delhi")
	public void ihgLogin(String user, String password) throws IOException {
		System.setProperty("webdriver.chrome.driver", ".//drivers//chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://opensource-demo.orangehrmlive.com/index.php/auth/login");
		driver.manage().window().maximize();
		System.out.println(driver.getCurrentUrl());
		System.out.println(driver.getTitle());
		test.log(LogStatus.INFO, "Validating the user login " + user);
		driver.findElement(By.xpath("//input[@id='txtUsername']")).sendKeys(user);
		test.log(LogStatus.PASS, "Entered Username" + test.addBase64ScreenShot("data:image/jpeg;base64,"+snapAsBase64String()));
		driver.findElement(By.xpath("//input[@id='txtPassword']")).sendKeys(password);
		test.log(LogStatus.PASS, "Entered Password" + test.addBase64ScreenShot("data:image/jpeg;base64,"+snapAsBase64String()));
		driver.findElement(By.xpath("//input[@id='btnLogin']")).click();
		test.log(LogStatus.PASS, test.addBase64ScreenShot("data:image/jpeg;base64,"+snapAsBase64String()));

	}

	@DataProvider(name = "delhi")
	public static Object[][] getSheet() {

		Object[][] data = null;

		try {
			XSSFWorkbook workbook = new XSSFWorkbook(".//data//OrangeHRMTestData.xlsx");
			XSSFSheet sheet = workbook.getSheetAt(0);

			// get the number of rows
			int rowCount = sheet.getLastRowNum();
			// get the number of columns
			int columnCount = sheet.getRow(0).getLastCellNum();

			data = new String[rowCount][columnCount];

			// loop through the rows
			for (int i = 1; i < rowCount + 1; i++) {
				try {
					XSSFRow row = sheet.getRow(i);
					for (int j = 0; j < columnCount; j++) { // loop through the columns
						try {
							String cellValue = "";
							try {
								cellValue = row.getCell(j).getStringCellValue();
							} catch (NullPointerException e) {

							}
							data[i - 1][j] = cellValue; // add to the data array
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}
	
	public String snap() throws IOException {
		Random r = new Random();
		String dummy = "";
		if (r.nextInt() != 0) {
			i = i + 1;
			File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			File dest = new File(".//images//pic "+ i + ".png");
			FileUtils.copyFile(src,dest);
			dummy = dest.getAbsolutePath();
		}
		return dummy;
	}
	
	public String snapAsBase64String() throws IOException {
		return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
	}
	
	@AfterSuite
	public void finish() {
		report.endTest(test);
		report.flush();
	}
}