package way2automation;

import com.way2automation.qa.base.TestBase;
import com.way2automation.qa.pages.WebTablesPage;
import com.way2automation.qa.utilities.TestUtil;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class AddUser extends TestBase {

    private WebDriver driver;
    private WebTablesPage webTablesPage;
    private TestUtil testUtil = null;

    @BeforeMethod
    @Parameters({"environment"})
    public void setUp(@Optional String env) throws Exception {
        Initialization(env);
    }

    @Test
    public void AddMultipleUsers() throws Exception {

        driver = GetWebDriver();
        testUtil = new TestUtil();
        webTablesPage = new WebTablesPage(driver);

        webTablesPage
                .NavigateToWebTables()
                .ClickAddUserButton()
                .CaptureMultipleUserDetails(2)
                .ClickCloseButton()
                .VerifyEntriesInTable(2,"UserName");
    }

    /*This test was created to verify Customer value is displayed after user details are captured - the issue was picked up during testing.
    * The test will fail as the bug still persists
    *  */
    @Test
    public void CheckCustomerValueIsCaptured() throws Exception {

        driver = GetWebDriver();
        testUtil = new TestUtil();
        webTablesPage = new WebTablesPage(driver);

        webTablesPage
                .NavigateToWebTables()
                .ClickAddUserButton()
                .CaptureUserDetails(1)
                .ClickSaveButton()
                .VerifyEntriesInTable(1,"Customer");
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
