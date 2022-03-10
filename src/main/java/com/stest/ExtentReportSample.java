package com.stest;

import java.io.File;

import java.io.IOException;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentReportSample {
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

	@Test
	public void loginPageHRM() throws IOException {
		System.setProperty("webdriver.chrome.driver", ".//drivers//chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://opensource-demo.orangehrmlive.com/");
		driver.manage().window().maximize();
		driver.findElement(By.id("txtUsername")).sendKeys("Admin");
		test.log(LogStatus.PASS, "Entered Username" + test.addScreenCapture(snap()));
		driver.findElement(By.name("txtPassword")).sendKeys("admin123");
		test.log(LogStatus.PASS, "Entered Password" + test.addScreenCapture(snap()));
		driver.findElement(By.className("button")).click();
		test.log(LogStatus.PASS, "Entered Username" + test.addScreenCapture(snap()));
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
	

	@AfterSuite
	public void finish() {
		report.endTest(test);
		report.flush();
	}

}
