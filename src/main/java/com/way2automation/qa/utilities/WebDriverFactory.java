package com.way2automation.qa.utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverFactory {

    public static WebDriver create(String type) throws IllegalAccessException {
        WebDriver driver = null;

        if ("Chrome".equals(type)) {
            String chromedriverFilePath = System.getProperty("user.dir") + "/src/main/resources/chromedriver.exe";
            System.setProperty("webdriver.chrome.driver", chromedriverFilePath);
            ChromeOptions options = new ChromeOptions();
            options.addArguments("Remove --disable-infobars");
            options.addArguments("disable-popup-blocking");
            driver = new ChromeDriver(options);
        } else {
            throw new IllegalAccessException();
        }
        return driver;
    }
}
