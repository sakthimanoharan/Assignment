package base;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.model.Author;
import com.aventstack.extentreports.model.Category;
import com.aventstack.extentreports.model.Log;
import com.aventstack.extentreports.model.ScreenCapture;
import com.aventstack.extentreports.model.Screencast;
import com.aventstack.extentreports.model.Test;

import design.Browser;
import utils.Reporter;

public class SeleniumBase extends Reporter implements Browser {

	public RemoteWebDriver driver;
	public WebDriverWait wait;

	int i=1;
	
	@Override
	public void click(WebElement ele) 
	{
		String text="";
		try 
		{
			wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			text = ele.getText();
			ele.click();
			reportStep("The Element "+text+" clicked", "pass"); 
		} 
		catch (StaleElementReferenceException e) 
		{
			reportStep("The Element "+text+" could not be clicked", "fail");
			throw new RuntimeException();
		} 
	}

	@Override
	public void append(WebElement ele, String data) 
	{
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(ele));
		ele.sendKeys(data);
	}

	@Override
	public void clear(WebElement ele) 
	{
		try 
		{
			ele.clear();
			reportStep("The field is cleared Successfully", "pass");
		} 
		catch (ElementNotInteractableException e) 
		{
			reportStep("The field is not Interactable", "fail");
			throw new RuntimeException();
		}
	}

	@Override
	public void clearAndType(WebElement ele, String data)
	{
		try 
		{
			ele.clear();
			ele.sendKeys(data);
			reportStep("The Data :"+data+" entered Successfully", "pass");
		} 
		catch (ElementNotInteractableException e) 
		{
			reportStep("The Element "+ele+" is not Interactable", "fail");
			throw new RuntimeException();
		}
	}

	@Override
	public String getElementText(WebElement ele) 
	{
		String text = ele.getText();
		return text;
	}

	@Override
	public String getTypedText(WebElement ele) 
	{
		String attributeValue = ele.getAttribute("value");
		return attributeValue;
	}

	@Override
	public boolean verifyExactText(WebElement ele, String expectedText) 
	{
		try 
		{
			if(ele.getText().equals(expectedText)) 
			{
				reportStep("The expected text contains the actual "+expectedText,"pass");
				return true;
			}
			else 
			{
				reportStep("The expected text doesn't contain the actual "+expectedText,"fail");
			}
		} 
		catch (WebDriverException e) 
		{
			System.out.println("Unknown exception occured while verifying the Text");
		} 

		return false;
	}

	@Override
	public boolean verifyPartialText(WebElement ele, String expectedText) 
	{
		try 
		{
			if(ele.getText().contains(expectedText)) 
			{
				reportStep("The expected text contains the actual "+expectedText,"pass");
				return true;
			}
			else 
			{
				reportStep("The expected text doesn't contain the actual "+expectedText,"fail");
			}
		} catch (WebDriverException e) 
		{
			System.out.println("Unknown exception occured while verifying the Text");
		} 

		return false;
	}

	@Override
	public boolean verifyExactAttribute(WebElement ele, String attribute, String value) 
	{
		try 
		{
			if(ele.getAttribute(attribute).equals(value)) 
			{
				reportStep("The expected attribute :"+attribute+" value contains the actual "+value,"pass");
				return true;
			}else 
			{
				reportStep("The expected attribute :"+attribute+" value does not contains the actual "+value,"fail");
			}
		} 
		catch (WebDriverException e) 
		{
			System.out.println("Unknown exception occured while verifying the Attribute Text");
		}
		return false;
	}

	@Override
	public void verifyPartialAttribute(WebElement ele, String attribute, String value) 
	{
		try 
		{
			if(ele.getAttribute(attribute).contains(value)) 
			{
				reportStep("The expected attribute :"+attribute+" value contains the actual "+value,"pass");
			}
			else 
			{
				reportStep("The expected attribute :"+attribute+" value does not contains the actual "+value,"fail");
			}
		} 
		catch (WebDriverException e) 
		{
			System.out.println("Unknown exception occured while verifying the Attribute Text");
		}
	}

	@Override
	public boolean verifyDisplayed(WebElement ele) 
	{
		try 
		{
			if(ele.isDisplayed()) 
			{
				reportStep("The element "+ele+" is visible","pass");
				return true;
			} 
			else 
			{
				reportStep("The element "+ele+" is not visible","fail");
			}
		} 
		catch (WebDriverException e) 
		{
			System.out.println("WebDriverException : "+e.getMessage());
		} 
		return false;
	}

	@Override
	public boolean verifyEnabled(WebElement ele) 
	{
		try 
		{
			if(ele.isEnabled()) 
			{
				reportStep("The element "+ele+" is Enabled","pass");
				return true;
			} 
			else 
			{
				reportStep("The element "+ele+" is not Enabled","fail");
			}
		} 
		catch (WebDriverException e) 
		{
			System.out.println("WebDriverException : "+e.getMessage());
		}
		return false;
	}

	@Override
	public void verifySelected(WebElement ele) 
	{
		try 
		{
			if(ele.isSelected()) 
			{
				reportStep("The element "+ele+" is selected","pass");
				//				return true;
			} else 
			{
				reportStep("The element "+ele+" is not selected","fail");
			}
		} 
		catch (WebDriverException e) 
		{
			System.out.println("WebDriverException : "+e.getMessage());
		}
		
	}

	@Override
	public RemoteWebDriver launchBrowser(String url) 
	{
		return launchBrowser("chrome", url);
	}

	@Override
	public RemoteWebDriver launchBrowser(String browser, String url) 
	{
		try 
		{
			if(browser.equalsIgnoreCase("chrome")) 
			{
				driver = new ChromeDriver();
			} 
			else if(browser.equalsIgnoreCase("firefox")) 
			{
				driver = new FirefoxDriver();
			} 
			else if(browser.equalsIgnoreCase("ie")) 
			{
				driver = new InternetExplorerDriver();
			}
			driver.navigate().to(url);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		} 
		catch (Exception e) 
		{
			reportStep("Browser not launched. Hence failed", "fail");
			throw new RuntimeException();
		} 
		return driver;
	}
	
	@Override
	public void navTo(String url)
	{
		driver.navigate().to(url); 
	}

	@Override
	public WebElement locateElement(String locatorType, String value) 
	{
		try 
		{
			switch(locatorType.toLowerCase()) 
			{
			case "id": 
				return driver.findElement(By.id(value));
			case "name": 
				return driver.findElement(By.name(value));
			case "class": 
				return driver.findElement(By.className(value));
			case "link": 
				return driver.findElement(By.linkText(value));
			case "xpath": 
				return driver.findElement(By.xpath(value));
			}
		} 
		catch (NoSuchElementException e) 
		{
			reportStep("The Element with locator:"+locatorType+" Not Found with value: "+value, "fail");
			throw new RuntimeException();
		}
		catch (Exception e) 
		{
			reportStep("The Element with locator:"+locatorType+" Not Found with value: "+value, "fail");
		}
		return null;
	}

	@Override
	public WebElement locateElement(String value) 
	{
		WebElement findElementById = driver.findElement(By.id(value));
		return findElementById;
	}

	@Override
	public List<WebElement> locateElements(String type, String value) 
	{
		try 
		{
			switch(type.toLowerCase()) 
			{
			case "id": 
				return driver.findElements(By.id(value));
			case "name": 
				return driver.findElements(By.name(value));
			case "class": 
				return driver.findElements(By.className(value));
			case "link": 
				return driver.findElements(By.linkText(value));
			case "xpath": 
				return driver.findElements(By.xpath(value));
			}
		} 
		catch (NoSuchElementException e) 
		{
			System.err.println("The Element with locator:"+type+" Not Found with value: "+value);
			throw new RuntimeException();
		}
		return null;
	}

	@Override
	public boolean verifyTitle(String title) 
	{
		if (driver.getTitle().equals(title)) 
		{
			System.out.println("Page title: "+title+" matched successfully");
			return true;
		} 
		else 
		{
			System.out.println("Page url: "+title+" not matched");
		}
		return false;
	}

	

	@Override
	public long takeSnap() 
	{
		long number = (long) Math.floor(Math.random() * 900000000L) + 10000000L; 
		try 
		{
//			File file=new File("./reports/images/"+number+".jpg"); 
//			File capture=driver.getScreenshotAs(OutputType.FILE); 
//			FileHandler.copy(capture, file);
			FileUtils.copyFile(driver.getScreenshotAs(OutputType.FILE) , new File("./reports/images/"+number+".jpg"));
		} 
		catch (WebDriverException e) 
		{
			System.out.println("The browser has been closed.");
		} 
		catch (IOException e) 
		{
			System.out.println("The snapshot could not be taken");
		}
		return number;
	}
	@Override
	public void close() 
	{
		driver.close();
	}

	@Override
	public void quit() {
		driver.quit();
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestStarted(Test test) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNodeStarted(Test node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLogAdded(Test test, Log log) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCategoryAssigned(Test test, Category category) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAuthorAssigned(Test test, Author author) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScreenCaptureAdded(Test test, ScreenCapture screenCapture) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScreenCaptureAdded(Log log, ScreenCapture screenCapture) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScreencastAdded(Test test, Screencast screencast) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTestList(List<Test> testList) {
		// TODO Auto-generated method stub
		
	}
}
