package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({ TestRegistracijaPrijavaProveraPodatakaNaProfilu.class, TestDodajProizvodeUKorpuIProveraCene.class,
		TestPoredjenjeProizvoda.class, TestPretragaProizvoda.class, TestOtvaranjeStranica.class,
		TestSortiranjeProizvoda.class, TestFiltriranjeProizvoda.class, TestPrikupljanjePodatakaOKompaniji.class })
class AllTests {

}
