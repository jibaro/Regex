package Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author shiqing
 * 
 *	Placeholder for specific regex checker, like url, img 
 *
 *	Some reference : 
 *	http://code.tutsplus.com/tutorials/8-regular-expressions-you-should-know--net-6149
 *	http://stackoverflow.com/questions/3512471/non-capturing-group
 */
public class SpecificRegexChecker {
	
	private enum RegexEnum {
		URL("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"),
		EMAIL("^([a-z0-9_\\.-]+)@([a-z0-9\\.-]+)\\.([a-z\\.]{2,6})$"),
		IP("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"); 
		
		private String val;
		
		private RegexEnum(String val) {
			this.val = val;
		}
		
		public String getVal() {
			return this.val;
		}
	}
	
	public static boolean isUrl(String input) {
		return findMatch(RegexEnum.URL.getVal(), input);
	}
	
	public static boolean isEmail(String input) {
		return findMatch(RegexEnum.EMAIL.getVal(), input);
	}
	
	public static boolean isIp(String input) {
		return findMatch(RegexEnum.IP.getVal(), input);
	}
	
	public static boolean findMatch(String p, String input) {
		Pattern pattern = Pattern.compile(p);
		Matcher matcher = pattern.matcher(input);
		boolean r = matcher.find();
		return r;
	}
}
