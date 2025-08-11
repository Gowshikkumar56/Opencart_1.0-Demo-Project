package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;


/*Data is valid  - login success - test pass  - logout
Data is valid -- login failed - test fail

Data is invalid - login success - test fail  - logout
Data is invalid -- login failed - test pass
*/

public class TC_003_LoginDDT extends BaseClass
{

	@Test(dataProvider="LoginData", dataProviderClass=utilities.DataProviders.class, groups = {"Master","DataDriven"})
	public void verify_loginDDT(String email, String password, String exp) 
	{
		logger.info("**** Starting TC_003_LoginDDT *****");
		
		try {
			logger.info("Email: " + email);
			logger.info("Password" + password);
		//Home page
			HomePage hp=new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin(); //Login link under MyAccount
				
			//Login page
			LoginPage lp=new LoginPage(driver);
			lp.setEmail(email);
			lp.setPassword(password);
			lp.clickLogin(); //Login button
				
			//My Account Page
			MyAccountPage macc=new MyAccountPage(driver);
			boolean targetPage=macc.isMyAccountPageExists();
			
			if(exp.equalsIgnoreCase("Valid"))
			{
				if(targetPage==true)
				{
					Thread.sleep(3000);
					logger.info("Login successful");
					hp.clickMyAccount();
					logger.info("Logging out");
					macc.clickLogout();
					Assert.assertTrue(true);
				}
				else
				{
					Assert.assertTrue(false);
				}
			}
			
			if(exp.equalsIgnoreCase("Invalid"))
			{
				if(targetPage==true)
				{
					Thread.sleep(3000);
					hp.clickMyAccount();
					logger.info("Login failed but My Account page is displayed, which is not expected");
					macc.clickLogout();
					logger.info("Logging out");
					Assert.assertTrue(false);
				}
				else
				{
					
					Assert.assertTrue(true);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("An exception occurred gowshik: " + e.getMessage());
			Assert.fail("An exception occurred: " + e.getMessage());
		}
			
		logger.info("**** Finished TC_003_LoginDDT *****");
	}
	
}








