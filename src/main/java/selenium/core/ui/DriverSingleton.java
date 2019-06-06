package selenium.core.ui;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class DriverSingleton {

    private DriverSingleton() { }

    private static WebDriver driver;
    private final static Logger logger = Logger.getLogger(DriverSingleton.class);

    public static WebDriver getWebDriverInstance() {

        if (driver != null) {
            return driver;
        }
        return driver = init();
    }

    private static WebDriver init(){
        ChromeOptions co = new ChromeOptions();
        co.addArguments("--start-maximized");
        driver = new ChromeDriver(co);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        return driver;
    }

    public static void kill() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                logger.info("Cannot kill browser");
            } finally {
                driver = null;
            }
        }
    }
}
