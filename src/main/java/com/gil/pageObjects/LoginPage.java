package com.gil.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginPage extends AbsPageObject {

    //class logger
    final private static Logger log = Logger.getLogger(LoginPage.class.getSimpleName());

    final public static String LOGIN_PAGE_URL = "https://enterprise-demo.orangehrmlive.com/auth/login";


    //Relevant Page Elements
    @FindBy(xpath="//*[@id='txtUsername']")
    public WebElement userNameTextInput;

    @FindBy(xpath="//*[@id='txtPassword']")
    public WebElement passwordTextInput;

    @FindBy(xpath="//*[@id='btnLogin']")
    public WebElement logInButton;

    @FindBy(xpath="//*[contains(text(), 'System Administrator')]")
    public WebElement systemAdminDetails;


    public LoginPage(WebDriver driver_) {
        super(driver_);
    }

    /**
     * Logs in as with SystemAdministrator credentials
     */
    public void loginAsSystemAdministrator()
    {
        loginAs(getSystemAdminUsername(), getSystemAdminPassword());
    }

    /**
     * Logs in as with ESS user credentials
     */
    public void loginAsEssUser()
    {
        //using constants, if it was a real test i would dynamically get it like implemented in loginAsSystemAdministrator()
        loginAs("linda.anderson", "linda.anderson");
    }

    /**
     * Tries to login with params given
     * @param userName user name
     * @param password password
     */
    public void loginAs(String userName, String password) {
        log.info(String.format("Logging in as user: '%s' Password: '%s'", userName, password));
        enterUsername(userName);
        enterPassword(password);
        logInButton.click();
        log.info(String.format("Finished Logging in successfully as user: '%s' Password: '%s'", userName, password));
    }

    /**
     * Enters the name given in username
     * @param name name to type as username
     */
    public void enterUsername(String name)
    {
        userNameTextInput.sendKeys(name);
        log.info(String.format("Sent keys to username input '%s'", name));
    }

    /**
     * Enters the name given in Password
     * @param pass name to type as Password
     */
    public void enterPassword(String pass)
    {
        passwordTextInput.sendKeys(pass);
        log.info(String.format("Sent keys to Password input '%s'", pass));
    }

    /**
     * @return system administrator username login detail
     */
    public String getSystemAdminUsername()
    {
        String prefix = "Username : ";
        Pattern p = Pattern.compile(prefix + "\\w+");
        Matcher m = p.matcher(systemAdminDetails.getText());
        log.info(String.format("Sys admin details: '%1$s'", systemAdminDetails.getText()));
        assert m.find() : "Could not find System Admin details element!";
        String result = m.group(0).replace(prefix, "").trim();
        log.info(String.format("Sys admin username found: '%1$s'", result));
        return result;
    }

    /**
     * @return system administrator password login detail
     */
    public String getSystemAdminPassword()
    {
        String prefix = "Password : ";
        Pattern p = Pattern.compile(prefix + "\\w+");
        Matcher m = p.matcher(systemAdminDetails.getText());
        assert m.find() : "Could not find System Admin details element!";
        String result = m.group(0).replace(prefix, "").trim();
        log.info(String.format("Sys admin password found: '%1$s'", result));
        return result;
    }


}
