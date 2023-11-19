package com.way2automation.qa.utilities;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.github.javafaker.Faker;
import com.way2automation.qa.base.TestBase;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class TestUtil extends TestBase {

    private String path = "/src/main/resources/TestData/TestData.xlsx";

    public void TakeScreenShotEndOfTest(String testName, WebDriver driver) throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String currentDir = System.getProperty("user.dir");
        SimpleDateFormat sdf = new SimpleDateFormat("HH_mma");
        Date resultdate = new Date(System.currentTimeMillis());
        FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + testName + "_" + sdf.format(resultdate) + ".png"));
    }

    public void InsertDataIntoExcel() throws FilloException {
        Fillo fillo = new Fillo();
        Connection connection = fillo.getConnection(System.getProperty("user.dir") + path);
        String strQuery = "INSERT INTO UserDetails(Name,Country) VALUES('Neo','USA')";
        connection.executeUpdate(strQuery);
        connection.close();
    }

    public void SelectDataFromExcel() throws FilloException {
        Fillo fillo = new Fillo();
        Connection connection = fillo.getConnection(System.getProperty("user.dir") + path);
        String strQuery = "Select * from UserDetails where ID=100 and name='John'";
        Recordset recordset = connection.executeQuery(strQuery);

        while (recordset.next()) {
            System.out.println(recordset.getField("Details"));
        }

        recordset.close();
        connection.close();
    }

    public static void UpdateDataInExcel(String columnName, String field) throws FilloException {
        Fillo fillo = new Fillo();
        Connection connection = fillo.getConnection(System.getProperty("user.dir") + "/src/main/resources/TestData/TestData.xlsx");
        String strQuery = "Update UserDetails Set " + columnName + "='" + field + "'";
        connection.executeUpdate(strQuery);
        connection.close();
    }

    public void CreateTestData() throws FilloException {
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String randomNumber = faker.number().digits(5);

        HashMap<String, String> testData = new HashMap<>();

        testData.put("firstName", firstName);
        testData.put("lastName", lastName);
        testData.put("userName", firstName + lastName + randomNumber);
        testData.put("password", faker.internet().password());
        testData.put("email", faker.internet().emailAddress());
        testData.put("mobileNumber", faker.phoneNumber().cellPhone());

        for (String key : testData.keySet()) {
            System.out.println(key);
            String value = testData.get(key);
            UpdateDataInExcel(key, value);
        }

    }

}
