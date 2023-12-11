package com.way2automation.qa.base;

import com.way2automation.qa.utilities.WebDriverFactory;
import org.openqa.selenium.WebDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {

    protected WebDriver driver;
    public Properties prop;
    public static String url = null;
    public static long PAGE_LOAD_TIMEOUT = 60;

    public static ThreadLocal<WebDriver> dr = new ThreadLocal<>();

    public TestBase() {
        try {
            prop = new Properties();
            FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "" + "/src/main/java/com/way2Automation/qa/config/config.properties");
            prop.load(ip);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Initialization(String env) throws IllegalAccessException {

        driver = new WebDriverFactory().create(prop.getProperty("browser"));
        SetWebDriver(driver);
        GetWebDriver().manage().window().maximize();
        GetWebDriver().manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        GetWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        try {
            prop.setProperty("environment", env);
        } catch (NullPointerException e) {
        }
        SetScriptTimeout(30);

        if (prop.getProperty("environment").equals("live")) {
            url = "https://www.way2automation.com/angularjs-protractor/webtables/";
        }
    }

    public void SetScriptTimeout(int timeout) {
        GetWebDriver().manage().timeouts().setScriptTimeout(timeout, TimeUnit.SECONDS);
    }

    public WebDriver GetWebDriver() {
        return dr.get();
    }

    public void SetWebDriver(WebDriver driver) {
        dr.set(driver);
    }

}
