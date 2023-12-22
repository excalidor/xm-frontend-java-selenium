# XM Frontend assignment
**Sample automation project**

 ## Test Automation Framework based on:

  - Java
  - Selenium WebDriver
  - TestNG
  - Maven

Detailed documentation about classes and methods can be found in Javadoc folder. 
Please feel free to access index.html file in Javadoc folder for more information. 

  ## Prerequisites:

  - Java > 1.8 or newer
  - Maven (latest)
  - Chrome browser (120.0.6099.129 or newer)

  ## To run tests:
  ______
  - right click on suite.xml (for the entire suite);
  - right click on each test class in testSuite package;
  - command line : open a terminal in the project root and type:  mvn clean test / mvn clean build
  ______

 ## Assignment requirements:
Automate next use case to run in three different browser’s screen resolution:
  ______
 -  Maximum (supported by your display)
 -  1024 x 768
 -  800 x 600
  ______

![img.png](img.png)1. Open Home page (make any check here if needed).

![img.png](img.png)2. Click the *Research and Education* link located at the top menu (make any check here if needed).

![img.png](img.png)3. Click *Economic Calendar* link in the opened menu (make any check here if needed).

![img.png](img.png)4. Select *Today* on Slider and check that the date is correct.

![img.png](img.png)5. Select *Tomorrow* on Slider and check that the date is correct.

![img.png](img.png)6. Select *Next Week* on Slider and check that the date is correct.

![img.png](img.png)7. Click *Educational Videos* link under *Research and Education*

![img.png](img.png)8. Click the Lesson 1.1 “Introduction to the Financial Markets.”

![img.png](img.png)9. Educational video should play for a minimum of 5 seconds

  ______
 ## How the project may be improved:
 - Adding support for CI/CD (switching browser to headless mode)
 - Adding support for parallelization (WebDriver => ThreadLocal)
 - Externalize various parameters (time to wait, etc.)
 - Using Cucumber/Serenity for better scenarios visibility
  ______