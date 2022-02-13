package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

class TestRegistracijaPrijavaProveraPodatakaNaProfilu {

	private WebDriver driver;
	private Map<String, Object> vars;
	private JavascriptExecutor js;

	private Logger log = Logger.getLogger(TestRegistracijaPrijavaProveraPodatakaNaProfilu.class.getName());
	private String testName1 = "";
	private String opisTesta = "";
	private boolean success1 = false;
	private TestInfo info1;


	@BeforeEach
	public void setUp(TestInfo info1) {
		System.setProperty("webdriver.chrome.driver", "..\\E kupi\\driver\\chromedriver.exe");
		driver = new ChromeDriver();
		js = (JavascriptExecutor) driver;
		vars = new HashMap<String, Object>();
		this.info1 = info1;
		
	}

	@AfterEach
	public void tearDown() {
		driver.quit();

	}

	static Random random = new Random();
	static final String email = "stefanstefan" + (Math.abs(random.nextInt())) + "@gmail.com";

	@Test
	@DisplayName(value = "Test registracija korisnika")
	public void registracija() throws InterruptedException {
		driver.get("https://www.ekupi.rs/rs/");
		driver.manage().window().maximize();

		driver.findElement(By.linkText("Prijava / Registracija")).click();
		driver.findElement(By.id("register.title")).click();
		{
			WebElement dropdown = driver.findElement(By.id("register.title"));
			dropdown.findElement(By.xpath("//option[. = 'Gospodin']")).click();
			Thread.sleep(2000);
		}
		driver.findElement(By.id("register.firstName")).click();
		driver.findElement(By.id("register.firstName")).sendKeys("Kupac");
		Thread.sleep(2000);
		driver.findElement(By.id("register.lastName")).click();
		driver.findElement(By.id("register.lastName")).sendKeys("Kupac");
		Thread.sleep(2000);
		driver.findElement(By.id("register.email")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("register.email")).sendKeys(email);
		Thread.sleep(2000);
		driver.findElement(By.id("password")).click();
		js.executeScript("window.scrollBy(0,300)");
		Thread.sleep(1000);
		driver.findElement(By.id("password")).sendKeys("kupac123kupac");
		driver.findElement(By.xpath("//input[@id='register.checkPwd']")).click();

		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@id='register.checkPwd']")).sendKeys("kupac123kupac");
		Thread.sleep(2000);
		driver.findElement(By.id("cbNewsletter")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("registerChkTermsConditions")).click();
		Thread.sleep(2000);
		driver.findElement(By.cssSelector(".btn-default")).click();

		
		String stvarniR = driver.findElement(By.xpath("//body/main[1]/div[5]/div[1]/div[1]")).getText();
		String ocekivaniR = "×\n"
				+ "Hvala vam na registraciji.";
		
		
		assertEquals(ocekivaniR, stvarniR);
		
		
		
		
		this.testName1 = info1.getDisplayName();
		this.opisTesta="Test registruje novog korisnika na sistem sa neophodnim podacima";
		success1 = true;
	}

	@Test
	@DisplayName(value = "Test prijava korisnika")
	public void testPrijavaKorisnika() throws InterruptedException {
		driver.get("https://www.ekupi.rs/");
		driver.manage().window().maximize();
		driver.findElement(By.linkText("Prijava / Registracija")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("j_username")).click();
		driver.findElement(By.id("j_username")).sendKeys(email);
		Thread.sleep(2000);
		driver.findElement(By.id("j_password")).click();
		driver.findElement(By.id("j_password")).sendKeys("kupac123kupac");
		Thread.sleep(2000);
		driver.findElement(By.id("submit")).click();
		Thread.sleep(2000);

		driver.findElement(By.linkText("Moj profil")).click();
		Thread.sleep(2000);
		driver.findElement(By.linkText("Ažurirajte Lične Podatke")).click();
		Thread.sleep(2000);
		driver.findElement(By.cssSelector(".col-sm-12:nth-child(4) span:nth-child(2)")).click();
		Thread.sleep(2000);

		var korisnicko_ime = driver.findElement(By.id("profile.email")).getAttribute("value");
		Thread.sleep(2000);
		driver.findElement(By.cssSelector(".aktive > span:nth-child(2)")).click();
		Thread.sleep(2000);
		driver.findElement(By.cssSelector(".col-sm-12:nth-child(3) > .switch-btn > span:nth-child(2)")).click();

		var ime = driver.findElement(By.id("profile.firstName")).getAttribute("value");
		Thread.sleep(2000);

		var prezime = driver.findElement(By.id("profile.lastName")).getAttribute("value");

		assertEquals(email, korisnicko_ime);

		assertEquals(ime, "Kupac");

		assertEquals(prezime, "Kupac");
		testName1 = info1.getDisplayName();
		success1 = true;
		this.opisTesta = "Test vrsi prijavu korisnika na sistem i proveru korisnickih podataka na profilu";
	}
	
    @AfterEach
	public void Izvestaj() {

		StringBuilder sb = new StringBuilder(
				"\n===============================================================" + "\n");
		sb.append("Naziv testa >> " + testName1 + "\n");
		sb.append("Opis testa:\n");
		sb.append(this.opisTesta+"\n");
		sb.append("Test je prosao  >> " + (success1 ? "Uspesno" : "Neuspesno"));

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
