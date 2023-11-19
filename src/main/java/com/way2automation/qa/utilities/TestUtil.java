package com.way2automation.qa.utilities;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.github.javafaker.Faker;
import com.github.javafaker.Options;
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
import java.util.Random;


public class TestUtil extends TestBase {

    public void TakeScreenShotEndOfTest(String testName, WebDriver driver) throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String currentDir = System.getProperty("user.dir");
        SimpleDateFormat sdf = new SimpleDateFormat("HH_mma");
        Date resultdate = new Date(System.currentTimeMillis());
        FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + testName + "_" + sdf.format(resultdate) + ".png"));
    }

    public void InsertDataIntoExcel() throws FilloException {
        Fillo fillo = new Fillo();
        Connection connection = fillo.getConnection(System.getProperty("user.dir") + "/src/main/resources/TestData/TestData.xlsx");
        String strQuery = "INSERT INTO UserDetails(Name,Country) VALUES('Neo','USA')";
        connection.executeUpdate(strQuery);
        connection.close();
    }

    public void SelectDataFromExcel() throws FilloException {
        Fillo fillo = new Fillo();
        Connection connection = fillo.getConnection(System.getProperty("user.dir") + "/src/main/resources/TestData/TestData.xlsx");
        String strQuery = "Select * from UserDetails where ID=100 and name='John'";
        Recordset recordset = connection.executeQuery(strQuery);

        while (recordset.next()) {
            System.out.println(recordset.getField("Details"));
        }

        recordset.close();
        connection.close();
    }

    public void UpdateDataInExcel(String columnName, String field) throws FilloException {
        Fillo fillo = new Fillo();
        Connection connection = fillo.getConnection(System.getProperty("user.dir") + "/src/main/resources/TestData/TestData.xlsx");
        String strQuery = "Update UserDetails Set Country='US' where ID=100 and name='John'";
        connection.executeUpdate(strQuery);
        connection.close();
    }

    public void CreateTestData() {
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();

        HashMap<String, String> testData = new HashMap<>();

        testData.put("firstName",firstName);
        testData.put("lastName", lastName);
        testData.put("userName", firstName + lastName + generateRandom5DigitNumber());
        testData.put("password", faker.internet().password());
        testData.put("email", faker.internet().emailAddress());
        testData.put("mobileNumber", faker.phoneNumber().cellPhone());

        for (String key : testData.values()) {
            System.out.println(key);
        }
    }

    public int generateRandom5DigitNumber() {
        Random random = new Random();
        // Generate a random number in the range [10000, 99999]
        int random5DigitNumber = 10000 + random.nextInt(90000);
        return random5DigitNumber;
    }

}
