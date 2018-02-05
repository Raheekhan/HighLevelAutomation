package pages;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.PageObject;

public class HomePage extends PageObject {

    private ExtentTest test;
    private WebDriverWait wait;

    public HomePage(WebDriver driver, ExtentTest test) {
        super(driver);
        this.test = test;
        wait = new WebDriverWait(driver, 10);
    }

    @FindBy(id = "")
    WebElement a;

    @FindBy(id = "")
    WebElement b;

    @FindBy(id = "")
    WebElement c;

    @FindBy(id = "")
    WebElement d;

    @FindBy(id = "")
    WebElement e;

    @FindBy(id = "")
    WebElement f;

    @FindBy(id = "")
    WebElement j;

    public void testing() {
        test.log(LogStatus.FAIL, "");
    }
}
