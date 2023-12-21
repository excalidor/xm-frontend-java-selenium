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

    public ResearchAndEducationPage() {

        assertOnPage();
    }


    public boolean checkPageIdentity() {

        log.info("Checking Research and Education page identity...");
        return (smallScreen && elementChecks.checkIfElementDisplayed(By.xpath("//*[@id=\"researchMenu\"]/ul/li[2]/a/span"))) ||
                elementChecks.checkIfElementDisplayed(By.xpath("//*[@id=\"main-nav\"]/li[4]/div/div/div[3]/div[1]/span"));
    }

    public boolean checkPageElements() {

        log.info("Checking Research and Education page elements...");
        return (smallScreen && elementChecks.checkIfElementDisplayed(PAGE_ELEMENT_SMALL))
                || elementChecks.checkIfElementDisplayed(PAGE_ELEMENT);
    }

    public EconomicCalendarPage clickOnEconomicCalendar() {
        if (smallScreen) {
            scrollIntoView(driver, driver.findElement(By.xpath("//*[@id=\"researchMenu\"]/ul/li[.//text()='Economic Calendar']/a/span")));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"researchMenu\"]/ul/li[.//text()='Economic Calendar']/a/span")));
            elementActions.clickOn("//*[@id=\"researchMenu\"]/ul/li[.//text()='Economic Calendar']/a/span");
        } else {
            elementActions.clickOn("//*[@id=\"main-nav\"]/li[4]/div/div/div[3]/div[1]/ul/li[6]");
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

