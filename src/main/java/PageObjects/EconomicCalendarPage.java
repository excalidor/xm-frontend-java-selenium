package PageObjects;

import BasePages.BasePage;
import BasePages.IPageIdentity;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


@Slf4j
public class EconomicCalendarPage extends BasePage implements IPageIdentity<BasePage> {

    private static final String IDENTITY_ELEMENT_1 = "//*[@id=\"research-app\"]/div[3]/div[1]/div/span";
    private static final String IDENTITY_ELEMENT_2 = "//*[@id=\"research-app\"]/div[2]/div/div/div/nav/ul/li[7]/a";
    private static final String LOW_RES_SLIDER = "//*[@id=\"RecogniaContent\"]/table/tbody/tr/td/economic-calendar/div/tc-economic-calendar-landing/div/tc-economic-calendar-view-container/tc-header-container/div[2]/tc-time-filter-container/div/div/span/mat-slider/div/div[3]/div[2]";
    private static final String SLIDER = "//*[@id=\"RecogniaContent\"]/table/tbody/tr/td/economic-calendar/div/tc-economic-calendar-landing/div/tc-economic-calendar-view-container/div/div/div[2]/div[1]/tc-time-filter-container/div/div/span/mat-slider/div/div[3]/div[2]";
    private static final String CONTAINER = "//*[@id=\"RecogniaContent\"]/table/tbody/tr/td/economic-calendar/div/tc-economic-calendar-landing/div/tc-economic-calendar-view-container/div/div/div[1]/div";
    private static final String ELM_CHECK = "//*[@id=\"RecogniaContent\"]/table/tbody/tr/td/economic-calendar/div/tc-economic-calendar-landing/div/tc-economic-calendar-view-container/tc-header-container/div[2]/tc-time-filter-container/div/div[1]/span/mat-slider/div/div[3]/div[2]";
    private static final String ICON = "//*[@id=\"RecogniaContent\"]/table/tbody/tr/td/economic-calendar/div/tc-economic-calendar-landing/div/tc-economic-calendar-view-container/tc-header-container/div/tc-header/mat-toolbar/div[2]/span/div[1]/span/mat-icon";
    private static final String DATE_FIELD_LOW_SIZE = "*//div/div/div[2]/div[2]/div/div/div[2]/div/span[1]";
    private static final String DATE_FIELD = "//*[@id=\"economic-calendar-list\"]/div[1]/div[1]/span[1]";

    EconomicCalendarPage() {
        assertOnPage();
    }

    public boolean checkPageIdentity() {

        log.info("Checking Economic Calendar page identity...");
        return elementChecks.checkIfElementDisplayed(By.xpath(IDENTITY_ELEMENT_1));
    }


    public boolean checkPageElements() {

        log.info("Checking Research and Education page elements...");
        return elementChecks.checkIfElementDisplayed(By.xpath(IDENTITY_ELEMENT_2));
    }

    private String getLocalDate(String when) {
        LocalDate nowDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MMM dd", Locale.ENGLISH);
        String now = nowDate.format(formatter);
        String choice = "";
        switch (when) {
            case "today":
                choice = nowDate.format(formatter);
                break;
            case "tomorrow":
                choice = nowDate.plusDays(1).format(formatter);
                break;
            case "nextWeek":
                choice = nowDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).format(formatter);
                break;
            default:
                System.out.println("Date choice error.");
                break;
        }
        return choice;
    }

    private void switchToIframe() {
        try {
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("iFrameResizer0")));
        } catch (Exception e) {
            System.out.println("No iframe found. Proceeding without switching.");
        }
    }

    private void moveSlider() {
        WebElement element;
        Actions actions = new Actions(driver);
        waitUntilPageIsLoaded();
        waitTime(5000);
        if (smallScreen) {
            element = driver.findElement(By.xpath(LOW_RES_SLIDER));
            actions.clickAndHold(element).dragAndDropBy(element, 50, 0).pause(500).release().pause(500).build().perform();
        } else {
            element = driver.findElement(By.xpath(SLIDER));
            actions.clickAndHold(element).moveByOffset(50, 0).pause(500).release().pause(500).build().perform();
        }
    }

    private void waitUntilSliderMoves() {
        waitTime(5000);
        waitUntilPageIsLoaded();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(CONTAINER))));
//        await()
//                .atMost(30, TimeUnit.SECONDS)
//                .pollInterval(1, TimeUnit.SECONDS)
//                .until(() -> driver.findElements(By.xpath("*//div/div/div[2]/div[2]/div/div/div[2]/div/*")).size() > 0);
        waitTime(6000);
//        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("*//div/div/div[2]/div[2]/div/div/div[2]/div/*"))));
    }

    public EconomicCalendarPage moveSliderAndCheckDate() {
        waitTime(4000);
        if (!smallScreen) scrollPage(0, 200);
        switchToIframe();
        List<String> dates = new ArrayList<>();
        dates.add(getLocalDate("today"));
        dates.add(getLocalDate("tomorrow"));
        dates.add(getLocalDate("nextWeek"));
        for (int i = 0; i < dates.size(); i++) {
            if (smallScreen && !elementChecks.checkIfElementDisplayed(By.xpath(ELM_CHECK))) {
                WebElement element = driver.findElement(By.xpath(ICON));
                scrollIntoView(driver, element);
                elementActions.clickOn(element);
            }
            moveSlider();
            if (dates.get(i).equals(getLocalDate("nextWeek"))) {
                waitUntilSliderMoves();
                moveSlider();
            }
            waitUntilSliderMoves();
            WebElement date;
            if (!smallScreen) {
                if (elementChecks.checkIfElementsDisplayed(By.xpath(DATE_FIELD_LOW_SIZE))) {
                    date = driver.findElement(By.xpath(DATE_FIELD_LOW_SIZE));
                } else {
                    date = driver.findElement(By.xpath(DATE_FIELD));
                }
                wait.until(ExpectedConditions.textToBePresentInElement(date, dates.get(i)));
            } else {
                wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath(DATE_FIELD)), dates.get(i)));
                date = driver.findElement(By.xpath(DATE_FIELD));
            }

            Assert.assertTrue(date.getText().contains(dates.get(i)));
        }
        driver.switchTo().defaultContent();
        return new EconomicCalendarPage();
    }

}
