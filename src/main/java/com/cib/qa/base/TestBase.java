package com.cib.qa.base;

import com.cib.qa.utilities.WebDriverFactory;
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
            FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "" + "/src/main/java/com/cib/qa/config/config.properties");
            prop.load(ip);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialization(String env) throws IllegalAccessException {

        driver = new WebDriverFactory().create(prop.getProperty("browser"));
        setWebDriver(driver);
        getWebDriver().manage().window().maximize();
        getWebDriver().manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        try {
            prop.setProperty("environment", env);
        } catch (NullPointerException e) {
        }
        setScriptTimeout(30);

        if (prop.getProperty("environment").equals("live")) {
            url = "https://www.way2automation.com/angularjs-protractor/webtables/";
        }
    }

    public void setScriptTimeout(int timeout) {
        getWebDriver().manage().timeouts().setScriptTimeout(timeout, TimeUnit.SECONDS);
    }

    public WebDriver getWebDriver() {
        return dr.get();
    }

    public void setWebDriver(WebDriver driver) {
        dr.set(driver);
    }

}
