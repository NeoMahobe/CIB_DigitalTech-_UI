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
        Wait <WebDriver> wait = new FluentWait(driver)
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
        Wait <WebDriver> wait = new FluentWait(driver)
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(WebDriverException.class);

        wait.until(
                d -> {
                    element.click();
                    return true;
                });
    }

    public void Click(WebElement element) {
        Wait <WebDriver> wait = new FluentWait(driver)
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofSeconds(polling))
                .ignoring(WebDriverException.class);

        wait.until(
                d -> {
                    element.click();
                    return true;
                });
    }

    public void ExSendKeys(WebElement element, String text) throws InterruptedException, IOException {
        TestUtil testUtil = new TestUtil();
        String value = testUtil.SelectDataFromExcel(text);
        SendKeys(element, value);
    }

    public void SelectByIndex(WebElement element, int index)  {

        Wait <WebDriver> wait = new FluentWait(driver)
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofSeconds(polling))
                .ignoring(WebDriverException.class);

        wait.until(
                d -> {
                    Select dropdown = new Select(element);
                    dropdown.selectByIndex(index);
                    return true;
                });
    }

}
