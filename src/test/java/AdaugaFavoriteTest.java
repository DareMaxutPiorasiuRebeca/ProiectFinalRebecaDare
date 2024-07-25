import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class AdaugaFavoriteTest {
    WebDriver driver;
    @Parameters({"browserParam"})
    @BeforeTest(alwaysRun = true)
    public void setUp(@Optional("chrome") String browser){
        String url = "https://www.e4home.ro/";
        switch (browser){
            case "chrome":driver=new ChromeDriver();break;
            case "edge":driver=new EdgeDriver();break;
            default:driver=new ChromeDriver();break;
        }
        driver.get(url);
        driver.manage().window().maximize();}

    @Test
    public void adaudaFavorite () {
        //eliminam cookies
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("/html//span[@id='agreeWithCookies']")));
        WebElement allowCookies = driver.findElement(By.xpath("/html//span[@id='agreeWithCookies']"));
        allowCookies.click();
        WebElement autentificare = driver.findElement(By.xpath("/html/body[@class='RO']/div[@class='container']//div[@class='info-block']/div[1]/a[@href='/autentificare/']"));
        autentificare.click();
        //enter username sau email
        WebElement usernameInput = driver.findElement(By.xpath("/html//input[@id='UserName']"));
        usernameInput.sendKeys(Keys.BACK_SPACE);
        usernameInput.sendKeys("TomSmithTest2024@gmail.com");
        //enter password
        WebElement passwordInput = driver.findElement(By.xpath("/html//input[@id='Password']"));
        passwordInput.sendKeys("ParolaSuperSecreta.");
        //click Login
        WebElement authButton = driver.findElement(By.xpath("/html//div[@id='login-box']/form[@action='/autentificare/']//input[@name='btnLoginSubmit']"));
        authButton.click();
        //cauta piscina
        WebElement cautare = driver.findElement(By.xpath("/html//input[@id='search-header-input']"));
        cautare.sendKeys("piscina");
        WebElement butonCautare = driver.findElement(By.xpath("//form[@id='search-box']//input[@title='Căutare']"));
        butonCautare.click();
        //selectez piscina de 244 cm
        WebElement selectare = driver.findElement(By.xpath("//div[@id='results']//a[@title='Piscină gonflabilă Bestway cu tobogan Sunnyland, 237 x 201 x 104 cm']//div[@class='product-thumbnail-photo-wrap']"));
        selectare.click();
        //ajung pe pagina produs si adaug la favorite
        WebElement favoriteButon = driver.findElement(By.xpath("//section[@id='content']//a[@title='La favorite']/span[.='La favorite']"));
        favoriteButon.click();
        // mergem in favorite
        WebElement favoriteSectiune = driver.findElement(By.xpath("/html//a[@id='ajax-favorites']"));
        favoriteSectiune.click();
        // verificam ca produsul selectat a ajuns in favorite
        WebElement raspunsCautareFavorite = driver.findElement(By.xpath("/html//section[@id='content']/div[2]/table[@class='cart-product-list']//h2/a[@href='/piscina-gonflabila-bestway-cu-tobogan-sunnyland-237-x-201-x-104-cm/']"));
        String expectedRaspunsCautareFavorite = "Piscină gonflabilă Bestway cu tobogan Sunnyland, 237 x 201 x 104 cm";
        String actualRaspunsCautareFavorite = raspunsCautareFavorite.getText();
        Assert.assertTrue(actualRaspunsCautareFavorite.contains(expectedRaspunsCautareFavorite));
    }
    @AfterTest(alwaysRun = true)
    public void tearDown(){
        driver.close();
    }
    public void wait(int milliseconds) {
        try {
            driver.wait(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
