package com.way2automation.qa.pages;


import com.codoid.products.exception.FilloException;
import com.github.javafaker.Faker;
import com.way2automation.qa.base.TestBase;
import com.way2automation.qa.utilities.TestUtil;
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
    private String userName;
    private String customerRadioButton;
    private String randomUserName;
    private String stringValue;

    //Initializing the Page Object
    public WebTablesPage(WebDriver driver) {
        super(driver);
    }

    public WebTablesPage NavigateToWebTables() {
        this.getDriver().get(TestBase.url);
        String pageTitle = this.getDriver().getTitle();
        assertEquals("Protractor practice website - WebTables", pageTitle);
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
        this.randomUserName = faker.number().digits(10);
        this.userRole = testUtil.SelectDataFromExcel("Role", selection);
        this.customerRadioButton = testUtil.SelectDataFromExcel("Customer", selection);
        testUtil.UpdateDataInExcel("UserName", randomUserName, stringValue, "ID");
        this.userName = testUtil.SelectDataFromExcel("UserName", selection);

        WebElement roleId = this.getDriver().findElement(By.xpath("//select[@name = 'RoleId']//option[text() ='" + userRole + "']"));
        WebElement customerOption = this.getDriver().findElement(By.xpath("//label[text() = '" + customerRadioButton + "']"));

        CaptureUserDetails(selection, roleId, customerOption);
        return this;
    }

    public WebTablesPage CaptureMultipleUserDetails(int count) throws Exception {
        for (int i = 1; i <= count; i++) {
            GetUserDetails(i);
            ClickSaveButton();
            ClickAddUserButton();
        }
        return this;
    }

    public void CaptureUserDetails(int selection, WebElement roleId, WebElement customerOption) throws IOException {
        this.ExcelSendKeys(firstNameInputBox, "FirstName", selection);
        this.ExcelSendKeys(lastNameInputBox, "LastName", selection);
        this.ExcelSendKeys(userNameInputBox, "UserName", selection);
        this.ExcelSendKeys(passwordInputBox, "Password", selection);
        this.ClickRadioButton(customerOption);
        this.Click(roleId);
        this.ExcelSendKeys(emailInputBox, "Email", selection);
        this.ExcelSendKeys(mobilePhoneInputBox, "Cell", selection);
    }

    public WebTablesPage ClickSaveButton() throws InterruptedException {
        this.Click(saveButton);
        return this;
    }

    public WebTablesPage ClickCloseButton() throws InterruptedException {
        this.Click(closeButton);
        return this;
    }

    public void VerifyEntriesInTable(int records) throws IOException {
        TestUtil testUtil = new TestUtil();
        WebElement element = null;

        for (int i = 1; i <= records; i++) {
            this.userName = testUtil.SelectDataFromExcel("UserName", i);
            try{
                 element = getDriver().findElement(By.xpath("//table[@class ='smart-table table table-striped']//td[text()='" + userName + "']/.."));
            }catch (WebDriverException  | NullPointerException e){
                this.IsDisplayed(element);
            }
            this.IsDisplayed(element);
        }
    }
}
