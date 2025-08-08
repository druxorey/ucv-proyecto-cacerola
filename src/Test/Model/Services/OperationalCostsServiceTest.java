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
		boolean result = service.saveOperationalCostsForWeek(
			java.time.LocalDate.of(2024, 6, 10),
			java.time.LocalDate.of(2024, 6, 14),
			1000.0, 500.0, 10, 5.0, 50.0, 30.0, 20.0
		);
		assertTrue(result);

		File file = new File(FILE_PATH);
		assertTrue(file.exists());
		file.delete();
	}

	@Test
	public void testLoadAllOperationalCostsReturnsJSONArray() {
		OperationalCostsService service = new OperationalCostsService();
		org.json.simple.JSONArray arr = service.loadAllOperationalCosts();
		assertNotNull(arr);
		assertTrue(arr instanceof org.json.simple.JSONArray);
	}

	@Test
	public void testSaveAndLoadOperationalCostsForWeek() {
		OperationalCostsService service = new OperationalCostsService();
		java.time.LocalDate weekStart = java.time.LocalDate.of(2024, 6, 10);
		java.time.LocalDate weekEnd = java.time.LocalDate.of(2024, 6, 14);
		service.saveOperationalCostsForWeek(weekStart, weekEnd, 100, 50, 5, 2, 60, 25, 15);

		org.json.simple.JSONObject obj = service.loadOperationalCostsForWeek(weekStart);
		assertNotNull(obj);
		assertEquals("2024-06-10", obj.get("weekStart"));
		assertEquals("2024-06-14", obj.get("weekEnd"));
		assertEquals(100.0, (double) obj.get("totalFixedCosts"), 0.001);
		assertEquals(50.0, (double) obj.get("variableCosts"), 0.001);
		assertEquals(5L, obj.get("numberOfTrays"));
		assertEquals(2.0, (double) obj.get("wastePercentage"), 0.001);
		assertEquals(60.0, (double) obj.get("studentPct"), 0.001);
		assertEquals(25.0, (double) obj.get("professorPct"), 0.001);
		assertEquals(15.0, (double) obj.get("employeePct"), 0.001);

		new File(FILE_PATH).delete();
	}

	@Test
	public void testGetAllWeeksReturnsCorrectFormat() {
		OperationalCostsService service = new OperationalCostsService();
		java.time.LocalDate weekStart = java.time.LocalDate.of(2024, 6, 10);
		java.time.LocalDate weekEnd = java.time.LocalDate.of(2024, 6, 14);
		service.saveOperationalCostsForWeek(weekStart, weekEnd, 100, 50, 5, 2, 60, 25, 15);

		java.util.List<String> weeks = service.getAllWeeks();
		assertNotNull(weeks);
		assertTrue(weeks.stream().anyMatch(s -> s.contains("2024-06-10->2024-06-14")));

		new File(FILE_PATH).delete();
	}

	@Test
	public void testCalculateCCBAndAmountsReturnsCorrectValues() {
		OperationalCostsService service = new OperationalCostsService();
		org.json.simple.JSONObject costs = new org.json.simple.JSONObject();
		costs.put("totalFixedCosts", 100.0);
		costs.put("variableCosts", 50.0);
		costs.put("numberOfTrays", 5);
		costs.put("wastePercentage", 10.0);
		costs.put("studentPct", 60.0);
		costs.put("professorPct", 25.0);
		costs.put("employeePct", 15.0);

		org.json.simple.JSONObject result = service.calculateCCBAndAmounts(costs);
		double ccb = ((100.0 + 50.0) / 5) * 1.1;
		assertEquals(ccb, (double) result.get("ccb"), 0.001);
		assertEquals(ccb * 0.6, (double) result.get("studentAmount"), 0.001);
		assertEquals(ccb * 0.25, (double) result.get("professorAmount"), 0.001);
		assertEquals(ccb * 0.15, (double) result.get("employeeAmount"), 0.001);
	}

	@Test
	public void testLoadOperationalCostsForWeekReturnsNullIfNotFound() {
		OperationalCostsService service = new OperationalCostsService();
		java.time.LocalDate weekStart = java.time.LocalDate.of(2099, 1, 1);
		org.json.simple.JSONObject obj = service.loadOperationalCostsForWeek(weekStart);
		assertNull(obj);
	}

	@Test
	public void testLoadAllOperationalCostsReturnsEmptyIfFileMissing() {
		File file = new File(FILE_PATH);
		if (file.exists()) file.delete();
		OperationalCostsService service = new OperationalCostsService();
		org.json.simple.JSONArray arr = service.loadAllOperationalCosts();
		assertNotNull(arr);
		assertTrue(arr.isEmpty());
	}

	@Test
	public void testLoadAllOperationalCostsReturnsEmptyIfMalformedJson() throws Exception {
		try (FileWriter fw = new FileWriter(FILE_PATH)) {
			fw.write("not a json");
		}
		OperationalCostsService service = new OperationalCostsService();
		org.json.simple.JSONArray arr = service.loadAllOperationalCosts();
		assertNotNull(arr);
		assertTrue(arr.isEmpty());
		new File(FILE_PATH).delete();
	}
}