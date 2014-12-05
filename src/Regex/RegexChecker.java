package Regex;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;

/**
 * @author shiqing
 *
 */
public class RegexChecker {
	/**
	 * Do a match check
	 * @param pattern
	 * @param matcher
	 * @return A list of matched result wrapped with pair, of which left is the start index, right is end index
	 */
	public static List<Pair<Integer, Integer>> check (Pattern pattern, Matcher matcher) {
		List<Pair<Integer, Integer>> allPosition = Lists.newArrayList();
        while (matcher.find()) {
            allPosition.add(Pair.of(matcher.start(), matcher.end()));
        }
        return allPosition;
	}
	
	/**
	 * This is how to create {@link Pattern} and {@link Matcher}
	 */
	public static  List<Pair<Integer, Integer>> getMatchedResult(String p, String m) {
		Pattern pattern = Pattern.compile(p);
		Matcher matcher = pattern.matcher(m);

		return RegexChecker.check(pattern, matcher);
	}
	
	public static boolean findMatched(String pattern, String matcher) {
		return !getMatchedResult(pattern, matcher).isEmpty();
	}
}
