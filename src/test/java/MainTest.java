
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainTest {
    private WebDriver webDriver;
    private ChromeOptions chromeOptions;
    private Actions actions;
    WebElement allowAllBtn;
    WebElement telerikSite;
    WebDriverWait wait;

    @BeforeEach
    public void setup(){
        chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--disable-blink-features=AutomationControlled");

        webDriver = new ChromeDriver(chromeOptions);
        webDriver.manage().deleteAllCookies();

        actions = new Actions(webDriver);

    }
    @AfterEach
    public void teardown() throws InterruptedException {
        if (webDriver != null){
            Thread.sleep(1000);
            webDriver.quit();
        }

    }

    @Test
    public void navigateToBing_and_SuccessfullyOpenTelerikAcademyURL() throws InterruptedException {

        webDriver.get("https://www.bing.com");
        webDriver.findElement(By.xpath("//textarea")).sendKeys("Telerik Academy Alpha");

        Thread.sleep(500);
        webDriver.findElement(By.xpath("//textarea")).submit();
        Thread.sleep(1000);
        webDriver.findElement(By.xpath("//a[@href=\"https://www.telerikacademy.com/alpha\" and contains(text(), \"Launch your IT career in your free time - Telerik Academy Alpha\")]")).click();

        WebElement titleFirstResult = webDriver.findElement(By.xpath("//a[@href=\"https://www.telerikacademy.com/alpha\" and contains(text(), \"Launch your IT career in your free time - Telerik Academy Alpha\")]"));
        String actualResult = titleFirstResult.getText();
        String expectedResult = "Launch your IT career in your free time - Telerik Academy Alpha";

        Assertions.assertEquals(expectedResult, actualResult, "Expected Title of the first page was: " + expectedResult + "but got: " + actualResult);

    }
    @Test
    public void navigateToGoogleSuccessfully_and_AcceptCookies_thenValidateTheTitleOfTheFirstResult() throws InterruptedException {

        webDriver.get("https://www.google.com/");

        wait = new WebDriverWait(webDriver, Duration.ofSeconds(4));
        allowAllBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[div[contains(text(), \"Приемане на всички\")]]")));

        Thread.sleep(500);
        allowAllBtn.click();

        WebElement searchInput = webDriver.findElement(By.xpath("//textarea[@autofocus and @title=\"Търсене\"]"));

        Thread.sleep(500);
        searchInput.click();

        searchInput.sendKeys("Telerik Academy Alpha");

        Thread.sleep(500);
        searchInput.submit();
        Thread.sleep(500);
        telerikSite = webDriver.findElement(By.xpath("//h3[contains(text(), \"Launch your IT career in your free time - Telerik Academy Alpha\")]"));

        String expectedResult = "Launch your IT career in your free time - Telerik Academy Alpha";
        String actualResult = telerikSite.getText();

        Thread.sleep(500);
        telerikSite.click();

        wait = new WebDriverWait(webDriver, Duration.ofSeconds(4));
        allowAllBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll")
        ));

        Thread.sleep(500);
        allowAllBtn.click();


        WebElement aboutQA = webDriver.findElement(By.xpath("//p[@class=\"-mt1  -mb2 -ls-0 -fwn -ff-base -lh-15\"]"));
        actions = new Actions(webDriver);
        actions.scrollToElement(aboutQA).build().perform();

        Thread.sleep(500);
        aboutQA.click();


        Assertions.assertEquals(expectedResult, actualResult, "Expected result was: " + expectedResult + ", but it was:" + actualResult);





    }

}