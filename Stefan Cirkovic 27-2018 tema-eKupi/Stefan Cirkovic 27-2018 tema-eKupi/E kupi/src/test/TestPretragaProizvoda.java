package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

class TestPretragaProizvoda {

	private WebDriver driver;
	private Map<String, Object> vars;
	private JavascriptExecutor js;

	private Logger log = Logger.getLogger(TestPretragaProizvoda.class.getName());
	private String testName = "";
	private boolean success = false;
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
	@DisplayName(value = "Test za pretragu proizvoda ")
	void pretragaProizvodaTest() throws InterruptedException {

		driver.get("https://www.ekupi.rs/");
		driver.manage().window().maximize();
		Thread.sleep(2000);
		driver.findElement(By.id("webpushr-deny-button")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@id='js-site-search-input']")).sendKeys("Nokia 6310 DS Black");
		Thread.sleep(2000);
		driver.findElement(By.xpath(
				"//header/nav[2]/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/form[1]/div[1]/span[1]/button[1]/span[1]"))
				.click();
		Thread.sleep(2000);
		driver.findElement(
				By.xpath("//body/main[1]/div[5]/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div[1]/a[1]/img[1]")).click();
		Thread.sleep(2000);
		driver.navigate().back();
		Thread.sleep(2000);

		testName = info.getDisplayName();
		success = true;
	}

	@AfterEach
	public void Izvestaj() {

		StringBuilder sb = new StringBuilder(
				"\n===============================================================" + "\n");
		sb.append("Naziv testa >> " + testName + "\n");
		sb.append("Opis testa:\n");
		sb.append("Test vrsi pretragu nekog proizvoda u ovom slucaju Nokia 6310 DS Black \n");
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
