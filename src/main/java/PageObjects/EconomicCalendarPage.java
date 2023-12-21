package PageObjects;

import BasePages.BasePage;
import BasePages.IPageIdentity;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

    EconomicCalendarPage() {
        assertOnPage();
    }

    public boolean checkPageIdentity() {

        log.info("Checking Economic Calendar page identity...");
        return elementChecks.checkIfElementDisplayed(By.xpath("//*[@id=\"research-app\"]/div[3]/div[1]/div/span"));
    }


    public boolean checkPageElements() {

        log.info("Checking Research and Education page elements...");
        return elementChecks.checkIfElementDisplayed(By.xpath("//*[@id=\"research-app\"]/div[2]/div/div/div/nav/ul/li[7]/a"));
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
        wait.until(
                driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
        waitTime(5000);
        if (smallScreen) {
            element = driver.findElement(By.xpath("//*[@id=\"RecogniaContent\"]/table/tbody/tr/td/economic-calendar/div/tc-economic-calendar-landing/div/tc-economic-calendar-view-container/tc-header-container/div[2]/tc-time-filter-container/div/div/span/mat-slider/div/div[3]/div[2]"));
            actions.clickAndHold(element).dragAndDropBy(element, 50, 0).pause(500).release().pause(500).build().perform();
        } else {
            element = driver.findElement(By.xpath("//*[@id=\"RecogniaContent\"]/table/tbody/tr/td/economic-calendar/div/tc-economic-calendar-landing/div/tc-economic-calendar-view-container/div/div/div[2]/div[1]/tc-time-filter-container/div/div/span/mat-slider/div/div[3]/div[2]"));
            actions.clickAndHold(element).moveByOffset(50, 0).pause(500).release().pause(500).build().perform();
        }
    }

    private void waitUntilSliderMoves() {
        waitTime(5000);
        wait.until(
                driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id=\"RecogniaContent\"]/table/tbody/tr/td/economic-calendar/div/tc-economic-calendar-landing/div/tc-economic-calendar-view-container/div/div/div[1]/div"))));
//        await()
//                .atMost(30, TimeUnit.SECONDS)
//                .pollInterval(1, TimeUnit.SECONDS)
//                .until(() -> driver.findElements(By.xpath("*//div/div/div[2]/div[2]/div/div/div[2]/div/*")).size() > 0);
        waitTime(6000);
//        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("*//div/div/div[2]/div[2]/div/div/div[2]/div/*"))));
    }

    public EconomicCalendarPage moveSlidenAndCheckDate() {
        waitTime(4000);
        if (!smallScreen) scrollPage(0, 200);
        switchToIframe();
        List<String> dates = new ArrayList<>();
        dates.add(getLocalDate("today"));
        dates.add(getLocalDate("tomorrow"));
        dates.add(getLocalDate("nextWeek"));
        for (int i = 0; i < dates.size(); i++) {
            if (smallScreen && !elementChecks.checkIfElementDisplayed(By.xpath("//*[@id=\"RecogniaContent\"]/table/tbody/tr/td/economic-calendar/div/tc-economic-calendar-landing/div/tc-economic-calendar-view-container/tc-header-container/div[2]/tc-time-filter-container/div/div[1]/span/mat-slider/div/div[3]/div[2]"))) {
                WebElement element = driver.findElement(By.xpath("//*[@id=\"RecogniaContent\"]/table/tbody/tr/td/economic-calendar/div/tc-economic-calendar-landing/div/tc-economic-calendar-view-container/tc-header-container/div/tc-header/mat-toolbar/div[2]/span/div[1]/span/mat-icon"));
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
                if(elementChecks.checkIfElementsDisplayed(By.xpath("*//div/div/div[2]/div[2]/div/div/div[2]/div/span[1]"))) {
                    date = driver.findElement(By.xpath("*//div/div/div[2]/div[2]/div/div/div[2]/div/span[1]"));
                }else {
                    date = driver.findElement(By.xpath("//*[@id=\"economic-calendar-list\"]/div[1]/div[1]/span[1]"));
                }
                wait.until(ExpectedConditions.textToBePresentInElement(date, dates.get(i)));
            } else {
                wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath("//*[@id=\"economic-calendar-list\"]/div[1]/div[1]/span[1]")), dates.get(i)));
                date = driver.findElement(By.xpath("//*[@id=\"economic-calendar-list\"]/div[1]/div[1]/span[1]"));
            }

            Assert.assertTrue(date.getText().contains(dates.get(i)));
        }
        driver.switchTo().defaultContent();
        return new EconomicCalendarPage();
    }

}
