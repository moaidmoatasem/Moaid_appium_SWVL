package appium;

import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class HelloAppium extends TestBase {

    @Test(enabled = false)
    public void skipIntro() {

        MobileElement skipBtn = (MobileElement) driver.findElement(By.id("io.swvl.customer:id/skip_btn"));
        skipBtn.click();
    }

    @Test(priority = 0)
    public void showOnBoarding() {
        MobileElement allowBtn = (MobileElement) driver.findElement(By.id("continue_btn"));
        boolean continueAvailable = allowBtn.isDisplayed(); //System.out.println(continueAvailable);

        for (int i = 0; i < 3; i++) {
            if (continueAvailable) {
                if (i == 0) {
                    Assert.assertEquals("Book your trip!", driver.findElement(By.id("io.swvl.customer:id/title_tv")).getText());
                }
                WebDriverWait wait = new WebDriverWait(driver, 100);
                wait.until(ExpectedConditions.visibilityOf(allowBtn));
                allowBtn.click();
            }
        }
////            driver.executeScript("mobile: scroll", ImmutableMap.of("direction", "left"));
//        }
    }

    @Test(priority = 1, dependsOnMethods = {"showOnBoarding"}) //or "showOnBoarding" if enabled
    public void loginFB() {
        try {
//            driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);

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
//            WebDriverWait waitFBbtn = new WebDriverWait(driver, 500);
//            waitFBbtn.until(ExpectedConditions.elementToBeClickable(fbBtn));
            waitElement(fbBtn);
            fbBtn.click();

//        WebElement fbMsg = driver.findElementByAccessibilityId("Log in to your Facebook account to connect to Swvl");
//            MobileElement mobileElement = (MobileElement)fbMsg; //casting

            MobileElement webProgBar1 = (MobileElement) driver.findElementById("android:id/progress");
            boolean progress1 = webProgBar1.isDisplayed();
            if (!progress1) {
                driver.findElement(By.xpath("//android.view.View[@content-desc=\"English (UK)\"]")).click();
                List<WebElement> FBwebView = driver.findElements(By.className("android.view.View")); //findElementsByClassName
                waitElements(FBwebView);
                for (WebElement language : FBwebView) {
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

    @Test(priority = 2, dependsOnMethods = {"showOnBoarding", "loginFB"})
    public void continueLoginFB() {
        try {
//            driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
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

//            WebDriverWait wait1 = new WebDriverWait(driver, 500);
//            wait1.until(ExpectedConditions.visibilityOf(continueBtn));
//            wait1.until(ExpectedConditions.elementToBeClickable(continueBtn));
            waitElement((MobileElement) continueBtn);
            continueBtn.click();
        } catch (Exception e) {
            System.out.println("Cause is" + e.getCause());
            System.out.println("Cause is" + e.getMessage());
        }

    }
//    }


//}

    @Test(priority = 3, dependsOnMethods = {"showOnBoarding", "loginFB", "continueLoginFB"})
    public static void searchRide() {
        try {
            driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
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
            waitElement(whereSearchBar);
            whereSearchBar.click();
            whereSearchBar.click();

        }
        catch(Exception e)

        {
            System.out.println("Cause is" + e.getCause());
            System.out.println("Cause is" + e.getMessage());
        }
    }

    @Test(priority = 4, dependsOnMethods = {"showOnBoarding", "loginFB", "continueLoginFB", "searchRide"})
    public static void chooseFromTo() {
        try{
            MobileElement dropoff = (MobileElement) driver.findElementById("io.swvl.customer:id/dropoff_et");
            waitElement(dropoff);
            dropoff.click();
//
            MobileElement pickup = (MobileElement) driver.findElementById("io.swvl.customer:id/pickup_et");
            waitElement(pickup);
            pickup.click();
            pickup.sendKeys("Mohandessin");
//            pickup.sendKeys("Heliopolis");
//            waitElements();
            //            MobileElement confirmPickUPBtn = (MobileElement) driver.findElementByXPath("//android.widget.Button[@text=\"CONFIRM PICKUP\"]");
//            waitElement(confirmPickUPBtn);
//            confirmPickUPBtn.click();

                //same mobile location so will forward to maps
//            List<MobileElement> pickupresults = (List<MobileElement>) (MobileElement)driver.findElementsByClassName("android.view.ViewGroup");
//            waitMobileElements(pickupresults);
            MobileElement selectMatchResult = (MobileElement) driver.findElementByXPath("//hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[1]");
           ///hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[1]
            waitElement(selectMatchResult);
            selectMatchResult.click();


            dropoff.click();
            dropoff.sendKeys("The 5th Settlement");
//            dropoff.sendKeys("Smart Village");
            //
//            List<MobileElement> dropoffresults = (List<MobileElement>) (MobileElement)driver.findElementsByClassName("android.view.ViewGroup");
//            waitMobileElements(dropoffresults);


//            MobileElement dropoffMatchResult = (MobileElement) driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[1]");
//            waitElement(dropoffMatchResult);
//            dropoffMatchResult.click();

            MobileElement selectMatchResult2 = (MobileElement) driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[1]");
            waitElement(selectMatchResult2);
//            driver.hideKeyboard();
            selectMatchResult.click();

        } catch(Exception e)
        {
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
////
//    @AfterSuite (enabled = false)
//    public void tearDown(){
//    driver.closeApp();
//        driver.quit();
//    }

    //
 //    }
    @Test(priority = 4, dependsOnMethods = {"showOnBoarding", "loginFB", "continueLoginFB", "searchRide","chooseFromTo"})
    //,"chooseRideList"
    public void confirmDropOff() {
        try {
//            WebElement dropOffBtn =  driver.findElementByClassName("android.widget.Button");
            MobileElement dropOffBtn = (MobileElement) driver.findElementByXPath("//android.widget.Button[@text=\"CONFIRM DROPOFF\"]");

            waitElement(dropOffBtn);
            //driver.toggleLocationServices();
            dropOffBtn.click();

        } catch (Exception e) {
            System.out.println("Cause is" + e.getCause());
            System.out.println("Cause is" + e.getMessage());
        }

    }

    @Test(priority = 5, dependsOnMethods = {"showOnBoarding", "loginFB", "continueLoginFB", "searchRide","chooseFromTo","confirmDropOff"})
    public void chooseRide() {
        try {
            int chooseNotFull = 4;
            MobileElement chooseTrip =(MobileElement) driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup["+chooseNotFull+"]");
          chooseTrip.click();


        } catch (Exception e) {
            System.out.println("Cause is" + e.getCause());
            System.out.println("Cause is" + e.getMessage());
        }
    }
    @Test(priority = 6,dependsOnMethods = {"showOnBoarding", "loginFB", "continueLoginFB", "searchRide","chooseFromTo","confirmDropOff","chooseRide"})
    public void confirmRide() {
//        driver.manage().timeouts().implicitlyWait(10000, TimeUnit.SECONDS);

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
    @Test( priority = 7 , dependsOnMethods = {"showOnBoarding", "loginFB", "continueLoginFB", "searchRide","chooseFromTo","confirmDropOff","chooseRide","confirmRide"})
    public void bookRide() {
//        driver.manage().timeouts().implicitlyWait(10000, TimeUnit.SECONDS);

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
    @Test( priority = 8 , dependsOnMethods = {"showOnBoarding", "loginFB", "continueLoginFB", "searchRide","chooseFromTo","confirmDropOff","chooseRide","confirmRide","bookRide"})
    public void bookRideDoneOrReturn() {
//        driver.manage().timeouts().implicitlyWait(10000, TimeUnit.SECONDS);

        try {
            WebElement bookedSuccessfully=driver.findElementById("title_tv");
            String successBooking=bookedSuccessfully.getText();
            Assert.assertEquals(successBooking,"Trip Successfully Booked");
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

//            while (!chooseTrip.isEnabled()) {
////
//                //                waitElement(chooseTrip);
//                MobileElement notifyBtn = (MobileElement) driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup["+chooseNotFull+"]/android.view.ViewGroup[2]/[contains(text(),'Notify me']");
////                if (notifyBtn.isDisplayed())
////                   waitElement(notifyBtn);
////                    notifyBtn.click();
//                chooseNotFull++;
//////                break;
//            }

//    waitElement(chooseTrip);
////            //to make sure that its not fully booked
//
//            waitElement(chooseTrip);
