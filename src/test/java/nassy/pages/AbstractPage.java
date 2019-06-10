package nassy.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.core.ui.DriverSingleton;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import static java.util.concurrent.TimeUnit.SECONDS;

public class AbstractPage {
    WebDriver driver;
    WebDriverWait wait;
    private final static Logger logger = Logger.getLogger(AbstractPage.class);

    AbstractPage() {
        this.driver = DriverSingleton.getWebDriverInstance("local");
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

    static void pause(int i){
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

    public void grabBrowserLog(){
        LogEntries logs = driver.manage().logs().get("browser");
        logger.info(logs);
    }

    public void setCookie(String name, String value, String domain, String path){
        logger.info("Setting cookies");
        Cookie cookie = new Cookie.Builder(name, value)
                .domain(domain)
                .isHttpOnly(true)
                .path(path)
                .build();

        driver.manage().addCookie(cookie);
    }
}
