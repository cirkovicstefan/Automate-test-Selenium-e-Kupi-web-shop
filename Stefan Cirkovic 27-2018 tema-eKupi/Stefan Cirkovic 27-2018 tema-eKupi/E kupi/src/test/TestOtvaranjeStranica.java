package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

class TestOtvaranjeStranica {

	private WebDriver driver;
	private Map<String, Object> vars;
	private JavascriptExecutor js;
	private List<Long> vremeUcitavanjaSvih = new ArrayList<>();
	private Logger log = Logger.getLogger(TestOtvaranjeStranica.class.getName());
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

	private double prosecnoVremeUcitavanja() {
		return vremeUcitavanjaSvih.stream().mapToDouble(t -> t).average().orElse(0);
	}

	@Test
	@DisplayName(value = "Test performanse otvaranja stranica i prosecno vreme otvaranja")
	void vremeUcitavanjaStranicaTest() {

		long pocetnoVreme = System.currentTimeMillis();

		driver.get("https://www.ekupi.rs/rs/");
		long krajnjeVreme = System.currentTimeMillis();
		long ukupno = krajnjeVreme - pocetnoVreme;
		vremeUcitavanjaSvih.add(ukupno);

		pocetnoVreme = System.currentTimeMillis();
		driver.get("https://www.ekupi.rs/rs/Ra%C4%8Dunari/c/10001");
		krajnjeVreme = System.currentTimeMillis();
		ukupno = krajnjeVreme - pocetnoVreme;
		vremeUcitavanjaSvih.add(ukupno);

		pocetnoVreme = System.currentTimeMillis();
		driver.get("https://www.ekupi.rs/rs/Elektronika/c/10002");
		krajnjeVreme = System.currentTimeMillis();
		ukupno = krajnjeVreme - pocetnoVreme;
		vremeUcitavanjaSvih.add(ukupno);

		pocetnoVreme = System.currentTimeMillis();
		driver.get("https://www.ekupi.rs/rs/Elektronika/Gaming/c/10018");
		krajnjeVreme = System.currentTimeMillis();
		ukupno = krajnjeVreme - pocetnoVreme;
		vremeUcitavanjaSvih.add(ukupno);

		pocetnoVreme = System.currentTimeMillis();
		driver.get("https://www.ekupi.rs/rs/Auto-i-moto-oprema/c/10005");
		krajnjeVreme = System.currentTimeMillis();
		ukupno = krajnjeVreme - pocetnoVreme;
		vremeUcitavanjaSvih.add(ukupno);

		pocetnoVreme = System.currentTimeMillis();
		driver.get("https://www.ekupi.rs/rs/Ku%C4%87a%2C-ba%C5%A1ta-i-alati/c/10006");
		krajnjeVreme = System.currentTimeMillis();
		ukupno = krajnjeVreme - pocetnoVreme;
		vremeUcitavanjaSvih.add(ukupno);

		pocetnoVreme = System.currentTimeMillis();
		driver.get("https://www.ekupi.rs/rs/Ku%C4%87ni-aparati/c/10003");
		krajnjeVreme = System.currentTimeMillis();
		ukupno = krajnjeVreme - pocetnoVreme;
		vremeUcitavanjaSvih.add(ukupno);

		StringBuilder sb = new StringBuilder("\n\n=========== Vreme otvaranja (ucitavanja) stranica ===========\n");
		sb.append("Pocetna >> " + vremeUcitavanjaSvih.get(0) + " ms\n");
		sb.append("Kategorija racunari >> " + vremeUcitavanjaSvih.get(1) + " ms\n");
		sb.append("Kategorija elektronika >> " + vremeUcitavanjaSvih.get(2) + " ms\n");
		sb.append("Kategorija gaming >> " + vremeUcitavanjaSvih.get(3) + " ms\n");
		sb.append("Kategorija auto moto oprema >> " + vremeUcitavanjaSvih.get(4) + " ms\n");
		sb.append("Kategorija kuca alati i basta >> " + vremeUcitavanjaSvih.get(5) + " ms\n");
		sb.append("Kategorija kucni aparati >> " + vremeUcitavanjaSvih.get(6) + " ms\n");
		sb.append("------------------------------------------------------------------\n");
		sb.append("Prosecno vreme ucitavanja >> " + prosecnoVremeUcitavanja() + " ms\n");
		try {

			FileWriter fw = new FileWriter("..\\E kupi\\Izvestaj\\test-report.txt", true);
			fw.write(sb.toString());
			fw.flush();
			fw.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println(sb);
		this.testName = info.getDisplayName();
		success = true;

	}

	@AfterEach
	public void WriteToReport() {

		StringBuilder sb = new StringBuilder(
				"\n===============================================================" + "\n");
		sb.append("Naziv testa >> " + testName + "\n");
		sb.append("Opis testa:\n");
		sb.append("Test vrsi prikaz vremena otvaranja 7 razlicitih stranica, kao i prosecno vreme \n");
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
