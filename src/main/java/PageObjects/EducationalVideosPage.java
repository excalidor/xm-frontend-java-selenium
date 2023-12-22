package PageObjects;

import BasePages.BasePage;
import BasePages.IPageIdentity;
import org.openqa.selenium.By;

import org.openqa.selenium.support.ui.ExpectedConditions;

public class EducationalVideosPage extends BasePage implements IPageIdentity<BasePage> {


    public static final String FIRST_VIDEO = "/html/body/div[3]/div/div[6]/div[1]/ul/li[2]/div/ul/li[1]";

    public static final String VIDEO_IFRAME = "//*[@id=\"top\"]/div[3]/div/div[6]/div[1]/div/div[1]/iframe";

    public static final String FIRST_LESSON_HOLDER = "//*[@id=\"js-videoPlaylist\"]/li[2]/button";
    public static final String PLAY = "/html/body/div/div[2]";
    private static final String EDUCATIONAL_VIDEOS = "https://www.xm.com/educational-videos";
    private static final String IDENTITY_ELEMENT_1 = "//*[@id=\"top\"]/div[3]/div/div[3]/div/div/div[1]/h1";

    EducationalVideosPage() {
        assertOnPage();
    }

    @Override
    public boolean checkPageIdentity() {
        return driver.getCurrentUrl().equals(EDUCATIONAL_VIDEOS);
    }

    @Override
    public boolean checkPageElements() {
        return (smallScreen && elementChecks.checkIfElementDisplayed(By.xpath(IDENTITY_ELEMENT_1))) ||
                elementChecks.checkIfElementsDisplayed(By.xpath("//*[@id=\"top\"]/div[3]/div/div[3]/div/div/div[1]/h1"));
    }

    public EducationalVideosPage clickOnFirstLesson() {

        elementActions.clickOn(By.xpath(FIRST_LESSON_HOLDER));
        elementActions.clickOn(By.xpath(FIRST_VIDEO));
        return new EducationalVideosPage();
    }

    public EducationalVideosPage clickOnFirstLessonLowResolution() {
        elementActions.clickOn(FIRST_LESSON_HOLDER);
        elementActions.clickOn(FIRST_VIDEO);

        return new EducationalVideosPage();
    }


    public EducationalVideosPage clickToStartVideoLowResolution() {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(VIDEO_IFRAME)));
        waitTime(3000);
        elementActions.clickOn(PLAY);
        waitTime(10000);
        driver.switchTo().defaultContent();
        elementActions.clickOn(FIRST_VIDEO);
        return new EducationalVideosPage();
    }

    public EducationalVideosPage clickToStartVideo() {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(VIDEO_IFRAME)));
        waitTime(3000);
        elementActions.clickOn(By.xpath(PLAY));
        waitTime(10000);
        driver.switchTo().defaultContent();
        elementActions.clickOn(By.xpath(FIRST_VIDEO));
        return new EducationalVideosPage();
    }
}
