package base;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import browserfactory.DriverManager;
import browserfactory.DriverManagerFactory;
import browserfactory.DriverType;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import reporting.ExtentManager;
import reporting.ExtentTestManager;

import java.io.*;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BaseUtil {

    public static ExtentReports extent;

    private DriverManager driverManager;
    public WebDriver driver;

    @BeforeSuite
    public void extentSetup(ITestContext context) {
        ExtentManager.setOutputDirectory(context);
        extent = ExtentManager.getInstance();
    }

    @BeforeMethod
    @Parameters({"useLocal", "useHeadless", "browserName"})
    public void setUp(@Optional boolean useLocal, @Optional boolean useHeadless,
                      @Optional("chrome") String browserName, @Optional Method method) {

        String className = method.getDeclaringClass().getSimpleName();
        ExtentTestManager.startTest(method.getName());
        ExtentTestManager.getTest().assignCategory(className);

        if (useLocal) {
            getLocalDriver(browserName);
        } else if (useHeadless) {
            getHeadlessDriver();
        }

        driver.manage().window().maximize();
        driver.navigate().to("https://www.phptravels.net");
    }

    public WebDriver getLocalDriver(String browserName) {
        if (browserName.equalsIgnoreCase("Chrome")) {
            driverManager = DriverManagerFactory.getManager(DriverType.CHROME);
            return driver = driverManager.getDriver();
        } else if (browserName.equalsIgnoreCase("Firefox")) {
            driverManager = DriverManagerFactory.getManager(DriverType.FIREFOX);
            return driver = driverManager.getDriver();
        } else {
            throw new RuntimeException("Driver not found!");
        }
    }

    public WebDriver getHeadlessDriver() {
        driverManager = DriverManagerFactory.getManager(DriverType.HEADLESS);
        return driver = driverManager.getDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        ExtentTestManager.getTest().getTest().setStartedTime(getTime(result.getStartMillis()));
        ExtentTestManager.getTest().getTest().setEndedTime(getTime(result.getEndMillis()));

        for (String group : result.getMethod().getGroups()) {
            ExtentTestManager.getTest().assignCategory(group);
        }

        if (result.getStatus() == 1) {
            ExtentTestManager.getTest().log(LogStatus.PASS, "Test Passed");
        } else if (result.getStatus() == 2) {
            ExtentTestManager.getTest().log(LogStatus.FAIL, getStackTrace(result.getThrowable()));
        } else if (result.getStatus() == 3) {
            ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");
        }
        ExtentTestManager.endTest();
        extent.flush();
        if (result.getStatus() == ITestResult.FAILURE) {
            captureScreenshot(result.getName());
        }
        driverManager.quitDriver();
    }

    @AfterSuite
    public void generateReport() {
        extent.close();
    }

    private String captureScreenshot(String screenshotName) {
        DateFormat dateFormat = new SimpleDateFormat("(MM.dd.yyyy-HH;mma)");
        Date date = new Date();
        dateFormat.format(date);

        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        File screenshotFile = new File(System.getProperty("user.dir") +
                "/screenshots/" + screenshotName + " " + dateFormat.format(date) + ".png");

        try {
            FileUtils.copyFile(file, screenshotFile);
        } catch (IOException e) {
            System.out.println("Exception while taking screenshot: " + e.getMessage());
            e.printStackTrace();
        }
        return screenshotName;
    }

    protected String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }
}
