package com.qainfotech.tap.oprime.pagefactory;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PageTest {
	WebElement element;
	List<WebElement> webElements = null;
	YamlElement yamlElement;
	Page page = new Page();
	
	@Test
	public void switchContextTest()
	{
		System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
		boolean result = false;
		try
		{
			result = page.switchContext();
		} 
		catch(Exception e)
		{
			e.getMessage();
		}
		Assert.assertTrue(result); 

	    System.out.println("Ending test " + new Object(){}.getClass().getEnclosingMethod().getName());
	}
	
	@Test
	public void switchToNativeContextTest()
	{
		System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
		boolean result = false;    
		try
		{
			result = page.switchToNativeContext();
		}
		catch(Exception e)
		{
			e.getMessage();
		}
		Assert.assertTrue(result); 
	    System.out.println("Ending test " + new Object(){}.getClass().getEnclosingMethod().getName());
	}
	
	@Test
	public void switchToPageContext()
	{
		System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
		boolean result = false;    
		try
		{
			result = page.switchToPageContext();
		} 
		catch(Exception e)
		{
			e.getMessage();
		}
		Assert.assertTrue(result); 

	    System.out.println("Ending test " + new Object(){}.getClass().getEnclosingMethod().getName());
	}
	
	@Test
	public void hideKeyboardTest()
	{
		System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
		boolean result = false;    
		try
		{
			result = page.hideKeyboard();
		} 
		catch(Exception e)
		{
			e.getMessage();
		}
		Assert.assertTrue(result); 

	    System.out.println("Ending test " + new Object(){}.getClass().getEnclosingMethod().getName());
	}
		
	@Test
	public void jsElementTest()
	{
		System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
    
		String name = "";
		try
		{
			element = page.jsElement(name);	
		} 
		catch(Exception e)
		{
			e.getMessage();
		}
		Assert.assertNotNull(element);
	    System.out.println("Ending test " + new Object(){}.getClass().getEnclosingMethod().getName());
   
	}
	
	@Test
	public void elementTest()
	{
		System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
	    
		String name = "";

		try
		{
			element = page.element(name);
		}
		catch(Exception e)
		{
			e.getMessage();
		}
		Assert.assertNotNull(element);
	    System.out.println("Ending test " + new Object(){}.getClass().getEnclosingMethod().getName());

	}
	
	
	@Test
	public void elementTest_1()
	{
		System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
	    
		String name = "";

		try
		{
			element = page.element(element, name);
		}
		catch(Exception e)
		{
			e.getMessage();
		}
		Assert.assertNotNull(element);
	    System.out.println("Ending test " + new Object(){}.getClass().getEnclosingMethod().getName());

	}
	
	
	@Test
	public void elementsTest()
	{
		System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
		String name = "";
		List<WebElement> webElements = null;
		try
		{
			webElements = page.elements(name);
		}
		catch(Exception e)
		{
			e.getMessage();
		}
	    Assert.assertEquals(Arrays.asList("Customer1", "Customer2", "Customer3"), webElements.toArray());

	    System.out.println("Ending test " + new Object(){}.getClass().getEnclosingMethod().getName());

	}
	
	@Test
	public void elementsTest_1()
	{
		System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
		String name = "";
		String containerElementName = "";
		try
		{
			webElements = page.elements(containerElementName, name);
		}
		catch(Exception e)
		{
			e.getMessage();
		}
	    Assert.assertEquals(Arrays.asList("Customer1", "Customer2", "Customer3"), webElements.toArray());

	    System.out.println("Ending test " + new Object(){}.getClass().getEnclosingMethod().getName());

	}
	
	@Test
	public void visibleElementTest()
	{
		System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
		String name = "";
		try
		{
			element = page.visibleElement(name);
		}
		catch(Exception e)
		{
			e.getMessage();
		}
		Assert.assertNotNull(element);
	    System.out.println("Ending test " + new Object(){}.getClass().getEnclosingMethod().getName());

	}
	
	@Test
	public void findElementTest()
	{
		System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
		String name = "";
		try
		{
			element = page.findElement(yamlElement);
		}
		catch(Exception e)
		{
			e.getMessage();
		}
		
		Assert.assertNotNull(element);
	    System.out.println("Ending test " + new Object(){}.getClass().getEnclosingMethod().getName());

	}
	
	@Test
	public void findElementsTest()
	{
		System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
		try
		{
			webElements = page.findElements(yamlElement);
		}
		catch(Exception e)
		{
			e.getMessage();
		}

		
	    Assert.assertEquals(Arrays.asList("Customer1", "Customer2", "Customer3"), webElements.toArray());
		System.out.println("Ending test " + new Object(){}.getClass().getEnclosingMethod().getName());
	}
	
	@Test
	public void findVisibleElementTest()
	{
		System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
		try
		{
			element = page.findVisibleElement(yamlElement);
		}
		catch(Exception e)
		{
			e.getMessage();
		}
		
		Assert.assertNotNull(element);
	    System.out.println("Ending test " + new Object(){}.getClass().getEnclosingMethod().getName());	
	}
	
	
	@Test
	public void findVisibleElementsTest()
	{
		System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
		try
		{
			webElements = page.findVisibleElements(yamlElement);
		}
		catch(Exception e)
		{
			e.getMessage();
		}

		
	    Assert.assertEquals(Arrays.asList("Customer1", "Customer2", "Customer3"), webElements.toArray());
		System.out.println("Ending test " + new Object(){}.getClass().getEnclosingMethod().getName());
	}
	
	
	@Test
	public void findByTest()
	{
		System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
		    
		By element = page.findBy(yamlElement);
		if(!element.equals(""))
		{
			System.out.println("Good to go");
		} else
		{
			Assert.assertTrue(false); 
		}
	    System.out.println("Ending test " + new Object(){}.getClass().getEnclosingMethod().getName());
	}
	
	@Test
	public void findByTest_1()
	{
		System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
		String name = "";
		By element = page.findBy(name);
		if(!element.equals(""))
		{
			System.out.println("Good to go");
		} else
		{
			Assert.assertTrue(false); 
		}
	    System.out.println("Ending test " + new Object(){}.getClass().getEnclosingMethod().getName());
	}


}
