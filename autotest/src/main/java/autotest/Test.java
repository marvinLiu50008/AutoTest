package autotest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Test {

    public static void main(String[] args) {
    	System.setProperty("webdriver.chrome.driver", "C:\\Users\\public.QGN14091801\\Downloads\\chromedriver_win32\\chromedriver.exe");
    	WebDriver driver = null;
    	try {
	        driver = new ChromeDriver();
	        
	        Properties prop = new Properties();
	        prop.load(Test.class.getClassLoader().getResourceAsStream("test.properties"));
	        String visitResult = prop.getProperty("VISIT_RESULT");
	        
	        driver.get("https://www.google.com/imghp?hl=EN");
	        driver.manage().window().maximize(); 
	        Thread.sleep(1000);
	        driver.findElements(By.xpath("//div[@aria-label=\"Search by image\"]")).get(0).click();
	        Thread.sleep(1000);
	        driver.findElements(By.xpath("//*[contains(text(), 'Upload an image')]")).get(0).click();
	        Thread.sleep(1000);
	        driver.findElement(By.name("encoded_image")).sendKeys("D:\\iManage_logo.png");
	        System.out.println(driver.getTitle());
	        Thread.sleep(3000);
	        System.out.println(driver.findElements(By.xpath("//*[contains(text(), 'No other sizes of this image found.')]")).size());
	        System.out.println(driver.findElements(By.xpath("//*[contains(text(), 'Find other sizes of this image:')]")).size());
	        
	        try {
	        	WebElement visitPage = driver.findElement(By.xpath("//*[@id=\"rso\"]/div[".concat(visitResult).concat("]/div/div[1]/a")));
	        	visitPage.click();
				Thread.sleep(3000);
				
				for(String winHandle : driver.getWindowHandles()) { 
		    		driver.switchTo().window(winHandle);
		    	}
				File srcfile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		        FileUtils.copyFile(srcfile, new File("D:\\testtest.jpg"));
			} catch (org.openqa.selenium.NoSuchElementException nse) {
				System.out.println("No such element found.");
			}
    	} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (driver != null) {
				driver.quit();
			}
		}
    }
}
