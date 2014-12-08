package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import Regex.SpecificRegexChecker;

public class SpecialRegexCheckerTest {
	@Test
	public void testUrl() {
		assertTrue(SpecificRegexChecker.isUrl("http://google.com"));
		assertTrue(SpecificRegexChecker.isUrl("file://google.com"));
		assertTrue(SpecificRegexChecker.isUrl("ftp://google.com"));
		assertTrue(SpecificRegexChecker.isUrl("https://google.com"));
		assertTrue(SpecificRegexChecker.isUrl("http://1.com"));
		assertTrue(SpecificRegexChecker.isUrl("http://t.tt"));
		assertTrue(SpecificRegexChecker.isUrl("http://_:;!.hell0.#@"));
		
		assertFalse(SpecificRegexChecker.isUrl("htp://_:;!.hell0.#@"));
		assertFalse(SpecificRegexChecker.isUrl("http//_:;!.hell0.#@"));
		assertFalse(SpecificRegexChecker.isUrl("httpp://_:;!.hell0.#@"));
		assertFalse(SpecificRegexChecker.isUrl("htp:/_:;!.hell0.#@"));
		assertFalse(SpecificRegexChecker.isUrl("htp://_:;!.hell0.#@."));    // . end
		assertFalse(SpecificRegexChecker.isUrl("htp://{}"));
	}
	
	@Test
	public void testEmail() {
		assertTrue(SpecificRegexChecker.isEmail("a@a.aa"));
		assertTrue(SpecificRegexChecker.isEmail("0@0.aaa"));
		assertTrue(SpecificRegexChecker.isEmail("_.-z@12-hell0.c.c"));
		assertTrue(SpecificRegexChecker.isEmail("john@gmail.com"));
		assertTrue(SpecificRegexChecker.isEmail("john.lopez@hotmail.com"));
		assertTrue(SpecificRegexChecker.isEmail("john-lopez@yahoo.com"));
		
		assertFalse(SpecificRegexChecker.isEmail("john.gmail.com"));
		assertFalse(SpecificRegexChecker.isEmail("!john@gmail.com"));
		assertFalse(SpecificRegexChecker.isEmail("@john@gmail.com"));
		assertFalse(SpecificRegexChecker.isEmail("john@gmail.something"));
		assertFalse(SpecificRegexChecker.isEmail("john@com"));
	}
}
