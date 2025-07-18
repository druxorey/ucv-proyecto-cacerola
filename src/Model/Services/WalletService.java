
package Model.Services;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import Model.Entities.WalletMovement;
import java.util.Date;
import java.net.URL;

public class WalletService {
	private static final String BALANCE_PATH = "/Model/Data/wallet_balance.json";
	private static final String MOVEMENTS_PATH = "/Model/Data/wallet_movements.json";

	public double getBalance() {
		try {
			JSONParser parser = new JSONParser();
			URL balanceUrl = getClass().getResource(BALANCE_PATH);
			if (balanceUrl == null) return 0.0;
			try (InputStream in = balanceUrl.openStream();
				InputStreamReader isr = new InputStreamReader(in);
				BufferedReader reader = new BufferedReader(isr)) {
				StringBuilder sb = new StringBuilder();
				String line;
				
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}

				JSONObject obj = (JSONObject) parser.parse(sb.toString());
				Object saldoObj = obj.get("saldo");

				if (saldoObj instanceof Number) {
					return ((Number) saldoObj).doubleValue();
				}

				return 0.0;
			}
		} catch (Exception e) {
			return 0.0;
		}
	}

	public List<WalletMovement> getMovements() {
		List<WalletMovement> movements = new ArrayList<>();
		try {
			JSONParser parser = new JSONParser();
			URL movementsUrl = getClass().getResource(MOVEMENTS_PATH);
			if (movementsUrl == null) return movements;
			try (InputStream in = movementsUrl.openStream();
				 InputStreamReader isr = new InputStreamReader(in);
				 BufferedReader reader = new BufferedReader(isr)) {
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				JSONArray arr = (JSONArray) parser.parse(sb.toString());
				for (Object o : arr) {
					JSONObject mov = (JSONObject) o;
					Date date = new Date((long) mov.get("date"));
					String description = (String) mov.get("description");
					double amount = (double) mov.get("amount");
					movements.add(new WalletMovement(date, description, amount));
				}
			}
		} catch (Exception e) {
			System.err.println("Error loading wallet movements: " + e.getMessage());
		}
		return movements;
	}
}
