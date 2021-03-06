package com.qainfotech.tap.oprime.pagefactory;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.galenframework.api.Galen;
import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.json.JsonReportBuilder;
import com.galenframework.reports.model.LayoutReport;
import com.jcabi.aspects.Loggable;
import com.qainfotech.custom.exceptions.MyOwnNoSuchContextException;
import com.qainfotech.custom.exceptions.MyOwnNoSuchElementException;
import com.qainfotech.custom.exceptions.MyOwnWebDriverException;
import com.qainfotech.security.DataEncryptor;
import com.qainfotech.tap.oprime.TestSession;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.NoSuchContextException;

/**
 * 
 * @author nimit.jain (Qainfotech)
 *
 */
@Loggable(Loggable.DEBUG)
public class Page {
    
    public TestSession session;
    public YamlPage pageUI;
    public DataEncryptor encryptor;
    //public File specFile;
    public String pageYamlFile;
    public Boolean hasContext;
    
    public Page(TestSession session, String pageYamlFile) throws Exception{
        this.session = session;
        this.pageYamlFile = pageYamlFile;
        this.pageUI = new YamlPage(
                session.config.get("page_spec_file_root").toString()
                , pageYamlFile);
        this.encryptor = new DataEncryptor();
 //       this.specFile = new SpecFile(pageYamlFile, pageUI);
        this.hasContext = false;
        if(pageUI.context!=null){
            this.hasContext = true;
        }
    }
    public Page() {
		// TODO Auto-generated constructor stub
	}

    /**
     * Method is used to switch the context.
     * @return true/false
     * @throws MyOwnNoSuchContextException
     */
	public boolean switchContext()throws MyOwnNoSuchContextException{
    	boolean result = false;
        if(hasContext)
        {
        	try
        	{
                ((AppiumDriver)session.driver).context(pageUI.context);
                result = true;
        	}
        	catch(NoSuchContextException e)
        	{
        		throw new MyOwnNoSuchContextException(pageUI.context+ "is not present");
        	}
        	catch(Exception e)
        	{
        		throw new MyOwnNoSuchContextException(pageUI.context+ "is not present");
        	}
        }
        
        return result;
    }
    
	/**
	 * Method help to switch on native context
	 * @return true/false
	 * @throws MyOwnNoSuchContextException
	 */
    public boolean switchToNativeContext() throws MyOwnNoSuchContextException{
    	boolean result = false;
    	try
    	{
    		((AppiumDriver)session.driver).context("NATIVE_APP");
    	    result = true;   
    	}
    	catch(NoSuchContextException e)
    	{
    		throw new MyOwnNoSuchContextException("NATIVE_APP view is not present");
    	}
    	catch(Exception e)
    	{
    		throw new MyOwnNoSuchContextException("NATIVE_APP view is not present");
    	}
        return result;
    }
    
    /**
     * Method is help to switch the page context
     * @return true/false
     */
    public boolean switchToPageContext() {
    	boolean result = false;
        switchToNativeContext();
        switchContext();
        
        for(String handle:session.driver.getWindowHandles()){
            session.driver.switchTo().window(handle);
            if(session.driver.getTitle().equals(pageUI.expectedTitle)){
            	result = true;
                break;
            } else 
            {
            	result = false;
            }
        }
        return result;
    }
    
    /**
     * Method is used to hide keyboard.
     * @return true/false
     * @throws MyOwnWebDriverException
     */
    public boolean hideKeyboard() throws MyOwnWebDriverException{
    	boolean result = false;
        try{
            ((AppiumDriver)session.driver).hideKeyboard();
            result = true;
        } 
        catch(WebDriverException e)
        {
        	throw new MyOwnWebDriverException("Keyboard is closed.");
        }
        catch(Exception e)
        {
        	throw new MyOwnWebDriverException("Keyboard is closed.");
        }
        
        return result;
    }
    
    /**
     * Method return the element
     * @param name
     * @return
     * @throws MyOwnWebDriverException
     */
    public WebElement jsElement(String name) throws MyOwnWebDriverException{
    	try
    	{
    	       return (WebElement)((JavascriptExecutor)session.driver).executeScript("return " + pageUI.element(name).locator);   	       
    	}
        catch(WebDriverException e)
        {
        	throw new MyOwnWebDriverException(pageUI.element(name).locator+ " is not executed using JavaScriptExecutor");
        }
        catch(Exception e)
        {
        	throw new MyOwnWebDriverException(pageUI.element(name).locator+" is not executed using JavaScriptExecutor");
        }
    }
    
    /**
     * Method return the element
     * @param name
     * @return
     */
    public WebElement element(String name) throws MyOwnNoSuchElementException{
    	try
    	{
            YamlElement yamlElement = pageUI.element(name);
            if(yamlElement.container != null){
                WebElement container = findElement(yamlElement.container);
                return (new WebDriverWait(session.driver, pageUI.timeout))
                    .until(ExpectedConditions.presenceOfNestedElementLocatedBy(
                            container, findBy(yamlElement)));
            }else{
                return findElement(yamlElement);
            }
    	}
    	
    	catch(MyOwnNoSuchElementException e)
    	{
    		throw new MyOwnNoSuchElementException(pageUI.element(name)+" not found" +e);
    	}
    	catch(Exception e)
    	{
    		throw new MyOwnNoSuchElementException(pageUI.element(name)+" not found" +e);
    	}
    }

    /**
     * Method is used to return the element
     * @param element
     * @param name
     * @return
     */
    public WebElement element(WebElement element, String name) throws MyOwnNoSuchElementException{
    	try
    	{
            YamlElement yamlElement = pageUI.element(name);
            return (new WebDriverWait(session.driver, pageUI.timeout))
                .until(ExpectedConditions.presenceOfNestedElementLocatedBy(
                        element, findBy(yamlElement)));
    	}
    	catch(MyOwnNoSuchElementException e)
    	{
    		throw new MyOwnNoSuchElementException(pageUI.element(name)+" not found" +e);
    	}
    	catch(Exception e)
    	{
    		throw new MyOwnNoSuchElementException(pageUI.element(name)+" not found" +e);
    	}
    }
    
    /**
     * Method return elements
     * @param name
     * @return
     */
    public List<WebElement> elements(String name) throws MyOwnNoSuchElementException{
    	try
    	{
            YamlElement yamlElement = pageUI.element(name);
            if(yamlElement.container != null){
                return (new WebDriverWait(session.driver, pageUI.timeout))
                    .until(ExpectedConditions.presenceOfNestedElementsLocatedBy(
                            findBy(yamlElement.container), findBy(yamlElement)));
            }
            return findElements(yamlElement);
    	}
    	catch(MyOwnNoSuchElementException e)
    	{
    		throw new MyOwnNoSuchElementException(pageUI.element(name)+" not found" +e);
    	}
    	catch(Exception e)
    	{
    		throw new MyOwnNoSuchElementException(pageUI.element(name)+" not found" +e);
    	}
    }
    
    /**
     * Method return elements
     * @param containerElementName
     * @param name
     * @return
     */
    public List<WebElement> elements(String containerElementName, String name) throws MyOwnNoSuchElementException{
    	try
    	{
            YamlElement yamlElement = pageUI.element(name);
            return (new WebDriverWait(session.driver, pageUI.timeout))
                .until(ExpectedConditions.presenceOfNestedElementsLocatedBy(
                        findBy(yamlElement.container), findBy(yamlElement)));
    	}
    	catch(MyOwnNoSuchElementException e)
    	{
    		throw new MyOwnNoSuchElementException(pageUI.element(name)+" not found" +e);
    	}
    	catch(Exception e)
    	{
    		throw new MyOwnNoSuchElementException(pageUI.element(name)+" not found" +e);
    	}
    }    
    
    /**
     * Method return the visibleElement
     * @param name
     * @return
     */
    public WebElement visibleElement(String name) throws MyOwnNoSuchElementException{
    	try
    	{
            YamlElement yamlElement = pageUI.element(name);
            if(yamlElement.container != null){
                WebElement container = findElement(yamlElement);
                return (new WebDriverWait(session.driver, pageUI.timeout))
                    .until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(
                            container, findBy(yamlElement))).get(0);
            }
            return findVisibleElement(yamlElement);
    		
    	}
    	catch(MyOwnNoSuchElementException e)
    	{
    		throw new MyOwnNoSuchElementException(yamlElement+" not found" +e);
    	}
    	catch(Exception e)
    	{
    		throw new MyOwnNoSuchElementException(yamlElement+" not found" +e);
    	}
    }
    
    /**
     * Method return visibleElements
     * @param name
     * @return
     */
    public List<WebElement> visibleElements(String name){
    	try
    	{
            YamlElement yamlElement = pageUI.element(name);
            if(yamlElement.container != null){
                WebElement container = findElement(yamlElement);
                return (new WebDriverWait(session.driver, pageUI.timeout))
                    .until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(
                            container, findBy(yamlElement)));
            }
            return findVisibleElements(yamlElement);
	
    	}catch(Exception e)
    	{
    		throw new MyOwnNoSuchContextException("Some information: "+e);
    	}
    }
    
    /**
     * Method return findElement
     * @param yamlElement
     * @return
     */
    protected WebElement findElement(YamlElement yamlElement) throws MyOwnNoSuchElementException{
    	try
    	{
            if(pageUI.timeout > 0){
                return (new WebDriverWait(session.driver, pageUI.timeout))
                    .until(ExpectedConditions.presenceOfElementLocated(
                            findBy(yamlElement)));
            }
            return session.driver.findElement(findBy(yamlElement));

            catch(MyOwnNoSuchElementException e)
        	{
        		throw new MyOwnNoSuchElementException(yamlElement+" not found" +e);
        	}
        	catch(Exception e)
        	{
        		throw new MyOwnNoSuchElementException(pageUI.element(name)+" not found" +e);
        	}
    }
    
    /**
     * Method returns findElements
     * @param yamlElement
     * @return
     */
    protected List<WebElement> findElements(YamlElement yamlElement){
    	try
    	{
            if(pageUI.timeout > 0){
                return (new WebDriverWait(session.driver, pageUI.timeout))
                    .until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                            findBy(yamlElement)));
            }
            return session.driver.findElements(findBy(yamlElement));
    	}
    	catch(MyOwnNoSuchElementException e)
    	{
    		throw new MyOwnNoSuchElementException(yamlElement+" not found" +e);
    	}
    	catch(Exception e)
    	{
    		throw new MyOwnNoSuchElementException(yamlElement+" not found" +e);
    	}
    }
    
    /**
     * Method return visibleElement
     * @param yamlElement
     * @return
     */
    protected WebElement findVisibleElement(YamlElement yamlElement) throws MyOwnNoSuchElementException{
    	try
    	{
            if(pageUI.timeout > 0){
                return (new WebDriverWait(session.driver, pageUI.timeout))
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            findBy(yamlElement)));
            }
            return session.driver.findElement(findBy(yamlElement));	
    	}
    	catch(MyOwnNoSuchElementException e)
    	{
    		throw new MyOwnNoSuchElementException(yamlElement+" not found" +e);
    	}
    	catch(Exception e)
    	{
    		throw new MyOwnNoSuchElementException(yamlElement+" not found" +e);
    	}
    }
    
    /**
     * Method return visibleElements
     * @param yamlElement
     * @return
     */
    protected List<WebElement> findVisibleElements(YamlElement yamlElement) throws MyOwnNoSuchElementException{
    	try
    	{
            if(pageUI.timeout > 0){
                return (new WebDriverWait(session.driver, pageUI.timeout))
                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                            findBy(yamlElement)));
            }
            return session.driver.findElements(findBy(yamlElement));
    	}
    	catch(MyOwnNoSuchElementException e)
    	{
    		throw new MyOwnNoSuchElementException(yamlElement+" not found" +e);
    	}
    	catch(Exception e)
    	{
    		throw new MyOwnNoSuchElementException(yamlElement+" not found" +e);
    	}
    }
    
    /**
     * Method returns the locator
     * @param yamlElement
     * @return
     * @throws MyOwnNoSuchContextException 
     */
    protected By findBy(YamlElement yamlElement) throws MyOwnNoSuchContextException{
    	try
    	{
            switch(yamlElement.findBy){
            case("id"):
                return By.id(yamlElement.locator);
            case("name"):
                return By.name(yamlElement.locator);
            case("css"):
                return By.cssSelector(yamlElement.locator);
            case("cssSelector"):
                return By.cssSelector(yamlElement.locator);
            case("linkText"):
                return By.linkText(yamlElement.locator);
            case("partialLinkText"):
                return By.partialLinkText(yamlElement.locator);
            case("xpath"):
                return By.xpath(yamlElement.locator);
            case("class"):
                return By.className(yamlElement.locator);
            case("className"):
                return By.className(yamlElement.locator);
        }
    	}
    	catch(Exception e)
    	{
    		throw new MyOwnNoSuchContextException(yamlElement.locator+ " is not correct. Please check "+e);
    	}
        return By.cssSelector(yamlElement.locator);
    }
    
    /**
     * 
     * @param elementName from yaml spec file
     * @return 
     */
    public By findBy(String elementName){
        YamlElement yamlElement = pageUI.element(elementName);
        return findBy(yamlElement);
    }
    
    @Loggable(Loggable.INFO)
    public Boolean hasExpectedTitle() throws MyOwnWebDriverException{
    }
    try
    {
        return session.driver.getTitle().equals(pageUI.expectedTitle);	
    }
    catch(MyOwnWebDriverException e)
    {
    	throw new MyOwnWebDriverException(session.driver.getTitle()+ "and expectedTitle: "+pageUI.expectedTitle+" does not matched.");
    }
    }
    
    public void sendUnencryptedKeys(WebElement el, String encryptedText) throws MyOwnNoSuchContextException{
        try {
			el.sendKeys(encryptor.decrypt(encryptedText));
		} catch (Exception e) {
    		throw new MyOwnNoSuchContextException("Check sendUnencryptedKeys method: "+e);
		}
    }
    
    /**
     * check all expected elements are displayed
     * 
     * @return 
     * @throws MyOwnNoSuchContextException 
     */
    @Loggable(Loggable.INFO)
    public Boolean isDisplayed() throws MyOwnNoSuchContextException{
        for(YamlElement yamlElement:pageUI.expectedElements()){
            if(!findElement(yamlElement).isDisplayed()){
                return false;
            }
        }
        return true;
    }
    
    public void waitForElementToBeClickable(WebElement el, Integer timeout){
        WebDriverWait wait = new WebDriverWait(session.driver, timeout);
        wait.until(ExpectedConditions.elementToBeClickable(el));
    }
    
    public void waitForPageToLoad(){
        WebDriverWait wait = new WebDriverWait(session.driver, 60);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("body")));
    }


    public Boolean checkLayout() throws IOException {
        List<String> tags = new ArrayList();
        tags.add("all");
        String context = "";
        return checkLayout(context, tags);
    }
    
    public Boolean checkLayout(Object classObject) throws IOException {
        List<String> tags = new ArrayList();
        tags.add("all");
        String context = classObject.getClass().getSimpleName();
        return checkLayout(context, tags);
    }
    
    @Loggable(Loggable.INFO)
    public Boolean checkLayout(String context, List tags) throws IOException {
//        if(session.config.get("environment").equals("mobileweb")){
//            session.logger.info("CHECK LAYOUT: Function currently not available on Mobile Devices");
//            return true;
//        }

        //checkLayout(WebDriver driver, String specPath, List<String> includedTags,
        //      List<String> excludedTags, Properties properties,
        //      Map<String, Object> jsVariables, File screenshotFile) throws IOException {
        switchToNativeContext();
        File ss = ((TakesScreenshot) session.driver).getScreenshotAs(OutputType.FILE);
        BufferedImage ssImage = ImageIO.read(ss);
        Integer height = (int)(session.driver.manage().window().getSize().getHeight()/3.5);
        Integer width = (int)(session.driver.manage().window().getSize().getWidth()/3.5);
        Image img = ssImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
//        ssImage = (BufferedImage) img;

        BufferedImage buffImg = new BufferedImage(img.getWidth(null), img.getHeight(null), 5);
        java.awt.Graphics2D g2 = buffImg.createGraphics();
        g2.drawImage(img, null, null);
        g2.dispose();
        File ss2 = new File("target/abc");
        ImageIO.write(buffImg, "png", ss2);
        switchToPageContext();
        LayoutReport layoutReport = Galen.checkLayout(session.driver
                , specFile.getAbsolutePath(), tags, new ArrayList(), new Properties(),
                new HashMap(), ss2
        );
        String reportName = context + "_" + pageYamlFile + " - layout test";
        if(context.equals("")){
            reportName = pageYamlFile + " - layout test";
        }
        GalenTestInfo test = GalenTestInfo.fromString(reportName);
        test.getReport().layout(layoutReport
                , pageYamlFile + " - layout test");
        
        List<GalenTestInfo> tests = new ArrayList();
        tests.add(test);
        new HtmlReportBuilder().build(tests, "./target/testactivity/layout-tests");
        
        saveReportToJson(test);
        
        return layoutReport.errors() == 0;
    }
    
    private void saveReportToJson(GalenTestInfo test)throws JsonProcessingException, IOException{
        List<GalenTestInfo> tests = new ArrayList();
        tests.add(test);
        
        JsonReportBuilder jsonReportBuilder = new JsonReportBuilder();
        String jsonReportString = jsonReportBuilder.exportReportOverviewToJsonAsString(
                jsonReportBuilder.createReportOverview(tests));
        
        /** removing duplicate "failed" key from json **/
        jsonReportString = jsonReportString.replaceFirst("failed", "failedFirst");
        
        JSONObject jsonReport = new JSONObject(jsonReportString);
        JSONArray testReports = jsonReport.getJSONArray("tests");
        
        session.logger.info(
                "Layout Test Performed -- target/testactivity/layout-tests/" 
                        + testReports.getJSONObject(0).get("testId") + ".html");
        
        /** create single json file **/
        File jsonFile = new File("./target/testactivity/layout-tests/json/"
                + testReports.getJSONObject(0).get("testId") + ".json");
        jsonFile.getParentFile().mkdirs();
        jsonFile.createNewFile();
        BufferedWriter out = new BufferedWriter(new FileWriter(jsonFile));
        out.write(testReports.getJSONObject(0).toString());
        out.close();
    }
    
}