package appium;

import Helpers.JsonParserHelper;
import com.google.gson.JsonObject;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestBase {
    public static AndroidDriver<WebElement> driver; //AppiumDriver <MobileElement> appiumDriver
    public static DesiredCapabilities desiredCapabilities;
    public static WebDriverWait wait;

    public void takeLocalScreenshot(String imageName) throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("failureScreenshots/" + imageName + ".png"));
    }

    //    Properties props;
//    InputStream inputStream;
    JsonParserHelper jsonParserHelper;
    JsonObject fbLoginCredentials;
    @BeforeSuite
    public void setUP() throws MalformedURLException {

        //File file = new File("src\\main\\resources\\Swvl.apk");

        //path to the project
        File rootPath = new File(System.getProperty("user.dir"));
        //path to folder where is the apk
        File appDir = new File(rootPath, "src\\test\\resources");
        //name of the apk
        File app = new File(appDir, "Swvl.apk");

        try {
            desiredCapabilities = new DesiredCapabilities();
            fbLoginCredentials = new JsonObject();
            jsonParserHelper= new JsonParserHelper();

            desiredCapabilities.setCapability("platformName", "Android");
            desiredCapabilities.setCapability("platformVersion", "8.0");
            desiredCapabilities.setCapability("deviceName", "Pixel3");
//        desiredCapabilities.setCapability("locationServicesAuthorized", "true");
            desiredCapabilities.setCapability("autoGrantPermissions", "true");//to accept the location or any required permissions
            desiredCapabilities.setCapability("autoAcceptAlerts", "true");
            desiredCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);
            desiredCapabilities.setCapability("fullReset", "true");
            desiredCapabilities.setCapability("dontStopAppOnReset", "false");
            desiredCapabilities.setCapability("noReset", "false");

            //Keep the app running
            //  desiredCapabilities.setCapability("autoAcceptAlerts", "true");  Capabilities.SetCapability("noReset", true);
            //        Capabilities.SetCapability("fullReset", false);


            desiredCapabilities.setCapability("app", app.getAbsolutePath());//src\main\resources\Swvl.apk
            URL url = new URL("http://0.0.0.0:4723/wd/hub");
            driver = new AndroidDriver<WebElement>(url, desiredCapabilities); //or = new AppiumDriver<MobileElement>(url,desiredCapabilities);
//            driver.toggleLocationServices();
            driver.setLocation(new Location(30, 31, 10));
            driver.manage().timeouts().implicitlyWait(500, TimeUnit.SECONDS);

            wait = new WebDriverWait(driver, 500);

            fbLoginCredentials=jsonParserHelper.getJsonObject("src/main/resources/FbLoginCredentials.json");
        } catch (Exception e) {
            System.out.println("Cause is" + e.getCause());
            System.out.println("Cause is" + e.getMessage());
        }

    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(final ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE || result.getStatus() == ITestResult.SKIP) {
            takeLocalScreenshot(result.getMethod().getMethodName().toLowerCase() + "_" + System.currentTimeMillis());
        }
    }

    @AfterSuite(enabled = false)
    public void tearDown(){
        driver.closeApp();
        driver.quit();
    }

    @BeforeMethod(alwaysRun = true)
    public void waitingPage(){
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.SECONDS);
    }

    public static void waitElement (MobileElement element){
        wait.until(ExpectedConditions.visibilityOf(element));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitElements (List<WebElement> elements){
        wait = new WebDriverWait(driver, 1000);
        wait.until(ExpectedConditions.visibilityOfAllElements((WebElement) elements));
    }

    public static void waitMobileElements (List<MobileElement> elements){
        wait = new WebDriverWait(driver, 1000);
        wait.until(ExpectedConditions.visibilityOfAllElements((WebElement) elements));
    }

    public static void  waitAndroidElements(List <AndroidElement> elements){
        wait = new WebDriverWait(driver, 1000);
        wait.until(ExpectedConditions.visibilityOfAllElements((WebElement) elements));
    }
}

