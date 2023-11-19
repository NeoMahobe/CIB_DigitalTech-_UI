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
    private TestUtil testUtil;

    @BeforeMethod
    @Parameters({"environment"})
    public void setUp(@Optional String env) throws Exception {
        Initialization(env);
        //testUtil
               // .CreateTestData();
    }

    @Test
    public void AddUserDetails() throws Exception {

        driver = GetWebDriver();
        testUtil = new TestUtil();
        webTablesPage = new WebTablesPage(driver);

        testUtil
                .CreateTestData();

        webTablesPage
                .NavigateToWebTables()
                .ClickAddUserButton()
                .AddUserDetails()
                .ClickSaveButton();
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
