
package Model.Services;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import Model.Entities.WalletMovement;
import java.util.Date;

public class WalletService {
	private static final String WALLET_DIR = "Model/Data/Wallets";
	private static final String MOVEMENTS_DIR = "Model/Data/Movements";


	public boolean hasPaidMenu(String userId, LocalDate date, String shift) {
		List<WalletMovement> movements = getMovements(userId);
		for (WalletMovement mov : movements) {
			if (date.equals(mov.getMenuDate()) && shift.equals(mov.getShift()) && "MENU".equals(mov.getConcept())) {
				return true;
			}
		}
		return false;
	}
	

	@SuppressWarnings("unchecked")
	public void updateTransactionHistory(String userId, double monto, String plato, LocalDate menuDate, String shift) {
		File dir = new File(MOVEMENTS_DIR);
		if (!dir.exists()) dir.mkdirs();

		String filePath = MOVEMENTS_DIR + "/" + userId + "_movements.json";
		File file = new File(filePath);

		JSONArray movimientos = new JSONArray();
		if (file.exists()) {
			try (FileReader reader = new FileReader(file)) {
				JSONParser parser = new JSONParser();
				Object obj = parser.parse(reader);
				if (obj instanceof JSONArray) {
					movimientos = (JSONArray) obj;
				}
			} catch (Exception e) {
				movimientos = new JSONArray();
			}
		}

		JSONObject mov = new JSONObject();
		mov.put("date", new Date().getTime());
		mov.put("description", "Pago de menú: " + plato);
		mov.put("amount", monto);
		mov.put("menuDate", menuDate.toString());
		mov.put("shift", shift);
		mov.put("concept", "MENU");

		movimientos.add(mov);

		try (FileWriter writer = new FileWriter(file)) {
			writer.write(movimientos.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public double getBalance(String userId) {
		String walletFilePath = WALLET_DIR + "/" + userId + "_wallet.json";
		File walletFile = new File(walletFilePath);
		if (!walletFile.exists()) return 0.0;
		try (BufferedReader reader = new BufferedReader(new FileReader(walletFile))) {
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(sb.toString());
			Object accountBalance = obj.get("wallet");
			if (accountBalance instanceof Number) {
				return ((Number) accountBalance).doubleValue();
			} else if (accountBalance instanceof Long) {
				return ((Long) accountBalance).doubleValue();
			} else if (accountBalance instanceof String) {
				try {
					return Double.parseDouble((String) accountBalance);
				} catch (NumberFormatException ex) {
					return 0.0;
				}
			}
			return 0.0;
		} catch (Exception e) {
			return 0.0;
		}
	}

	
	public List<WalletMovement> getMovements(String userId) {
		List<WalletMovement> movements = new ArrayList<>();
		String filePath = MOVEMENTS_DIR + "/" + userId + "_movements.json";
		File file = new File(filePath);

		if (!file.exists()) return movements;

		try (FileReader reader = new FileReader(file)) {
			JSONParser parser = new JSONParser();
			JSONArray arr = (JSONArray) parser.parse(reader);
			for (Object o : arr) {
				JSONObject mov = (JSONObject) o;
				Date date = new Date((long) mov.get("date"));
				String description = (String) mov.get("description");
				double amount = ((Number) mov.get("amount")).doubleValue();
				LocalDate menuDate = LocalDate.parse((String) mov.get("menuDate"));
				String shift = (String) mov.get("shift");
				String concept = (String) mov.get("concept");
				movements.add(new WalletMovement(date, description, amount, menuDate, shift, concept));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return movements;
	}

	@SuppressWarnings("unchecked")
	public boolean deductBalance(String userId, double monto) {
		String walletFilePath = WALLET_DIR + "/" + userId + "_wallet.json";
		File walletFile = new File(walletFilePath);
		if (!walletFile.exists()) return false;
		try {
			double saldo = getBalance(userId);
			if (saldo < monto) return false;

			double nuevoSaldo = saldo - monto;
			org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
			obj.put("wallet", nuevoSaldo);

			try (FileWriter writer = new FileWriter(walletFile)) {
				writer.write(obj.toJSONString());
			}

			updateTransactionHistory(userId, -monto);

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private void updateTransactionHistory(String userId, double monto) {
	}
}
