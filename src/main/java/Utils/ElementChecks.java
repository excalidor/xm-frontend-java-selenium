package Utils;


import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
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
      message.append("RUE");
    } else {
      message.append("FAILED");
      log.error("COMPARISON FAILED: '" + compare_with + "' does not " + comparison + " '" + text + "'");
    }
    message.append("\n");

    log.info(message.toString());

    return result;
  }
}