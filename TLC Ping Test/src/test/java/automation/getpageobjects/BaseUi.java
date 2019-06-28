/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package automation.getpageobjects;

import static automation.getpageobjects.ObjectFileReader.getPageTitleFromFile;
import static automation.utils.ConfigPropertyReader.getProperty;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import automation.utils.DateUtil;
import automation.utils.Msg;
import automation.utils.SeleniumWait;

public class BaseUi {

	WebDriver driver;
	public SeleniumWait wait;
	public DateUtil dateUtil;
	public Msg msg;
	private String pageName;
	int numberOfDaysToUpdate;

	protected BaseUi(WebDriver driver, String pageName) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		this.pageName = pageName;
		this.wait = new SeleniumWait(driver, Integer.parseInt(getProperty(
				"Config.properties", "timeout")));
		this.msg = new Msg();
		dateUtil = new DateUtil();
	}

	protected String getPageTitle() {
		return driver.getTitle();
	}

	protected void logMessage(String message) {
		msg.log(message);
	}

	protected String getCurrentURL() {
		return driver.getCurrentUrl();
	}

	public void verifyPageTitleExact() {
		String pageTitle = getPageTitleFromFile(pageName);
		verifyPageTitleExact(pageTitle);
	}

	public void verifyPageTitleExact(String expectedPagetitle) {
		if (((expectedPagetitle == "") || (expectedPagetitle == null) || (expectedPagetitle
				.isEmpty()))
				&& (getProperty("browser").equalsIgnoreCase("chrome"))) {
			expectedPagetitle = getCurrentURL();
		}
		try {
			wait.waitForPageTitleToBeExact(expectedPagetitle);
			logMessage("ASSERT PASSED: Verified PageTitle as "
					+ expectedPagetitle + "'");
		} catch (TimeoutException ex) {
			Assert.fail("ASSERT FAILED: PageTitle for " + pageName
					+ " is not exactly: '" + expectedPagetitle
					+ "'!!!\n instead it is :- " + driver.getTitle());
		}
	}

	/**
	 * Verification of the page title with the title text provided in the page
	 * object repository
	 */
	protected void verifyPageTitleContains() {
		String expectedPagetitle = getPageTitleFromFile(pageName).trim();
		verifyPageTitleContains(expectedPagetitle);
	}

	/**
	 * this method will get page title of current window and match it partially
	 * with the param provided
	 * 
	 * @param expectedPagetitle
	 *            partial page title text
	 */
	public void verifyPageTitleContains(String expectedPagetitle) {

		if (((expectedPagetitle == "") || (expectedPagetitle == null) || (expectedPagetitle
				.isEmpty()))
				&& (getProperty("browser").equalsIgnoreCase("chrome"))) {
			expectedPagetitle = getCurrentURL();
		}
		try {
			wait.waitForPageTitleToContain(expectedPagetitle);
		} catch (TimeoutException exp) {
			String actualPageTitle = driver.getTitle().trim();
			logMessage("ASSERT FAILED: As actual Page Title: '"
					+ actualPageTitle
					+ "' does not contain expected Page Title : '"
					+ expectedPagetitle + "'.");
		}
	
	}

	protected WebElement getElementByIndex(List<WebElement> elementlist,
			int index) {
		return elementlist.get(index);
	}

	protected WebElement getElementByExactText(List<WebElement> elementlist,
			String elementtext) {
		WebElement element = null;
		for (WebElement elem : elementlist) {
			if (elem.getText().equalsIgnoreCase(elementtext.trim())) {
				element = elem;
			}
		}
		// FIXME: handle if no element with the text is found in list No element
		// exception
		if (element == null) {
		}
		return element;
	}

	protected WebElement getElementByContainsText(List<WebElement> elementlist,
			String elementtext) {
		WebElement element = null;
		for (WebElement elem : elementlist) {
			if (elem.getText().contains(elementtext.trim())) {
				element = elem;
			}
		}
		// FIXME: handle if no element with the text is found in list
		if (element == null) {
		}
		return element;
	}

	protected void switchToFrame(WebElement element) {
		// switchToDefaultContent();
		wait.waitForElementToBeVisible(element);
		driver.switchTo().frame(element);
	}

	public void switchToFrame(int i) {
		driver.switchTo().frame(i);
	}

	public void switchToFrame(String id) {
		driver.switchTo().frame(id);
	}

	public void switchToDefaultContent() {
		driver.switchTo().defaultContent();
	}

	protected void executeJavascript(String script) {
		((JavascriptExecutor) driver).executeScript(script);
	}
	
	protected Object executeJavaScriptTest(String script)
	{
		return ((JavascriptExecutor) driver).executeScript(script);
		
	}
	
	 public String executeJavascriptWithStringReturn(String script)
     {
         return (String)((JavascriptExecutor)driver).executeScript(script);
     }

	protected void clickUsingJS(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()",
				element);
	}

	protected void hover(WebElement element) {
		Actions hoverOver = new Actions(driver);
		hoverOver.moveToElement(element).build().perform();
	}

	protected void handleAlert() {
		try {
			// holdExecution(2000);
			switchToAlert().accept();
			logMessage("Alert handled..");
			switchToDefaultContent();
		} catch (Exception e) {
			// System.out.println("Exception = "+e);
			msg.log("No Alert window appeared...");
		}
	}

	private Alert switchToAlert() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public String getAlertText() {
		Alert alert = driver.switchTo().alert();
		return alert.getText();
	}

	
	protected void scrollDown(WebElement element) {
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView(true);", element);
	}
	
	 protected void scrollDown2(WebElement element) {
		  JavascriptExecutor jse = (JavascriptExecutor) driver;
		  jse.executeScript("window.scrollBy(0, -100000)");
		  hardWait(5);
		  ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		  hardWait(1);
		  jse.executeScript("window.scrollBy(0,-350)");
		  hardWait(3);
		 }
	

	protected void hoverClick(WebElement element) {
		Actions hoverClick = new Actions(driver);
		hoverClick.moveToElement(element).click().build().perform();
	}

	protected void click(WebElement element) {
		wait.waitForElementToBeVisible(element);
		wait.waitForElementToBeClickable(element);
		try {
			//scrollDown(element);
			element.click();
			System.out.println(element);
			logMessage("Clicked Element " + element);
		} catch (StaleElementReferenceException ex1) {
			scrollDown(element);
			clickUsingJavaScript(element);
		} catch (WebDriverException ex3) {
			clickUsingJavaScript(element);
			logMessage("Clicked Element " + element
					+ " after catching WebDriver Exception");
		}
	}
	
	protected void enterText(WebElement element, String text) {
		wait.waitForElementToBeClickable(element);
		click(element);
		element.clear();
		wait.hardWait(2);
		element.sendKeys(text);
		logMessage("Entered Text " + text);
	}
	
	protected void clickUsingJavaScript(WebElement element){
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", element);
	}
	
	public void performClickByActionBuilder(WebElement element) {
		Actions builder = new Actions(driver);
		builder.moveToElement(element).build().perform();
		builder.moveToElement(element).click().perform();
	}	

//	public static String getPageTextOfPdf(String fileURL, int pageNumber) {
//		PDFParser parser;
//		String parsedText = null;
//
//		PDFTextStripper pdfStripper = null;
//		PDDocument pdDoc = null;
//		COSDocument cosDoc = null;
//
//		InputStream input;
//		try {
//			input = new URL(fileURL).openStream();
//			parser = new PDFParser(input);
//
//			parser.parse();
//			cosDoc = parser.getDocument();
//			pdfStripper = new PDFTextStripper();
//			pdDoc = new PDDocument(cosDoc);
//			pdfStripper.setStartPage(pageNumber);
//			pdfStripper.setEndPage(pageNumber);
//			parsedText = pdfStripper.getText(pdDoc);
//			cosDoc.close();
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return parsedText;
//	}

	protected void holdExecution(int milliSeconds) {
		try {
			Thread.sleep(milliSeconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void switchWindow() {
		System.out.println("window size " + driver.getWindowHandles().size());
		int windowSize = driver.getWindowHandles().size();
		if (windowSize == 1) {
			holdExecution(4000);
		}
		for (String current : driver.getWindowHandles()) {
			driver.switchTo().window(current);
		}
	}

	public void closeCurrentWindow() {
	
		driver.close();
		
	}

	public void switchToMainWindow(String mainWindow) {
		driver.switchTo().window(mainWindow);
	}

	public void switchToOtherWindow(String mainWindow) {
		holdExecution(2000);
		System.out.println("window size " + driver.getWindowHandles().size());
		for (String current : driver.getWindowHandles()) {
			if (!current.equals(mainWindow)) {
				driver.switchTo().window(current);
			} else {
				driver.switchTo().window(mainWindow);
			}
		}
	}

	public void pageRefresh() {
		driver.navigate().refresh();
	}
	
	public void navigateToBackPage() {
		driver.navigate().back();
		logMessage("Step : navigate to back page\n");
	}

	protected void selectDropDownValue(WebElement el, int index) {

		try {
			wait.waitForElementToBeVisible(el);
			scrollDown(el);
			Select sel = new Select(el);
			sel.selectByIndex(index);
		} catch (StaleElementReferenceException ex1) {
			// wait.waitForElementToBeVisible(el);
			// scrollDown(el);
			Select sel = new Select(el);
			sel.selectByIndex(index);
			logMessage("select Element " + el
					+ " after catching Stale Element Exception");
		} catch (Exception ex2) {
			logMessage("Element " + el + " could not be clicked! "
					+ ex2.getMessage());
		}
	}

	protected void selectDropDownValueByValue(WebElement el, String value) {
		try {
			
			
			wait.waitForElementToBeVisible(el);
			scrollDown2(el);
			Select sel = new Select(el);
			sel.selectByVisibleText(value);

		} catch (StaleElementReferenceException ex1) {
			// wait.waitForElementToBeVisible(el);
			// scrollDown(el);
			Select sel = new Select(el);
			sel.selectByVisibleText(value);
			logMessage("select Element " + el
					+ " after catching Stale Element Exception");
		} catch (Exception ex2) {
			logMessage("Element " + el + " could not be clicked! "
					+ ex2.getMessage());
		}
	}
	
	protected void selectDropDownValueByValueAttribute(WebElement el, String value) {
			wait.waitForElementToBeVisible(el);
			scrollDown2(el);
			Select sel = new Select(el);
			sel.selectByValue(value);		
	}
	

	protected void selectValueInDropDownByText(WebElement el , String value)
	{
		
		wait.waitForElementToBeVisible(el);
		scrollDown(el);
		clickUsingXpathInJavaScriptExecutor(el);
		//el.click();
		wait.hardWait(5);
		driver.findElement(By.xpath("//option[text()='"+value+"']")).click();
		msg.log("Select value "+value+" in dropdown !!");
		
	}
	
	protected void selectProvidedTextFromDropDownCheckBox(WebElement el, String text) {

		try {
			
			el.click();
			
		} catch (StaleElementReferenceException ex1) {
			// wait.waitForElementToBeVisible(el);
			// scrollDown(el);
			Select sel = new Select(el);
			sel.selectByVisibleText(text);
			logMessage("select Element " + el
					+ " after catching Stale Element Exception");
		} catch (Exception ex2) {
			logMessage("Element " + el + " could not be clicked! "
					+ ex2.getMessage());
		}
	}
	
	protected void selectProvidedTextFromDropDown(WebElement el, String text) {

		try {
			wait.waitForElementToBeVisible(el);
			scrollDown(el);
			Select sel = new Select(el);
			sel.selectByVisibleText(text);
		} catch (StaleElementReferenceException ex1) {
			// wait.waitForElementToBeVisible(el);
			// scrollDown(el);
			Select sel = new Select(el);
			sel.selectByVisibleText(text);
			logMessage("select Element " + el
					+ " after catching Stale Element Exception");
		} catch (Exception ex2) {
			logMessage("Element " + el + " could not be clicked! "
					+ ex2.getMessage());
		}
	}

	
	
	protected String getSelectedValueOfDropDown(WebElement el) {
		wait.waitForElementToBeVisible(el);
		scrollDown(el);

		Select sel = new Select(el);
		String monthValue = sel.getFirstSelectedOption().getText();
		// int value = Integer.parseInt(monthValue);
		return monthValue;
	}

	protected int getListOfTheElements(List<WebElement> el) {
		numberOfDaysToUpdate=0;
		try {
			System.out.println("in method");
			hardWait(10);
			// wait.waitForElementToBeVisible(el);
			List<WebElement> daysList = el;
			numberOfDaysToUpdate = daysList.size();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return numberOfDaysToUpdate;
	}

	public void clickUsingXpathInJavaScriptExecutor(WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
		System.out.println("button clicked");
	}
	
	public void searchElementUsingJavaScriptExecutor()
	{
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("document.getElementById('successmsgFromDate');");
	}
	public void hardWait(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	protected void scrollUpBy(int n) {
		wait.hardWait(4);
		executeJavascript("window.scrollBy(0,-" + n + ")");
		//selenium.getEval("scrollBy(0, 250)");
		wait.hardWait(2);
	}

	protected void scrollDownBy(int n) {
		executeJavascript("window.scrollBy(0," + n + ")");		
	}

	protected List<WebElement> verifyIsDisplayedDropDownValue(WebElement el) {

		wait.waitForElementToBeVisible(el);
		scrollDown(el);
		Select sel = new Select(el);
		List<WebElement> element = sel.getOptions();

		return element;
	}

	protected void scrollToDefaultSize() {
		executeJavascript("window.scrollTo(0,0)");
		msg.log("Window scroll to default size !!");
		wait.hardWait(2);
	}

	public boolean getResponseCode(String url) {
		boolean isValid = false;
		try {
			URL u = new URL(url);
			HttpURLConnection h = (HttpURLConnection) u.openConnection();
			h.setRequestMethod("GET");
			h.connect();
			if (h.getResponseCode() == 200) {
				isValid = true;
			} else {
				logMessage("link : " + url + " is broken.\n");
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return isValid;
	}

	public boolean isGivenStringIsPresentInGivenStringList(String givenString,
			List<String> givenStringList) {

		for (String str : givenStringList) {
			if (str.equalsIgnoreCase(givenString))
				return true;
		}
		return false;
	}

	public int noOfMatchesOccursBetweenGivenDates(String fromDate,
			String toDate, List<String> matchesList) {
		int noOfMatches = 0;
		int noOfDays = DateUtil.daysDifferenceBetweenFirstAndSecondDate(
				fromDate, toDate, "yyyy-MM-dd");
		for (int i = 1; i <= noOfDays; i++) {
			if (isGivenStringIsPresentInGivenStringList(fromDate, matchesList)) {
				noOfMatches++;
			}
			fromDate = DateUtil.getNextDateFromGivenDateFoGivenDateModule(
					"yyyy-MM-dd", "day", fromDate, 1);
		}
		return noOfMatches;
	}
	

}
