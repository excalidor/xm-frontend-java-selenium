package BasePages;

import Annotations.NavigationLinks;
import lombok.extern.slf4j.Slf4j;

import Utils.DriverSetup;
import Enums.NavigationMenuItems;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@Slf4j
public class NavigationMenu extends DriverSetup {

    @NavigationLinks(value = NavigationMenuItems.Home)
    public WebElement home;

    @NavigationLinks(value = NavigationMenuItems.ResearchAndEducation)
    public WebElement researchAndEducation;

    private final WebDriver driver;


    public NavigationMenu(WebDriver driver) {

        this.driver = driver;
    }


    public <T> T clickOn(NavigationMenuItems page) {

        Field[] fields = this.getClass().getDeclaredFields();
        for (Field f : fields) {
            try {
                NavigationLinks metadata = f.getAnnotation(NavigationLinks.class);
                if (metadata != null && metadata.value() == page) {
                    WebElement el = (WebElement) f.get(this);
                    if (el == null) {
                        el = driver.findElement(By.xpath(metadata.value().getXpath()));
                    }
                    el.click();
                    Class<? extends BasePage> clazz = metadata.value().getKmClass();
                    Constructor constructor = clazz.getConstructor();

                    return (T) constructor.newInstance();
                }
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException |
                     InstantiationException e) {
                log.error(e.getMessage());
            }
        }
        throw new RuntimeException("Navigation menu exception...");
    }
}
