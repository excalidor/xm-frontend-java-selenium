package BasePages;


import PageObjects.ResearchAndEducationPage;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import Utils.DriverSetup;
import Enums.NavigationMenuItems;

import org.openqa.selenium.support.FindBy;


@Slf4j
public class BasePage extends DriverSetup implements IPageIdentity<BasePage> {

    private static final String TITLE = "Forex & CFD Trading on Stocks, Indices, Oil, Gold by XM™";
    private static final String HOME_PAGE_URL = "https://www.xm.com/";
    private @FindBy(xpath = "//*[@id=\"cookieModal\"]/div/div/div[1]/div[3]/div[2]/div[3]/button")
    WebElement acceptCookiesButton;

    private static final String MENU_SMALL_XPATH = "*//button[.//text()='Menu']";
    private static final String RESEARCH_AND_EDUCATION_SMALL_XPATH = "*//li[contains(.,'Research & Education')]/a/span";

    public BasePage() {

        log.info("Loading BasePage...");
    }

    @Override
    public boolean checkPageIdentity() {

        log.info("Checking base page identity...");
        return driver.getTitle().equals(TITLE)
                && driver.getCurrentUrl().equals(HOME_PAGE_URL);
    }

    @Override
    public boolean checkPageElements() {

        log.info("Checking base page elements...");
        return (smallScreen && elementChecks.checkIfElementDisplayed(By.xpath("*//div[3]/div/div/a/img")))
                || (elementChecks.checkIfElementDisplayed(By.xpath("//*[@id=\"navigation-collapse\"]/div/div[1]/a/img")));
    }

    public BasePage navigateToBasePage() {

        log.info("Navigating to base page...");
        driver.get(properties.getProperty("URL"));
        acceptCookies();
        assertOnPage();
        return new BasePage();
    }

    public BasePage acceptCookies() {
        if (elementChecks.checkIfElementDisplayed("//*[@id=\"cookieModal\"]/div/div/div[1]/div[3]/div[2]/div[3]/button")) {
            elementActions.clickOn("//*[@id=\"cookieModal\"]/div/div/div[1]/div[3]/div[2]/div[3]/button");
        }
        return new BasePage();
    }

    public void scrollIntoView(WebDriver driver, WebElement element) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void scrollPage(int x, int y) {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = "window.scrollBy(" + x + "," + y + ")";
        log.info(script);
        js.executeScript("window.scrollBy(" + x + "," + y + ")", "");
    }

    public <T extends BasePage> T clickMenuLink(NavigationMenuItems pageObjectType) {

        NavigationMenu menu = new NavigationMenu(driver);

        return menu.clickOn(pageObjectType);
    }

    public void waitUntilPageIsLoaded() {
        wait.until(
                driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
    }

    public ResearchAndEducationPage navigateToResearchAndEducationPage() {
        if (smallScreen) {
            elementActions.clickOn(MENU_SMALL_XPATH);
            elementActions.clickOn(RESEARCH_AND_EDUCATION_SMALL_XPATH);
        } else {
            clickMenuLink(NavigationMenuItems.ResearchAndEducation);
        }
        return new ResearchAndEducationPage();
    }
}
