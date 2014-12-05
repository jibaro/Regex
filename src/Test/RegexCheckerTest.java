package Test;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import Regex.RegexChecker;

/**
 * @author shiqing
 *
 */
public class RegexCheckerTest {
	
	/*   String Literals   */
	
	@Test
	public void testAllSameString() {
        List<Pair<Integer, Integer>> result = RegexChecker.getMatchedResult("abc", "abc");
        
        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getLeft().intValue(), 0);
        assertEquals(result.get(0).getRight().intValue(), 3);
	}
	
	@Test
	public void testPartialSameString() {
        List<Pair<Integer, Integer>> result = RegexChecker.getMatchedResult("abc", "helloabcworldabc");
        
        assertEquals(result.size(), 2);
	}
	
	@Test
	public void testDotString() {
		// Dot will map to any single character - one of Metacharacters, others are <([{\^-=$!|]})?*+.>
        List<Pair<Integer, Integer>> result = RegexChecker.getMatchedResult("abc.", "abcabcd");
        // Cannot have overlap
        assertEquals(result.size(), 1);
        
        List<Pair<Integer, Integer>> result2 = RegexChecker.getMatchedResult("abc.", "abcaabcd");
        assertEquals(result2.size(), 2);
	}
	
	
	/*   Character    */
	
	@Test
	public void testSimple() {
		// [abc]   -  a or b or c
		assertTrue(RegexChecker.findMatched("[abc]", "a"));
		assertTrue(RegexChecker.findMatched("[abc]", "b"));
		assertTrue(RegexChecker.findMatched("[abc]", "c"));
		assertFalse(RegexChecker.findMatched("[abc]", "d"));
		assertTrue(RegexChecker.findMatched("[brc]at", "cat"));
	}
	
	@Test
	public void testNegation() {
		// [^abc]   -  not a,b,c
		assertTrue(RegexChecker.findMatched("[^brc]at", "hat"));
		assertFalse(RegexChecker.findMatched("[^brc]at", "bat"));
	}
	
	@Test
	public void testRange() {
		// [a-zA-Z]    random char from a-z or A-Z
		assertTrue(RegexChecker.findMatched("[a-zA-Z]", "h"));
		assertFalse(RegexChecker.findMatched("[a-zA-Z]", "1"));
		assertTrue(RegexChecker.findMatched("[^a-zA-Z]", "1"));  // not a-z and A-Z
		assertTrue(RegexChecker.findMatched("[1-5]", "1"));
	}

	@Test
	public void testUnion() {
		// [a-d[w-z]]   [a-dw-z]   single char a-d or w-z
		assertTrue(RegexChecker.findMatched("[a-d[w-z]]", "b"));
		assertTrue(RegexChecker.findMatched("[a-d[w-z]]", "y"));
		assertTrue(RegexChecker.findMatched("[a-dw-z]", "y"));
		assertFalse(RegexChecker.findMatched("[a-d[w-z]]", "m"));
	}
	
	@Test
	public void testIntersection() {
		// [0-9&&[345]]   3 or 4 or 5
		assertTrue(RegexChecker.findMatched("[0-9&&[345]]", "3"));
		assertFalse(RegexChecker.findMatched("[0-9&&[345]]", "6"));
		assertTrue(RegexChecker.findMatched("[0-5&&[4567]]", "4"));
		assertFalse(RegexChecker.findMatched("[0-5&&[4567]]", "7"));
		assertTrue(RegexChecker.findMatched("[0-5&&4-7]", "4"));
	}
	
	@Test
	public void testSubtraction() {
		// [0-9&&[^345]]   0,1,2,3,6,7,8,9
		assertTrue(RegexChecker.findMatched("[0-9&&[^345]]", "0"));
		assertFalse(RegexChecker.findMatched("[0-9&&[^345]]", "3"));
	}
	
	
	/*    Predefined Character     */
	
	@Test
	public void testPredefinedCharacter() {
		/**
		 *  .	Any character (may or may not match line terminators)
			\d	A digit: [0-9]
			\D	A non-digit: [^0-9]
			\s	A whitespace character: [ \t\n\x0B\f\r]   
				http://stackoverflow.com/questions/2461647/what-the-meaning-of-various-types-of-white-space-in-java
			\S	A non-whitespace character: [^\s]
			\w	A word character: [a-zA-Z_0-9]
			\W	A non-word character: [^\w]
		 */
		assertTrue(RegexChecker.findMatched(".", "0"));
		assertTrue(RegexChecker.findMatched(".", "@"));
		assertTrue(RegexChecker.findMatched(".", " "));
		
		assertTrue(RegexChecker.findMatched("\\d", "1"));
		assertFalse(RegexChecker.findMatched("\\d", "a"));
		
		assertTrue(RegexChecker.findMatched("\\D", "a"));
		assertFalse(RegexChecker.findMatched("\\D", "1"));
		
		assertTrue(RegexChecker.findMatched("\\s", " "));
		assertTrue(RegexChecker.findMatched("\\s", "\n"));
		assertFalse(RegexChecker.findMatched("\\s", "1"));
		assertTrue(RegexChecker.findMatched("\\s", "	"));  // this is the tab

		assertFalse(RegexChecker.findMatched("\\S", " "));
		assertFalse(RegexChecker.findMatched("\\S", "\n"));
		assertTrue(RegexChecker.findMatched("\\S", "1"));
		assertFalse(RegexChecker.findMatched("\\S", "	"));  // this is the tab
		
		assertTrue(RegexChecker.findMatched("\\w", "5"));
		assertTrue(RegexChecker.findMatched("\\w", "a"));
		assertTrue(RegexChecker.findMatched("\\w", "W"));
		assertFalse(RegexChecker.findMatched("\\w", " "));
		
		assertFalse(RegexChecker.findMatched("\\W", "5"));
		assertFalse(RegexChecker.findMatched("\\W", "a"));
		assertFalse(RegexChecker.findMatched("\\W", "W"));
		assertTrue(RegexChecker.findMatched("\\W", " "));
	}
	
	
	/*      Quantifiers     */
	
	@Test
	public void testQuantifiers() {
		/**
		 * Greedy	Reluctant	Possessive		Meaning
			X?			X??			X?+			X, 	once or not at all
			X*			X*?			X*+			X, zero or more times
			X+			X+?			X++			X, one or more times
			X{n}		X{n}?		X{n}+		X, exactly n times
			X{n,}		X{n,}?		X{n,}+		X, at least n times
			X{n,m}		X{n,m}?		X{n,m}+		X, at least n but not more than m times
			
			https://docs.oracle.com/javase/tutorial/essential/regex/quant.html
			Take a look at the difference among three types
		 */
		assertTrue(RegexChecker.findMatched("a?", "a"));
		assertTrue(RegexChecker.findMatched("a*", "a"));
		assertTrue(RegexChecker.findMatched("a+", "a"));

		assertTrue(RegexChecker.findMatched("a?", ""));
		assertTrue(RegexChecker.findMatched("a*", ""));
		assertFalse(RegexChecker.findMatched("a+", ""));

		assertTrue(RegexChecker.findMatched("a{3}", "aaa"));
		assertFalse(RegexChecker.findMatched("a{3}", "aa"));
		assertTrue(RegexChecker.findMatched("a{3,}", "aaa"));
		assertTrue(RegexChecker.findMatched("a{3,}", "aaaaa"));
		assertFalse(RegexChecker.findMatched("a{3,}", "aa"));
		assertTrue(RegexChecker.findMatched("a{3,5}", "aaa"));
		assertTrue(RegexChecker.findMatched("a{3,5}", "aaaaa"));
		assertTrue(RegexChecker.findMatched("a{3,5}", "aaaaaa"));   // This is still true, coz it will find the first 5
		assertEquals(RegexChecker.getMatchedResult("a{3,5}", "aaaaaa").size(), 1);
		assertEquals(RegexChecker.getMatchedResult("a{3,5}", "aaaaaaaa").size(), 2);  // 5+3
	}
	
	@Test
	public void testQuantifierWithGrouping() {
		assertTrue(RegexChecker.findMatched("(dog){3}", "dogdogdog"));
		assertFalse(RegexChecker.findMatched("dog{3}", "dogdogdog"));
		assertTrue(RegexChecker.findMatched("dog{3}", "dogggg"));
		assertTrue(RegexChecker.findMatched("[abc]{3}", "acbabbca"));
		assertFalse(RegexChecker.findMatched("[abc]{3}", "acdaae"));
	}
}
