package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileWriter;
import java.util.Map;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
import org.junit.rules.TestName;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.google.common.collect.DiscreteDomain;

class TestRunner {

	public static void main(String[] args) {

		
		
		try {

			FileWriter fw = new FileWriter("..\\E kupi\\Izvestaj\\test-report.txt");
			fw.write("");
			fw.flush();
			fw.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		
		Result result = JUnitCore.runClasses(AllTests.class);
		Logger log = Logger.getLogger(TestRunner.class.toString());

		log.info("" + result.getRunCount());

		result.getFailures().forEach(t -> log.warning(t.toString()));

		log.info("Broj izvrsenih testova: " + result.getRunCount());
		log.info("Vreme izvrsenih tesova: " + result.getRunTime());
		log.info("Broj preskocenih testova:" + result.getIgnoreCount());
		log.info("Broj uspesno izvrsenih testova: " + (result.getRunCount() - result.getIgnoreCount()
				- result.getFailureCount() - result.getAssumptionFailureCount()));
		log.info("Broj neuspesnih testova:" + result.getFailureCount());
		log.info("Broj dinamicki preskocenih testova:" + result.getAssumptionFailureCount());
		if (result.wasSuccessful()) {
			log.info("Svi testovi uspesno izvrseni");
		} else {
			log.info("Neki testovi nisu uspesno izvrseni");
		}

	}

}
