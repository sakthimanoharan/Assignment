package design;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public interface Browser {
	public RemoteWebDriver launchBrowser(String url);
	public RemoteWebDriver launchBrowser(String browser, String url);
	public WebElement locateElement(String locatorType, String value);	
	public WebElement locateElement(String value);
	public List<WebElement> locateElements(String type, String value);	
	public boolean verifyTitle(String title);
	public void close();
	public void quit();
	public void click(WebElement ele);
	public void append(WebElement ele, String data);
	public void clear(WebElement ele);
	public void clearAndType(WebElement ele,String data);
	public String getElementText(WebElement ele);	
	public String getTypedText(WebElement ele);
	public boolean verifyExactText(WebElement ele, String expectedText);
	public boolean verifyPartialText(WebElement ele, String expectedText);
	public boolean verifyExactAttribute(WebElement ele, String attribute, String value);
	public void verifyPartialAttribute(WebElement ele, String attribute, String value);
	public boolean verifyDisplayed(WebElement ele);	
	public boolean verifyEnabled(WebElement ele);	
	public void verifySelected(WebElement ele);
	public void navTo(String url);
}
