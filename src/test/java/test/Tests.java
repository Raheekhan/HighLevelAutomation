package test;

import base.BaseUtil;
import reporting.ExtentTestManager;
import org.testng.annotations.Test;
import pages.GooglePage;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.testng.Assert.assertTrue;

public class Tests extends BaseUtil {

    @Test
    public void testing() {
        GooglePage google = new GooglePage(driver, ExtentTestManager.getTest());
        google.searchFor("Cool Stuff");
        assertTrue(google.isOnresultsPage());
    }

    @Test
    public void testing02() throws InterruptedException {
        Thread.sleep(5000);
        System.out.println(driver.getCurrentUrl());
        System.out.println(driver.getTitle());
    }

    @Test
    public void psuedoCode() {

        // Connect to Database

        // DO the actual test, eg. Register Account
        // Assert that you are logged in

        // Use Database method to validate if user exists

    }
}
