package com.way2automation.qa.pages;


import com.github.javafaker.Faker;
import com.way2automation.qa.base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
    @FindBy(xpath = "//select[@name = 'RoleId']")
    WebElement roleId;
    @FindBy(xpath = "//input[@name = 'Email']")
    WebElement emailInputBox;
    @FindBy(xpath = "//input[@name = 'Mobilephone']")
    WebElement mobilePhoneInputBox;
    @FindBy(xpath = "//button[@class = 'btn btn-success'][not(@disabled= 'disabled')]")
    WebElement saveButton;

    private WebDriver driver;

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
        this.ngClick(addButton);
        return this;
    }

    public WebTablesPage AddUserDetails() throws InterruptedException {
        Faker faker = new Faker();
        this.ngSendKeys(firstNameInputBox,faker.name().firstName());
        this.ngSendKeys(lastNameInputBox,faker.name().lastName());
        this.ngSendKeys(userNameInputBox,"Test 3");
        this.ngSendKeys(passwordInputBox,"Test 4");
        this.clickRadio(customer);
        this.ngSelectbyIndex(roleId,2);
        this.ngSendKeys(emailInputBox,faker.internet().emailAddress());
        this.ngSendKeys(mobilePhoneInputBox,"Test 6");
        return this;
    }

    public WebTablesPage ClickSaveButton() throws InterruptedException {
        this.ngClick(saveButton);
        return this;
    }

    public void CreateUsername () {

        List listofelements = getDriver().findElements(By.xpath("//table[@class ='smart-table table table-striped']//tbody//tr//td"));
        String value = listofelements.toString();
        System.out.println(value);

    }
}
