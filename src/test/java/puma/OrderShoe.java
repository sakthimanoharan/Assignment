package puma;

import java.io.IOException;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import base.SeleniumBase;
import objects.Pages;

public class OrderShoe extends SeleniumBase {

	@BeforeMethod
	public void beforeMethod() 
	{
		driver = launchBrowser("chrome", "https://in.puma.com/in/en/account/login?action=login_with_email");
		node = test.createNode(testCaseName);
	}

	@AfterMethod
	public void afterMethod() 
	{
		close();
	}
	
	@BeforeClass
	public void beforeClass() throws IOException
	{
		report();
	}
	
	@BeforeTest
	public void beforeTest()
	{
		testCaseName = "Login and Order Shoe";
		testDescription = "Login into Puma site and Order a shoe";
		authors = "Sakthi";
		category = "Sanity";
	}
	
	@Test
	public void pumaOrderShoe() throws InterruptedException 
	{
		//Test data
		String shoeSize="13";
		String[] shoeColor= {"Black","Gray","Green","White"};
		String productName="PUMA ONE 4 Synthetic FG Kids' Football Boots";
		String userName="sakthi893340@gmail.com";
		String password="Ma$k@2021";
		String menShoe="https://in.puma.com/in/en/mens/mens-shoes";
		String priceAmount="2689";
		
		
		//Login Page - Username, Password,Submit
		click(locateElement("id",Pages.btnBanner_Id));
		append(locateElement("id",Pages.email_Id), userName);
		append(locateElement(Pages.password_Id),password);
		Thread.sleep(3000);
		click(locateElement(Pages.btnSubmit_Id));
		
		System.out.println(driver.getTitle());
		Thread.sleep(3000);
		//Verify account name
		String text = getElementText(locateElement("xpath",Pages.accountName_Xpath));
		if(text.equals("Sakthi Manoharan"))
		{
			System.out.println("Loggedin successfully");
		}
		
		//Navigate to Men shoe
		navTo(menShoe);
		
		//Size filter section
		click(locateElement("xpath",Pages.sizeFilter_Xpath));
		List<WebElement> sizeElements = locateElements("xpath",Pages.listOfSize_Xpath);
		for (WebElement webElement : sizeElements) 
		{
			String textSize=webElement.getText();
			if(textSize.contains(shoeSize))
			{
				webElement.click();
				if(verifyExactAttribute(webElement,"aria-pressed","true"))
				{
					System.out.println("Size: " +textSize+ " selected");
				}
				else
				{
					System.out.println("Size: " +textSize+ " not selected");
				}
			}
		}
		click(locateElement("xpath",Pages.sizeFilter_Xpath));
		
		//Color filter section
		click(locateElement("xpath",Pages.colorFilter_Xpath));
		List<WebElement> colorElements = locateElements("xpath",Pages.listOfColor_Xpath);
		for (WebElement webElement : colorElements) {
			String textColor=webElement.getText();
			if(textColor.contains(shoeColor[0])||textColor.contains(shoeColor[1])||textColor.contains(shoeColor[2])||textColor.contains(shoeColor[3]))
			{
				webElement.click();
				if(verifyExactAttribute(webElement,"aria-pressed","true"))
				{
					System.out.println("Color: " +textColor+ "selected");
				}
				else
				{
					System.out.println("Color: " +textColor+ "not selected");
				}
			}
		}
		click(locateElement("xpath",Pages.colorFilter_Xpath));
		
		//Verify the products
		String totalProducts =getElementText(locateElement("xpath",Pages.productList_Xpath));
		System.out.println("Total Products: "+totalProducts);
		
		//Select the product-Shoe
		WebElement product = locateElement("xpath",Pages.product_Xpath);
		String productText = getElementText(product);
		product.click();
		
		//Verify product title
		WebElement productTitle = locateElement(Pages.productTitle_Id);
		String text3 = getElementText(productTitle);
		if(productText.equals(text3))
		{
			System.out.println("Product selection matched");
		}
		//Verify price,size
		String price = getElementText(locateElement("xpath",Pages.productPrice_Xpath));
		price=price.replace(",", "");
		price=price.replace("₹", "");
		boolean priceMatch = price.equals(priceAmount);
		System.out.println("Price amount match:" +priceMatch);
		WebElement sizeElement = locateElement("xpath",Pages.sizeValue_Xpath);
		System.out.println("Size match:" +verifyExactText(sizeElement,"13"));
		click(sizeElement);

		click(locateElement("xpath",Pages.addToCart_Xpath));
		//Minicart
		System.out.println("Added to cart text: "+verifyDisplayed(locateElement("xpath",Pages.addedToCart_Xpath)));
		click(locateElement("xpath",Pages.goToCart_Xpath));
		//Carts page
		System.out.println("Cart Title is displayed: "+verifyDisplayed(locateElement("xpath",Pages.cartTitle_Xpath)));
		System.out.println("Cart product title: "+verifyExactText(locateElement("xpath",Pages.cartProductTitle_Xpath), text3));
		verifyExactText(locateElement("xpath",Pages.cartProductSize_Xpath), shoeSize);
		System.out.println("Verify price of cart: "+verifyExactText(locateElement("xpath",Pages.cartEstimatedTotal_Xpath), priceAmount));
		click(locateElement("xpath",Pages.cartCheckOut_Xpath));


	}

}
