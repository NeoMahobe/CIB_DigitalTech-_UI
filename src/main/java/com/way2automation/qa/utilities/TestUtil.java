package com.way2automation.qa.utilities;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.github.javafaker.Faker;
import com.way2automation.qa.base.TestBase;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
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
import java.util.Set;


public class TestUtil extends TestBase {

    private String path = "/src/main/resources/TestData/TestData.xlsx";

    public void TakeScreenShotEndOfTest(String testName, WebDriver driver) throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String currentDir = System.getProperty("user.dir");
        SimpleDateFormat sdf = new SimpleDateFormat("HH_mma");
        Date resultdate = new Date(System.currentTimeMillis());
        FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + testName + "TestClass" + "_" + sdf.format(resultdate) + ".png"));
    }

    //This method is used to get cell values using Apache POI Api library
    public String SelectDataFromExcel(String fieldName, int rownum) throws IOException {
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
                            Cell cellValueType = ws.getRow(rownum).getCell(i);
                            DataFormatter formatter = new DataFormatter();
                            cellValue = formatter.formatCellValue(cellValueType);
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
    public void UpdateDataInExcel(String columnName, String field, String value, String idColumn) throws FilloException {
        Fillo fillo = new Fillo();
        Connection connection = fillo.getConnection(System.getProperty("user.dir") + "/src/main/resources/TestData/TestData.xlsx");
        String strQuery = "Update UserDetails Set " + columnName + "='" + field + "' where " + idColumn + "='" + value + "'";
        connection.executeUpdate(strQuery);
        connection.close();
    }

}
