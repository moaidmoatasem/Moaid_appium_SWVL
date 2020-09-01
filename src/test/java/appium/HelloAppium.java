package appium;

import Helpers.JsonParserHelper;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.TapOptions;
import org.apache.commons.io.FileUtils;
import org.omg.CORBA.TIMEOUT;
import org.openqa.selenium.*;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.core.io.ByteArrayResource;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class HelloAppium {

    public static AndroidDriver<WebElement> driver; //AppiumDriver <MobileElement> appiumDriver
    public static DesiredCapabilities desiredCapabilities;

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

            fbLoginCredentials = new JsonObject();
            jsonParserHelper= new JsonParserHelper();
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


    @Test(priority = 0)
    public void skipIntro() {

//        MobileElement allowBtn = (MobileElement) driver.findElement(By.id("continue_btn"));
//        Boolean continueAvailable = allowBtn.isDisplayed(); //System.out.println(continueAvailable);
//
//        for (int i = 0; i < 3; i++) {
//            if (continueAvailable == true){
//                if (i==0){
//                    Assert.assertEquals("Book your trip!",driver.findElement(By.id("io.swvl.customer:id/title_tv")).getText());
//                }
//            WebDriverWait wait = new WebDriverWait(driver, 100);
//            wait.until(ExpectedConditions.visibilityOf(allowBtn));
//            allowBtn.click();}
////            driver.executeScript("mobile: scroll", ImmutableMap.of("direction", "left"));
//        }
        MobileElement skipBtn = (MobileElement) driver.findElement(By.id("io.swvl.customer:id/skip_btn"));
        skipBtn.click();
    }

    @Test(priority = 1, dependsOnMethods = {"skipIntro"})
    public void loginFB() {
        try {
            driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);

//        MobileElement fbLoginBtn = (MobileElement) driver.findElement(By.id("facebook_tv"));
//        fbLoginBtn.click();
//        m_login_email////android.webkit.WebView[@content-desc="Log in to Facebook | Facebook"]/android.view.View/android.view.View[2]/android.view.View[2]/android.view.View[2]/android.view.View/android.view.View[2]/android.widget.EditText[1]
//                m_login_password ////android.webkit.WebView[@content-desc="Log in to Facebook | Facebook"]/android.view.View/android.view.View[2]/android.view.View[2]/android.view.View[2]/android.view.View/android.view.View[2]/android.widget.EditText[2]
//        Log In ////android.widget.Button[@content-desc="Log In "]
//        ..progress
//
//                //confirmation message:
//        ////android.view.View[@content-desc="You previously logged in to Swvl with Facebook. Would you like to continue?"]
//
//        Continue: //android.widget.Button[@content-desc="Continue"]


            MobileElement fbBtn = (MobileElement) driver.findElement(By.id("io.swvl.customer:id/facebook_tv"));
            WebDriverWait waitFBbtn = new WebDriverWait(driver, 500);
            waitFBbtn.until(ExpectedConditions.elementToBeClickable(fbBtn));
            fbBtn.click();
            // driver.manage().timeouts().implicitlyWait(5000, TimeUnit.SECONDS);
//        WebDriverWait wait = new WebDriverWait(driver, 1000);
//        WebElement fbMsg = driver.findElementByAccessibilityId("Log in to your Facebook account to connect to Swvl");
//        wait.until(ExpectedConditions.visibilityOf(fbMsg));
            MobileElement webProgBar1 = (MobileElement) driver.findElementById("android:id/progress");
            Boolean progress1 = webProgBar1.isDisplayed();
            if (!progress1) {
                driver.findElement(By.xpath("//android.view.View[@content-desc=\"English (UK)\"]")).click();
                List<WebElement> webView = driver.findElements(By.className("android.view.View")); //findElementsByClassName
                WebDriverWait wait = new WebDriverWait(driver, 500);
                wait.until(ExpectedConditions.visibilityOfAllElements(webView));
                for (WebElement language : webView) {
                    if (!language.getAttribute("contentDescription").equals("English (UK)")) {
                        language.click();
                        break;
                    }
                }
            }

            //android.widget.EditText
            List<WebElement> txtFields = driver.findElements(By.className("android.widget.EditText")); //findElementsByClassName
            txtFields.get(0);
            WebElement emailTxt = txtFields.get(0);
            //driver.findElement(By.xpath("//*[@text = 'Mobile number or email address']"));
            emailTxt.click();
            emailTxt.sendKeys(fbLoginCredentials.get("emailTxt").getAsString());//"01065006657"); //FbLoginCredentials.get("emailTxt").getAsString()

            //WebElement el2 = driver.findElement(By.xpath("//android.widget.Button[@index=\"4\"]"));
            WebElement passTxt = txtFields.get(1);
            //driver.findElement(By.xpath("//*[@text = 'Facebook password']"));
            passTxt.click();
            passTxt.sendKeys(fbLoginCredentials.get("passTxt").getAsString());//"moaid_01065006657");
            driver.hideKeyboard();
            WebElement loginBtn = driver.findElement(By.className("android.widget.Button"));
//                xpath("//android.widget.Button[@index=\"4\"]"));
            WebDriverWait waitloginBtn = new WebDriverWait(driver, 500);
            waitloginBtn.until(ExpectedConditions.elementToBeClickable(loginBtn));
            loginBtn.click();
        } catch (Exception e) {
            System.out.println("Cause is" + e.getCause());
            System.out.println("Cause is" + e.getMessage());
        }


    }

    @Test(priority = 2, dependsOnMethods = {"skipIntro", "loginFB"})
    public void continueLoginFB() {
        try {
            driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
//        List<WebElement> continueScreenElements = driver.findElements(By.className("android.webkit.WebView"));
//        WebDriverWait wait1 = new WebDriverWait(driver, 500);
//        wait1.until(ExpectedConditions.visibilityOfAllElements(continueScreenElements));

//        WebElement webProgBar2 =  driver.findElement(By.id("android:id/progress"));
//        Boolean progress2 = webProgBar2.isDisplayed();

//        while (!progress2) {
//            System.out.println("progress should be false = "+progress2);

            //                xpath("//android.widget.Button[@text=\"Continue\"]"));

            WebElement continueBtn = driver.findElement(By.xpath("//android.widget.Button[@text='Continue']"));//id("u_0_1")) className("android.widget.Button")
            //  System.out.println(continueBtn.getText());

            WebDriverWait wait1 = new WebDriverWait(driver, 500);
            wait1.until(ExpectedConditions.visibilityOf(continueBtn));
            wait1.until(ExpectedConditions.elementToBeClickable(continueBtn));
            continueBtn.click();
        } catch (Exception e) {
            System.out.println("Cause is" + e.getCause());
            System.out.println("Cause is" + e.getMessage());
        }

    }
//    }


//}

    @Test(priority = 3, dependsOnMethods = {"skipIntro", "loginFB", "continueLoginFB"})
    public static void searchRide() {
        try {
            driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
//        desiredCapabilities.setCapability("locationServicesAuthorized", "true");


//        MobileElement gotIt = (MobileElement) driver.findElement(By.id("io.swvl.customer:id/got_it_tv"));
//        WebDriverWait wait0 = new WebDriverWait(driver, 500);
//        wait0.until(ExpectedConditions.visibilityOf(gotIt)).click();
//        if (gotIt.isDisplayed()) {
//            gotIt.click();
//        }

//        MobileElement locationAlert = (MobileElement) driver.findElement(By.id("io.swvl.customer:id/enable_location_layout"));
//        if(locationAlert.isDisplayed()){
//            MobileElement locationAlertBtn = (MobileElement) driver.findElement(By.id("io.swvl.customer:id/enable_location_btn"));
//            locationAlertBtn.click();
//        }

            MobileElement whereSearchBar = (MobileElement) driver.findElement(By.id("io.swvl.customer:id/where_to"));///hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.view.ViewGroup[4]/android.widget.TextView[4]
            WebDriverWait wait1 = new WebDriverWait(driver, 500);
            wait1.until(ExpectedConditions.visibilityOf(whereSearchBar)).click();
            whereSearchBar.click();


//        MobileElement where_to = (MobileElement) driver.findElement(By.id("io.swvl.customer:id/where_to"));
//        where_to.click();
//        driver.manage().timeouts().implicitlyWait(5000, TimeUnit.SECONDS);


//        TouchActions action = new TouchActions(driver);
//        action.singleTap(where_to_Txt);
//        action.perform();
//        where_to_Txt.click();

//        MobileElement el2 = (MobileElement) driver.findElement(By.id("io.swvl.customer:id/dropoff_frame_layout"));
//        el2.click();
//
//        MobileElement pickUp = (MobileElement) driver.findElement(By.id("io.swvl.customer:id/pickup_et"));
//        pickUp.click();

//        MobileElement el1 = (MobileElement) driver.findElementById("io.swvl.customer:id/places_rv");
//        el1.click();

//        MobileElement whereTo = (MobileElement) driver.findElementById("io.swvl.customer:id/dropoff_et");
//        WebDriverWait wait = new WebDriverWait(driver, 1000);
//        wait.until(ExpectedConditions.visibilityOf(whereTo)).click();

//        MobileElement fromLoc = (MobileElement) driver.findElementById("io.swvl.customer:id/pickup_et");
//        wait.until(ExpectedConditions.visibilityOf(fromLoc));
//        fromLoc.clear();
//        fromLoc.click();
//        fromLoc.sendKeys("Roxy Square");
//        whereTo.clear();
//        whereTo.click();
//        whereTo.sendKeys("Maadi");
            MobileElement el7 = (MobileElement) driver.findElementById("io.swvl.customer:id/dropoff_et");
            el7.click();

            MobileElement el5 = (MobileElement) driver.findElementById("io.swvl.customer:id/pickup_et");
            el5.click();
            el5.sendKeys("Mohandessin");
            MobileElement el6 = (MobileElement) driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[1]");
            el6.click();

            el7.click();
            el7.sendKeys("The 5th Settlement");
            MobileElement el8 = (MobileElement) driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[1]");
            driver.hideKeyboard();
            el8.click();
            //android.widget.Button

//        whereTo.click();


//        MobileElement progBar = (MobileElement) driver.findElementById("io.swvl.customer:id/progressbar");
//        Boolean progress = progBar.isDisplayed();
//        List <MobileElement> searchResult = (List <MobileElement>)(MobileElement) driver.findElementsByClassName("android.view.ViewGroup");
//        driver.manage().timeouts().implicitlyWait(5000, TimeUnit.SECONDS);

//        driver.findElement(By.xpath("//android.widget.TextView/[@Text=\"Maadi\"]"));
//        MobileElement maadi;
//        do
//        {
//            List<WebElement> searchResult = driver.findElements(By.className("androidx.recyclerview.widget.RecyclerView"));
//        wait.until(ExpectedConditions.visibilityOfAllElements((MobileElement)searchResult));

//        MobileElement firstResults = (MobileElement)  driver.findElementsByXPath("//android.view.ViewGroup[0]/androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout");
//        wait.until(ExpectedConditions.visibilityOf(firstResults));
//        firstResults.click();
        } catch (Exception e) {
            System.out.println("Cause is" + e.getCause());
            System.out.println("Cause is" + e.getMessage());
        }

    }

    //    @Test (priority = 4 , dependsOnMethods = {"skipIntro","loginFB","continueLoginFB","searchRide"})
//        public void chooseRideList(){
//
//        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
//        //io.swvl.customer:id/places_rv
////        List<MobileElement> list1 = (List<MobileElement>) driver.findElementById("io.swvl.customer:id/places_rv"); //("android.view.ViewGroup/[@index=\"0\"]"));
////        List<MobileElement> list = (List<MobileElement>) driver.findElement(By.className("androidx.recyclerview.widget.RecyclerView")); //("android.view.ViewGroup/[@index=\"0\"]"));
//////        WebDriverWait wait = new WebDriverWait(driver, 1000);
////        MobileElement el1 = list.get(0);
//////        wait.until(ExpectedConditions.elementToBeClickable(el1));
//////                "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[1]/android.widget.TextView[1]");
////        el1.click();
////        MobileElement el2 = list.get(0);
//        MobileElement el2 = (MobileElement) driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[1]");
//        el2.click();
//
//
////                    findElementsByClassName("android.widget.TextView");
//
////        driver.manage().timeouts().implicitlyWait(5000, TimeUnit.SECONDS);
////            wait.until(ExpectedConditions.visibilityOfAllElements((MobileElement)searchResult));
//////            wait.until(ExpectedConditions.elementsToBeClickable(searchResult));
////
//////            TouchActions action = new TouchActions(driver);
//////            action.doubleTap(maadi);
//////            action.longPress(maadi);
//////            action.perform();
////            searchResult.get(0);
////            System.out.println(searchResult.get(0).getText());
////            for (WebElement chooseFirst : searchResult) {
////                System.out.println(chooseFirst.getAttribute("text"));
//////                if (chooseFirst.getAttribute("text").equals("Maadi")) {
////////                    maadi = chooseFirst;
//////                    chooseFirst.click();
//////////                     action = new TouchActions(driver);
//////////                    action.doubleTap(maadi);
//////////                    action.perform();
//////                    chooseFirst.click();
//////                    System.out.println(chooseFirst.getText());
////////                    Assert.assertEquals(chooseFirst.getText(),"Maadi");
//////                    break;
//////                }
////            }
////    }while (!progress);
//
//
//
//
//        //MobileElement clickSpace = (MobileElement) driver.findElements(By.xpath("android.view.ViewGroup[@index=\"0\"]"));
////        clickSpace.click();
//
////        action = new Actions(driver);
////        action.moveToElement(el1);
////        action.doubleClick();
////        action.perform();
////
////        List <MobileElement> searchResult = (List <MobileElement>)(MobileElement) driver.findElementsById("io.swvl.customer:id/places_rv");
////    google_tv
//
//    /*
//MobileElement el7 = (MobileElement) driver.findElementById("io.swvl.customer:id/facebook_tv");
//el7.click();
//MobileElement el8 = (MobileElement) driver.findElementByXPath("//android.webkit.WebView[@content-desc=\"Log in to Facebook | Facebook\"]/android.view.View/android.view.View[2]/android.view.View[2]/android.view.View[3]/android.view.View/android.view.View[2]/android.widget.EditText[1]");
//el8.sendKeys("moaid@eventtus.com");
//MobileElement el9 = (MobileElement) driver.findElementByXPath("//android.webkit.WebView[@content-desc=\"Log in to Facebook | Facebook\"]/android.view.View/android.view.View[2]/android.view.View[2]/android.view.View[3]/android.view.View/android.view.View[2]/android.widget.EditText[2]");
//el9.click();
//el9.sendKeys("moaid_eventtus");
//MobileElement el10 = (MobileElement) driver.findElementByAccessibilityId("Log In ");
//el10.click();
//MobileElement el11 = (MobileElement) driver.findElementByAccessibilityId("Continue");
//el11.click();
//
//     */
//
    @AfterSuite (enabled = false)
    public void tearDown(){
    driver.closeApp();
        driver.quit();
    }

//
//    }
    @Test(priority = 4, dependsOnMethods = {"skipIntro", "loginFB", "continueLoginFB", "searchRide"})
    //,"chooseRideList"
    public void confirmDropOff() {
        try {
            driver.manage().timeouts().implicitlyWait(5000, TimeUnit.SECONDS);
            List<WebElement> maps = driver.findElements(By.className("android.widget.RelativeLayout"));
            WebDriverWait wait = new WebDriverWait(driver, 1000);
            wait.until(ExpectedConditions.visibilityOfAllElements(maps));

            MobileElement dropOffBtn = (MobileElement) driver.findElementByClassName("android.widget.Button"); //(""android.widget.Button);
            //WebDriverWait wait = new WebDriverWait(driver, 1000);
            wait.until(ExpectedConditions.elementToBeClickable(dropOffBtn)).click();
            driver.manage().timeouts().implicitlyWait(10000, TimeUnit.SECONDS);
//        driver.toggleLocationServices();
            dropOffBtn.click();

            List<MobileElement> listPlaces = (List<MobileElement>) (MobileElement) driver.findElementsById("io.swvl.customer:id/no_search_results_layout");
            wait.until(ExpectedConditions.visibilityOfAllElements((MobileElement) listPlaces));
            driver.manage().timeouts().implicitlyWait(10000, TimeUnit.SECONDS);

//        List <MobileElement> elements= (List <MobileElement>)(MobileElement) driver.findElements(By.id("io.swvl.customer:id/ride_card_view"));
//        elements.get(1).click();
        } catch (Exception e) {
            System.out.println("Cause is" + e.getCause());
            System.out.println("Cause is" + e.getMessage());
        }

    }

    @Test(priority = 5, dependsOnMethods = {"skipIntro", "loginFB", "continueLoginFB", "searchRide"})
    public void chooseRide() {
        try {
            driver.toggleLocationServices();
            driver.manage().timeouts().implicitlyWait(10000, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(driver, 1000);

//            List<MobileElement> locations = (List<MobileElement>) (MobileElement) driver.findElements(By.id("io.swvl.customer:id/ride_card_view"));
//            List<MobileElement> locations = (List<MobileElement>) (MobileElement) driver.findElements(MobileBy.id("io.swvl.customer:id/trip_item_without_date_layout"));//io.swvl.customer:id/fixed_trip_view"));
////io.swvl.customer:id/trip_item_without_date_layout
//            WebDriverWait wait = new WebDriverWait(driver, 1000);
//            wait.until(ExpectedConditions.visibilityOfAllElements((MobileElement) locations));
//            locations.get(5).click();

            MobileElement ride = (MobileElement)driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[4]"));
            wait.until(ExpectedConditions.visibilityOfAllElements(ride));
            ride.click();
            //MobileElement el2 = (MobileElement) driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[3]");
            //el2.click();



        } catch (Exception e) {
            System.out.println("Cause is" + e.getCause());
            System.out.println("Cause is" + e.getMessage());
        }
    }
    @Test(priority = 6, dependsOnMethods = {"skipIntro", "loginFB", "continueLoginFB", "searchRide"})
    public void confirmRide() {
        driver.manage().timeouts().implicitlyWait(10000, TimeUnit.SECONDS);

        try {
            WebDriverWait wait = new WebDriverWait(driver, 1000);
            MobileElement nextBtn = (MobileElement) driver.findElement(By.id("io.swvl.customer:id/next_btn"));
            wait.until(ExpectedConditions.visibilityOf(nextBtn));
            nextBtn.click();
        }
        catch (Exception e) {
            System.out.println("Cause is" + e.getCause());
            System.out.println("Cause is" + e.getMessage());
        }
    }
    @Test( priority = 7, dependsOnMethods = {"skipIntro", "loginFB", "continueLoginFB", "searchRide","chooseRide","confirmDropOff","confirmRide"})
    public void bookRide() {
        driver.manage().timeouts().implicitlyWait(10000, TimeUnit.SECONDS);

        try {
            WebDriverWait wait = new WebDriverWait(driver, 1000);
            MobileElement nextBtn = (MobileElement) driver.findElement(By.id("io.swvl.customer:id/book_btn"));
            wait.until(ExpectedConditions.visibilityOf(nextBtn));
            nextBtn.click();
        }
        catch (Exception e) {
            System.out.println("Cause is" + e.getCause());
            System.out.println("Cause is" + e.getMessage());
        }
    }//io.swvl.customer:id/book_btn //io.swvl.customer:id/done_btn
    @Test( priority = 8, dependsOnMethods = {"skipIntro", "loginFB", "continueLoginFB", "searchRide","chooseRide","confirmDropOff","confirmRide"})
    public void bookRideDoneOrReturn() {
        driver.manage().timeouts().implicitlyWait(10000, TimeUnit.SECONDS);

        try {
            WebDriverWait wait = new WebDriverWait(driver, 1000);
            MobileElement nextBtn = (MobileElement) driver.findElement(By.id("io.swvl.customer:id/done_btn"));//io.swvl.customer:id/book_btn //io.swvl.customer:id/done_btn
            wait.until(ExpectedConditions.visibilityOf(nextBtn));
            nextBtn.click();
        }
        catch (Exception e) {
            System.out.println("Cause is" + e.getCause());
            System.out.println("Cause is" + e.getMessage());
        }
    }
}

//WebElement allowLocationBtn = driver.findElement(MobileBy.id("permission_allow_button")); //xpath("//*[@class='android.widget.Button'][2]").click();

//failed trials to accept location permission may be works on other android versions

//        WebElement allowLocationBtn = driver.findElement(By.id("permission_allow_button"));
// WebElement allowButton = (WebElement) driver.findElements(By.xpath("//android.widget.Button[@resource-id='com.android.packageinstaller:id/permission_allow_button'])"));

//        //allowButton.wait(500).click();
//        final WebElement allowButton = driver.findElement(By.id("com.android.packageinstaller:id/permission_allow_button"));
//        allowButton.click();
//        desiredCapabilities.setCapability("appPackage", "com.google.android.packageinstaller");
//        desiredCapabilities.setCapability("appActivity", "com.android.packageinstaller.permission.ui.GrantPermissionsActivity");
//
//        driver.findElementById("com.android.packageinstaller:id/permission_allow_button").click();
//
//        WebDriverWait wait = new WebDriverWait(driver, 5);
//        wait.until(ExpectedConditions.alertIsPresent());
//        driver.switchTo().alert().accept();


