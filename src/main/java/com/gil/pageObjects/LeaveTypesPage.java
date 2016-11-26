package com.gil.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.logging.Logger;

public class LeaveTypesPage extends AbsPageObject {

    //class logger
    final private static Logger log = Logger.getLogger(LeaveTypesPage.class.getSimpleName());

    final public static String LOGIN_PAGE_URL = "https://enterprise-demo.orangehrmlive.com/auth/login";


    //Relevant Page Elements
    @FindBy(xpath="//*[@id='search-results']")
    public WebElement leaveTypeTable;

    @FindBy(xpath="//*[@id='btnAdd']")
    public WebElement addButton;

    @FindBy(xpath="//*[@id='btnDelete']")
    public WebElement deleteButton;

    @FindBy(xpath="//*[@id='dialogDeleteBtn']")
    public WebElement okButtonConfirmationDialog;

    @FindBy(xpath="//*[@id='menu_admin_viewAdminModule']")
    public WebElement AdminTabTitle;


    public LeaveTypesPage(WebDriver driver_) {
        super(driver_);
    }

    /**
     * Click on "Add" button, which leads to Add New Leave Type Page
     * @return new AddNewLeaveTypePage object (Page Object Model methodology)
     */
    public AddNewLeaveTypePage clickOnAddButton() {
        addButton.click();
        return new AddNewLeaveTypePage(driver);
    }

    /**
     * @param leaveTypeText text describing a leave type
     * @return The required checkbox WebElement
     */
    public WebElement getCheckboxByLeaveTypeText(String leaveTypeText) {
        String xpath = String.format("//a[contains(text(), '%s')]/../../*/*[@type='checkbox']", leaveTypeText);
        return driver.findElement(By.xpath(xpath));
    }

}
