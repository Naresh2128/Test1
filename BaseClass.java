package BasePackage;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseClass {
	public static WebDriver driver;
	public void driverInit()
	{
//		System.setProperty("webdriver.chrome.driver","D:\\Tools\\chromedriver.exe" );
//		driver = new ChromeDriver();
		System.setProperty("webdriver.gecko.driver","D://Tools//geckodriver.exe");
		driver=new FirefoxDriver();
		driver.manage().window().maximize();
		driver.navigate().to("https://www.google.co.in");	
		wait_for_page_load(20);
		
	}
	
	@BeforeClass
	public void openBrowser()
	{
		driverInit();
	}
	@AfterClass
	public void closeBrowser() throws InterruptedException
	{
		driver.quit();
		Thread.sleep(3000);
	}
	public void scroll_down()
	{
		JavascriptExecutor js = ((JavascriptExecutor) driver);

		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	/*...................> JavaScriptExecutor code to Scroll down to particular web element <..........................*/

	public static void scroll_down_to_element(WebElement ele)
	{
		JavascriptExecutor js = ((JavascriptExecutor) driver);

		js.executeScript("arguments[0].scrollIntoView();", ele);
	}

	/*...................> JavaScriptExecutor code to click an element in chrome <..........................*/

	public static void click_element(WebElement ele)
	{
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+ele.getLocation().y+")");
		ele.click();
	}

	/*...................> Scrolling down using Robot Class <..........................*/

	public static void scroll_down_with_robot() throws AWTException
	{
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_PAGE_DOWN);
		robot.keyRelease(KeyEvent.VK_PAGE_DOWN);

	}

	/*...................> Implicitly wait Method to wait for loading a page <..........................*/

	public void wait_for_page_load(int time)
	{
		driver.manage().timeouts().implicitlyWait(time,TimeUnit.SECONDS);
	}

	/*...................> Explicitly wait Method for waiting an element in a page <..........................*/

	public void wait_for_element_present(WebElement wb)
	{
		WebDriverWait wait=new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.visibilityOf(wb));
	}

	/*...................> Explicitly wait Method for URL in a page <..........................*/

	public void wait_until_url_contains(String url)
	{
		WebDriverWait wait=new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.urlContains(url));
	}

	/*...................> Explicitly wait Method for URL in a page <..........................*/

	public void wait_until_text_present(WebElement wb,String text)
	{
		WebDriverWait wait=new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.textToBePresentInElement(wb, text));
	}

	/*...................> Method to use Fluent wait <..........................*/

	public void fluent_wait(WebElement ele)
	{
		FluentWait<WebDriver> wait=new FluentWait<WebDriver>(driver);
//		wait.withTimeout(30, TimeUnit.SECONDS);
//		wait.pollingEvery(5, TimeUnit.SECONDS);
		wait.ignoring(NoSuchElementException.class);
		wait.until(ExpectedConditions.elementToBeClickable(ele));
	}

	/*...................> Normal wait method using Thread.Sleep method <..........................*/
	public void wait_in_seconds(int i) throws InterruptedException
	{
		Thread.sleep(1000*i);
	}

	/*...................> Method to Accept an Alert in a browser <..........................*/

	public void accept_alert()
	{
		WebDriverWait wait=new WebDriverWait(driver,10);
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alt=driver.switchTo().alert();
		alt.accept();
	}

	/*...................> Method to Cancel an Alert in a browser <..........................*/

	public void cancel_alert()
	{
		WebDriverWait wait=new WebDriverWait(driver,10);
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert=driver.switchTo().alert();
		alert.dismiss();
	}

	/*...................> Method to get an alert text <..........................*/

	public String get_alert_text()
	{
		WebDriverWait wait=new WebDriverWait(driver,10);
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alt=driver.switchTo().alert();
		String text=alt.getText();
		return text;
	}

	/*...................> Method for switching to a frame using id <..........................*/

	public void swith_to_frame_using_id(String id)
	{
		driver.switchTo().frame(id);
	}

	/*...................> Method for switching to a frame using name <..........................*/

	public void swith_to_frame_using_name(String name)
	{
		driver.switchTo().frame(name);
	}

	/*...................> Method for switching to a frame using number <..........................*/

	public void swith_to_frame_using_number(String number)
	{
		driver.switchTo().frame(number);
	}

	/*...................> Method for switching to default content <..........................*/

	public void swith_to_defaulat_content()
	{
		driver.switchTo().defaultContent();
	}

	/*...................> Browser Navigation Methods <..........................*/

	public void navigate_back()
	{
		driver.navigate().back();
	}

	public void navigate_forword()
	{
		driver.navigate().forward();
	}

	public void navigate_refresh()
	{
		driver.navigate().refresh();
	}

	/*...................> Method to Navigate to windows <..........................*/

	public Iterator<String> getAllWindows() {
		Set<String> windows = driver.getWindowHandles();
		Iterator<String> itr = windows.iterator();
		return itr;
	}

	public void select_by_visibletext(WebElement ele, String text)
	{
		Select dropdown= new Select(ele);
		dropdown.selectByVisibleText(text);
	}

	public void select_by_value(WebElement ele, String value)
	{
		Select dropdown= new Select(ele);
		dropdown.selectByValue(value);
	}

	public void select_by_index(WebElement ele, int index)
	{
		Select dropdown= new Select(ele);
		dropdown.selectByIndex(index);
	}

	public void bootstrap_dropdown(List <WebElement> ele, String text) throws InterruptedException
	{
		/*for(int i=0;i<ele.size();i++)
		    {
			WebElement choice=ele.get(i);
			String innerhtml=choice.getText();
			if(innerhtml.contains("text"))
			{
				wait_in_seconds(5);
				choice.click();
				break;
			}
            }*/

		for(WebElement choice: ele)
		{
			if(choice.getText().contains(text))
			{
				choice.click();
				break;
			}
		}
	}
	public void handlingactions(WebElement ele)
	{
		Actions a=new Actions(driver);
		a.moveToElement(ele).build().perform();

	}

	public void drag_and_drop(WebElement e1, WebElement e2)
	{
		Actions a=new Actions(driver);
		a.dragAndDrop(e1, e2).build().perform();
	}

	public void set_position(int newpoint1, int newpoint2)
	{
		//int x=driver.manage().window().getPosition().getX();
		//int y=driver.manage().window().getPosition().getY();
		Point p=new Point(newpoint1,newpoint2);
		driver.manage().window().setPosition(p);
	}

	public void set_height_and_width(int height, int width)
	{
		//int x=driver.manage().window().getSize().getHeight();
		//int y=driver.manage().window().getSize().getWidth();
		Dimension d=new Dimension(height,width);
		driver.manage().window().setSize(d);
	}
	public void context_click(WebElement ele)
	{
		Actions a=new Actions(driver);
		a.contextClick(ele).build().perform();
	}

	public void double_click(WebElement ele)
	{
		Actions a=new Actions(driver);
		a.doubleClick(ele).build().perform();
	}

	public void file_upload(String path) throws InterruptedException, AWTException
	{
		Robot r=new Robot();
		wait_in_seconds(3);
		StringSelection s=new StringSelection(path);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);
		wait_in_seconds(4);
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_V);
		r.keyRelease(KeyEvent.VK_CONTROL);
		r.keyRelease(KeyEvent.VK_V);
		wait_in_seconds(3);
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
		wait_in_seconds(3);
	}


}

