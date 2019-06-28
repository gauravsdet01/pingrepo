package tests;

import java.awt.AWTException;
import java.io.IOException;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.nexmo.client.NexmoClientException;

import automation.session.SeleniumSession;
import automation.utils.YamlReader;

public class LoginPageTest {
	SeleniumSession test;
	
	
   @Test
   public void OpenBrowserWindow() throws IOException, NexmoClientException {
		test = new SeleniumSession(this.getClass().getSimpleName());
		test.launchApplication();
		test.loginPage.Logo();
	}
  //public void PingTest() throws IOException, NexmoClientException {
//	   test.loginPage.Logo();
//	}

	
	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.takescreenshot.takeScreenShotOnException(result);
	}

	@AfterClass(alwaysRun = true)
	public void Close_Test_Session() {
		test.closeBrowserSession();
	}

}
