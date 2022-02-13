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
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

class TestDodajProizvodeUKorpuIProveraCene {

	private WebDriver driver;
	private Map<String, Object> vars;
	private JavascriptExecutor js;
	private Logger log = Logger.getLogger(TestDodajProizvodeUKorpuIProveraCene.class.getName());
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
	@DisplayName(value = "Test dodavanje proizvoda u korpu i provera ukupne cene")
	void dodajProizvodeUKorpu() throws InterruptedException {
		driver.get("https://www.ekupi.rs/rs/");
		driver.manage().window().maximize();

		/*
		 * ======================Test prozvoda 1========================
		 **/
		driver.findElement(By.linkText("Računari")).click();
		Thread.sleep(2000);
		driver.findElement(By.linkText("Laptopovi i oprema")).click();
		Thread.sleep(2000);
		js.executeScript("window.scrollBy(0,600)");
		Thread.sleep(2000);

		driver.findElement(By.cssSelector("#comp_00171008 .owl-item:nth-child(1) .carousel__item--thumb > img"))
				.click();
		String cenaProizvoda1 = driver.findElement(By.xpath("//dd[contains(text(),'86.990,00 din')]")).getText();
		Thread.sleep(2000);
		String nazivProizvoda1 = driver
				.findElement(By.xpath("//body/main[1]/div[5]/div[2]/div[3]/div[1]/div[1]/div[1]/div[1]/h1[1]"))
				.getText();

		Thread.sleep(2000);

		driver.findElement(By.xpath("//button[@id='addToCartButton']")).click();
		Thread.sleep(2000);

		/*
		 * ======================Test prozvoda 2========================
		 **/
		driver.findElement(By.linkText("Elektronika")).click();
		Thread.sleep(2000);
		driver.findElement(By.linkText("Audio")).click();
		Thread.sleep(2000);
		driver.findElement(
				By.xpath("//body/main[1]/div[5]/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div[1]/a[1]/img[1]")).click();
		Thread.sleep(2000);
		String nazivProizvoda2 = driver.findElement(By.cssSelector(".deskview > .name")).getText();
		Thread.sleep(2000);
		String cenaProizvoda2 = driver
				.findElement(By.xpath(
						"/html[1]/body[1]/main[1]/div[5]/div[2]/div[3]/div[1]/div[1]/div[1]/div[2]/div[2]/dl[1]/dd[1]"))
				.getText();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[@id='addToCartButton']")).click();
		Thread.sleep(2000);

		/*
		 * ======================Test prozvoda 3========================
		 **/
		driver.findElement(By.linkText("Auto i moto oprema")).click();
		Thread.sleep(2000);
		driver.findElement(By.linkText("Auto delovi i pribor")).click();
		Thread.sleep(2000);
		String nazivProizvoda3 = driver
				.findElement(By.xpath(
						"/html[1]/body[1]/main[1]/div[5]/div[1]/div[2]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/a[1]"))
				.getText();
		Thread.sleep(2000);
		driver.findElement(By
				.xpath("/html[1]/body[1]/main[1]/div[5]/div[1]/div[2]/div[1]/div[1]/div[2]/div[2]/div[1]/a[1]/img[1]"))
				.click();
		Thread.sleep(2000);

		String cenaProizvoda3 = driver
				.findElement(By.xpath(
						"/html[1]/body[1]/main[1]/div[5]/div[2]/div[3]/div[1]/div[1]/div[1]/div[2]/div[2]/dl[1]/dd[1]"))
				.getText();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[@id='addToCartButton']")).click();
		Thread.sleep(2000);
		System.out.println(nazivProizvoda1);
		System.out.println(nazivProizvoda2);
		System.out.println(nazivProizvoda3);
		System.out.println(cenaProizvoda1);
		System.out.println(cenaProizvoda2);
		System.out.println(cenaProizvoda3);

		assertEquals(
				"HP ProBook 445 G7 175V5EA 14 FullHD IPS,Six-Core AMD Ryzen 5 4500U 2.3GHz,8GB RAM,512 GB PCIe NVMe SSD,AMD Radeon Graphic,Windows 10 Pro, laptopID: EK000412382",
				nazivProizvoda1);
		assertEquals("86.990,00 din", cenaProizvoda1);

		assertEquals("Vivax BS-800, karaoke zvučnikID: EK000397660", nazivProizvoda2);
		assertEquals("17.990,00 din", cenaProizvoda2);

		assertEquals("Energizer Premium 63 Ah Desno", nazivProizvoda3);
		assertEquals("10.090,00 din", cenaProizvoda3);

		/*
		 * ======================Test provera ukupneCene u korpi========================
		 **/
		Thread.sleep(2000);
		driver.findElement(By.xpath(
				"/html[1]/body[1]/main[1]/header[1]/nav[2]/div[1]/div[2]/div[2]/ul[1]/li[1]/div[1]/div[1]/div[1]/a[1]/div[2]"))
				.click();
		Thread.sleep(2000);
		js.executeScript("window.scrollBy(0,400)");
		Thread.sleep(2000);
		String ukupnaCena = driver.findElement(By.cssSelector(".cart-totals-right")).getText();
		System.out.println(ukupnaCena);
		Thread.sleep(2000);

		/* Praznjenje korpe */

		driver.findElement(By.id("clearCartButton")).click();
		Thread.sleep(2000);
		driver.findElement(By.linkText("ISPRAZNI")).click();
		System.out.println(ukupnaCena);

		/* Provera ukupne cene u kori */

		assertEquals("115.070,00 din", ukupnaCena);

		testName = info.getDisplayName();
		success = true;

	}

	@AfterEach
	public void Izvestaj() {

		StringBuilder sb = new StringBuilder(
				"\n===============================================================" + "\n");
		sb.append("Naziv testa >> " + testName + "\n");
		sb.append("Opis testa:\n");
		sb.append("Test vrsi dodavanje proizvoda u korpu, proverava da li je to taj poizvod koji "
				+ "smo dodali u korpu i vrsi proveru ukupne cene proizvoda \n");
		sb.append("Test je prosao >> " + (success ? "Uspesno" : "Neuspesno"));

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
