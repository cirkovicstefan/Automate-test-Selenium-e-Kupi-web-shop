package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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

class TestFiltriranjeProizvoda {

	private WebDriver driver;
	private Map<String, Object> vars;
	private JavascriptExecutor js;

	private Logger log = Logger.getLogger(TestFiltriranjeProizvoda.class.getName());
	private String testName = "";
	private boolean success = false;
	private TestInfo info;
	private String testOpis = "";

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
	@DisplayName(value = "Test za filtriranje proizvoda ")
	void filterProizvodaTest() throws InterruptedException {

		driver.get("https://www.ekupi.rs/");
		driver.manage().window().maximize();
		Thread.sleep(2000);

		driver.findElement(By.xpath("//a[contains(text(),'Računari')]")).click();

		Thread.sleep(2000);

		driver.findElement(By.xpath("//a[contains(text(),'Laptopovi i oprema')]")).click();

		Thread.sleep(2000);

		driver.findElement(
				By.xpath("//body/main[1]/div[5]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/ul[1]/li[2]/span[1]/a[1]"))
				.click();

		Thread.sleep(2000);

		List<String> listaProizvodaOcekivaniRezultat = driver.findElements(By.cssSelector(".details > .name"))
				.parallelStream().map(t -> t.getText().toUpperCase())
				.filter(t -> t.contains("HP") && t.contains("16GB") && t.contains("I7")).collect(Collectors.toList());

		Thread.sleep(2000);
		driver.findElement(By.linkText("2")).click();
		listaProizvodaOcekivaniRezultat.addAll(driver.findElements(By.cssSelector(".details > .name")).parallelStream()
				.map(t -> t.getText().toUpperCase())
				.filter(t -> t.contains("HP") && t.contains("16GB") && t.contains("I7")).collect(Collectors.toList()));
		Thread.sleep(2000);

		driver.findElement(By.linkText("3")).click();

		listaProizvodaOcekivaniRezultat.addAll(driver.findElements(By.cssSelector(".details > .name")).parallelStream()
				.map(t -> t.getText().toUpperCase())
				.filter(t -> t.contains("HP") && t.contains("16GB") && t.contains("I7")).collect(Collectors.toList()));
		Thread.sleep(2000);

		driver.findElement(By.linkText("4")).click();

		listaProizvodaOcekivaniRezultat.addAll(driver.findElements(By.cssSelector(".details > .name")).parallelStream()
				.map(t -> t.getText().toUpperCase())
				.filter(t -> t.contains("HP") && t.contains("16GB") && t.contains("I7")).collect(Collectors.toList()));
		Thread.sleep(2000);

		driver.findElement(By.linkText("5")).click();
		listaProizvodaOcekivaniRezultat.addAll(driver.findElements(By.cssSelector(".details > .name")).parallelStream()
				.map(t -> t.getText().toUpperCase())
				.filter(t -> t.contains("HP") && t.contains("16GB") && t.contains("I7")).collect(Collectors.toList()));
		Thread.sleep(2000);

		driver.findElement(By.linkText("6")).click();

		listaProizvodaOcekivaniRezultat.addAll(driver.findElements(By.cssSelector(".details > .name")).parallelStream()
				.map(t -> t.getText().toUpperCase())
				.filter(t -> t.contains("HP") && t.contains("16GB") && t.contains("I7")).collect(Collectors.toList()));
		Thread.sleep(2000);

		listaProizvodaOcekivaniRezultat.forEach(System.out::println);

		Thread.sleep(2000);
		js.executeScript("window.scrollBy(0,500)");
		Thread.sleep(2000);
		driver.findElement(By.cssSelector(".facet:nth-child(5) li:nth-child(6) .facet__list__text")).click();
		Thread.sleep(2000);

		js.executeScript("window.scrollBy(0,1000)");

		Thread.sleep(2000);

		driver.findElement(By.cssSelector(".facet:nth-child(8) li:nth-child(4) .facet__list__text")).click();

		Thread.sleep(2000);
		js.executeScript("window.scrollBy(0,1200)");
		Thread.sleep(2000);
		driver.findElement(By.cssSelector(".facet:nth-child(8) .facet__list__text")).click();

		Thread.sleep(2000);

		List<String> listaProizvodaStvarniRezultat = driver.findElements(By.cssSelector(".details > .name"))
				.parallelStream().map(t -> t.getText().toUpperCase()).collect(Collectors.toList());

		System.out.println("=========================================================");

		listaProizvodaStvarniRezultat.forEach(System.out::println);

		testName = info.getDisplayName();
		testOpis = "Test vrsi proveru funkcionalnosti filtera za laptop racunare\n  "
				+ "filtriranje se vrsi po modeli laptopa tipu procesora i ram memoriji,\n "
				+ "test je pronasao gresku i mozemo zakljuciti da ne funkcionise filter na e-prodavnici kako treba\n";
		success = true;

		assertFalse(listaProizvodaOcekivaniRezultat.equals(listaProizvodaStvarniRezultat));
	}

	@AfterEach
	public void Izvestaj() {

		StringBuilder sb = new StringBuilder(
				"\n===============================================================" + "\n");
		sb.append("Naziv testa >> " + testName + "\n");
		sb.append("Opis testa:\n");
		sb.append(testOpis + " \n");
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
