package com.stest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginHRM {
	
	@Test
	@Parameters({"user","pwd"})
	public void login(String data1,String data2)
	{
		System.setProperty("webdriver.chrome.driver", ".//drivers//chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://opensource-demo.orangehrmlive.com/");
		driver.manage().window().maximize();
		driver.findElement(By.id("txtUsername")).sendKeys(data1);
		driver.findElement(By.name("txtPassword")).sendKeys(data2);
		driver.findElement(By.className("button")).click();
		
	}
		

	}


