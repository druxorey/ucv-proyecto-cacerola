package Model.Services;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MenuService {
	private static final String DIR = "Model/Data/Menu";

	public String getMenuFileName(LocalDate date, String shift) {
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd");
		return DIR + "/" + date.format(fmt) + "_" + shift + ".json";
	}

	public boolean menuExists(LocalDate date, String shift) {
		File file = new File(getMenuFileName(date, shift));
		return file.exists();
	}

	@SuppressWarnings("unchecked")
	public void saveMenu(LocalDate date, String shift, String dish, String drink, String dessert) {
		File dir = new File(DIR);
		if (!dir.exists()) dir.mkdirs();
		JSONObject obj = new JSONObject();
		obj.put("date", date.toString());
		obj.put("plato", dish);
		obj.put("bebida", drink);
		obj.put("postre", dessert);
		try (FileWriter writer = new FileWriter(getMenuFileName(date, shift))) {
			writer.write(obj.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public JSONObject loadMenu(LocalDate date, String shift) {
		String fileName = getMenuFileName(date, shift);
		File file = new File(fileName);
		if (!file.exists()) return null;
		try (FileReader reader = new FileReader(file)) {
			JSONParser parser = new JSONParser();
			return (JSONObject) parser.parse(reader);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	
}