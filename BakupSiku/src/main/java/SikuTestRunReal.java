package com.sikulix.testrun;

import java.io.File;

import org.sikuli.basics.Settings;
import org.sikuli.script.*;
import org.sikuli.basics.FileManager;
import static java.lang.Thread.sleep;
import org.sikuli.script.TextRecognizer;

public class SikuTestRun {

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


        ImagePath.add(TestRun.class.getCanonicalName() + "/ImagesAPI.sikuli");
        File fResults = new File(System.getProperty("user.home"), "TestResults");
        String fpResults = fResults.getPath();
        FileManager.deleteFileOrFolder(fpResults);
        fResults.mkdirs();
        //TO DO: print logging to this directory eventually as well as Unit Test results

        App app = new App(appName);
        Boolean appOpened = false;


//      try {
//          appOpened = openChanalyzer(app);
//      } catch (Exception e) {
//          e.printStackTrace();
//      }

        //need to wait for Chanalyzer to start up
        sleep(1500);

        if (appOpened = true && app.isRunning()) {
//          Region currentWindow = App.focusedWindow();
//          clickOnTarget(currentWindow, "help");
//          clickOnTarget(currentWindow, "register");
//          readFromTopRightOfRegistration(currentWindow);
            //focusOnWindowJustBelowImageAndRead(currentWindow, "registration_image_above_name_field");
        }

        Screen screen = new Screen();
        doubleClickOnTarget(screen, "installer_msi");
        sleep(1200);
        Screen screen2 = new Screen();
        clickOnTarget(screen2, "next");


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

    private static void tryAndClickOnSomethingInScreen(Region currentWin, String imageName) throws Exception{


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

    private static void doubleClickOnTarget(Region currentWin, String imageName) throws Exception{


        try {
            String imageSpecs, imageText,actualImageLocInApp,  matchingImagePathInTest = null;
            Boolean clickStatus = false;

            Match foundImage = null;
            foundImage = currentWin.exists(imageName);

            if (null != foundImage) {
                imageSpecs = p("Image specs: %s", foundImage);
                actualImageLocInApp = p("image location on app and confidence of match %s", foundImage.getImage());
                matchingImagePathInTest = p("path to the image used for matching: %s", foundImage.getImageFilename());
                currentWin.doubleClick();
                p("Click on " + imageName + " should have worked!");
                clickStatus = true;
                setWindowToNameField(currentWin);
                imageText = p("All of the text found on this image is: %s", currentWin.text());
            }

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



    private static boolean openChanalyzer(App app) throws Exception {
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



