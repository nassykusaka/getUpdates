package selenium.core.ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

public class DriverSingleton {

    private DriverSingleton() {
    }

    private static WebDriver driver;

    public static WebDriver getWebDriverInstance() {

        if (driver == null) {
            driver = new ChromeDriver();
            driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            driver.manage().window().maximize();
        }
        return driver;
    }

    public static void kill() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.out.println("Cannot kill browser");
            } finally {
                driver = null;
            }
        }
    }

}
