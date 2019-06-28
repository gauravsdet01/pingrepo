package automation.getpageobjects;

import static automation.getpageobjects.ObjectFileReader.getELementFromFile;
import static automation.utils.ConfigPropertyReader.getProperty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import automation.utils.ConfigPropertyReader;
import automation.utils.LayoutValidation;

import junit.framework.Assert;

public class GetPage extends BaseUi {

	private static final long THREAD_SLEEP = 0;
	protected WebDriver webdriver;
	String pageName;
	LayoutValidation layouttest;
	protected static String department;

	public GetPage(WebDriver driver, String pageName) {
		super(driver, pageName);
		this.webdriver = driver;
		this.pageName = pageName;
		layouttest = new LayoutValidation(driver, pageName);
	}

	public void testPageLayout(List<String> tagsToBeTested) {
		layouttest.checklayout(tagsToBeTested);
	}

	public void testPageLayout(String tagToBeTested) {
		testPageLayout(Arrays.asList(tagToBeTested));
	}

	public void testPageLayout() {
		testPageLayout(Arrays.asList(getProperty("./Config.properties", "browser")));
	}

	public void isElementNotDisplayed(List<WebElement> elements) {

		assertTrue(elements.size() < 1, "Element is display");
		msg.log("Element is not display");

	}

	// TODO: put this in right place, create dedicated class for frame and
	// window handlers
	protected void switchToNestedFrames(String frameNames) {
		switchToDefaultContent();
		String[] frameIdentifiers = frameNames.split(":");
		for (String frameId : frameIdentifiers) {
			wait.waitForFrameToBeAvailableAndSwitchToIt(getLocator(frameId.trim()));
		}
	}

	protected WebElement element(String elementToken) throws NoSuchElementException {
		waitjsForPageLoad(wait.timeout);
		return element(elementToken, "");
	}

	protected WebElement elementNoScroll(String elementToken) throws NoSuchElementException {
		// waitjsForPageLoad(wait.timeout);
		return elementNoScroll(elementToken, "");
	}

	public Object executeJavascript1(String js, Object... args) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		return executor.executeScript(js, args);
	}

	public void waitjsForPageLoad(long milliseconds) {
		boolean waitJS = false;
		long endTime;
		long startTime = System.currentTimeMillis();

		do {
			try {
				waitJS = executeJavascript1("return document.readyState").equals("complete");
				Thread.sleep(THREAD_SLEEP);
			} catch (InterruptedException e) {

			}

			endTime = System.currentTimeMillis();
		} while (!waitJS && endTime - startTime <= milliseconds);
		if (!waitJS) {

		}
		// System.out.println(" JS readyState Value : "+waitJS+" and time :
		// "+(endTime
		// - startTime));

	}

	protected WebElement element(String elementToken, String replacement) throws NoSuchElementException {
		WebElement elem = null;
		By locator = getLocator(elementToken, replacement);
		try {
			elem = wait.waitForElementToBeVisible(webdriver.findElement(locator));
			// scrollDown(elem);
		} catch (TimeoutException excp) {
			throw new NoSuchElementException("Element " + elementToken + " with locator "
					+ locator.toString().substring(2) + " not found on the " + this.pageName + " !!!");
		}
		return elem;
	}

	protected WebElement elementNoScroll(String elementToken, String replacement) throws NoSuchElementException {
		WebElement elem = null;
		By locator = getLocator(elementToken, replacement);
		try {
			elem = wait.waitForElementToBeVisible(webdriver.findElement(locator));

		} catch (TimeoutException excp) {
			throw new NoSuchElementException("Element " + elementToken + " with locator "
					+ locator.toString().substring(2) + " not found on the " + this.pageName + " !!!");
		}
		return elem;
	}

	protected WebElement elementNoScroll(String elementToken, String replacement1, String replacement2)
			throws NoSuchElementException {
		WebElement elem = null;
		By locator = getLocator(elementToken, replacement1, replacement2);
		try {
			elem = wait.waitForElementToBeVisible(webdriver.findElement(locator));

		} catch (TimeoutException excp) {
			throw new NoSuchElementException("Element " + elementToken + " with locator "
					+ locator.toString().substring(2) + " not found on the " + this.pageName + " !!!");
		}
		return elem;
	}

	protected WebElement element(String elementToken, String replacement1, String replacement2)
			throws NoSuchElementException {
		WebElement elem = null;
		By locator = getLocator(elementToken, replacement1, replacement2);
		try {
			elem = wait.waitForElementToBeVisible(webdriver.findElement(locator));
		} catch (TimeoutException excp) {
			throw new NoSuchElementException("Element " + elementToken + " with locator "
					+ locator.toString().substring(2) + " not found on the " + this.pageName + " !!!");
		}
		return elem;
	}

	protected WebElement element(String elementToken, String replacement1, String replacement2, String replacement3)
			throws NoSuchElementException {
		WebElement elem = null;
		By locator = getLocator(elementToken, replacement1, replacement2, replacement3);
		try {
			elem = wait.waitForElementToBeVisible(webdriver.findElement(locator));
		} catch (TimeoutException excp) {
			throw new NoSuchElementException("Element " + elementToken + " with locator "
					+ locator.toString().substring(2) + " not found on the " + this.pageName + " !!!");
		}
		return elem;
	}
	
	protected WebElement element(String elementToken, String replacement1, String replacement2, String replacement3, String replacement4)
			throws NoSuchElementException {
		WebElement elem = null;
		By locator = getLocator(elementToken, replacement1, replacement2, replacement3, replacement4);
		try {
			elem = wait.waitForElementToBeVisible(webdriver.findElement(locator));
		} catch (TimeoutException excp) {
			throw new NoSuchElementException("Element " + elementToken + " with locator "
					+ locator.toString().substring(2) + " not found on the " + this.pageName + " !!!");
		}
		return elem;
	}

	protected List<WebElement> elements(String elementToken, String replacement) {
		return (webdriver.findElements(getLocator(elementToken, replacement)));
	}

	protected List<WebElement> elements(String elementToken, String replacement1, String replacement2) {
		return webdriver.findElements(getLocator(elementToken, replacement1, replacement2));
	}

	protected List<WebElement> elements(String elementToken) {
		return elements(elementToken, "");
	}

	protected void isStringMatching(String actual, String expected) {
		Assert.assertEquals(actual, expected);
		logMessage("ACTUAL STRING : " + actual);
		logMessage("EXPECTED STRING :" + expected);
		logMessage("String compare Assertion passed.");
	}
	
	public void clickIfElementDisplayedOnScreen(String elementName) {
		if (ifElementDisplayedOnScreen(elementName)) {
			element(elementName).click();
		}
		else {
			msg.log(elementName +"- Not present on Screen");
		}
	}
	
	protected boolean ifElementDisplayedOnScreen(String elementName) {
		boolean isElementPresent = false;
        try {
        	element(elementName).isDisplayed();
            isElementPresent = true;
        } catch (Exception e) {
            isElementPresent = false;
        }
        System.out.println(isElementPresent+":::::::::Boolean vlaue::::::::");
        return isElementPresent;
	}

	protected boolean isElementDisplayed(String elementName, String elementTextReplace) {
		wait.waitForElementToBeVisible(element(elementName, elementTextReplace));
		boolean result = element(elementName, elementTextReplace).isDisplayed();
		assertTrue(result,
				"ASSERT FAILED: element '" + elementName + "with text " + elementTextReplace + "' is not displayed.");
		logMessage("ASSERT PASSED: element " + elementName + " with text '" + elementTextReplace + "' is displayed.");
		return result;
	}

	protected boolean isElementDisplayedCondition(String elementName, String elementTextReplace) {
		wait.hardWait(1);
		int numberOfElement = elements(elementName, elementTextReplace).size();
		if (numberOfElement > 0) {
			return true;
		} else {
			return false;
		}

		// boolean result = element(elementName,
		// elementTextReplace).isDisplayed();
		// logMessage("ASSERT PASSED: element " + elementName + " with text '" +
		// elementTextReplace + "' is displayed.");
		// return result;
	}
	//
	// protected boolean isElementNotDisplayedCondition(String elementName,
	// String elementTextReplace) {
	// wait.waitForElementToBeVisible(element(elementName, elementTextReplace));
	// boolean result = element(elementName, elementTextReplace).isDisplayed();
	// logMessage("ASSERT PASSED: element " + elementName + " with text '" +
	// elementTextReplace + "' is displayed.");
	// return result;
	// }
	
	protected boolean isElementDisplayed(String elementName, String elementTextReplace1, String elementTextReplace2,String elementTextReplace3) {
		wait.waitForElementToBeVisible(element(elementName, elementTextReplace1, elementTextReplace2,elementTextReplace3));
		boolean result = element(elementName, elementTextReplace1, elementTextReplace2,elementTextReplace3).isDisplayed();
		assertTrue(result, "TEST FAILED: element '" + elementName + "with text " + elementTextReplace1
				+ elementTextReplace2 + "' is not displayed.");
		logMessage("TEST PASSED: element " + elementName + " with text " + elementTextReplace1 + " " + " "
				+ elementTextReplace2 +  " "
				+ elementTextReplace3 + " is displayed.");
		return result;
	}

	protected boolean isElementDisplayed(String elementName, String elementTextReplace1, String elementTextReplace2) {
		wait.waitForElementToBeVisible(element(elementName, elementTextReplace1, elementTextReplace2));
		boolean result = element(elementName, elementTextReplace1, elementTextReplace2).isDisplayed();
		assertTrue(result, "TEST FAILED: element '" + elementName + "with text " + elementTextReplace1
				+ elementTextReplace2 + "' is not displayed.");
		logMessage("TEST PASSED: element " + elementName + " with text " + elementTextReplace1 + " " + " "
				+ elementTextReplace2 + " is displayed.");
		return result;
	}

	protected boolean isElementDisplayedNoScroll(String elementName, String elementTextReplace) {
		wait.waitForElementToBeVisible(elementNoScroll(elementName, elementTextReplace));
		boolean result = elementNoScroll(elementName, elementTextReplace).isDisplayed();
		assertTrue(result,
				"ASSERT FAILED: element '" + elementName + "with text " + elementTextReplace + "' is not displayed.");
		logMessage("ASSERT PASSED: element " + elementName + " with text '" + elementTextReplace + "' is displayed.");
		return result;
	}

	protected void verifyElementText(String elementName, String expectedText) {
		wait.waitForElementToBeVisible(element(elementName));
		assertEquals(element(elementName).getText().trim(), expectedText,
				"ASSERT FAILED: element '" + elementName + "' Text is not as expected: ");
		logMessage("ASSERT PASSED: element " + elementName + " is visible and Text is " + expectedText);
	}

	protected void verifyElementText(String elementName, String replacement, String expectedText) {
		wait.waitForElementToBeVisible(element(elementName, replacement));
		assertEquals(element(elementName, replacement).getText().trim(), expectedText,
				"ASSERT FAILED: element '" + elementName + "' Text is not as expected: ");
		logMessage("ASSERT PASSED: element " + elementName + " is visible and Text is " + expectedText);
	}

	protected void verifyElementTextContains(String elementName, String expectedText) {
		wait.waitForElementToBeVisible(element(elementName));
		assertThat("ASSERT FAILED: element '" + elementName + "' Text is not as expected: ",
				element(elementName).getText().trim(), containsString(expectedText));
		logMessage("ASSERT PASSED: element " + elementName + " is visible and Text is " + expectedText);
	}

	protected boolean isElementDisplayed(String elementName) throws NoSuchElementException {
		wait.hardWait(1);
		boolean result = wait.waitForElementToBeVisible(element(elementName)).isDisplayed();
		assertTrue(result, "ASSERT FAILED: element '" + elementName + "' is not displayed.");
		logMessage("ASSERT PASSED: element " + elementName + " is displayed.");
		return result;
	}
	
	protected boolean isElementNotDisplayed(String elementName , String elementReplaceText) {
		boolean result;
		result = checkIfElementIsNotThere(elementName);
		assertTrue(result, "[Assertion Failed] : element '" + elementName + "' is displayed which should not be there");
		msg.log("[ASSERT PASSED]: element " + elementName
				+ " is not displayed after waiting for 1 minute on the page as expected!!!");
		return result;
	}

	protected boolean checkIfElementIsNotThere(String eleString) {
		boolean flag = false;
		try {
			if (webdriver.findElement(getLocator(eleString)).isDisplayed()) {
				flag = false;
			} else {
				flag = true;
			}
		} catch (NoSuchElementException ex) {
			flag = true;
		}
		return flag;
	}

	protected boolean isElementsDisplayed(String elementName) throws NoSuchElementException {
		boolean result = wait.waitForElementToBeVisible(elements(elementName).get(0)).isDisplayed();
		assertTrue(result, "ASSERT FAILED: element '" + elementName + "' is not displayed.");
		logMessage("ASSERT PASSED: element " + elementName + " is displayed.");
		return result;
	}

	protected boolean isElementDisplayedNoScroll(String elementName) throws NoSuchElementException {
		boolean result = wait.waitForElementToBeVisible(elementNoScroll(elementName)).isDisplayed();
		assertTrue(result, "ASSERT FAILED: element '" + elementName + "' is not displayed.");
		logMessage("ASSERT PASSED: element " + elementName + " is displayed.");
		return result;
	}

	protected boolean isElementEnabled(String elementName, boolean expected) {
		wait.waitForElementToBeVisible(element(elementName));
		boolean result = expected && element(elementName).isEnabled();
		assertTrue(result, "ASSERT FAILED: element '" + elementName + "' is  ENABLED :- " + !expected);
		logMessage("ASSERT PASSED: element " + elementName + " is enabled :- " + expected);
		return result;
	}

	protected By getLocator(String elementToken) {
		return getLocator(elementToken, "");
	}

	protected By getLocator(String elementToken, String replacement) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		locator[2] = locator[2].replaceAll("\\$\\{.+\\}", replacement);
		return getBy(locator[1].trim(), locator[2].trim());
	}

	protected By getLocator(String elementToken, String replacement1, String replacement2) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		locator[2] = locator[2].replaceFirst("\\$\\{.+?\\}", replacement1);
		locator[2] = locator[2].replaceFirst("\\$\\{.+?\\}", replacement2);
		return getBy(locator[1].trim(), locator[2].trim());
	}

	protected By getLocator(String elementToken, String replacement1, String replacement2, String replacement3) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		locator[2] = locator[2].replaceFirst("\\$\\{.+?\\}", replacement1);
		locator[2] = locator[2].replaceFirst("\\$\\{.+?\\}", replacement2);
		locator[2] = locator[2].replaceFirst("\\$\\{.+?\\}", replacement3);
		return getBy(locator[1].trim(), locator[2].trim());
	}
	
	protected By getLocator(String elementToken, String replacement1, String replacement2, String replacement3, String replacement4) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		locator[2] = locator[2].replaceFirst("\\$\\{.+?\\}", replacement1);
		locator[2] = locator[2].replaceFirst("\\$\\{.+?\\}", replacement2);
		locator[2] = locator[2].replaceFirst("\\$\\{.+?\\}", replacement3);
		locator[2] = locator[2].replaceFirst("\\$\\{.+?\\}", replacement4);
		return getBy(locator[1].trim(), locator[2].trim());
	}

	private By getBy(String locatorType, String locatorValue) {
		switch (Locators.valueOf(locatorType)) {
		case id:
			return By.id(locatorValue);
		case xpath:
			return By.xpath(locatorValue);
		case css:
			return By.cssSelector(locatorValue);
		case name:
			return By.name(locatorValue);
		case classname:
			return By.className(locatorValue);
		case linktext:
			return By.linkText(locatorValue);
		case partialLinkText:
			return By.partialLinkText(locatorValue);
		default:
			return By.id(locatorValue);
		}
	}

	public void acceptAlert() {
		try {
			WebDriverWait wait = new WebDriverWait(driver,10);
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			alert.accept();
			msg.log("Alert handled");
			driver.switchTo().defaultContent();
		} catch (Exception e) {
			msg.log("No alert  window appeared ,exception = " + e);
		}
		// wait.waitForPageToLoadCompletely();
		//switchToDefaultContent();
		wait.hardWait(1);
	}

	public boolean isGivenElementIsDisplayed(String elementName, String replacement1, String replacement2) {
		try {
			if (element(elementName, replacement1, replacement2).isDisplayed())
				return true;
		} catch (NoSuchElementException nse) {
			return false;
		}
		return false;
	}

	protected boolean checkIfIsElementDisplayed(String elementName) {
		boolean result = false;
		try {
			wait.resetImplicitTimeout(10);
			result = element(elementName).isDisplayed();
			return result;
		} catch (NoSuchElementException nse) {
			return result;
		} catch (AssertionError nse) {
			return result;
		} finally {
			int configTimeout = Integer.parseInt(ConfigPropertyReader.getProperty("timeout"));
			wait.resetImplicitTimeout(configTimeout);
		}
	}

	protected boolean checkIfIsElementDisplayed(String elementName, String replacement) {
		boolean result = false;
		try {
			wait.resetImplicitTimeout(10);
			wait.waitForElementToBeVisible(element(elementName, replacement));
			scrollDown(element(elementName, replacement));
			result = element(elementName, replacement).isDisplayed();
			return result;
		} catch (NoSuchElementException nse) {
			return result;
		} catch (AssertionError nse) {
			return result;
		} finally {
			int configTimeout = Integer.parseInt(ConfigPropertyReader.getProperty("timeout"));
			wait.resetImplicitTimeout(configTimeout);
		}
	}

	protected boolean checkIfIsElementDisplayed(String elementName, String replacement1, String replacement2,
			String replacement3) {
		boolean result = false;
		try {
			wait.resetImplicitTimeout(10);
			result = element(elementName, replacement1, replacement2, replacement3).isDisplayed();
			return result;
		} catch (NoSuchElementException nse) {
			return result;
		} catch (AssertionError nse) {
			return result;
		} finally {
			int configTimeout = Integer.parseInt(ConfigPropertyReader.getProperty("timeout"));
			wait.resetImplicitTimeout(configTimeout);
		}
	}

	public void setDepartment(String dept) {
		department = dept;
	}

	public String getCurrentTimeStamp() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		long currentTimeStamp = timestamp.getTime();
		String strCurrentTimeStamp = Long.toString(currentTimeStamp);
		return strCurrentTimeStamp;
	}

	public String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String currentDate = dateFormat.format(date);
		System.out.println(currentDate);
		return currentDate;
	}

	public void isActualStringContainsExpectedString(String actual, String expected) {
		logMessage("Comparing 2 strings");
		logMessage("ACTUAL STRING :" + actual);
		logMessage("EXPECTED STRING :" + expected);
		Assert.assertTrue("Actual String Does Not Contains Expected String!!!", actual.contains(expected));
		logMessage("ASSERTION Passed : Actual String Contains Expected String");
	}

	public boolean isStringNumericOrNot(String num) {
		wait.hardWait(5);
		if (num.matches("-?\\d+(\\.\\d+)?")) {
			logMessage("Element " + num + " is Numeric");
			return true;
		}

		else
			logMessage("Element " + num + " is Not Numeric");
		return false;
	}
	
	public void hoverOnGivenDate(String date) {
		
		  wait.hardWait(2);
		  System.out.println("date::" + date);
		  hover(driver.findElement(By.cssSelector("#dv_" + date + " .date.ng-binding")));
		  msg.log("Hover on given date " + date);
		  wait.hardWait(1);
		 }
	
	
}
