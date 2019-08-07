package mikolo.springWebApp.features.namedays;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;

import mikolo.springWebApp.features.namedays.model.NameDays;

public class NameDaysApi {

	private NameDaysUtils utilities = new NameDaysUtils();

	public String getDateAndNameDays() {
		String returnString = "";

		try {
			URL url = new URL(NameDaysStatics.URL + utilities.getDay() + NameDaysStatics.MONTH + utilities.getMonth()
					+ NameDaysStatics.COUNTRY);
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent", "Chrome");
			try (Scanner s = new Scanner(connection.getInputStream())) {
				String text = s.nextLine();
				Gson gson = new Gson();

				NameDays data = gson.fromJson(text, NameDays.class);
				returnString = "Dziś jest " + utilities.getDate() + ". Imieniny obchodzą: "
						+ data.getData().getName_pl() + ".";
			}

		} catch (Exception e) {
			e.printStackTrace();
			returnString = "";
			return returnString;
		}
		return returnString;
	}

	public List<String> getNameDays() {
		List<String> namesList = new ArrayList<>();

		try {
			URL url = new URL(NameDaysStatics.URL + utilities.getDay() + NameDaysStatics.MONTH + utilities.getMonth()
					+ NameDaysStatics.COUNTRY);
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent", "Chrome");
			try (Scanner s = new Scanner(connection.getInputStream())) {

				String text = s.nextLine();
				Gson gson = new Gson();

				NameDays data = gson.fromJson(text, NameDays.class);
				String allNames = data.getData().getName_pl();
				namesList = Arrays.asList(allNames.split(", "));
				namesList.forEach(String::trim);
			}
		} catch (Exception e) {
			e.printStackTrace();
			namesList = Collections.emptyList();
			return namesList;
		}

		return namesList;
	}
}
