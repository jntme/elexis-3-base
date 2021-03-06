package at.medevit.elexis.ehc.vacdoc.service;

import static ch.elexis.core.constants.XidConstants.DOMAIN_AHV;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.ehealth_connector.cda.ch.vacd.CdaChVacd;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import at.medevit.elexis.ehc.vacdoc.service.internal.MeineImpfungenServiceImpl;
import ch.elexis.core.data.activator.CoreHub;
import ch.elexis.data.Anschrift;
import ch.elexis.data.Patient;
import ch.elexis.data.Query;

public class MeineImpfungenServiceTest {
	
	private static Patient patient;
	
	@BeforeClass
	public static void beforeClass(){
		Query<Patient> query = new Query<Patient>(Patient.class);
		List<Patient> patients = query.execute();
		if (!patients.isEmpty()) {
			patient = patients.get(0);
		} else {
			patient = new Patient("Wurst", "Hans", "01.04.1970", Patient.MALE);
			Anschrift anschrift = new Anschrift();
			anschrift.setOrt("Küsnacht ZH");
			anschrift.setPlz("8700");
			anschrift.setStrasse("Testweg 1");
			patient.setAnschrift(anschrift);
		}
		String ahvNumber = getAHVNumber(patient);
		if (ahvNumber == null || ahvNumber.isEmpty()) {
			addAHVNumber(patient, 1);
		}
		CoreHub.mandantCfg.set(MeineImpfungenService.CONFIG_TRUSTSTORE_PATH,
			System.getProperty(MeineImpfungenService.CONFIG_TRUSTSTORE_PATH));
		CoreHub.mandantCfg.set(MeineImpfungenService.CONFIG_TRUSTSTORE_PASS,
			System.getProperty(MeineImpfungenService.CONFIG_TRUSTSTORE_PASS));
		CoreHub.mandantCfg.set(MeineImpfungenService.CONFIG_KEYSTORE_PATH,
			System.getProperty(MeineImpfungenService.CONFIG_KEYSTORE_PATH));
		CoreHub.mandantCfg.set(MeineImpfungenService.CONFIG_KEYSTORE_PASS,
			System.getProperty(MeineImpfungenService.CONFIG_KEYSTORE_PASS));
	}
	
	private static String getAHVNumber(Patient pat){
		return pat.getXid(DOMAIN_AHV);
	}
	
	private static void addAHVNumber(Patient pat, int index){
		String country = "756";
		String number = String.format("%09d", index);
		StringBuilder ahvBuilder = new StringBuilder(country + number);
		ahvBuilder.append(getCheckNumber(ahvBuilder.toString()));
		
		pat.addXid(DOMAIN_AHV, ahvBuilder.toString(), true);
	}
	
	private static String getCheckNumber(String string){
		int sum = 0;
		for (int i = 0; i < string.length(); i++) {
			// reveresd order
			char character = string.charAt((string.length() - 1) - i);
			int intValue = Character.getNumericValue(character);
			if (i % 2 == 0) {
				sum += intValue * 3;
			} else {
				sum += intValue;
			}
		}
		return Integer.toString(sum % 10);
	}
	
	@Test
	public void isValid(){
		MeineImpfungenService service = new MeineImpfungenServiceImpl();
		assertTrue(service.isVaild());
	}
	
	@Test
	public void getPatients(){
		MeineImpfungenService service = new MeineImpfungenServiceImpl();
		List<org.ehealth_connector.common.Patient> patients = service.getPatients(patient);
		assertNotNull(patients);
	}
	
	@Test
	public void getDocuments() throws Exception{
		MeineImpfungenService service = getMeineImpfungenService();
		List<org.ehealth_connector.common.Patient> patients = service.getPatients(patient);
		List<CdaChVacd> documents = service.getDocuments(patients.get(0));
		assertNotNull(documents);
		assertFalse(documents.isEmpty());
		assertNotNull(documents.get(0).getPatient());
		assertNotNull(documents.get(0).getImmunizations());
	}
	
	private MeineImpfungenService getMeineImpfungenService(){
		// Register directly with the service
		BundleContext context =
			FrameworkUtil.getBundle(MeineImpfungenServiceTest.class).getBundleContext();
		ServiceReference<?> reference =
			context.getServiceReference(MeineImpfungenService.class.getName());
		return (MeineImpfungenService) context.getService(reference);
	}
}
