package nassy.runners;

import nassy.pages.MainPage;
import nassy.pages.SchedulePage;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import selenium.core.ui.DriverSingleton;

public class MainRunner {
    private static final String URL = System.getProperty("url");
    private final static Logger logger = Logger.getLogger(MainRunner.class);
    private MainPage mainPage;

    @BeforeClass
    public void setUp(){
        logger.info("Running script");
        mainPage = new MainPage().openURL(URL);
    }

    @AfterClass
    public void tearDown(){
        logger.info("Closing browser");
        DriverSingleton.kill();
    }

    @AfterMethod
    public void screenShot(ITestResult result) {
        if(ITestResult.FAILURE==result.getStatus()){
            try {
                mainPage.takeScreenshot();
                logger.info("Taking a screenshot");
            }catch (Exception e){
                logger.error("Failed to take a screenshot");
            }

        }
    }

    @Test(dataProvider = "testData")
    public void checkNearestDate(String locationName){
        SchedulePage schedulePage = mainPage.goToSchedule(locationName);
    }

    @DataProvider
    public Object[][] testData(){
        return new Object[][]{
                {"Some data#1"},
                {"Some data#2"}
        };
    }

}
