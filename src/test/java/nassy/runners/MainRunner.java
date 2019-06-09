package nassy.runners;

import nassy.pages.MainPage;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import selenium.core.ui.DriverSingleton;

public class MainRunner {
    private static final String URL =  System.getProperty("url");
    private final static Logger logger = Logger.getLogger(MainRunner.class);
    private static final String LOCATION = System.getProperty("locationName");
    private static final String APP_TYPE = System.getProperty("appointmentType");
    private static final String SUB_TYPE = System.getProperty("subType");
    private MainPage mainPage;

    @BeforeClass
    public void setUp(){
        logger.info("Running script");
        mainPage = new MainPage().openURL(URL).chooseLanguage("English");
        mainPage.grabBrowserLog();
    }

    @AfterClass
    public void tearDown(){
        mainPage.grabBrowserLog();
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
    public void checkNearestDate(String locationName, String appointmentType, String subType){
        Assert.assertEquals(1, mainPage.goToSchedule(locationName).chooseAppointmentType(appointmentType, subType));
    }

    @DataProvider
    public Object[][] testData(){
        return new Object[][]{
                {LOCATION, APP_TYPE, SUB_TYPE}
        };
    }
}
