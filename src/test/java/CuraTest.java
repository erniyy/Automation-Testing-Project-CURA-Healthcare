import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class CuraTest {
    WebDriver driver;

    @Test
    public void BookAppointmentTest() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.get("https://katalon-demo-cura.herokuapp.com/");
        WebElement makeAppointment = driver.findElement(By.id("btn-make-appointment"));
        makeAppointment.click();
        driver.findElement(By.id("txt-username")).sendKeys("John Doe");
//        driver.findElement(By.xpath("//input[@id='txt-username']")).sendKeys("John Doe");
        driver.findElement(By.id("txt-password")).sendKeys("ThisIsNotAPassword");
//        driver.findElement(By.xpath("//input[@id='txt-password']")).sendKeys("ThisIsNotAPassword");

        driver.findElement(By.id("btn-login")).click();
        System.out.println("Sudah login.");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement bookButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-book-appointment")));

        Select facilitySelect = new Select(driver.findElement(By.id("combo_facility")));
        facilitySelect.selectByVisibleText("Seoul CURA Healthcare Center");
        WebElement checkbox = driver.findElement(By.id("chk_hospotal_readmission"));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        driver.findElement(By.id("radio_program_medicare")).click();
        WebElement visitDate = driver.findElement(By.id("txt_visit_date"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='20/04/2025';", visitDate);
        bookButton.click();

        WebElement confirmation = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("summary"))
        );
        System.out.println("📋 Ringkasan Konfirmasi:\n" + confirmation.getText());
        System.out.println("✅ Appointment berhasil dilakukan.");

    }
}
