package browserfactory;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

public class HeadlessChromeDriverManager extends DriverManager {

    private ChromeDriverService chromeService;

    @Override
    public void startService() {
        if (null == chromeService) {
            try {
                chromeService = new ChromeDriverService.Builder()
                        .usingDriverExecutable(new File(System.getProperty("user.dir") + "/driver/chromedriver"))
                        .usingAnyFreePort()
                        .build();
                chromeService.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void stopService() {
        if (null != chromeService && chromeService.isRunning()) {
            chromeService.stop();
        }
    }

    @Override
    public void createDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        options.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(chromeService);
    }
}
