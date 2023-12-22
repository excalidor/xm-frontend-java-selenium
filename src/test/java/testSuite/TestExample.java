package testSuite;


import BasePages.BasePage;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

@Slf4j
public class TestExample extends BasePage {

    @Test
    public void xmTestFullScreenResolution() {
        if (!smallScreen) {
            navigateToBasePage()
                    .navigateToResearchAndEducationPage()
                    .clickOnEconomicCalendar()
                    .moveSliderAndCheckDate()
                    .navigateToResearchAndEducationPage();
            navigateToBasePage()
                    .navigateToResearchAndEducationPage()
                    .clickOnEducationalVideos()
                    .clickOnFirstLesson()
                    .clickToStartVideo();
        } else {
            log.info("This test is intended to run on higher resolution displays.");
        }
    }

    @Test
    public void xmTestMediumResolution() {
        setMediumResolution();
        if (!smallScreen) {
            navigateToBasePage()
                    .navigateToResearchAndEducationPage()
                    .clickOnEconomicCalendar()
                    .moveSliderAndCheckDate()
                    .navigateToResearchAndEducationPage();
            navigateToBasePage()
                    .navigateToResearchAndEducationPage()
                    .clickOnEducationalVideos()
                    .clickOnFirstLessonLowResolution()
                    .clickToStartVideoLowResolution();
        } else {
            log.info("This test is intended to run on higher resolution displays.");
        }
    }

    @Test
    public void xmTestLowResolution() {
        setLowResolution();
        navigateToBasePage()
                .navigateToResearchAndEducationPage()
                .clickOnEconomicCalendar()
                .moveSliderAndCheckDate()
                .navigateToResearchAndEducationPage();
        navigateToBasePage()
                .navigateToResearchAndEducationPage()
                .clickOnEducationalVideos()
                .clickOnFirstLessonLowResolution()
                .clickToStartVideoLowResolution();
    }
}
