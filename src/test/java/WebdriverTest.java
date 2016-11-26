import com.gil.pageObjects.AddNewLeaveTypePage;
import com.gil.pageObjects.LeaveTypesPage;
import com.gil.pageObjects.LoginPage;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


@FixMethodOrder(MethodSorters.JVM)
public class WebdriverTest {

    //class logger
    final private static Logger log = Logger.getLogger(WebdriverTest.class.getSimpleName());
    private static WebDriver driver;
    private LeaveTypesPage leaveTypesPage = new LeaveTypesPage(driver);

    private static String CHROME_EXECUTABLE_PATH = "/Users/gil/Development/JobHunt/chromedriver_mac64/chromedriver";

    @BeforeClass
    public static void setUp() {
        log.info("setUp()");
        System.setProperty("webdriver.chrome.driver" , CHROME_EXECUTABLE_PATH);
    }

    @Test
    /**
     * Step 1 - 2
     */
    public void loginAsSystemAdministratorTest()
    {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(LoginPage.LOGIN_PAGE_URL);
        log.info("driver navigated to url: " + LoginPage.LOGIN_PAGE_URL);
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue("Login page is not present!", loginPage.logInButton.isDisplayed());
        loginPage.loginAsSystemAdministrator();
    }

    @Test
    /**
     * Step 3
     */
    public void leavesTypeTest()
    {
        LeaveTypesPage leaveTypesPage = new LeaveTypesPage(driver);
        leaveTypesPage.getCheckboxByLeaveTypeText("Community leave AU").click();
        log.info(String.format("Clicked on \"%s\" checkbox", "Community leave AU"));
        try { Thread.sleep(1500); } catch (InterruptedException e) {}
        leaveTypesPage.getCheckboxByLeaveTypeText("Vacation US").click();
        log.info(String.format("Clicked on \"%s\" checkbox", "Vacation US"));
        leaveTypesPage.deleteButton.click();
        log.info(String.format("Clicked on delete button"));
        try { Thread.sleep(1500); } catch (InterruptedException e) {}
        leaveTypesPage.okButtonConfirmationDialog.click();
        log.info(String.format("Clicked on ok button"));
    }

    @Test
    /**
     * Step 4 - 5
     */
    public void addNewLeaveTypeTest()
    {
        //Click on "add" button, this leads to a Add New Leave Type Page, get new AddNewLeaveTypePage object
        AddNewLeaveTypePage addNewLeaveTypePage = leaveTypesPage.clickOnAddButton();
        log.info("Add button clicked.");

        //Add the required input
        String countryName = "Italy";
        log.info(String.format("Adding new Leave Type Item: %s %s", countryName, "Gil"));
        addNewLeaveTypePage.addNewLeaveType(countryName, "Gil");
        log.info("Item saved.");

        // wait for the new item to appear
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(String.format("//*[@id='search-results']//*[contains(text(), '%s')]", countryName))));
        log.info(String.format("Added new Leave Type Item: %s", countryName));
    }


    @Test
    /**
     * Step 6 - 9
     */
    public void goToAuthentication()
    {
        //click on admin tab
        leaveTypesPage.AdminTabTitle.click();

        //click on configuration
        driver.findElement(By.xpath("//*[@id='menu_admin_Configuration']")).click();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

        //click on Authentication
        driver.findElement(By.xpath("//*[contains(text(), 'Authentication')]")).click();

        //Enable Default settings status
        driver.findElement(By.xpath("//*[@value='Enable']")).click();

        //Captcha after 1 failed attempts
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        WebElement enableCaptchaCheckbox =
                driver.findElement(By.xpath("//*[@id='securityAuthentication_enableCaptchaOption']"));
        if (!enableCaptchaCheckbox.isSelected()) {
            enableCaptchaCheckbox.click();  //Click the checkbox only if unchecked
        }
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        driver.findElement(By.xpath("//*[@id='securityAuthentication_attemptsToEnableCaptcha']")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        driver.findElement(By.xpath("//*[@value='1']")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        //save configuration
        driver.findElement(By.xpath("//*[@id='btnSave']")).click();
    }


    @Test
    /**
     * Step 10 - 13
     */
    public void logoutAndTestCaptcha()
    {
        logout();
        // login with bad credentials
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs("x", "x");
        log.info("Entered bad credentials.");

        // assert if captcha appeared...
        // wait for captcha, if captcha won't appear an NoSuchElement exception will be thrown and test will fail
        By captchaImage = By.xpath("//*[@id='binaraCAPTCHA']");
        new WebDriverWait(driver, 3).until(ExpectedConditions.presenceOfElementLocated(captchaImage));
        log.info("Captcha appeared after 1 try of bad credentials, as expected.");

        // Close the selenium driver
        driver.quit();
        log.info("Closed selenium driver.");
    }


    @Test
    /**
     * Step 13
     */
    public void loginAsE​ssUserTest()
    {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(LoginPage.LOGIN_PAGE_URL);
        log.info("driver navigated to url: " + LoginPage.LOGIN_PAGE_URL);
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue("Login page is not present!", loginPage.logInButton.isDisplayed());
        loginPage.loginAsEssUser();
    }


    @Test
    /**
     * Step 14 - 17
     */
    public void addPhotoAttachmentTest()
    {
        //click on My info
        driver.findElement(By.xpath("//*[@id='menu_pim_viewMyDetails']")).click();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

        //Identify the WebElement which will appear after scrolling down
        WebElement addAttachmentButton = driver.findElement(By.xpath("//*[@id='btnAddAttachment']"));
        scrollToElement(addAttachmentButton);
        //click on add
        addAttachmentButton.click();

        //choose photo
        String myPhotoFileName = "checkbox_img.png";
        URL resource = this.getClass().getResource(myPhotoFileName);
        try {
            String filePath = new File(resource.toURI()).getAbsolutePath();
            log.info(String.format("File path to upload '%s'", filePath));
            driver.findElement(By.xpath("//*[@id='ufile']")).sendKeys(filePath);
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        }
        catch (URISyntaxException e) {
            log.severe("Failed to find image resource to upload");
            throw new RuntimeException("Failed to find image resource to upload");
        }

        //write comment
        String requiredComment = "this is my file";
        WebElement commentInputText = driver.findElement(By.xpath("//*[@id='txtAttDesc']"));
        commentInputText.clear();
        commentInputText.sendKeys(requiredComment);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        String currentComment = commentInputText.getAttribute("value").trim();
        Assert.assertTrue(
                String.format("Comment should be \"%s\", instead text is \"%s\"", requiredComment, currentComment),
                currentComment.equals(requiredComment));

        //scroll to save button and click it
        WebElement saveAttachmentButton = driver.findElement(By.xpath("//*[@id='btnSaveAttachment']"));
        // execute - scroll until that saveAttachmentButton appeared
        scrollToElement(saveAttachmentButton);
        //click on save
        saveAttachmentButton.click();

        //wait for the uploaded file to appear
        By myAttachmentBy = By.xpath(
                String.format("//*[@id='tblAttachments']//*[contains(text(), '%s')]", myPhotoFileName));
        WebElement myAttachment = driver.findElement(myAttachmentBy);
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(myAttachmentBy));
        log.info("The attachment uploaded appears in the table, as expected.");
    }

    /**
     * logs out
     */
    private static void logout() {
        // open user menu
        WebElement userMenu = driver.findElement(By.xpath("//*[@id='welcome']"));
        userMenu.click();
        log.info("User menu clicked.");
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        // Logout
        try {
            Robot robot = new Robot();  //use robot to press TAB to make the "logout" button valid
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyPress(KeyEvent.VK_DOWN);
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            driver.findElement(By.xpath("//a[@href='/auth/logout']")).click();
            log.info("Logout button clicked.");
        } catch (AWTException ex) {
            throw new AssertionError("Could not click on logout option in user menu");
        }
    }

    /**
     * scrolls the element given into view
     * @param element the element to scroll into view
     */
    private void scrollToElement(WebElement element)
    {
        // Create instance of Javascript executor
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        // execute - scroll until that addAttachmentButton appeared
        jse.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    @AfterClass
    /**
     * Step 18
     */
    public static void tearDown() {
        driver.quit();
        log.info("Driver was terminated.");
        log.info("tearDown() end.");
    }
}
