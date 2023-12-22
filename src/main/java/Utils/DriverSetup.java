package Utils;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


@Slf4j
public class DriverSetup {

    public static WebDriver driver;

    public Properties properties;

    public static ElementActions elementActions;

    public static ElementChecks elementChecks;

    public static boolean smallScreen = false;
    public static int maxHeight;

    public static int maxWidth;

    public static Select select;

    protected static int timeout = 30;

    protected static Wait<WebDriver> wait;

    @BeforeTest
    public void setUp() {

        log.info("Setup driver starting...");
        if (System.getProperty("os.name").contains("Windows")) {
            System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
        } else {
            System.setProperty("webdriver.chrome.driver", "resources/chromedriver");
        }
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        maxHeight = driver.manage().window().getSize().getHeight();
        maxWidth = driver.manage().window().getSize().getWidth();
        if (maxWidth <= 802 || maxHeight <= 602) smallScreen = true;
        properties = loadProperties();
        wait = new WebDriverWait(driver, timeout);
        elementActions = new ElementActions();
        elementChecks = new ElementChecks();
        log.info("Setup driver done...");
    }

    public void setLowResolution() {
        Dimension dimension = new Dimension(800, 600);
        driver.manage().window().setSize(dimension);
        smallScreen = true;
    }

    public void setMediumResolution() {
        maxHeight = driver.manage().window().getSize().getHeight();
        maxWidth = driver.manage().window().getSize().getWidth();
        if (maxWidth <= 1026 || maxHeight <= 770) {
            setLowResolution();
        } else {
            Dimension dimension = new Dimension(1024, 768);
            driver.manage().window().setSize(dimension);
            smallScreen = false;
        }
    }

    public Properties loadProperties() {

        Properties prop = new Properties();

        try (InputStream input = Files.newInputStream(Paths.get("src/main/resources/config.properties"))) {
            prop.load(input);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        log.info("Properties loaded....");
        return prop;
    }

    @AfterTest
    public void tearDown() {

        log.info("Driver tear down...");
        driver.quit();
    }

    /**
     *
     * @param millisecs the length of the pause, in milliseconds
     */
    public void waitTime(int millisecs) {


        log.debug("Waiting " + (double) millisecs / 1000 + " seconds.");
        try {
            Thread.sleep(millisecs);
        } catch (InterruptedException e) {
            log.error(String.valueOf(e), driver);
            Thread.currentThread().interrupt();
        }
    }

}