package com.gil.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

abstract class AbsPageObject {

    WebDriver driver;
    long WAIT_FOR_PAGE_TIMEOUT = 5000;

    public AbsPageObject(WebDriver driver_) {
        driver = driver_;
        PageFactory.initElements(driver, this);
    }
}
