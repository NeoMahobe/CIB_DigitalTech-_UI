package com.way2automation.qa.pages;


import com.codoid.products.exception.FilloException;
import com.github.javafaker.Faker;
import com.way2automation.qa.base.TestBase;
import com.way2automation.qa.utilities.TestUtil;
import org.hamcrest.core.Is;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class WebTablesPage extends Page {

    //Initialize web objects
    @FindBy(xpath = "//*[text() = ' Add User']")
    WebElement addButton;
    @FindBy(xpath = "//input[@name = 'FirstName']")
    WebElement firstNameInputBox;
    @FindBy(xpath = "//input[@name = 'LastName']")
    WebElement lastNameInputBox;
    @FindBy(xpath = "//input[@name = 'UserName']")
    WebElement userNameInputBox;
    @FindBy(xpath = "//input[@name = 'Password']")
    WebElement passwordInputBox;
    @FindBy(xpath = "//input[@name = 'Email']")
    WebElement emailInputBox;
    @FindBy(xpath = "//input[@name = 'Mobilephone']")
    WebElement mobilePhoneInputBox;
    @FindBy(xpath = "//button[@class = 'btn btn-success'][not(@disabled= 'disabled')]")
    WebElement saveButton;
    @FindBy(xpath = "//button[@class = 'btn btn-danger'][not(@disabled= 'disabled')]")
    WebElement closeButton;

    private String userRole;
    private String value;
    private String customerRadioButton;
    private String randomUserName;
    private String stringValue;
    private String userName;

    //Initializing the Page Object
    public WebTablesPage(WebDriver driver) {
        super(driver);
    }

    public WebTablesPage NavigateToWebTables() {
        this.getDriver().get(TestBase.url);
        String pageTitle = this.getDriver().getTitle();
        //Assert that you are on the correct page
        assertEquals("Protractor practice website - WebTables", pageTitle);
        WebElement tableElement = getDriver().findElement(By.xpath("//table[@table-title='Smart Table example']"));
        //Ensure User List Table is displayed
        IsDisplayed(tableElement);
        return this;
    }

    public WebTablesPage ClickAddUserButton() throws Exception {
        this.Click(addButton);
        return this;
    }

    public WebTablesPage GetUserDetails(int selection) throws IOException, FilloException, InterruptedException {
        stringValue = String.valueOf(selection);
        Faker faker = new Faker();
        TestUtil testUtil = new TestUtil();
        //Create Unique Username everytime
        this.randomUserName = faker.number().digits(10);
        //Store unique username in flat file and use later in the test to assert against
        testUtil.UpdateDataInExcel("UserName", randomUserName, stringValue, "ID");
        //this.value = testUtil.SelectDataFromExcel("UserName", selection);

        CaptureUserDetails(selection);
        GetAndClickElements(selection);
        return this;
    }

    public void GetAndClickElements(int selection) throws IOException {
        TestUtil testUtil = new TestUtil();
        this.userRole = testUtil.SelectDataFromExcel("Role", selection);
        this.customerRadioButton = testUtil.SelectDataFromExcel("Customer", selection);
        WebElement roleId = this.getDriver().findElement(By.xpath("//select[@name = 'RoleId']//option[text() ='" + userRole + "']"));
        WebElement customerOption = this.getDriver().findElement(By.xpath("//label[text() = '" + customerRadioButton + "']"));
        this.ClickRadioButton(customerOption);
        this.Click(roleId);
    }

    //This method enables you to capture multiple users
    public WebTablesPage CaptureMultipleUserDetails(int count) throws Exception {
        for (int i = 1; i <= count; i++) {
            GetUserDetails(i);
            ClickSaveButton();
            ClickAddUserButton();
        }
        return this;
    }

    public WebTablesPage CaptureUserDetails(int selection) throws IOException {
        this.RetrieveAndSendKeys(firstNameInputBox, "FirstName", selection);
        this.RetrieveAndSendKeys(lastNameInputBox, "LastName", selection);
        this.RetrieveAndSendKeys(userNameInputBox, "UserName", selection);
        this.RetrieveAndSendKeys(passwordInputBox, "Password", selection);
        GetAndClickElements(selection);
        this.RetrieveAndSendKeys(emailInputBox, "Email", selection);
        this.RetrieveAndSendKeys(mobilePhoneInputBox, "Cell", selection);
        return this;
    }

    public WebTablesPage ClickSaveButton() throws InterruptedException {
        this.Click(saveButton);
        return this;
    }

    public WebTablesPage ClickCloseButton() throws InterruptedException {
        this.Click(closeButton);
        return this;
    }

    //This method will verify a row that contains *unique userName* and validate any value in said row based on the  'fieldname'
    public void VerifyEntriesInTable(int records, String fieldName) throws IOException {
        TestUtil testUtil = new TestUtil();
        WebElement element = null;
        WebElement elementInclUsername = null;

        for (int i = 1; i <= records; i++) {

            //This method will ALWAYS rely on using the unique UserName value to verify any data on any specific row
            this.userName = testUtil.SelectDataFromExcel("UserName", i);
            try {
                //We need to use unique Username value to check if record exists
                element = getDriver().findElement(By.xpath("//table[@class ='smart-table table table-striped']//td[text()='" + userName + "']/.."));
                if (fieldName != "UserName") {
                    try {
                        //Then we search for the missing value not displayed while also using Username value in the same xpath
                        elementInclUsername = this.getDriver().findElement(By.xpath("//table[@class ='smart-table table table-striped']//td[text()='" + userName + "']/..//td[text()='" + fieldName + "']"));
                    } catch (NoSuchElementException e) {
                        this.IsDisplayed(elementInclUsername);
                    }
                }
            } catch (WebDriverException | NullPointerException e) {
                this.IsDisplayed(element);
            }
            this.IsDisplayed(element);
        }
    }

}
