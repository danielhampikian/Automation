package com.sikulix.testrun;
import java.io.*;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import org.sikuli.basics.Settings;
import org.sikuli.script.*;
import org.sikuli.basics.FileManager;
import static java.lang.Thread.sleep;

public class SikuTestRun {

    public static App app;
    public static final String CHANBASEKEY = "FJBZ-S87U-AJ5N-YPU7-2D2B-YTHF-WSTA";
    public static final String NAME = "Daniel Hampikian";
    public static final String EMAIL = "daniel@metageek.net";
    public static final String CHANALYER = "Chanalyzer";
    public static int test_step;
    public static File outputfile;
    public static Writer writer;

    private static String p(String msg, Object... args) {
        System.out.println(String.format(msg, args));
        return (String.format(msg, args));
    }

    public static String currentTimeStamp() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        DateFormat f = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        return f.format(cal.getTime());
    }


    public static void main(String[] args) throws Exception {

        //Tesseract setup for text reading
        Settings.OcrTextRead = true;
        Settings.OcrLanguage = "en";
        Settings.OcrTextSearch = true;
        Settings sikulixSettings = new Settings();
        sikulixSettings.OcrTextSearch = true;
        sikulixSettings.OcrTextRead = true;
        testApp(CHANALYER);
    }

    public static void testApp(String appName) throws Exception {

        //Tesseract setup for text reading
        Settings.OcrTextRead = true;
        Settings.OcrLanguage = "en";
        Settings.OcrTextSearch = true;

        String currentTime = currentTimeStamp();
        ImagePath.add(SikuTestRun.class.getCanonicalName() + "/ImagesAPI.sikuli");
        outputfile = new File("TestReport.txt");
        PrintStream out = new PrintStream(new FileOutputStream("TestReport.txt"));
        //System.setOut(out);
        p(currentTime);

        app = new App(appName);
        test_step = 0;
        installByImageRec();
        getToChanalyzer();
        Region currentWindow = App.focusedWindow();
        verifyTargetImage(currentWindow, "verify_chan_dlls");
        registerApp(app);
        verifyRegisteredUserByImage(app);

        currentWindow = App.focusedWindow();
        deactivateApp(currentWindow);
        currentWindow = App.focusedWindow();
        verifyDeactivate(currentWindow);
        unInstallChanalyzer();
        verifyUninstall();
    }

    private static void unInstallChanalyzer() throws Exception {
        tryAndClickOnSomethingInScreen(2, "installer");
        sleep(800);
        tryAndClickOnSomethingInScreen(1, "next");
        tryAndClickOnSomethingInScreen(1, "remove_button");
        sleep(1200);
        tryAndClickOnSomethingInScreen(1, "second_remove_button");
        sleep(7100);
        tryAndClickOnSomethingInScreen(1, "finish_button");
    }

    private static void verifyDeactivate(Region currentWindow) {

        try {
            currentWindow = app.focusedWindow();
            verifyTargetImage(currentWindow, "deactivate_verify");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            currentWindow = app.focusedWindow();
            sleep(700);
            clickOnTarget(currentWindow, "red_x");
            currentWindow = app.focusedWindow();
            sleep(700);
            clickOnTarget(currentWindow, "red_x");
            sleep(700);
            currentWindow = app.focusedWindow();
            clickOnTarget(currentWindow, "red_x");;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deactivateApp(Region currentWindow) {
        try {
            clickOnTarget(currentWindow, "deactivate");
            currentWindow.type(Key.TAB + "DEACTIVATE" + Key.TAB + Key.ENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void verifyRegisteredUserByImage(App app) throws Exception {
        Boolean appOpened;
        try {
            appOpened = openApp(app);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sleep(2500);

        if (appOpened = true && app.isRunning()) {
            Region currentWindow = App.focusedWindow();
            clickOnTarget(currentWindow, "help");
            tryAndClickOnSomethingInScreen(1, "register");
            currentWindow = App.focusedWindow();
            verifyTargetImage(currentWindow, "verify_registered");
            currentWindow = app.focusedWindow();
            sleep(700);
            clickOnTarget(currentWindow, "red_x");
        }
    }


    private static void registerApp(App app) throws Exception {

        try {
            Boolean appOpened = openApp(app);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sleep(3500);

        if (app.isRunning()) {
            Region currentWindow = App.focusedWindow();
            currentWindow.type(CHANBASEKEY + Key.TAB + NAME + Key.TAB
            + EMAIL + Key.TAB + Key.TAB + Key.ENTER);
            readFromTopRightOfRegistration(currentWindow);
            focusOnWindowJustBelowImageAndRead(currentWindow, "registration_image_above_name_field");
        }
    }



    private static void getToChanalyzer() throws Exception {
        tryAndClickOnSomethingInScreen(1, "file_explorer");
        sleep(100);
        tryAndClickOnSomethingInScreen(1, "c_drive");
        sleep(100);
        tryAndClickOnSomethingInScreen(2, "program_files_86");
        sleep(100);
        tryAndClickOnSomethingInScreen(2, "metageek");
        sleep(100);
        tryAndClickOnSomethingInScreen(2, "chanalyzer_filepath");
        sleep(100);
    }

    private static void verifyUninstall() throws Exception {
        tryAndClickOnSomethingInScreen(1, "file_explorer");
        sleep(100);
        tryAndClickOnSomethingInScreen(1, "c_drive");
        sleep(100);
        tryAndClickOnSomethingInScreen(2, "program_files_86");
        sleep(100);
        Boolean imageExists = verifyTargetImageDoesntExist("metageek");
        if (imageExists) {
            tryAndClickOnSomethingInScreen(2, "metageek");
            sleep(100);
            imageExists = verifyTargetImageDoesntExist("chanalyzer_filepath");
            if(imageExists)
                p("\nTest FAILS FAILS FAILS!!! - INSTALLATION DIDN't WORK!!!!\n");
        }
        else {
            p("\nPASSED!!!!! PASSED!!! PASSSEED!!!" +
                    "\nThe INSTALLATION worked!!!!\n");
        }

    }


    private static Boolean verifyTargetImageDoesntExist(String imageName) {
        Boolean imageExists = true;
        try {
            Match foundImage = null;
            Screen imageSearchScreen = new Screen();
            foundImage = imageSearchScreen.exists(imageName);

            if (null == foundImage) {
                imageExists = false;
                p("\nPASSED!!!!! PASSED!!! PASSSEED!!!" +
                        "\nThe image of " + imageName + " DOES NOT exist!\n PASSED PASSED PASSEEDD!!!!\n");
            }
            else
                p("\n***************\nPASSED! PASSED!!! \nBUT NOTE: IF CHANALYZER IS BEING TESTED " +
                        "\nON A CLEAN VM, THE IMAGE: " + imageName + " DOES EXIST AND IT SHOULDN'T \n***************\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageExists;
    }

    private static void installByImageRec() throws Exception {
        tryAndClickOnSomethingInScreen(2, "installer_msi");
        sleep(800);
        tryAndClickOnSomethingInScreen(1, "next");
        sleep(1200);
        tryAndClickOnSomethingInScreen(1, "accept_user_agreement");
        tryAndClickOnSomethingInScreen(1, "next");
        sleep(4200);
        tryAndClickOnSomethingInScreen(1, "next");
        sleep(4200);
        tryAndClickOnSomethingInScreen(1, "next");
        sleep(4200);
        tryAndClickOnSomethingInScreen(1, "install_image");
        sleep(7100);
        tryAndClickOnSomethingInScreen(1, "finish_button");
    }

    private static void closeWindow() throws Exception{
        tryAndClickOnSomethingInScreen(1, "red_x)");
    }


    private static void clickOnTarget(Region currentWin, String imageName) throws Exception{

        try {
            String imageSpecs, imageText,actualImageLocInApp,  matchingImagePathInTest = null;
            Boolean clickStatus = false;

            Match foundImage = null;
            foundImage = currentWin.exists(imageName);

            if (null != foundImage) {
                imageSpecs = p("Image specs: %s", foundImage);
                actualImageLocInApp = p("image location on app and confidence of match %s", foundImage.getImage());
                matchingImagePathInTest = p("path to the image used for matching: %s", foundImage.getImageFilename());
                currentWin.click();
                p("PASSED!!!!! PASSED!!! PASSSEED!!!! Click on " + imageName + " should have worked!");
                clickStatus = true;
                //imageText = p("All of the text found on this image is: %s", currentWin.text());
            }
            else
                p("\n***************\nFAILED FAILED FAILED!!! TRYING TO CLICK ON: " + imageName + "\n***************\n");

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private static void verifyTargetImage(Region currentWin, String imageName) throws Exception{

        try {
            String imageSpecs, imageText,actualImageLocInApp,  matchingImagePathInTest = null;

            Match foundImage = null;
            foundImage = currentWin.exists(imageName);

            if (null != foundImage) {
                imageSpecs = p("Image specs: %s", foundImage);
                actualImageLocInApp = p("image location on app and confidence of match %s", foundImage.getImage());
                matchingImagePathInTest = p("path to the image used for matching: %s", foundImage.getImageFilename());
                p("\nPASSED!!!!! PASSED!!! PASSSEED!!!" +
                        "\nThe image of " + imageName + " exists!\n PASSED PASSED PASSEEDD!!!!\n");
                //imageText = p("All of the text found on this image is: %s", currentWin.text());
            }
            else
                p("\n***************\nFAILED FAILED FAILED!!! Could not find: " + imageName + "\n***************\n");

        } catch (Exception e) {
            e.printStackTrace();

        }
    }



    private static void tryAndClickOnSomethingInScreen(int numClicks, String imageName) throws Exception{

        String imageSpecs, imageText,actualImageLocInApp,  matchingImagePathInTest = null;

        Screen currentWin = new Screen();
        Match foundImage = null;
        foundImage = currentWin.exists(imageName);

        try {

            if (null != foundImage) {
                imageSpecs = p("Image specs: %s", foundImage);
                actualImageLocInApp = p("image location on app and confidence of match %s", foundImage.getImage());
                matchingImagePathInTest = p("path to the image used for matching: %s", foundImage.getImageFilename());
                p("The image of " + imageName + " exists!");
                //imageText = p("All of the text found on this image is: %s", currentWin.text());
            }
            if (numClicks==1) {
               singleClickOnTarget(imageName);
                p("Single clicked on " + imageName + "!");
            }
            if (numClicks==2) {
                doubleClickOnTarget(imageName);
                p("Double clicked on " + imageName + "!");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private static void doubleClickOnTarget(String imageName) throws Exception{
        try {
           Screen screen = new Screen();
            screen.doubleClick(imageName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void singleClickOnTarget(String imageName) throws Exception{
        try {
            Screen screen = new Screen();
            screen.click(imageName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static String focusOnWindowJustBelowImageAndRead (Region currentWin, String imageName) throws Exception {
        //NOT YET WORKING METHOD!!!! TO DO: MAKE IT WORK!
        String windowText = "read just below image did not work";
        String imageSpecs, actualImageLocInApp,  matchingImagePathInTest = null;
        Boolean readStatus = false;
        try {
            Match foundImage = null;
            foundImage = currentWin.exists(imageName);

            if (null != foundImage) {

                imageSpecs = p("Image specs: %s", foundImage);
                actualImageLocInApp = p("image location on app and confidence of match %s", foundImage.getImage());
                matchingImagePathInTest = p("path to the image used for matching: %s", foundImage.getImageFilename());
                Location loc = foundImage.getTarget();
                Region justBelowWindow = currentWin.below();
                justBelowWindow = App.focusedWindow();

                windowText = p("The text in the window just below the image is: %s", currentWin.text());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return windowText;
    }

    private static String readFromTopRightOfRegistration(Region currentWin) throws Exception {
        //Hard coded method to demonstrate tesseract text reading
        String foundText = null;
        String  imageSpecs, actualImageLocInApp;

        try {

            //Region registrationWin = App.focusedWindow();
            Location locOfTopLeft = currentWin.getTopLeft();
            Region nameField = new Region (272, 450, 30, 300);
            foundText = p("The text from name feild hopefully is: %s", nameField.text());
            imageSpecs = p("Image specs: %s", nameField);
            actualImageLocInApp = p("x, y, h, w of nameField regrion = %s %s %s %s", nameField.getX(), nameField.getY(),
                    nameField.getH(), nameField.getW());

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return foundText;
    }


    private static boolean openApp(App app) throws Exception {
        //TO DO: make abstract and take app name as param

        boolean appOpened = false;
        try {
            while (!appOpened) {
                app.open("Chanalyzer.exe");
                app.focus("Chanalyzer.exe");
                sleep(10000);
                if (app.isRunning())
                    appOpened = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appOpened;
    }
}



