package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

class TestSortiranjeProizvoda {

	private WebDriver driver;
	private Map<String, Object> vars;
	private JavascriptExecutor js;

	private Logger log = Logger.getLogger(TestSortiranjeProizvoda.class.getName());
	private String testName = "";
	private boolean success = false;
	private String testOpis = "";
	private TestInfo info;

	@BeforeEach
	public void setUp(TestInfo testInfo) {
		System.setProperty("webdriver.chrome.driver", "..\\E kupi\\driver\\chromedriver.exe");
		driver = new ChromeDriver();
		js = (JavascriptExecutor) driver;
		vars = new HashMap<String, Object>();
		info = testInfo;

	}

	@AfterEach
	public void tearDown() {
		driver.quit();
	}

	
	@Test
	@DisplayName(value = "Test za sortiranje proizvoda prema nazivu")
	void sortiranjeProizvodaPoNazivu() throws InterruptedException {

		driver.get("https://www.ekupi.rs/");
		driver.manage().window().maximize();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[contains(text(),'Elektronika')]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[contains(text(),'Fotoaparati i videokamere')]")).click();
		Thread.sleep(2000);

		/******************* Ocekivana vrednost **************************/

		List<String> listaNazivaProizvoda = driver.findElements(By.cssSelector(".details > .name")).stream()
				.map(t -> t.getText().toUpperCase()).collect(Collectors.toList());

		/**************************
		 * Sortiranje liste u rastucem poretku
		 *************************************/
		List<String> sortURastucemOcekivaneList = listaNazivaProizvoda.parallelStream().sorted()
				.collect(Collectors.toList());

		List<String> sortUOpadajucemOcekivaneListe = listaNazivaProizvoda.parallelStream()
				.sorted(Comparator.reverseOrder()).collect(Collectors.toList());

		/**************************
		 * Stvarna vrednost
		 **************************************/

		driver.findElement(By.id("sortOptions1")).click();

		Thread.sleep(2000);

		driver.findElement(By.xpath(
				"//body/main[1]/div[5]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/form[1]/select[1]/option[4]"))
				.click();

		Thread.sleep(2000);

		List<String> stvarnaListaURastucemPoretku = driver.findElements(By.cssSelector(".details > .name")).stream()
				.map(t -> t.getText().toUpperCase()).collect(Collectors.toList());

		assertTrue(stvarnaListaURastucemPoretku.equals(sortURastucemOcekivaneList));

		Thread.sleep(2000);

		driver.findElement(By.id("sortOptions1")).click();

		Thread.sleep(2000);
		driver.findElement(By.xpath(
				"//body/main[1]/div[5]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/form[1]/select[1]/option[5]"))
				.click();
		Thread.sleep(2000);
		List<String> stvarnaListaUOpadajucemPoretku = driver.findElements(By.cssSelector(".details > .name")).stream()
				.map(t -> t.getText().toUpperCase()).collect(Collectors.toList());

		assertTrue(sortUOpadajucemOcekivaneListe.equals(stvarnaListaUOpadajucemPoretku));

		testName = info.getDisplayName();
		testOpis = "Test vrsi proveru funkcionalnosti za sortiranje u rastucem i opadajucem poretku prema nazivu proizvoda";
		success = true;

	}

	@Test
	@DisplayName(value = "Test za sortiranje proizvoda prema ceni ")
	void sortiranjeProizvodaPremaCeniTest() throws InterruptedException {

		driver.get("https://www.ekupi.rs/");

		driver.manage().window().maximize();

		Thread.sleep(2000);

		driver.findElement(By.xpath("//a[contains(text(),'Elektronika')]")).click();

		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[contains(text(),'Fotoaparati i videokamere')]")).click();

		Thread.sleep(2000);

		List<String> listaCenaProizvoda = driver.findElements(By.cssSelector(".price-block > .price")).stream()
				.parallel().map(t -> t.getText()).collect(Collectors.toList());

		List<Double> listaCenaX = new ArrayList<Double>();

		for (String pom : listaCenaProizvoda) {

			listaCenaX.add(Double.parseDouble(pom.replace(".", "").replace(",00 din", "")));

		}

		List<Double> sortListCenaURastucemOcekivaniRezultat = listaCenaX.parallelStream().sorted().toList();

		List<Double> sortListaCenaUOpadajucemOcekivaniRezultat = listaCenaX.parallelStream()
				.sorted(Comparator.reverseOrder()).collect(Collectors.toList());

		Thread.sleep(2000);
		driver.findElement(By.id("sortOptions1")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(
				"//body/main[1]/div[5]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/form[1]/select[1]/option[6]"))
				.click();

		List<String> listaCenaPosleSortiranjaSajtaStavrna = driver.findElements(By.cssSelector(".price-block > .price"))
				.parallelStream().map(t -> t.getText()).collect(Collectors.toList());

		List<Double> listaCenaProizvodaSortURastucemStavrniRezultat = new ArrayList<Double>();

		for (String pom : listaCenaPosleSortiranjaSajtaStavrna) {

			listaCenaProizvodaSortURastucemStavrniRezultat
					.add(Double.parseDouble(pom.replace(".", "").replaceAll(",00 din", "")));

		}

		assertTrue(sortListCenaURastucemOcekivaniRezultat.equals(listaCenaProizvodaSortURastucemStavrniRezultat));

		Thread.sleep(2000);

		driver.findElement(By.id("sortOptions1")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(
				"//body/main[1]/div[5]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/form[1]/select[1]/option[7]"))
				.click();
		Thread.sleep(2000);
		List<String> listaCenaPosleSortiranjaUOpadajucem = driver.findElements(By.cssSelector(".price-block > .price"))
				.parallelStream().map(t -> t.getText()).collect(Collectors.toList());

		List<Double> listaCenaProizvodaSortUOpadajucemStavrniRezultat = new ArrayList<Double>();

		for (String pom : listaCenaPosleSortiranjaUOpadajucem) {

			listaCenaProizvodaSortUOpadajucemStavrniRezultat
					.add(Double.parseDouble(pom.replace(".", "").replaceAll(",00 din", "")));

		}

		assertTrue(sortListaCenaUOpadajucemOcekivaniRezultat.equals(listaCenaProizvodaSortUOpadajucemStavrniRezultat));

		testName = info.getDisplayName();
		testOpis = "Test vrsi proveru funkcionalnosti za sortiranje u rastucem i opadajucem poretku prema ceni";
		success = true;

	}

	@AfterEach
	public void Izvestaj() {

		StringBuilder sb = new StringBuilder(
				"\n===============================================================" + "\n");
		sb.append("Naziv testa >> " + testName + "\n");
		sb.append("Opis testa:\n");
		sb.append(testOpis+"\n");
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
