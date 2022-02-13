package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

class TestPoredjenjeProizvoda {

	private WebDriver driver;
	private Map<String, Object> vars;
	private JavascriptExecutor js;
	private Logger log = Logger.getLogger(TestPoredjenjeProizvoda.class.getName());
	private String testName = "";
	private boolean success = false;
	private TestInfo info;

	@BeforeEach
	public void setUp(TestInfo info) {
		System.setProperty("webdriver.chrome.driver", "..\\E kupi\\driver\\chromedriver.exe");
		driver = new ChromeDriver();
		js = (JavascriptExecutor) driver;
		vars = new HashMap<String, Object>();
		this.info = info;
	}

	@AfterEach
	public void tearDown() {
		driver.quit();
	}

	@Test
	@DisplayName(value = "Test poredjenje proizvoda")
	void poredjenjeProizvodaTest() throws InterruptedException {
		driver.get("https://www.ekupi.rs/");
		driver.manage().window().maximize();
		Thread.sleep(2000);
		driver.findElement(By.linkText("Kućni aparati")).click();
		Thread.sleep(2000);
		driver.findElement(By.linkText("Mali kućni aparati")).click();
		Thread.sleep(2000);

		driver.findElement(By.linkText("Friteze")).click();
		Thread.sleep(2000);
		String nazivProizvoda1 = driver.findElement(By.xpath("//a[contains(text(),'Vivax friteza DF-2003SS')]"))
				.getText();
		Thread.sleep(2000);
		driver.findElement(
				By.xpath("//body/main[1]/div[5]/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div[1]/a[1]/img[1]")).click();
		Thread.sleep(2000);
		/*
		 * String nazivProizvoda1 =
		 * driver.findElement(By.cssSelector(".deskview > .name")).getText();
		 */
		Thread.sleep(2000);
		String cenaProizvoda1 = driver.findElement(By.xpath("//dd[contains(text(),'5.390,00 din')]")).getText();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//body/main[1]/div[5]/div[2]/div[3]/div[1]/div[1]/div[3]/label[1]/input[1]"))
				.click();
		Thread.sleep(2000);
		System.out.println(nazivProizvoda1);
		System.out.println(cenaProizvoda1);
		Thread.sleep(2000);
		driver.navigate().back();
		Thread.sleep(2000);
		String nazivProizvoda2 = driver.findElement(By.xpath("//a[contains(text(),'Vivax friteza DF-1800B')]"))
				.getText();
		Thread.sleep(2000);
		driver.findElement(
				By.xpath("//body/main[1]/div[5]/div[1]/div[2]/div[1]/div[1]/div[2]/div[2]/div[1]/a[1]/img[1]")).click();
		Thread.sleep(20000);
		/*
		 * String nazivProizvoda2 =
		 * driver.findElement(By.cssSelector(".deskview > .name")).getText();
		 */
		Thread.sleep(2000);
		String cenaProzvoda2 = driver.findElement(By.xpath("//dd[contains(text(),'4.490,00 din')]")).getText();

		driver.findElement(By.xpath("//body/main[1]/div[5]/div[2]/div[3]/div[1]/div[1]/div[3]/label[1]/input[1]"))
				.click();
		Thread.sleep(2000);
		driver.findElement(By.cssSelector(".js-cookie-notification-accept")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[@id='product-compare-button']")).click();
		Thread.sleep(3000);
		System.out.println(nazivProizvoda2);
		System.out.println(cenaProzvoda2);

		/*
		 * =========Kupljenje podataka iz starnice za poredjenje================
		 */
		String nazivProizvodaP1 = driver.findElement(By.xpath("//a[contains(text(),'Vivax friteza DF-2003SS')]"))
				.getText();
		String cenaProizvodaP1 = driver.findElement(By.xpath("//div[contains(text(),'5.390,00 din')]")).getText();

		String nazivProizvodaP2 = driver.findElement(By.xpath("//a[contains(text(),'Vivax friteza DF-1800B')]"))
				.getText();
		String cenaProizvodaP2 = driver.findElement(By.xpath("//div[contains(text(),'4.490,00 din')]")).getText();

		assertEquals(cenaProizvoda1, cenaProizvodaP1);
		assertEquals(nazivProizvoda1, nazivProizvodaP1);
		assertEquals(nazivProizvoda2, nazivProizvodaP2);
		assertEquals(cenaProzvoda2, cenaProizvodaP2);
		this.testName = info.getDisplayName();
		success = true;
	}

	@AfterEach
	public void Izvestaj() {

		StringBuilder sb = new StringBuilder(
				"\n===============================================================" + "\n");
		sb.append("Naziv testa >> " + testName + "\n");
		sb.append("Opis testa:\n");
		sb.append("Test proverava da li su  proizvodi koji su dodati u tabelu za poredjenje zaprovo ti proizvodi\n");
		sb.append("Test je prosao  >> " + (success ? "Uspesno" : "Neuspesno"));

		log.info(sb.toString());
		try {

			FileWriter fw = new FileWriter("..\\E kupi\\Izvestaj\\test-report.txt", true);
			fw.write(sb.toString());
			fw.flush();
			fw.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
