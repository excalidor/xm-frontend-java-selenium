package PageObjects;


import BasePages.BasePage;
import BasePages.IPageIdentity;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;


@Slf4j
public class ResearchAndEducationPage extends BasePage implements IPageIdentity<BasePage> {

    private static final String PAGE_ELEMENT = "//*[@id=\"main-nav\"]/li[4]/div/div/div[1]/span";
    private static final String PAGE_ELEMENT_SMALL = "//*[@id=\"researchMenu\"]/ul/li[1]";
    private static final String IDENTITY_ELM_LOW_1 = "//*[@id=\"researchMenu\"]/ul/li[2]/a/span";
    private static final String IDENTITY_ELM_1 = "//*[@id=\"main-nav\"]/li[4]/div/div/div[3]/div[1]/span";
    private static final String ECONOMIC_CALENDAR_LOW_RES = "//*[@id=\"researchMenu\"]/ul/li[.//text()='Economic Calendar']/a/span";
    private static final String ECONOMIC_CALENDAR = "//*[@id=\"main-nav\"]/li[4]/div/div/div[3]/div[1]/ul/li[6]";

    public ResearchAndEducationPage() {

        assertOnPage();
    }


    public boolean checkPageIdentity() {

        log.info("Checking Research and Education page identity...");
        return (smallScreen && elementChecks.checkIfElementDisplayed(By.xpath(IDENTITY_ELM_LOW_1))) ||
                elementChecks.checkIfElementDisplayed(By.xpath(IDENTITY_ELM_1));
    }

    public boolean checkPageElements() {

        log.info("Checking Research and Education page elements...");
        return (smallScreen && elementChecks.checkIfElementDisplayed(PAGE_ELEMENT_SMALL))
                || elementChecks.checkIfElementDisplayed(PAGE_ELEMENT);
    }

    public EconomicCalendarPage clickOnEconomicCalendar() {
        if (smallScreen) {
            scrollIntoView(driver, driver.findElement(By.xpath(ECONOMIC_CALENDAR_LOW_RES)));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ECONOMIC_CALENDAR_LOW_RES)));
            elementActions.clickOn(ECONOMIC_CALENDAR_LOW_RES);
        } else {
            elementActions.clickOn(ECONOMIC_CALENDAR);
        }
        return new EconomicCalendarPage();
    }

    public EducationalVideosPage clickOnEducationalVideos() {
        if (smallScreen) {
            scrollIntoView(driver, driver.findElement(By.xpath("//*[@id=\"researchMenu\"]/ul/li[14]/a")));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"researchMenu\"]/ul/li[14]/a")));
            elementActions.clickOn("//*[@id=\"researchMenu\"]/ul/li[14]/a");
        } else {
            scrollIntoView(driver, driver.findElement(By.xpath("//*[@id=\"main-nav\"]/li[4]/div/div/div[3]/div[2]/ul/li[4]")));
            elementActions.clickOn(By.xpath("//*[@id=\"main-nav\"]/li[4]/div/div/div[3]/div[2]/ul/li[4]"));
        }
        return new EducationalVideosPage();
    }
}

