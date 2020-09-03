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
////       use this to scroll instead of clicking:
//          driver.executeScript("mobile: scroll", ImmutableMap.of("direction", "left"));
//        }
    }

    @Test(priority = 1, dependsOnMethods = {"showOnBoarding"}) //or "showOnBoarding" if enabled
    public void loginFB() {
        try {
//        MobileElement fbLoginBtn = (MobileElement) driver.findElement(By.id("facebook_tv"));
            MobileElement fbBtn = (MobileElement) driver.findElement(By.id("io.swvl.customer:id/facebook_tv"));
            waitElement(fbBtn);
            fbBtn.click();

//        WebElement fbMsg = driver.findElementByAccessibilityId("Log in to your Facebook account to connect to Swvl");
//            MobileElement mobileElement = (MobileElement)fbMsg; //casting

            //check progress bar if still dislpayed
            MobileElement webProgressBar1 = (MobileElement) driver.findElementById("android:id/progress");
            boolean progress1 = webProgressBar1.isDisplayed();
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

            //get all elements with textfields the class of email && password
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
//        WebElement webProgBar2 =  driver.findElement(By.id("android:id/progress"));
//        Boolean progress2 = webProgBar2.isDisplayed();

//        while (!progress2) {
            WebElement continueBtn = driver.findElement(By.xpath("//android.widget.Button[@text='Continue']"));//id("u_0_1")) className("android.widget.Button")
            //  System.out.println(continueBtn.getText());
            waitElement((MobileElement) continueBtn);
            continueBtn.click();

        } catch (Exception e) {
            System.out.println("Cause is" + e.getCause());
            System.out.println("Cause is" + e.getMessage());
        }

    }

    @Test(priority = 3, dependsOnMethods = {"showOnBoarding", "loginFB", "continueLoginFB"})
    public static void searchRide() {
        try {
            driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);

            // to confirm the got it message instead of clicking on where to
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

            //to confirm the pick up on maps if requested
            //MobileElement confirmPickUPBtn = (MobileElement) driver.findElementByXPath("//android.widget.Button[@text=\"CONFIRM PICKUP\"]");
//            waitElement(confirmPickUPBtn);
//            confirmPickUPBtn.click();

            //select the first suggestion
            int firstSuggestion = 1;
            MobileElement selectMatchResult = (MobileElement) driver.findElementByXPath("//hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup["+firstSuggestion+"]");
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

            MobileElement selectMatchResult2 = (MobileElement) driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup["+firstSuggestion+"]");
            waitElement(selectMatchResult2);
//            driver.hideKeyboard();
            selectMatchResult.click();

        } catch(Exception e)
        {
            System.out.println("Cause is" + e.getCause());
            System.out.println("Cause is" + e.getMessage());
        }

    }

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

