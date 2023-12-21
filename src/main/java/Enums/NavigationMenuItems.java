package Enums;


import BasePages.BasePage;

import PageObjects.ResearchAndEducationPage;


public enum NavigationMenuItems {

  Home("*//li[contains(.,'Home')]", BasePage.class),
  ResearchAndEducation("*//li[contains(.,'RESEARCH & EDUCATION')]", ResearchAndEducationPage.class);

  private final String xpath;
  private final Class<? extends BasePage> baseClass;

  NavigationMenuItems(String xpath, Class<? extends BasePage> baseClass) {
    this.xpath = xpath;
    this.baseClass = baseClass;
  }

  public Class<? extends BasePage> getKmClass() {
    return baseClass;
  }

  public String getXpath() {
    return xpath;
  }

}

