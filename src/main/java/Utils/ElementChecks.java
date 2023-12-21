package Utils;


import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;

import Enums.TextComparison;

@Slf4j
public class ElementChecks extends DriverSetup {

  public ElementChecks () {

    super();
  }


  public boolean checkIfElementDisplayed (String xpath) {

    List<WebElement> allElements = driver.findElements(By.xpath(xpath));
    return allElements.size() > 0 && allElements.get(0).isDisplayed();
  }


  /**
   * Checks if an element is displayed and also prints out the selector of the element
   * in case it is not displayed properly or it's not found
   *
   * @param locator the entire selector of an element (can be cssSelector, xpath, byID, etc)
   * @return true if it is displayed, false otherwise (if it's not displayed or not even found)
   */
  public boolean checkIfElementDisplayed (By locator) {

    try {
      if (!driver.findElement(locator).isDisplayed()) {
        log.info("NOT DISPLAYED: " + locator.toString());
        return false;
      }

      log.info("DISPLAYED: " + locator.toString());
      return true;
    } catch (NoSuchElementException e) {
      log.error("NOT FOUND: " + locator.toString());
      log.error(e.getMessage());
      return false;
    }
  }


  public boolean checkIfElementNotDisplayed (By locator) {

    try {
      if (!driver.findElement(locator).isDisplayed()) {
        log.error("\033[32mNOT DISPLAYED: " + locator.toString());
        return true;
      }

      log.info("\033[31mDISPLAYED: " + locator.toString());
      return false;
    } catch (NoSuchElementException e) {
      log.error("\033[32mNOT DISPLAYED: " + locator.toString());
      return true;
    }
  }


  /**
   * Check a series of elements in page if they are displayed
   *
   * @param locator as array
   * @return true or false if they are displayed
   */
  public boolean checkIfElementsDisplayed (By... locator) {

    boolean isElementDisplayed = true;
    for (By element : locator) {
      isElementDisplayed = isElementDisplayed && checkIfElementDisplayed(element);
    }

    return isElementDisplayed;
  }


  /**
   * Checks if the text / caption of a webpage element matches, is similar with or
   * includes the provided text. If not, also prints out the
   * xpath id of the element
   *
   * @param xpath        xpath id of the webpage element
   * @param compare_with text with which the text / caption of the webpage element is compared with
   * @param comparison   how to compare the text.
   * @return true if the text on the element matches with the expected text
   * false otherwise
   */
  public boolean checkElementText (String xpath, String compare_with, TextComparison comparison) {

    return checkElementText(By.xpath(xpath), compare_with, comparison);
  }


  /**
   * Checks if the text / caption of a webpage element matches, is similar with or
   * includes the provided text. If not, also prints out the
   * selector of the element (can be xpath, cssSelector, ID, class, etc.)
   *
   * @param locator      selector of the webpage element
   * @param compare_with text with which the text / caption of the webpage element is compared with
   * @param comparison   how to compare text
   * @return true if the text on the element matches with the expected text
   * false otherwise
   */
  public boolean checkElementText (By locator, String compare_with, TextComparison comparison) {

    StringBuilder message = new StringBuilder();

    String text = driver.findElement(locator).getText();
    message.append(String.format(
        "On element %s comparing the text: \"%s\" with \"%s\" using %s. ",
        locator,
        text,
        compare_with,
        comparison
    ));

    boolean result = comparison.match(text, compare_with);
    if (result) {
      message.append("\033[32mTRUE");
    } else {
      message.append("\033[31mFAILED");
      log.error("COMPARISON FAILED: '" + compare_with + "' does not " + comparison + " '" + text + "'");
    }
    message.append("\033[0m");

    log.info(message.toString());

    return result;
  }


  /**
   * Checks if the value of an attribute from a webpage element matches, is similar with or
   * includes the provided text. If not, also prints out the
   * xpath id of the element
   *
   * @param xpath        xpath id of the webpage element
   * @param attribute    name of the element's attribute
   * @param compare_with text with which the text / caption of the webpage element is compared with
   * @param comparison   how to compare text
   * @return true if the text on the element matches with the expected text
   * false otherwise
   */
  public boolean checkElementAttribute (
      String xpath,
      String attribute,
      String compare_with,
      TextComparison comparison
  ) {

    StringBuilder message = new StringBuilder();

    By locator = By.xpath(xpath);
    String text = driver.findElement(By.xpath(xpath)).getAttribute(attribute);
    message.append(String.format(
        "On element %s comparing the attribute \"%s\": \"%s\" with \"%s\" using %s. ",
        locator,
        attribute,
        text,
        compare_with,
        comparison
    ));

    boolean result = comparison.match(text, compare_with);
    if (result) {
      message.append("\033[32mTRUE");
    } else {
      message.append("\033[31mFAILED");
    }
    message.append("\033[0m");

    log.info(message.toString());

    return result;
  }


  public boolean checkElementAttribute (By locator, String attribute, String compare_with, TextComparison comparison) {

    StringBuilder message = new StringBuilder();

    String text = driver.findElement(locator).getAttribute(attribute);
    message.append(String.format(
        "On element %s comparing the attribute \"%s\": \"%s\" with \"%s\" using %s. ",
        locator,
        attribute,
        text,
        compare_with,
        comparison
    ));

    boolean result = comparison.match(text, compare_with);
    if (result) {
      message.append("\033[32mTRUE");
    } else {
      message.append("\033[31mFAILED");
    }
    message.append("\033[0m");

    log.info(message.toString());

    return result;
  }


  /**
   * Verifies that the image specified by its xpath id has been loaded successfully
   * <p>
   * It goes a bit beyond just checking it using isDisplayed() from Selenium
   *
   * @param xpath xpath id of the image to be verified
   * @return true if the image is loaded, false otherwise
   */
  public boolean checkImage (String xpath) {

    WebElement image = null;
    try {
      image = driver.findElement(By.xpath(xpath));
    } catch (Exception e) {
      log.error(e.getMessage());
    }

    Boolean image_loaded;
    image_loaded = (Boolean) ((JavascriptExecutor) driver).executeScript(
            "return arguments[0].complete && " +
                    "typeof arguments[0].naturalWidth != \"undefined\" &&" +
                    "arguments[0].naturalWidth > 0", image);

    if (image_loaded) {
      log.info(image_loaded.toString());
      return image_loaded;
    } else {
      log.info(image_loaded.toString());
      log.error("\033[31mNOT LOADED: " + xpath);
      return image_loaded;
    }
  }

    public boolean checkImage (WebElement image) {

      Boolean image_loaded;
      image_loaded = (Boolean) ((JavascriptExecutor) driver).executeScript(
              "return arguments[0].complete && " +
                      "typeof arguments[0].naturalWidth != \"undefined\" &&" +
                      "arguments[0].naturalWidth > 0", image);

      if (image_loaded) {
        log.info(image_loaded.toString());
        return image_loaded;
      } else {
        log.info(image_loaded.toString());
        log.error("\033[31mNOT LOADED: " + image);
        return image_loaded;
      }
  }
}