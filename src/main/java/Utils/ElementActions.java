package Utils;


import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;


@Slf4j
public class ElementActions extends DriverSetup {

  public ElementActions () {

    super();
  }

  public void clickOn (WebElement element) {

    log.info("Clicking on: " + element);

    try {
      element.click();
    } catch (Exception e) {
      throw e;
    }
  }


  /**
   * Perform a left mouse click on the provided element
   *
   * @param locator locator of the element to be clicked
   */
  public void clickOn (By locator) {

    wait.until(visibilityOfElementLocated(locator));
    clickOn(driver.findElement(locator));
  }


  /**
   * Perform a left mouse click on the provided element, with scrolling if needed in order for the
   * element to come into view
   *
   * @param xpath xpath id of the element to be clicked
   */
  public void clickOn (String xpath) {

    WebElement element = driver.findElement(By.xpath(xpath));
    ((JavascriptExecutor) driver).executeScript(
        "arguments[0].scrollIntoView({behavior: 'instant', block: 'start'});",
        element
    );
    wait.until(visibilityOf(element));
    clickOn(element);
  }


  /**
   * Hovers the mouse over an element provided through a locator
   *
   * @param locator The locator of the element we want to mouse over
   */
  public void mouseOver (By locator) {

    log.info("Mousing over " + locator.toString());
    Actions action = new Actions(driver);

    try {
      action.moveToElement(driver.findElement(locator)).build().perform();
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }


  /**
   * Gets the text from By locator location and returns it as a String
   *
   * @param locator locator for the element that we want to get the text from
   * @return a String with the text found for this element
   */
  public String getTextFrom (By locator) {

    String returned_text = "";

    try {
      returned_text = driver.findElement(locator).getText();
    } catch (Exception e) {
      log.error(e.getMessage());
    }

    log.info("Text returned: \"" + returned_text + "\"");
    return returned_text;
  }


  public String getTextFromAttribute (String xpath, String attribute) {

    return getTextFromAttribute(By.xpath(xpath), attribute);

  }


  public String getTextFromAttribute (By locator, String attribute) {

    String returned_text = "";

    try {
      returned_text = driver.findElement(locator).getAttribute(attribute);
    } catch (Exception e) {
      log.error(e.getMessage());
    }

    log.info("Text returned: \"" + returned_text + "\"");
    return returned_text;
  }


  public void clearAndTypeText (By locator, String content) {

    clearText(locator);
    typeText(locator, content);
  }


  /**
   * Types in the provided text into the provided webpage element
   *
   * @param locator selector of the element to receive the text
   * @param content text to be typed in
   */
  public void typeText (By locator, String content) {

    log.info("Typing \"" + content + "\" \n\tinto " + locator.toString());

    try {
      wait.until(elementToBeClickable(locator));
      driver.findElement(locator).sendKeys(content);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }


  /**
   * Types in the provided password into the provided webpage element, without showing it in the
   * logs
   *
   * @param locator selector of the element to receive the text
   * @param content text to be typed in
   */
  public void typePassword (By locator, String content) {

    // we don't want passwords displayed in the logs
    log.info(
        "Typing the provided password, '" + content.substring(0, 2) + "***' \n\tinto "
            + locator.toString());

    try {
      wait.until(elementToBeClickable(locator));
      driver.findElement(locator).sendKeys(content);
    } catch (Exception e) {
      log.error(e.getMessage());
    }

  }


  /**
   * Clears all text from an element
   *
   * @param locator selector of the element to be cleared
   */
  public void clearText (By locator) {

    log.info("Clearing text from " + locator.toString());

    try {
      wait.until(elementToBeClickable(locator));
      driver.findElement(locator).clear();
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }


  /**
   * Moves a slider to a set of specific coordinates: x and y
   *
   * @param locator the locator for the slider
   * @param x       coordinate for the target
   * @param y       coordinate for the target
   */
  public void moveSlider (By locator, int x, int y) {

    log.info("Moving slider " + locator.toString() + " \n\tto (" + x + ", " + y + ")");

    try {
      Actions move = new Actions(driver);
      WebElement slide = driver.findElement(locator);
      move.dragAndDropBy(slide, x, y).perform();
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }


  /**
   * Checks if a box is selected
   *
   * @param locator the locator of the box we want to check if it is selected
   * @return true if it's selected, false otherwise
   */
  public boolean checkboxSelected (By locator) {

    boolean result = false;

    try {
      result = driver.findElement(locator).isSelected();
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return result;
  }


  /**
   * Selects one of the options of the element identified by locator, according to value
   *
   * @param locator the locator of the element to which we wish to apply the selection
   * @param value   the value of the option we wish to select
   */
  public void selectOption (By locator, String value) {

    log.info("Selecting option: " + value + " \n\tfrom element: " + locator.toString());

    try {
      Select select = new Select(driver.findElement(locator));
      select.selectByValue(value);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }
}