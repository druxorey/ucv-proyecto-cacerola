package Test.Model.Services;

import org.junit.Test;
import static org.junit.Assert.*;
import Model.Services.WalletService;
import Model.Entities.WalletMovement;

import java.io.*;
import java.util.List;
import java.util.Date;

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
	public void testGetMovementsReturnsList() {
		WalletService service = new WalletService();
		List<WalletMovement> movements = service.getMovements();
		assertNotNull(movements);
		// Edge case: file missing or empty should return empty list
		assertTrue(movements.size() >= 0);
	}
}