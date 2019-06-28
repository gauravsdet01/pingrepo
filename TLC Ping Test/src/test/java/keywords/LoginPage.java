package keywords;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import com.nexmo.client.NexmoClientException;

import automation.getpageobjects.GetPage;
import automation.report.SendSMS;
import junit.framework.Assert;

public class LoginPage extends GetPage {

	WebDriver driver;
	private String testname;

	public LoginPage(WebDriver driver) {
		super(driver, "LoginPage");
		this.driver = driver;
	}

	public void Logo() throws IOException, NexmoClientException {
		if(ifElementDisplayedOnScreen("logoTLC") == true)
		{
		msg.log("Website is Up");
		}
		else
		msg.log("Website is Down");
		SendSMS SO = new SendSMS();
		SO.FailSMS();
			
	}

	void enterPassword(String password) {
		isElementDisplayed("password");
		element("password").clear();
		element("password").sendKeys(password);
		msg.log("enter password as Suspense");
	}

	void clickOnContinueWithEmail() {
		isElementDisplayed("continueWithEmail");
		element("continueWithEmail").click();
		msg.log("Click on continue with Email button !!");
	}
	
	void clickOnLogin() {
		isElementDisplayed("LoginEnter");
		element("LoginEnter").click();
		msg.log("Click on Login button !!");
	}

	void clickOnGivenPanel(String panelName) {
		isElementDisplayed("lnk_sidePanel", panelName);
		element("lnk_sidePanel", panelName).click();
		msg.log("Click On Panel " + panelName);
		wait.hardWait(1);
	}
	
	public boolean IsGivenPanelDisplay(String panelName)
	 {
	  return  isElementDisplayed("loginSuccess", panelName);
	 }

	
	
	public void verifyUserLoggedOutSuccessfully() {
		wait.waitForPageToLoadCompletely();
		Assert.assertTrue(isElementDisplayed("signinbtn"));
	}
	

	public Boolean isUserOnLoginPage(){
		
		return IsGivenPanelDisplay("Login Panel");
	}


}
