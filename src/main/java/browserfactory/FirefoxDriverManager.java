package browserfactory;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverService;

import java.io.File;

public class FirefoxDriverManager extends DriverManager {

    private GeckoDriverService geckoService;

    @Override
    public void startService() {
        if (null == geckoService) {
            try {
                geckoService = new GeckoDriverService.Builder()
                        .usingDriverExecutable(new File(System.getProperty("user.dir") + "/driver/geckodriver"))
                        .usingAnyFreePort()
                        .build();
                geckoService.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void stopService() {
        if (null != geckoService && geckoService.isRunning()) {
            geckoService.stop();
        }
    }

    @Override
    public void createDriver() {
        driver = new FirefoxDriver(geckoService);
    }
}
