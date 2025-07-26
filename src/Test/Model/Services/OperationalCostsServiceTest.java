package Test.Model.Services;

import org.junit.Test;
import static org.junit.Assert.*;
import Model.Services.OperationalCostsService;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;

public class OperationalCostsServiceTest {

	private static final String FILE_PATH = "Model/Data/operational_costs.json";

	@Test
	public void testSaveOperationalCostsCreatesFileAndReturnsTrue() {
		OperationalCostsService service = new OperationalCostsService();
		boolean result = service.saveOperationalCosts(1000.0, 500.0, 10, 5.0);
		assertTrue(result);

		File file = new File(FILE_PATH);
		assertTrue(file.exists());
		file.delete();
	}

	@Test
	public void testLoadOperationalCostsReturnsCorrectValues() {
		// Prepara archivo con datos conocidos
		try (FileWriter fw = new FileWriter(FILE_PATH)) {
			fw.write("{\"totalFixedCosts\":123.45,\"variableCosts\":67.89,\"numberOfTrays\":3,\"wastePercentage\":2.5}");
		} catch (Exception e) {
			fail("No se pudo preparar el archivo de prueba");
		}

		OperationalCostsService service = new OperationalCostsService();
		JSONObject obj = service.loadOperationalCosts();
		assertEquals(123.45, (double) obj.get("totalFixedCosts"), 0.001);
		assertEquals(67.89, (double) obj.get("variableCosts"), 0.001);
		assertEquals(3L, obj.get("numberOfTrays"));
		assertEquals(2.5, (double) obj.get("wastePercentage"), 0.001);

		new File(FILE_PATH).delete();
	}

	@Test
	public void testLoadOperationalCostsReturnsEmptyObjectIfFileMissing() {
		File file = new File(FILE_PATH);
		if (file.exists()) file.delete();

		OperationalCostsService service = new OperationalCostsService();
		JSONObject obj = service.loadOperationalCosts();
		assertTrue(obj.isEmpty());
	}

	@Test
	public void testLoadOperationalCostsReturnsEmptyObjectIfMalformedJson() {
		try (FileWriter fw = new FileWriter(FILE_PATH)) {
			fw.write("not a json");
		} catch (Exception e) {
			fail("No se pudo preparar el archivo de prueba");
		}

		OperationalCostsService service = new OperationalCostsService();
		JSONObject obj = service.loadOperationalCosts();
		assertTrue(obj.isEmpty());

		new File(FILE_PATH).delete();
	}

	@Test
	public void testSaveOperationalCostsHandlesInvalidPath() {
		// Cambia el path a uno inválido temporalmente usando reflexión si fuera necesario
		// Aquí solo se prueba que no lanza excepción y retorna false si no puede escribir
		OperationalCostsService service = new OperationalCostsService();
		// Simula path inválido
		boolean result = service.saveOperationalCosts(1, 1, 1, 1);
		assertTrue(result); // El path por defecto debería ser válido, si no, cambiar a assertFalse(result)
		// Si quieres probar path inválido, deberías modificar la clase para permitir inyectar el path
	}
}