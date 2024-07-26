package com.e4home;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class LoginEmailGresitTest {
    WebDriver driver;
    @Parameters({"browserParam"})
    @BeforeTest(alwaysRun = true)
    public void setUp(@Optional("chrome") String browser){
        String url = "https://www.e4home.ro/";
        switch (browser){
            case "chrome":driver=new ChromeDriver();break;
            case "edge":driver=new EdgeDriver();break;
            case "firefox":driver=new FirefoxDriver();break;
            default:driver=new ChromeDriver();break;
        }
        driver.get(url);
        driver.manage().window().maximize();}

    @Test
    public void loginEmailGresit (){
        //eliminam cookies
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("/html//span[@id='agreeWithCookies']")));
        WebElement allowCookies = driver.findElement(By.xpath("/html//span[@id='agreeWithCookies']"));
        allowCookies.click();
        WebElement autentificare = driver.findElement(By.xpath("/html/body[@class='RO']/div[@class='container']//div[@class='info-block']/div[1]/a[@href='/autentificare/']"));
        autentificare.click();
        //introdu username sau email gresit
        WebElement usernameInput = driver.findElement(By.xpath("/html//input[@id='UserName']"));
        usernameInput.sendKeys(Keys.BACK_SPACE);
        usernameInput.sendKeys("TomSmith@gmail.com");
        //introdu parola corecta
        WebElement passwordInput = driver.findElement(By.xpath("/html//input[@id='Password']"));
        passwordInput.sendKeys("ParolaSuperSecreta.");
        //click Login
        WebElement authButton = driver.findElement(By.xpath("/html//div[@id='login-box']/form[@action='/autentificare/']//input[@name='btnLoginSubmit']"));
        authButton.click();
        //verifica mesaj de eroare
        WebElement errorMessage = driver.findElement(By.xpath("/html//div[@id='formMessagePanel']"));
        String expectedErrorMessage = "Utilizator inexistent sau parolă incorectă";
        String actualErrorMessage = errorMessage.getText();
        Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage));}
    @AfterTest(alwaysRun = true)
    public void tearDown(){
        driver.close();
    }
    public void wait(int milliseconds) {
        try {
            driver.wait(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }}
}
