package com.example.junit;

import org.sikuli.script.*;

/**
 * Created by danielhampikian on 6/18/15.
 */
public class SikulixProto {

    /**
     * Tests the test setup itself, this should fail the first test
     *
     * @return String
     */
    public String sayHello() {
        return "Teseting Test Failure Detection";
    }

    /**
     * Opens Chanalyzer
     * Returns PID if possible to verify process is running
     * Otherwise just an int 1 that means it worked
     */
        public int openChanalyzer() {

            App chan = new App("Chanalyzer");
            App.getApps("Chanalyzer");
            App.open("Chanalyzer");
            chan.focus();
            App.focusedWindow();


            App.open("Windows");

            // we're going to need this to check the name:
            // public java.lang.String getName();

        return 0;
    }

    /**
     *
     */
}
