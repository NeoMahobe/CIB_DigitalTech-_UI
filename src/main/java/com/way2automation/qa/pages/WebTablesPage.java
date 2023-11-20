package com.way2automation.qa.pages;


import com.codoid.products.exception.FilloException;
import com.github.javafaker.Faker;
import com.way2automation.qa.base.TestBase;
import com.way2automation.qa.utilities.TestUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.IOException;
import java.util.List;

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
    @FindBy(xpath = "//label[text() = 'Company AAA']")
    WebElement customer;
    @FindBy(xpath = "//select[@name = 'RoleId']//option")
    WebElement roleId;
    @FindBy(xpath = "//input[@name = 'Email']")
    WebElement emailInputBox;
    @FindBy(xpath = "//input[@name = 'Mobilephone']")
    WebElement mobilePhoneInputBox;
    @FindBy(xpath = "//button[@class = 'btn btn-success'][not(@disabled= 'disabled')]")
    WebElement saveButton;

    private String userRole;
    private String userName;
    private String customerRadioButton;
    private String randomUserName;

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

    public WebTablesPage AddUserDetails(int selection) throws IOException, FilloException {
        TestUtil testUtil = new TestUtil();
        Faker faker = new Faker();

        this.randomUserName = faker.number().digits(10);
        this.userRole = testUtil.SelectDataFromExcel("Role",selection);
        this.customerRadioButton = testUtil.SelectDataFromExcel("Customer",selection);
        this.userName = testUtil.SelectDataFromExcel("UserName",selection);


        testUtil.UpdateDataInExcel("UserName",randomUserName,userName);
        WebElement roleId = this.getDriver().findElement(By.xpath("//select[@name = 'RoleId']//option[text() ='"+userRole+"']"));
        WebElement customerOption = this.getDriver().findElement(By.xpath("//label[text() = '"+customerRadioButton+"']"));


        this.ExcelSendKeys(firstNameInputBox, "FirstName",selection);
        this.ExcelSendKeys(lastNameInputBox,"LastName",selection);
        this.ExcelSendKeys(userNameInputBox,"UserName",selection);
        this.ExcelSendKeys(passwordInputBox,"Password",selection);
        //Needs to take in a string value for each radio button option
        this.ClickRadioButton(customerOption);
        this.Click(roleId);
        this.ExcelSendKeys(emailInputBox,"Email",selection);
        this.ExcelSendKeys(mobilePhoneInputBox,"Cell",selection);
        return this;
    }

    public WebTablesPage ClickSaveButton() throws InterruptedException {
        this.Click(saveButton);
        return this;
    }

    public void CreateUsername () {

        List listofelements = getDriver().findElements(By.xpath("//table[@class ='smart-table table table-striped']//tbody//tr//td"));
        String value = listofelements.toString();
        System.out.println(value);

    }
}
