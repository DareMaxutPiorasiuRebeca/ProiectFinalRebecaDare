import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class CautareDupaNumeNegativTest {
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
    public void cautareDupaNume(){
        //eliminam cookies
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("/html//span[@id='agreeWithCookies']")));
        WebElement allowCookies = driver.findElement(By.xpath("/html//span[@id='agreeWithCookies']"));
        allowCookies.click();
        //cautare
        WebElement cautare = driver.findElement(By.xpath("/html//input[@id='search-header-input']"));
        cautare.sendKeys("ggbantes");
        WebElement butonCautare = driver.findElement(By.xpath("//form[@id='search-box']//input[@title='Căutare']"));
        butonCautare.click();
        //check message
        WebElement raspunsCautareNume = driver.findElement(By.xpath("/html//section[@id='content']//p[@class='result-info']"));
        String expectedRaspunsCautareNume = "Din păcate nu a fost găsit nici un rezultat pentru întrebarea căutată.";
        String actualRaspunsCautareNume = raspunsCautareNume.getText();
        Assert.assertTrue(actualRaspunsCautareNume.contains(expectedRaspunsCautareNume));
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
