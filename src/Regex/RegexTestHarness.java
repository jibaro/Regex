package Regex;
import java.io.Console;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 	Copy from  
 *	https://docs.oracle.com/javase/tutorial/essential/regex/test_harness.html
 */
public class RegexTestHarness {
	/*
	 * If you cannot run this in your Eclipse and get "No console" error output
	 * Notice there is a bug in the Eclipse that System.console() will return null
	 * https://bugs.eclipse.org/bugs/show_bug.cgi?id=122429
	 * So try to run "java RegexTestHarness" in your terminal to make it work
	 */
	public static void main(String[] args){
        Console console = System.console();
        if (console == null) {
            System.err.println("No console.");
            System.exit(1);
        }
        while (true) {

            Pattern pattern = 
            Pattern.compile(console.readLine("%nEnter your regex: "));

            Matcher matcher = 
            pattern.matcher(console.readLine("Enter input string to search: "));

            boolean found = false;
            while (matcher.find()) {
                console.format("I found the text" +
                    " \"%s\" starting at " +
                    "index %d and ending at index %d.%n",
                    matcher.group(),
                    matcher.start(),
                    matcher.end());
                found = true;
            }
            if(!found){
                console.format("No match found.%n");
            }
        }
    }
}
