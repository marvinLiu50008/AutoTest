package autotest;

import java.io.File;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AutotestDemo {

	private WebDriver driver = null;
	private Properties prop = null;
	private String destFile = "D:\\testtest.jpg";

	public void search() throws Exception {
		driver.get("https://www.google.com/imghp?hl=EN");
        driver.manage().window().maximize(); 
        Thread.sleep(2000);
        driver.findElements(By.xpath("//div[@aria-label=\"Search by image\"]")).get(0).click();
        Thread.sleep(2000);
        driver.findElements(By.xpath("//*[contains(text(), 'Upload an image')]")).get(0).click();
        Thread.sleep(2000);
	}
	
	@Test
    public void test_notFound() throws Exception{
		search();
		driver.findElement(By.name("encoded_image")).sendKeys("D:\\1.jpg");
        Thread.sleep(2000);
		Assert.assertTrue(driver.findElements(By.xpath("//*[contains(text(), 'No other sizes of this image found.')]")).size() > 0);
	}
	
	@Test
    public void test_found() throws Exception{
		search();
		driver.findElement(By.name("encoded_image")).sendKeys("D:\\iManage_logo.png");
        Thread.sleep(2000);
        
        String visitResult = prop.getProperty("VISIT_RESULT");
    	WebElement visitPage = driver.findElement(By.xpath("//*[@id=\"rso\"]/div[".concat(visitResult).concat("]/div/div[1]/a")));
    	visitPage.click();
    	Thread.sleep(3000);
    	
    	for(String winHandle : driver.getWindowHandles()) { 
    		driver.switchTo().window(winHandle);
    	}
    	File srcfile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcfile, new File(destFile));
	}
	
	@BeforeMethod
	public void init() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\public.QGN14091801\\Downloads\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		prop = new Properties();
        try {
            prop.load(this.getClass().getResourceAsStream("test.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	@AfterMethod
	public void afterTest() {
		driver.quit();
	}
}
