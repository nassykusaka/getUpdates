package nassy.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SchedulePage extends AbstractPage {
    private final static Logger logger = Logger.getLogger(SchedulePage.class);
    private static final By CONTINUE_BUTTON_LOCATOR = By.xpath("//a[contains(text(), 'Continue')]");

    public int chooseAppointmentType(String typeName, String subTypeName){
        pause(2);
        By chooseAppointmentLinkLocator = By.xpath("//div[contains(text(), '" + typeName
                + "')]/following-sibling::div/following-sibling::div/a");
        By chooseSubTypeLinkLocator = By.xpath("//div[contains(text(), '" + subTypeName + "')]/following-sibling::div/a");
        wait.until(ExpectedConditions.elementToBeClickable(chooseAppointmentLinkLocator));
        logger.info("Click on desired type of appointment");
        driver.findElement(chooseAppointmentLinkLocator).click();
        logger.info("Wait for the sub-type appointment page");
        wait.until(ExpectedConditions.elementToBeClickable(chooseSubTypeLinkLocator));
        driver.findElement(chooseSubTypeLinkLocator).click();
        pause(5);
        driver.findElement(CONTINUE_BUTTON_LOCATOR).click();
        takeScreenshot();
        return driver.findElements(By.tagName("captcha")).size();
    }
}
