package nassy.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MainPage extends AbstractPage {
    private final static Logger logger = Logger.getLogger(MainPage.class);
    private static final By SERVICE_LINK_LOCATOR = By.xpath("//div[contains(@class, 'footer__list-content-wrapper')]/span[text()='Service']");
    private static final By SCHEDULE_LINK_LOCATOR = By.xpath("(//a[@class='teaser__link'])[12]");

    public MainPage openURL(String url){
        driver.get(url);
        return this;
    }

    public MainPage chooseLanguage(String language){
        By activeLanguageLocator = By.xpath("//span[@class='metanavigation__active-item-content']/abbr[@title='" + language + "']");
        logger.info("Check if desired language is chosen");
        if (driver.findElements(activeLanguageLocator).isEmpty()){
            logger.info("Choosing " + language);
            driver.findElement(By.xpath("//abbr[@title='" + language + "']/../..")).click();
        }
        return this;
    }

    public SchedulePage goToSchedule(String location){
        driver.findElement(SERVICE_LINK_LOCATOR).click();
        wait.until(ExpectedConditions.titleContains("Service"));
        driver.findElement(SCHEDULE_LINK_LOCATOR).click();
        By LOCATION_LINK_LOCATOR = By.xpath("//strong[text()='" + location + "']/..");
        driver.findElement(LOCATION_LINK_LOCATOR).click();
        String parentWindow = driver.getWindowHandle();
        logger.info("Switch to the opened window");
        for (String winHandle: driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }
        return new SchedulePage();
    }
}