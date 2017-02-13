package com.qainfotech.tap.oprime.pagefactory;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PageTest {
	
	Page page = new Page();
	
	@Test
	public void switchContextTest()
	{
		try
		{
			boolean result = page.switchContext();
			Assert.assertTrue(result); 
		} 
		catch(Exception e)
		{
			e.getMessage();
		}
	}
	
	@Test
	public void switchToNativeContextTest()
	{
		try
		{
			boolean result = page.switchToNativeContext();
			Assert.assertTrue(result); 
		}
		catch(Exception e)
		{
			e.getMessage();
		}
	}
	
	@Test
	public void switchToPageContext()
	{
		try
		{
			boolean result = page.switchToPageContext();
			Assert.assertTrue(result); 
		} 
		catch(Exception e)
		{
			e.getMessage();
		}
	}
	
	@Test
	public void hideKeyboardTest()
	{
		try
		{
			boolean result = page.hideKeyboard();
			Assert.assertTrue(result); 
		} 
		catch(Exception e)
		{
			e.getMessage();
		}
	}
	
	@Test
	public void findByTest()
	{
		String yamlElement = "";
		By element = page.findBy(yamlElement);
		if(!element.equals(""))
		{
			System.out.println("Good to go");
		} else
		{
			Assert.assertTrue(false); 
		}
	}

}
