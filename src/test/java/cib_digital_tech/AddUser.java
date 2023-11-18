package cib_digital_tech;

import com.cib.qa.base.TestBase;
import com.cib.qa.pages.WebTablesPage;
import com.cib.qa.utilities.TestUtil;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class AddUser extends TestBase {

    private WebDriver driver;
    private WebTablesPage webTablesPage;
    private TestUtil testUtil;

    @BeforeMethod
    @Parameters({"environment"})
    public void setUp(@Optional String env) throws Exception {
        initialization(env);
    }

    @Test
    public void AddUserDetails() throws Exception {

        driver = getWebDriver();
        testUtil = new TestUtil();
        webTablesPage = new WebTablesPage(driver);

        webTablesPage
                .NavigateToWebTables()
                .ClickAddUserButton()
                .AddUserDetails();
    }

    @AfterMethod
    public void closeDown(ITestResult result) throws Exception {
        if (result.getStatus() == ITestResult.FAILURE) {
            testUtil.TakeScreenShotEndOfTest(result.getName().trim(), driver);
        } else if (result.getStatus() == ITestResult.SUCCESS) {
        }
        driver.quit();
    }
}
