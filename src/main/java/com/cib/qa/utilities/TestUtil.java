package com.cib.qa.utilities;

import com.cib.qa.base.TestBase;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TestUtil extends TestBase {

    public void TakeScreenShotEndOfTest(String testName, WebDriver driver) throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String currentDir = System.getProperty("user.dir");
        SimpleDateFormat sdf = new SimpleDateFormat("HH_mma");
        Date resultdate = new Date(System.currentTimeMillis());
        FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + testName + "_" + sdf.format(resultdate) + ".png"));
    }

/*    public void InsertDataIntoExcel() throws FilloException {
        Fillo fillo=new Fillo();
        Connection connection=fillo.getConnection("C:Test.xlsx");
        String strQuery="INSERT INTO sheet4(Name,Country) VALUES('Peter','UK')";

        connection.executeUpdate(strQuery);

        connection.close();
    }*/

}
