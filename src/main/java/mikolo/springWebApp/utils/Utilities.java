package mikolo.springWebApp.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {
	
	public static boolean validateEmailOrPassword(String pattern, String data) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(data);
		return m.matches();
	}

}
