package com.way2automation.qa.utilities;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.github.javafaker.Faker;
import com.way2automation.qa.base.TestBase;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;


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

    //This method is used to get cell values using Apache POI Api library
    public String SelectDataFromExcel(String fieldName) throws IOException {
        String cellValue = null;
        FileInputStream file = new FileInputStream(System.getProperty("user.dir") + path);
        XSSFWorkbook wb = new XSSFWorkbook(file);
        XSSFSheet ws = wb.getSheet("UserDetails");
        Row firstRow = ws.getRow(0);
        Iterator<Cell> cellIterator = firstRow.cellIterator();

        while (cellIterator.hasNext()) {
            int firstCellNum = firstRow.getFirstCellNum();
            int lastCellNum = firstRow.getLastCellNum();
            for (int i = firstCellNum; i < lastCellNum; i++) {
                Cell cell = firstRow.getCell(i);
                switch (cell.getCellType()) {
                    case STRING:
                        if (fieldName != null && fieldName.equalsIgnoreCase(cell.getStringCellValue())) {
                            cellValue = ws.getRow(1).getCell(i).getStringCellValue();
                            break;
                        }
                        break;
                }
            }
            break;
        }
        file.close();
        return cellValue;
    }

    //This method is used to insert cell values using Fillo (Excel API for Java)
    public void UpdateDataInExcel(String columnName, String field) throws FilloException {
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
            String value = testData.get(key);
            UpdateDataInExcel(key, value);
        }

    }

}
