package Test.Model.Services;

import org.junit.Test;
import static org.junit.Assert.*;
import Model.Services.WalletService;
import Model.Entities.WalletMovement;

import java.io.*;
import java.time.LocalDate;
import java.util.List;

public class WalletServiceTest {

	@Test
	public void testGetBalanceReturnsZeroForNonexistentUser() {
		WalletService service = new WalletService();
		double balance = service.getBalance("nonexistent");
		assertEquals(0.0, balance, 0.001);
	}

	@Test
	public void testGetBalanceReturnsCorrectValueForExistingUser() throws Exception {
		String userId = "testuser";
		String walletDir = "Model/Data/Wallets";
		File dir = new File(walletDir);
		if (!dir.exists()) dir.mkdirs();
		File walletFile = new File(walletDir + "/" + userId + "_wallet.json");
		try (FileWriter fw = new FileWriter(walletFile)) {
			fw.write("{\"wallet\": 1234.56}");
		}

		WalletService service = new WalletService();
		double balance = service.getBalance(userId);
		assertEquals(1234.56, balance, 0.001);

		walletFile.delete();
	}

	@Test
	public void testGetBalanceHandlesMalformedJson() throws Exception {
		String userId = "malformed";
		String walletDir = "Model/Data/Wallets";
		File dir = new File(walletDir);
		if (!dir.exists()) dir.mkdirs();
		File walletFile = new File(walletDir + "/" + userId + "_wallet.json");
		try (FileWriter fw = new FileWriter(walletFile)) {
			fw.write("{not a valid json}");
		}

		WalletService service = new WalletService();
		double balance = service.getBalance(userId);
		assertEquals(0.0, balance, 0.001);

		walletFile.delete();
	}

	@Test
	public void testGetBalanceHandlesStringBalance() throws Exception {
		String userId = "stringbalance";
		String walletDir = "Model/Data/Wallets";
		File dir = new File(walletDir);
		if (!dir.exists()) dir.mkdirs();
		File walletFile = new File(walletDir + "/" + userId + "_wallet.json");
		try (FileWriter fw = new FileWriter(walletFile)) {
			fw.write("{\"wallet\": \"789.01\"}");
		}

		WalletService service = new WalletService();
		double balance = service.getBalance(userId);
		assertEquals(789.01, balance, 0.001);

		walletFile.delete();
	}

	@Test
	public void testGetBalanceHandlesInvalidStringBalance() throws Exception {
		String userId = "invalidstring";
		String walletDir = "Model/Data/Wallets";
		File dir = new File(walletDir);
		if (!dir.exists()) dir.mkdirs();
		File walletFile = new File(walletDir + "/" + userId + "_wallet.json");
		try (FileWriter fw = new FileWriter(walletFile)) {
			fw.write("{\"wallet\": \"notanumber\"}");
		}

		WalletService service = new WalletService();
		double balance = service.getBalance(userId);
		assertEquals(0.0, balance, 0.001);

		walletFile.delete();
	}

	@Test
	public void testGetMovementsReturnsListForNonexistentUser() {
		WalletService service = new WalletService();
		List<WalletMovement> movements = service.getMovements("nouser");
		assertNotNull(movements);
		assertTrue(movements.isEmpty());
	}

	@Test
	public void testGetMovementsReturnsListForExistingUser() throws Exception {
		String userId = "movuser";
		String movementsDir = "Model/Data/Movements";
		File dir = new File(movementsDir);
		if (!dir.exists()) dir.mkdirs();
		File movFile = new File(movementsDir + "/" + userId + "_movements.json");
		String json = "[{\"date\": 1718323200000, \"description\": \"desc\", \"amount\": 10.5, \"menuDate\": \"2024-06-14\", \"shift\": \"M\", \"concept\": \"MENU\"}]";
		try (FileWriter fw = new FileWriter(movFile)) {
			fw.write(json);
		}
		WalletService service = new WalletService();
		List<WalletMovement> movements = service.getMovements(userId);
		assertNotNull(movements);
		assertEquals(1, movements.size());
		assertEquals("desc", movements.get(0).getDescription());
		movFile.delete();
	}

	@Test
	public void testDeductBalanceReturnsFalseIfUserNotExists() {
		WalletService service = new WalletService();
		boolean result = service.deductBalance("nouser", 10.0);
		assertFalse(result);
	}

	@Test
	public void testDeductBalanceReturnsFalseIfInsufficientFunds() throws Exception {
		String userId = "lowfunds";
		String walletDir = "Model/Data/Wallets";
		File dir = new File(walletDir);
		if (!dir.exists()) dir.mkdirs();
		File walletFile = new File(walletDir + "/" + userId + "_wallet.json");
		try (FileWriter fw = new FileWriter(walletFile)) {
			fw.write("{\"wallet\": 5.0}");
		}
		WalletService service = new WalletService();
		boolean result = service.deductBalance(userId, 10.0);
		assertFalse(result);
		walletFile.delete();
	}

	@Test
	public void testDeductBalanceReturnsTrueAndUpdatesBalance() throws Exception {
		String userId = "deductuser";
		String walletDir = "Model/Data/Wallets";
		File dir = new File(walletDir);
		if (!dir.exists()) dir.mkdirs();
		File walletFile = new File(walletDir + "/" + userId + "_wallet.json");
		try (FileWriter fw = new FileWriter(walletFile)) {
			fw.write("{\"wallet\": 100.0}");
		}
		WalletService service = new WalletService();
		boolean result = service.deductBalance(userId, 30.0);
		assertTrue(result);
		double balance = service.getBalance(userId);
		assertEquals(70.0, balance, 0.001);
		walletFile.delete();
	}

	@Test
	public void testHasPaidMenuReturnsFalseIfNoMovements() {
		WalletService service = new WalletService();
		boolean paid = service.hasPaidMenu("nouser", LocalDate.of(2024, 6, 14), "M");
		assertFalse(paid);
	}

	@Test
	public void testHasPaidMenuReturnsTrueIfMovementExists() throws Exception {
		String userId = "paidmenu";
		String movementsDir = "Model/Data/Movements";
		File dir = new File(movementsDir);
		if (!dir.exists()) dir.mkdirs();
		File movFile = new File(movementsDir + "/" + userId + "_movements.json");
		String json = "[{\"date\": 1718323200000, \"description\": \"Pago de menú: Plato\", \"amount\": 10.0, \"menuDate\": \"2024-06-14\", \"shift\": \"M\", \"concept\": \"MENU\"}]";
		try (FileWriter fw = new FileWriter(movFile)) {
			fw.write(json);
		}
		WalletService service = new WalletService();
		boolean paid = service.hasPaidMenu(userId, LocalDate.of(2024, 6, 14), "M");
		assertTrue(paid);
		movFile.delete();
	}

	@Test
	public void testUpdateTransactionHistoryCreatesFileAndAppends() throws Exception {
		String userId = "historyuser";
		String movementsDir = "Model/Data/Movements";
		File dir = new File(movementsDir);
		if (!dir.exists()) dir.mkdirs();
		File movFile = new File(movementsDir + "/" + userId + "_movements.json");
		if (movFile.exists()) movFile.delete();
		WalletService service = new WalletService();
		service.updateTransactionHistory(userId, 20.0, "Plato", LocalDate.of(2024, 6, 14), "M");
		assertTrue(movFile.exists());
		List<WalletMovement> movements = service.getMovements(userId);
		assertFalse(movements.isEmpty());
		movFile.delete();
	}
}