package Test.Model.Services;

import org.junit.*;
import static org.junit.Assert.*;
import Model.Services.RegisterService;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RegisterServiceTest {
	private static final String REQUEST_DIR = "Model/Data/Requests";
	private static final String COUNT_FILE = REQUEST_DIR + "/request_counts.txt";

	@Before
	public void cleanUp() throws Exception {
		File dir = new File(REQUEST_DIR);
		if (dir.exists()) {
			for (File file : dir.listFiles()) {
				file.delete();
			}
		}
		File countFile = new File(COUNT_FILE);
		if (countFile.exists()) countFile.delete();
	}

	@Test
	public void testSaveRegistrationRequestCreatesFile() throws Exception {
		RegisterService.saveRegistrationRequest("Juan", "Pérez", "juan@test.com", "12345678");
		File dir = new File(REQUEST_DIR);
		File[] files = dir.listFiles((_, name) -> name.matches("S\\d{6}\\.json"));
		assertNotNull(files);
		assertEquals(1, files.length);

		try (BufferedReader reader = new BufferedReader(new FileReader(files[0]))) {
			String json = reader.readLine();
			assertTrue(json.contains("Juan"));
			assertTrue(json.contains("Pérez"));
			assertTrue(json.contains("juan@test.com"));
			assertTrue(json.contains("12345678"));
		}
	}

	@Test
	public void testSaveRegistrationRequestIncrementsFileNumber() throws Exception {
		RegisterService.saveRegistrationRequest("A", "B", "a@b.com", "1");
		RegisterService.saveRegistrationRequest("C", "D", "c@d.com", "2");
		File dir = new File(REQUEST_DIR);
		File[] files = dir.listFiles((_, name) -> name.matches("S\\d{6}\\.json"));
		assertNotNull(files);
		assertEquals(2, files.length);
	}

	@Test
	public void testRequestCountFileIsUpdated() throws Exception {
		RegisterService.saveRegistrationRequest("A", "B", "a@b.com", "1");
		assertTrue(new File(COUNT_FILE).exists());
		String content = new String(Files.readAllBytes(Paths.get(COUNT_FILE)));
		assertTrue(content.contains(",1"));
	}

	@Test
	public void testDeleteRegistrationRequestByUserIdDeletesFile() throws Exception {
		RegisterService.saveRegistrationRequest("Juan", "Pérez", "juan@test.com", "deleteMe");
		File dir = new File(REQUEST_DIR);
		File[] files = dir.listFiles((_, name) -> name.matches("S\\d{6}\\.json"));
		assertNotNull(files);
		assertEquals(1, files.length);

		RegisterService.deleteRegistrationRequestByUserId("deleteMe");
		files = dir.listFiles((_, name) -> name.matches("S\\d{6}\\.json"));
		assertTrue(files == null || files.length == 0);
	}

	@Test
	public void testDeleteRegistrationRequestByUserIdWithNonexistentUserDoesNothing() throws Exception {
		RegisterService.saveRegistrationRequest("Juan", "Pérez", "juan@test.com", "exists");
		RegisterService.deleteRegistrationRequestByUserId("nope");
		File dir = new File(REQUEST_DIR);
		File[] files = dir.listFiles((_, name) -> name.matches("S\\d{6}\\.json"));
		assertNotNull(files);
		assertEquals(1, files.length);
	}

	@Test
	public void testSendAppointmentEmailWithNonexistentUserDoesNothing() throws Exception {
		RegisterService.sendAppointmentEmail("nope");
	}

	@Test(expected = Exception.class)
	public void testSendAppointmentEmailThrowsIfEmailInvalid() throws Exception {
		RegisterService.saveRegistrationRequest("Juan", "Pérez", "notanemail", "bademail");
		RegisterService.sendAppointmentEmail("bademail");
	}
}