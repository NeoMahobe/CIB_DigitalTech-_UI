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
    private Properties prop;
    private WebDriver driver;
    private String sin;
    int maxTries = 60;
    int seconds = 30;

    public Page(WebDriver driver) {
//        try {
//            prop = new Properties();
//            FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/com/cib/qa/config/config.properties");
//            prop.load(ip);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public Properties getProp() {
        return prop;
    }

    public void SwitchWindow() {
        String parentWindowHandler = driver.getWindowHandle(); // Store your parent window
        String subWindowHandler = null;

        Set<String> handles = driver.getWindowHandles(); // get all window handles
        Iterator<String> iterator = handles.iterator();
        while (iterator.hasNext()) {
            subWindowHandler = iterator.next();
        }
        driver.switchTo().window(subWindowHandler); // switch to popup window
        driver.switchTo().window(parentWindowHandler);  // switch back to parent window
        driver.switchTo().alert().accept();// switch to alert and accept

    }

    public void sendKeys(WebElement element, String text) throws InterruptedException {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(1))
                .pollingEvery(Duration.ofSeconds(1));

        int count = 0;

        WebElement we;
        WebElement we2;

        while (true) {
            try {
                we = wait.until(ExpectedConditions.visibilityOf(element));
                we2 = wait.until(ExpectedConditions.elementToBeClickable(element));
                element.clear();
                ((JavascriptExecutor) driver).executeScript("arguments[1].value = arguments[0];", text, element);

                break;
            } catch (InvalidElementStateException e) {
                Thread.sleep(1000);
                if (++count == maxTries) throw e;
            } catch (StaleElementReferenceException e) {
                Thread.sleep(1000);
                if (++count == maxTries) throw e;
            } catch (NoSuchElementException e) {
                Thread.sleep(1000);
                if (++count == maxTries) throw e;
            } catch (WebDriverException e) {
                Thread.sleep(1000);
                if (++count == maxTries) throw e;
            }
        }

    }

    public void sendKeyz(WebElement element, String text) throws InterruptedException {
//        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
//                .withTimeout(1, TimeUnit.SECONDS)
//                .pollingEvery(1, TimeUnit.SECONDS);
//
//        int count = 0;
//
//        WebElement we;
//        WebElement we2;
//
//        while (true) {
//            try {
//                we = wait.until(ExpectedConditions.visibilityOf(element));
//                we2 = wait.until(ExpectedConditions.elementToBeClickable(element));
                element.clear();
                element.sendKeys(text);
//                break;
//            } catch (InvalidElementStateException e) {
//                Thread.sleep(1000);
//                if (++count == maxTries) throw e;
//            } catch (StaleElementReferenceException e) {
//                Thread.sleep(1000);
//                if (++count == maxTries) throw e;
//            } catch (NoSuchElementException e) {
//                Thread.sleep(1000);
//                if (++count == maxTries) throw e;
//            } catch (WebDriverException e) {
//                Thread.sleep(1000);
//                if (++count == maxTries) throw e;
//            }
       // }
    }

    public void ngSendKeys(WebElement element, String text) throws InterruptedException {
        new NgWebDriver((JavascriptExecutor) driver).waitForAngularRequestsToFinish();
        sendKeyz(element, text);
    }

    public void exSendKeys(WebElement element, String text) throws InterruptedException, FilloException, IOException {
        TestUtil testUtil = new TestUtil();
        new NgWebDriver((JavascriptExecutor) driver).waitForAngularRequestsToFinish();
        String value = testUtil.SelectDataFromExcel(text);
        sendKeyz(element, value);
    }

    public void clickRadio(WebElement element) throws InterruptedException {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(1, TimeUnit.SECONDS)
                .pollingEvery(1, TimeUnit.SECONDS);

        int count = 0;
        WebElement we;
        WebElement we2;

        while (true) {
            try {
                we = wait.until(ExpectedConditions.visibilityOf(element));
                we2 = wait.until(ExpectedConditions.elementToBeClickable(element));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                break;
            } catch (InvalidElementStateException e) {
                Thread.sleep(1000);
                if (++count == maxTries) throw e;
            } catch (StaleElementReferenceException e) {
                Thread.sleep(1000);
                if (++count == maxTries) throw e;
            } catch (NoSuchElementException e) {
                Thread.sleep(1000);
                if (++count == maxTries) throw e;
            } catch (WebDriverException e) {
                Thread.sleep(1000);
                if (++count == maxTries) throw e;
            }
        }
        Thread.sleep(300);
        if (element.isSelected()) {
        } else {
            element.click();
        }
    }

    public void Select(WebElement element, String text) throws InterruptedException {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(getDriver())
                .withTimeout(1, TimeUnit.SECONDS)
                .pollingEvery(1, TimeUnit.SECONDS);

        int count = 0;
        WebElement we;
        WebElement we2;

        while (true) {
            try {
                we = wait.until(ExpectedConditions.elementToBeClickable(element));
                we2 = wait.until(ExpectedConditions.elementToBeClickable(element));
                Select dropdown = new Select(element);
                dropdown.selectByVisibleText(text);
                break;
            } catch (InvalidElementStateException e) {
                Thread.sleep(1000);
                if (++count == maxTries) throw e;
            } catch (StaleElementReferenceException e) {
                Thread.sleep(1000);
                if (++count == maxTries) throw e;
            } catch (NoSuchElementException e) {
                Thread.sleep(1000);
                if (++count == maxTries) throw e;
            } catch (WebDriverException e) {
                Thread.sleep(1000);
                if (++count == maxTries) throw e;
            }
        }
    }

    public void ngSelectbyIndex(WebElement element, int index) throws InterruptedException {
        for (int count = 0; count < 59; count++) {
            try {
                new NgWebDriver((JavascriptExecutor) driver).waitForAngularRequestsToFinish();
                Select dropdown = new Select(element);
                dropdown.selectByIndex(index);
                break;
            } catch (NoSuchElementException e) {
                Thread.sleep(1000);
                System.out.println("Dropdown element not found: " + count);
            } catch (WebDriverException e) {
                Thread.sleep(1000);
                System.out.println("Dropdown element not ready: " + count);
            }
        }
    }

    public void overlayWait() {
        waitForAllOverlays();
    }

    public void waitForAllOverlays() {
        List<WebElement> myTagsWithId = driver.findElements(By.xpath("//div[contains(@id,'overlay') and  not(contains(@id,'content'))]"));
        for (int i = 0; i < myTagsWithId.size(); i++) {
            try {
                new WebDriverWait(driver, 60).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[id=" + myTagsWithId.get(i).getAttribute("id") + "]")));
            } catch (StaleElementReferenceException e) {
            } catch (NoSuchElementException e) {
            }
        }
    }


    public void waitForPageLoaded() {
        ExpectedCondition<Boolean> expectation = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
                    }
                };
        try {
            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(expectation);
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for Page Load Request to complete.");
        }
    }

    public void scrollIntoView(WebElement element) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(1, TimeUnit.SECONDS)
                .pollingEvery(seconds, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class, WebDriverException.class);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void click(WebElement element) throws InterruptedException {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(1, TimeUnit.SECONDS)
                .pollingEvery(1, TimeUnit.SECONDS);

        int count = 0;
        WebElement we;
        WebElement we2;

        while (true) {
            try {
                we = wait.until(ExpectedConditions.visibilityOf(element));
                we2 = wait.until(ExpectedConditions.elementToBeClickable(element));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                break;
            } catch (InvalidElementStateException e) {
                Thread.sleep(1000);
                if (++count == maxTries) throw e;
            } catch (StaleElementReferenceException e) {
                Thread.sleep(1000);
                if (++count == maxTries) throw e;
            } catch (NoSuchElementException e) {
                Thread.sleep(1000);
                if (++count == maxTries) throw e;
            } catch (WebDriverException e) {
                Thread.sleep(1000);
                if (++count == maxTries) throw e;
            }
        }
    }

    public void mousehover(WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element).click(element).perform();
        //action.click();
    }

    public String getText(WebElement element) throws InterruptedException {
        new WebDriverWait(driver, 20).pollingEvery(1, TimeUnit.SECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).until(
                ExpectedConditions.visibilityOf(element));
        String text = element.getText();
        return text;
    }

    public boolean isDisplayed(WebElement element) throws InterruptedException {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        } catch (StaleElementReferenceException e) {
            try {
                return element.isDisplayed();
            } catch (NoSuchElementException f) {
                return false;
            }
        }
    }

    public void isVisible(WebElement element) throws InterruptedException {
        int count = 0;
        int maxTries = 3;

        while (true) {
            try {
                new WebDriverWait(driver, 1).until(ExpectedConditions.visibilityOf(element));
                break;
            } catch (InvalidElementStateException e) {
                Thread.sleep(1000);
                if (++count == maxTries) {
                    throw e;
                }
            } catch (StaleElementReferenceException e) {
                Thread.sleep(1000);
                if (++count == maxTries) throw e;
            } catch (NoSuchElementException e) {
                Thread.sleep(1000);
                if (++count == maxTries) throw e;
            } catch (WebDriverException e) {
                Thread.sleep(1000);
                if (++count == maxTries) throw e;
            }
        }
    }

    public void ngClick(WebElement element) throws InterruptedException {
        new NgWebDriver((JavascriptExecutor) driver).waitForAngularRequestsToFinish();
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }


    private static double determinExpensePerAct(double value) {
        // [(Income - Minimum Income Band) x Monthly fixed factor %] + Min Monthly Fixed Factor
        return ((value - determineMinBoundary(value)) * (determineMonthlyFixedFactor(value) / 100)) + determineMinMonthlyFixedFactor(value);
    }

    public static double determineMonthlyFixedFactor(double income) {
        double monthlyFixedFact = 100.0;

        if ((income >= 800.01) && (income < 6250.01))
            monthlyFixedFact = 6.75;
        if ((income >= 6250.01) && (income < 25000.01))
            monthlyFixedFact = 9.0;
        if ((income >= 25000.01) && (income < 50000.01))
            monthlyFixedFact = 8.2;
        if ((income >= 50000.01))
            monthlyFixedFact = 6.75;

        return monthlyFixedFact;
    }

    public static double determineMinBoundary(double income) {
        double minBoundary = 0.0;

        if ((income >= 800.01) && (income < 6250.01))
            minBoundary = 800.01;
        if ((income >= 6250.01) && (income < 25000.01))
            minBoundary = 6250.01;
        if ((income >= 25000.01) && (income < 50000.01))
            minBoundary = 25000.01;
        if ((income >= 50000.01))
            minBoundary = 50000.01;

        return minBoundary;
    }

    public static double determineMinMonthlyFixedFactor(double income) {
        double minMonthlyFixedFact = 0.0;

        if ((income >= 800.01) && (income < 6250.01))
            minMonthlyFixedFact = 800.0;
        if ((income >= 6250.01) && (income < 25000.01))
            minMonthlyFixedFact = 1167.88;
        if ((income >= 25000.01) && (income < 50000.01))
            minMonthlyFixedFact = 2855.38;
        if ((income >= 50000.01))
            minMonthlyFixedFact = 4905.38;
        return minMonthlyFixedFact;
    }

    private double formatDoubleValues(double value) {
        DecimalFormat dc = new DecimalFormat("#.00");
        return new Double(dc.format(value));
    }

    public void VERIFY_MINUTE_STATUS_CHECK(String SIN, String Expected) throws IOException {

        String Actual = MINUTE_STATUS_CHECK(SIN);
        if (!Actual.equalsIgnoreCase(Expected)) {
            Assert.fail("Assertion Failed: The Credit Minute status does not equal ' " + Expected + "'");
        }
    }

    public String MINUTE_STATUS_CHECK(String SIN) throws IOException {
        String EnvUrl = null;
        String Sin = SIN;
        if (prop.getProperty("environment").equals("stg")) {
            EnvUrl = "https://pbzaesb-stg.investec.corp:5443/rest/InvZaSbBpCreditEsb/api/v1/creditminutes/" + Sin;
        } else if (prop.getProperty("environment").equals("dev")) {
            EnvUrl = "https://pbzaesb-dev.investec.corp:5443/rest/InvZaSbBpCreditEsb/api/v1/creditminutes/" + Sin;
        } else if (prop.getProperty("environment").equals("tst")) {
            EnvUrl = "https://pbzaesb-tst.investec.corp:5443/rest/InvZaSbBpCreditEsb/api/v1/creditminutes/" + Sin;
        }

        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
        }
        System.setProperty("javax.net.ssl.trustStore", "keystore.jks");

        URL obj = new URL(EnvUrl);
        HttpsURLConnection httpCon = (HttpsURLConnection) obj.openConnection();
        httpCon.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(httpCon.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        String Actual = StringUtils.substringBetween(response.toString(), "\"minuteStatus\":\"", "\",\"");
        return Actual;
    }

//    public  void takeScreenshotAtEndOfTest(String testName) throws IOException {
//        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//        String currentDir = System.getProperty("user.dir");
//        SimpleDateFormat sdf = new SimpleDateFormat("HH_mma");
//        Date resultdate = new Date(System.currentTimeMillis());
//        FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/"+testName+ "_"+ sdf.format(resultdate) + ".png"));
//    }

    public void toByVal(WebElement we) throws InterruptedException {
        String two = we.toString();
        if (two.contains("->")) {
            String[] data = two.split(" -> ")[1].split(": ");
            String strategy = data[0];
            String locator = data[1].substring(0, data[1].length() - 1);
            getLocatorStrategy(strategy, locator);
        } else if (two.contains("linkText:")) {
            String strategy = "link text";
            String[] data = two.split("linkText: ");
            String locator = data[1].substring(0, data[1].length() - 1);
            getLocatorStrategy(strategy, locator);
        } else if (two.contains("By.xpath:")) {
            String strategy = "xpath";
            String[] data = two.split("By.xpath: ");
            String locator = data[1].substring(0, data[1].length() - 1);
            getLocatorStrategy(strategy, locator);
        } else if (two.contains("By.cssSelector:")) {
            String strategy = "css";
            String[] data = two.split("By.cssSelector: ");
            String locator = data[1].substring(0, data[1].length() - 1);
            getLocatorStrategy(strategy, locator);
        } else {
            Assert.fail();
        }

    }

    public WebElement getElementByLocator(final By locator) throws InterruptedException {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(1, TimeUnit.SECONDS)
                .pollingEvery(1, TimeUnit.SECONDS);

        int count = 0;
        boolean found;
        WebElement we;
        WebElement we2;

        while (true) {
            try {
                we = wait.until(ExpectedConditions.elementToBeClickable(locator));
                we2 = wait.until(ExpectedConditions.elementToBeClickable(locator));
                found = true;
                break;
            } catch (InvalidElementStateException e) {
                Thread.sleep(1000);
                if (++count == maxTries) throw e;
            } catch (StaleElementReferenceException e) {
                Thread.sleep(1000);
                if (++count == maxTries) throw e;
            } catch (NoSuchElementException e) {
                Thread.sleep(1000);
                if (++count == maxTries) throw e;
            } catch (WebDriverException e) {
                Thread.sleep(1000);
                if (++count == maxTries) throw e;
            }
        }

        if (found) {

        } else {
            System.out.println("Failed to find element");
        }
        return we;
    }

    public void getLocatorStrategy(String strategy, String locator) throws InterruptedException {

        if (strategy.contains("css"))
            getElementByLocator(By.cssSelector(locator));
        if (strategy.contains("xpath"))
            getElementByLocator(By.xpath(locator));
        if (strategy.contains("id"))
            getElementByLocator(By.id(locator));
        if (strategy.contains("link text"))
            getElementByLocator(By.linkText(locator));
        if (strategy.contains("name"))
            getElementByLocator(By.name(locator));
    }

}
