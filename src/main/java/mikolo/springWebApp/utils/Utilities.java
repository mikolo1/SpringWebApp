package mikolo.springWebApp.utils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {
	
	public static boolean validateEmailOrPassword(String pattern, String data) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(data);
		return m.matches();
	}
	
	public static String activationCodeGenerator() {
		String code = "";
		String numbers = "0123456789";
		String letters = "abcdefghijklmnopqrstuvwxyz";
		String signs = numbers+letters+letters.toUpperCase();
		
		Random random = new Random();
		for(int i = 0; i<40; i++) {
			code += signs.charAt(random.nextInt(signs.length()));
		}	
		return code;
	}

}
