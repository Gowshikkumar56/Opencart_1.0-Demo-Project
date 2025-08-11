package testCases;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC_001_AccountRegistrationTest extends BaseClass
{	
	@Test(groups = {"Master","Sanity"})
	public void verify_account_registration() throws InterruptedException
	{
		try 
		{
			HomePage hp=new HomePage(driver);
			hp.clickMyAccount();
			logger.info("*********Account is cliked*******");
			hp.clickRegister();
			logger.info("Registering.........");
			
			AccountRegistrationPage regpage=new AccountRegistrationPage(driver);
			
			regpage.setFirstName(randomeString().toUpperCase());
			regpage.setLastName(randomeString().toUpperCase());
			regpage.setEmail(randomeString()+"@gmail.com");// randomly generated the email
			String password=randomAlphaNumeric();
			regpage.setPassword(password);
			regpage.setPrivacyPolicy();
			logger.info("All details are Filled.....");
			regpage.clickContinue();
			
			String confmsg=regpage.getConfirmationMsg();
			Assert.assertEquals(confmsg, "Your Account Has Been Created!");
		}
		catch(Exception e)
		{
			logger.error("Test Failed....Sorry please check the issues");
			logger.debug("Debug logs...");
			Assert.fail();
		}
	}
	
	
	
	
}








