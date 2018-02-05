package pages;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GooglePage {

    private ExtentTest test;
    private WebDriver driver;
    private WebDriverWait wait;

    public GooglePage(WebDriver driver, ExtentTest test) {
        this.driver = driver;
        this.test = test;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 10);
    }

    @FindBy(name = "q")
    WebElement searchField;

    @FindBy(name = "btnK")
    WebElement searchButton;

    @FindBy(id = "search")
    WebElement searchResult;

    public void searchFor(String input) {
        wait.until(ExpectedConditions.elementToBeClickable(searchField));
        searchField.sendKeys(input);
        searchButton.click();
    }

    public boolean isOnresultsPage() {
        wait.until(ExpectedConditions.visibilityOf(searchResult));
        if (searchResult.isDisplayed()) {
            test.log(LogStatus.PASS, "Results Found on page");
            return true;
        }
        test.log(LogStatus.FAIL, "Results not found on page");
        return false;
    }
}
