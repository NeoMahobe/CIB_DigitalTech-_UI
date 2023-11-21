package com.way2automation.qa.pages;

import com.codoid.products.exception.FilloException;
import com.paulhammant.ngwebdriver.NgWebDriver;
import com.way2automation.qa.utilities.TestUtil;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Page {
    private WebDriver driver;
    int polling = 1;
    int timeout = 15;

    public Page(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void SendKeys(WebElement element, String text) {
        Wait<WebDriver> wait = new FluentWait(driver)
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofSeconds(polling))
                .ignoring(WebDriverException.class);

        wait.until(
                d -> {
                    element.clear();
                    element.sendKeys(text);
                    return true;
                });

    }

    public void ClickRadioButton(WebElement element) {
        Wait<WebDriver> wait = new FluentWait(driver)
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofSeconds(polling))
                .ignoring(WebDriverException.class);

        wait.until(
                d -> {
                    element.click();
                    return true;
                });
    }

    public void Click(WebElement element) {
        Wait<WebDriver> wait = new FluentWait(driver)
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofSeconds(polling))
                .ignoring(WebDriverException.class);

        wait.until(
                d -> {
                    element.click();
                    return true;
                });
    }

    public void RetrieveAndSendKeys(WebElement element, String text, int value) throws IOException {
        TestUtil testUtil = new TestUtil();
        String cellvalue = testUtil.SelectDataFromExcel(text, value);
        SendKeys(element, cellvalue);
    }

    public void IsDisplayed(WebElement element) {

        Wait<WebDriver> wait = new FluentWait(driver)
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofSeconds(polling))
                .ignoring(WebDriverException.class);

        try {
            wait.until(
                    d -> {
                        element.isDisplayed();
                        return true;
                    });
        }catch (NullPointerException |NoSuchElementException e){
            Assert.fail("Element is not displayed as expected!");
        }
    }

}
