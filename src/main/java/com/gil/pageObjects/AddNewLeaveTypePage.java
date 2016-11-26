package com.gil.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class AddNewLeaveTypePage extends AbsPageObject {

    @FindBy(xpath = "//*[@id='leaveType_operational_country']")
    WebElement countryDropDownInput;

    @FindBy(xpath = "//*[@id='leaveType_txtLeaveTypeName']")
    WebElement nameTextInput;

    @FindBy(xpath = "//*[@id='saveButton']")
    WebElement saveButton;

    AddNewLeaveTypePage(WebDriver driver_) {
        super(driver_);
    }

    public void addNewLeaveType(String countryName, String name) {
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.visibilityOf(countryDropDownInput));
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        countryDropDownInput.sendKeys(countryName);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        nameTextInput.sendKeys(name);
        saveButton.click();
    }
}
