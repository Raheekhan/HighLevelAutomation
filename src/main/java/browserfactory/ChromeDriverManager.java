package browserfactory;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;

import java.io.File;

public class ChromeDriverManager extends DriverManager {

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
        driver = new ChromeDriver(chromeService);
    }
}
