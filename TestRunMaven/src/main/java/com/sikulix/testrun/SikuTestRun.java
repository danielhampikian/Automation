package com.sikulix.testrun;

import java.io.File;

import org.sikuli.basics.Settings;
import org.sikuli.script.*;
import org.sikuli.basics.FileManager;
import static java.lang.Thread.sleep;

public class SikuTestRun {

    public static App app;

    private static String p(String msg, Object... args) {
        System.out.println(String.format(msg, args));
        return (String.format(msg, args));
    }


    public static void main(String[] args) throws Exception {

        //Tesseract setup for text reading
        Settings.OcrTextRead = true;
        Settings.OcrLanguage = "en";
        Settings.OcrTextSearch = true;
        Settings sikulixSettings = new Settings();
        sikulixSettings.OcrTextSearch = true;
        sikulixSettings.OcrTextRead = true;
        testApp("Chanalyzer");
    }

    public static void testApp(String appName) throws Exception {


        //Tesseract setup for text reading
        Settings.OcrTextRead = true;
        Settings.OcrLanguage = "en";
        Settings.OcrTextSearch = true;


        ImagePath.add(SikuTestRun.class.getCanonicalName() + "/ImagesAPI.sikuli");
        File fResults = new File(System.getProperty("user.home"), "TestResults");
        String fpResults = fResults.getPath();
        FileManager.deleteFileOrFolder(fpResults);
        fResults.mkdirs();
        //TO DO: print logging to this directory eventually as well as Unit Test results

        app = new App(appName);
        Boolean appOpened = false;

        //TO DO: make a method that takes key-value pairs or arrays of objects that contain images paired
        // with click ammounts, so you just send in a list of Strings and ints

        installByImageRec();
        get_to_chanalyzer();
        Region currentWindow = App.focusedWindow();
        verifyTargetImage(currentWindow, "verify_chan_dlls");

        //helpMenuRegisteredUserTest(app);
    }

    private static void helpMenuRegisteredUserTest(App app) throws Exception {
        Boolean appOpened;
        try {
            appOpened = openApp(app);
        } catch (Exception e) {
            e.printStackTrace();
        }

        sleep(1500);

        if (appOpened = true && app.isRunning()) {
            Region currentWindow = App.focusedWindow();
            clickOnTarget(currentWindow, "help");
            clickOnTarget(currentWindow, "register");
            readFromTopRightOfRegistration(currentWindow);
            focusOnWindowJustBelowImageAndRead(currentWindow, "registration_image_above_name_field");
        }
    }


    private static void registerApp(App app) throws Exception {
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
            clickOnTarget(currentWindow, "register");
            readFromTopRightOfRegistration(currentWindow);
            focusOnWindowJustBelowImageAndRead(currentWindow, "registration_image_above_name_field");
        }
    }



    private static void get_to_chanalyzer() throws Exception {
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
                p("Click on " + imageName + " should have worked!");
                clickStatus = true;
                setWindowToNameField(currentWin);
                imageText = p("All of the text found on this image is: %s", currentWin.text());
            }

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
                p("The image of " + imageName + " exists!");

                setWindowToNameField(currentWin);
                imageText = p("All of the text found on this image is: %s", currentWin.text());
                tryAndClickOnSomethingInScreen(1, "close_window");

            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private static void tryAndClickOnSomethingInScreen(int numClicks, String imageName) throws Exception{

        String imageSpecs, imageText,actualImageLocInApp,  matchingImagePathInTest = null;

        Screen screen = new Screen();
        Region currentWin = screen;
        Match foundImage = null;
        foundImage = currentWin.exists(imageName);

        try {

            if (null != foundImage) {
                imageSpecs = p("Image specs: %s", foundImage);
                actualImageLocInApp = p("image location on app and confidence of match %s", foundImage.getImage());
                matchingImagePathInTest = p("path to the image used for matching: %s", foundImage.getImageFilename());
                p("The image of " + imageName + " exists!");
                setWindowToNameField(currentWin);
                imageText = p("All of the text found on this image is: %s", currentWin.text());
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


    private static void setWindowToNameField(Region currentWin) {
        currentWin.setX(268);
        currentWin.setY(393);
        currentWin.setH(40);
        currentWin.setW(300);
    }

    private static String focusOnWindowJustBelowImageAndRead (Region currentWin, String imageName) throws Exception {
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


