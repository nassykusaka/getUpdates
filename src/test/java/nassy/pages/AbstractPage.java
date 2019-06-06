package nassy.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.core.ui.DriverSingleton;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import static java.util.concurrent.TimeUnit.SECONDS;

public class AbstractPage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    private final static Logger logger = Logger.getLogger(AbstractPage.class);

    public AbstractPage(WebDriver driver) {
        this.driver = DriverSingleton.getWebDriverInstance();
        wait = new WebDriverWait(driver, 60);
    }

    public String getPageTitle() {return driver.getTitle();}

    public void refreshPage() {driver.navigate().refresh();}

    public void goBack(){driver.navigate().back();}

    public void goBack(int n){
        for (int i = 0; i < n; i ++){
            driver.navigate().back();
        }
    }

    public static void pause(int i){
        try{
            SECONDS.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void takeScreenshot(){
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            String screenshotName = "screenshot/src" + System.nanoTime();
            File copy = new File(screenshotName + ".png");
            FileUtils.copyFile(screenshot, copy);
            logger.info("Saved screenshot: " + screenshotName);
        } catch (IOException e){
            logger.error("Failed to take screenshot");
        }
    }
}
