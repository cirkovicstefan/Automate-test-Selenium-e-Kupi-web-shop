package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
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
import org.openqa.selenium.chrome.ChromeDriver;

class TestPrikupljanjePodatakaOKompaniji {

	private WebDriver driver;
	private Map<String, Object> vars;
	private JavascriptExecutor js;
	private Logger log = Logger.getLogger(TestPrikupljanjePodatakaOKompaniji.class.getName());
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
	@DisplayName(value = "Test prikupljanje podataka o kompaniji")
	void podaciOKompanijiTest() throws InterruptedException {

		driver.get("https://www.ekupi.rs/");
		driver.manage().window().maximize();
		Thread.sleep(2000);
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		driver.findElement(By.linkText("O nama")).click();
		Thread.sleep(2000);
		js.executeScript("window.scrollBy(0,400)");
		String text = driver
				.findElement(By.xpath(
						"/html[1]/body[1]/main[1]/div[5]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]"))
				.getText();
		Thread.sleep(2000);

		

		try {

			FileWriter fw = new FileWriter("..\\E kupi\\Izvestaj\\test-report.txt", true);
			String t = "\n============================ Podaci o kompaniji ===================================\n";
			t+=text;
			fw.write(t);
			fw.flush();
			fw.close();

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		this.testName = info.getDisplayName();
        success = true;
	}
	
	@AfterEach
	public void Izvestaj() {

		StringBuilder sb = new StringBuilder(
				"\n===============================================================" + "\n");
		sb.append("Naziv testa >> " + testName + "\n");
		sb.append("Opis testa:\n");
		sb.append("Test prikuplja podatke o kompaniji \n");
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
