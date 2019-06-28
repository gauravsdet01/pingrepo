package automation.session;

import static automation.utils.YamlReader.getYamlValue;
import static automation.utils.YamlReader.setYamlFilePath;

import java.awt.Toolkit;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;

import automation.utils.ConfigPropertyReader;
import automation.utils.DataReadWrite;
import automation.utils.DateUtil;
import automation.utils.Msg;
import automation.utils.SeleniumWait;
import automation.utils.TakeScreenshot;
//import keywords.GiveFeedback;
import keywords.LoginPage;

//import keywords.NetworkFeedbackVerification;
//import keywords.ResumeUpload;


public class SeleniumSession {

	protected WebDriver driver;
	private final WebDriverFactory wdfactory;
	String browser;
	String seleniumserver;
	String seleniumserverhost;
	String appbaseurl;
	String applicationpath;
	String chromedriverpath;
	String datafileloc = "";
	static int timeout;
	Map<String, Object> chromeOptions = null;
	DesiredCapabilities capabilities;

	/**
	 * Initiating the page objects
	 */

	
	public Msg msg;
	public DateUtil dateUtil;
	public TakeScreenshot takescreenshot;
	public SeleniumWait seleniumWait;
	public LoginPage loginPage;
	
	//public NetworkFeedbackVerification	networkFeedbackVerificationPage;

	public static HashMap<String, String> configSettings;

	public WebDriver getDriver() {
		
		return this.driver;
	}

	private void _initPage() {
		
		msg = new Msg();	
		dateUtil = new DateUtil();
		seleniumWait = new SeleniumWait(driver, Integer.parseInt(configSettings.get("timeout")));	
		loginPage = new LoginPage(driver);
		
		
	}

	/**
	 * Page object Initiation done
	 */

	public SeleniumSession(String testname) {
		_getSessionConfig();
		wdfactory = new WebDriverFactory();
		testInitiator(testname);
	}

	private void testInitiator(String testname) {
		Reporter.log(setYamlFilePath(), true);
		_configureBrowser();
		_initPage();
		takescreenshot = new TakeScreenshot(testname, this.driver);
	}

	private void _configureBrowser() {
		driver = wdfactory.getDriver(_getSessionConfig());
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(configSettings.get("timeout")), TimeUnit.SECONDS);
		
	}

	/*
	 * private Map<String, String> _getSessionConfig() { String[] configKeys = {
	 * "tier", "browser", "seleniumserver", "seleniumserverhost", "timeout",
	 * "driverpathchrome", "driverpathie" }; Map<String, String> config = new
	 * HashMap<String, String>(); for (String string : configKeys) {
	 * config.put(string, getProperty("./Config.properties", string)); } return
	 * config; }
	 */

	public static HashMap<String, String> _getSessionConfig() {
		// Read all value from Config file
		configSettings = ConfigPropertyReader.readAllPropertyVlauesFromConfigFile();
		// Read all property of system at run time
		Properties prop = System.getProperties();
		for (Object ob : configSettings.keySet()) {
			if (prop.keySet().contains(ob)) {
				configSettings.replace(ob.toString(), prop.get(ob).toString());
			}
		}
		return configSettings;
	}

	public void launchApplication() {
		System.out.println("Launch Application URL : " + getYamlValue("baseurl"));
		Reporter.log("\nThe application url is :- " + getYamlValue("baseurl"), true);
		launchApplication(getYamlValue("baseurl"));
	}

	public void launchApplication(String getnadaurl) {
		Reporter.log("\nThe application url is :- " + getnadaurl, true);
		Reporter.log("The test browser is :- " + _getSessionConfig().get("browser") + "\n", true);
		driver.manage().deleteAllCookies();
		driver.get(getnadaurl);
		if(configSettings.get("browser").equalsIgnoreCase("firefox")){
			java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			double width = screenSize.getWidth();
			double height = (int)screenSize.getHeight();
			driver.manage().window().setSize(new Dimension((int)width+5, (int)height+5));
		}else{
			System.out.println("Title of page is : "+driver.getTitle());
//			driver.manage().window().maximize();
		}
	}

	public void openUrl(String url) {
		driver.get(url);
		if(configSettings.get("browser").equalsIgnoreCase("firefox")){
			java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			double width = screenSize.getWidth();
			double height = (int)screenSize.getHeight();
			driver.manage().window().setSize(new Dimension((int)width+5, (int)height+5));
		}else{
			driver.manage().window().maximize();
		}
	}

	public void closeBrowserSession() {

		try {
			driver.quit();
			Thread.sleep(3000);// [INFO]: this to wait before you close every
								// thing
		} catch (Exception b) {
			b.getMessage();
		}
	}

	public void closeBrowserWindow() {
		driver.close();
	}

	public void stepStartMessage(String testStepName) {
		Reporter.log(" ", true);
		Reporter.log(" STARTING TEST STEP:- " + testStepName + " ", true);
		Reporter.log(" ", true);
	}
	
//	public void decryptURL(String url) throws Throwable {
////		String result = java.net.URLDecoder.decode(url, "UTF-8");
//		System.out.println(new java.net.URI(url).getPath());
////		DataReadWrite.writeDataToFile("result", result);
//	}
	public String decode(String url)  
    {  
              try {  
                   String prevURL="";  
                   String decodeURL=url;  
                   while(!prevURL.equals(decodeURL))  
                   {  
                        prevURL=decodeURL;  
                        decodeURL=URLDecoder.decode( decodeURL, "UTF-8" );  
                   }
                   DataReadWrite.writeDataToFile("decodeURL", decodeURL);
                   return decodeURL;  
              } catch (UnsupportedEncodingException e) {  
                   return "Issue while decoding" +e.getMessage();  
              }  
    }
	
	public void storeURL() {
		String url = DataReadWrite.readDataFromFile("URL");
		String decodedURL = decode(url);
		DataReadWrite.writeDataToFile("decodedURL", decodedURL);
	}
}