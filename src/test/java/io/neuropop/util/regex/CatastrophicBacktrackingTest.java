package io.neuropop.util.regex;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class CatastrophicBacktrackingTest {

	/* compound quantifiers */

	@Test(timeout = 1_000L)
	public void testCompoundQuantifiers() {
		// O(2^n)
		Expression expr = Regex.ch('a').atLeastOnce().anyTimes()
				.and(Regex.ch('b'));
		StringBuilder input = new StringBuilder();
		for (int i = 0; i < 50; ++i)
			input.append("a");
		RegexAssert.assertNotMatches(input, expr);
	}

	@Test(timeout = 1_000L)
	public void testCompoundQuantifiersOnLogicalAnd() {
		// O(2^n)
		Expression expr = Regex.ch('a').atLeastOnce()
				.and(Regex.ch('a').atLeastOnce()).anyTimes()
				.and(Regex.ch('b'));
		StringBuilder input = new StringBuilder();
		for (int i = 0; i < 50; ++i)
			input.append("a");
		RegexAssert.assertNotMatches(input, expr);
	}

	/* contiguous non-exclusive quantified tokens */

	@Test(timeout = 1_000L)
	public void testNonExclusiveContiguousClasses() {
		// O(n^2)
		Expression expr = Regex.digit().atLeastOnce()
				.and(Regex.word().atLeastOnce())
				.and("@");
		StringBuilder input = new StringBuilder();
		for (int i = 0; i < 100_000; ++i)
			input.append(Integer.toString(1));
		RegexAssert.assertNotMatches(input, expr);
	}

	/* contiguous non-exclusive quantified tokens */

	@Test(timeout = 1_000L)
	public void testNonExclusiveContiguousRanges() {
		// O(n^2)
		Expression expr = Regex.range('a', 'b').atLeastOnce()
				.and(Regex.range('b', 'c').atLeastOnce())
				.and('x');
		StringBuilder input = new StringBuilder();
		for (int i = 0; i < 100_000; ++i)
			input.append('b');
		RegexAssert.assertNotMatches(input, expr);
	}

	@Test(timeout = 1_000L)
	public void testNonExclusiveContiguousClassesSeparatedByLiteral() {
		Expression anything = Regex.anyCharacter().anyTimes();
		Expression expr = anything
				.and("a")
				.and(anything)
				.and("b");
		StringBuilder input = new StringBuilder();
		for (int i = 0; i < 100_000; ++i)
			input.append("a");
		RegexAssert.assertNotMatches(input, expr);
	}

	/* non-exclusive alternations */

	@Test(timeout = 1_000L)
	public void testNonExclusiveAlternatingClasses() {
		// O(2^n)
		Expression expr = Regex.digit().or(Regex.word())
				.atLeastOnce()
				.and("@");
		StringBuilder input = new StringBuilder();
		for (int i = 0; i < 50; ++i)
			input.append(Integer.toString(1));
		RegexAssert.assertNotMatches(input, expr);
	}

	@Test(timeout = 1_000L)
	public void testClassicalNonExclusiveAlternation() {
		// O(2^n)
		Expression expr = Regex.str("a").or("aa")
				.atLeastOnce()
				.and("b");
		StringBuilder input = new StringBuilder();
		for (int i = 0; i < 50; ++i)
			input.append("a");
		RegexAssert.assertNotMatches(input, expr);
	}
}
