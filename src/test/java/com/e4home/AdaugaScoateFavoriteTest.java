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

public class AdaugaScoateFavoriteTest {
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
    public void adaudaScoateFavorite () {
        //eliminam cookies
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("/html//span[@id='agreeWithCookies']")));
        WebElement allowCookies = driver.findElement(By.xpath("/html//span[@id='agreeWithCookies']"));
        allowCookies.click();
        WebElement autentificare = driver.findElement(By.xpath("/html/body[@class='RO']/div[@class='container']//div[@class='info-block']/div[1]/a[@href='/autentificare/']"));
        autentificare.click();
        //introdu username sau email corect
        WebElement usernameInput = driver.findElement(By.xpath("/html//input[@id='UserName']"));
        usernameInput.sendKeys(Keys.BACK_SPACE);
        usernameInput.sendKeys("TomSmithTest2024@gmail.com");
        //introdu parola corecta
        WebElement passwordInput = driver.findElement(By.xpath("/html//input[@id='Password']"));
        passwordInput.sendKeys("ParolaSuperSecreta.");
        //click buton Login
        WebElement authButton = driver.findElement(By.xpath("/html//div[@id='login-box']/form[@action='/autentificare/']//input[@name='btnLoginSubmit']"));
        authButton.click();
        //cauta produs dupa nume piscina
        WebElement cautare = driver.findElement(By.xpath("/html//input[@id='search-header-input']"));
        cautare.sendKeys("piscina");
        WebElement butonCautare = driver.findElement(By.xpath("//form[@id='search-box']//input[@title='Căutare']"));
        butonCautare.click();
        //selectez piscina
        WebElement selectare = driver.findElement(By.xpath("//div[@id='results']//a[@title='Piscină gonflabilă Bestway Mickey, 122 x 25 cm']//div[@class='product-thumbnail-photo-wrap']"));
        selectare.click();
        //ajung pe pagina produs si adaug la favorite
        WebElement favoriteButon = driver.findElement(By.xpath("//section[@id='content']//a[@title='La favorite']/span[.='La favorite']"));
        favoriteButon.click();
        // mergem in favorite
        WebElement favoriteSectiune = driver.findElement(By.xpath("/html//a[@id='ajax-favorites']"));
        favoriteSectiune.click();
        // verificam ca produsul selectat a ajuns in favorite
        WebElement raspunsCautareFavorite = driver.findElement(By.xpath("/html//section[@id='content']/div[2]/table[@class='cart-product-list']//h2/a[@href='/piscina-gonflabila-bestway-mickey-122-x-25-cm/']"));
        String expectedRaspunsCautareFavorite = "Piscină gonflabilă Bestway Mickey, 122 x 25 cm";
        String actualRaspunsCautareFavorite = raspunsCautareFavorite.getText();
        Assert.assertTrue(actualRaspunsCautareFavorite.contains(expectedRaspunsCautareFavorite));
        //stergem produs din favorite
         WebElement revenirePagina = driver.findElement(By.xpath("/html//section[@id='content']/div[2]/table[@class='cart-product-list']//h2/a[@href='/piscina-gonflabila-bestway-mickey-122-x-25-cm/']"));
         revenirePagina.click();
         WebElement scoateDinFavorite = driver.findElement(By.xpath("//section[@id='content']//a[@title='Scoate din favorite']/span[.='Scoate din favorite']"));
         scoateDinFavorite.click();
       //verificam din nou favoritele
        WebElement revenireFav = driver.findElement(By.xpath("//a[@id='ajax-favorites']//span[.='buc']"));
        revenireFav.click();
        WebElement mesajFav = driver.findElement(By.xpath("/html//section[@id='content']//p[.='Nu aveţi nici un produs preferat']"));
        String expectMesaj = "Nu aveţi nici un produs preferat";
        String actualMesaj = mesajFav.getText();
        Assert.assertTrue(actualMesaj.contains(expectMesaj)); }
    @AfterTest(alwaysRun = true)
    public void tearDown(){
        driver.close();}
    public void wait(int milliseconds) {
        try {
            driver.wait(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }}
}
