package com.stest;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class Frame {
	@Test
	public void frame() throws InterruptedException
	{
		System.setProperty("webdriver.chrome.driver",".//drivers//chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.w3schools.com/js/tryit.asp?filename=tryjs_prompt");
		driver.manage().window().maximize();
		driver.switchTo().frame("iframeResult");
		Thread.sleep(3000);
		WebElement button = driver.findElement(By.xpath("//button[text()='Try it']"));
		button.click();
		Alert a = driver.switchTo().alert();
		a.accept();
	}

}
