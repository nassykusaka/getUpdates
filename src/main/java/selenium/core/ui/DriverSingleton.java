package selenium.core.ui;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;


public class DriverSingleton {

    private DriverSingleton() {
    }

    private static WebDriver driver;
    private final static Logger logger = Logger.getLogger(DriverSingleton.class);
    private static String remoteURL = System.getenv("remoteURL");

    public static WebDriver getWebDriverInstance(String option) {

        if (driver != null) {
            return driver;
        }
        return driver = init(option);
    }

    private static WebDriver init(String option) {
        ChromeOptions co = new ChromeOptions();
        co.addArguments("--headless");
        switch (option.toLowerCase()) {
            case "local":
                logger.info("Running driver locally");
                driver = new ChromeDriver(co);
                break;
            case "remote":
                try {
                    logger.info("Running remote driver");
                    driver = new RemoteWebDriver(new URL(remoteURL), DesiredCapabilities.chrome());
                    break;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            default:
                driver = new ChromeDriver(co);
        }
        driver.manage().window().maximize();
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
