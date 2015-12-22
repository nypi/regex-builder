package io.neuropop.util.regex;

import org.junit.Test;

public class LogicalCharacterClassTest {

	@Test
	public void testNegation() {
		CharacterClass expr = Regex.ch('a').not();
		RegexAssert.assertNotMatches("a", expr);
		RegexAssert.assertMatches("b", expr);
	}

	@Test
	public void testDoubleNegation() {
		CharacterClass expr = Regex.ch('a').not().not();
		RegexAssert.assertMatches("a", expr);
		RegexAssert.assertNotMatches("b", expr);
	}

	@Test
	public void testUnion() {
		CharacterClass expr = Regex.ch('a')
				.union(Regex.ch('b'));
		RegexAssert.assertMatches("a", expr);
		RegexAssert.assertMatches("b", expr);
		RegexAssert.assertNotMatches("c", expr);
	}

	@Test
	public void testUnionChain() {
		CharacterClass expr = Regex.ch('a')
				.union(Regex.ch('b'))
				.union(Regex.ch('c'))
				.union(Regex.ch('d'));
		RegexAssert.assertMatches("a", expr);
		RegexAssert.assertMatches("b", expr);
		RegexAssert.assertMatches("c", expr);
		RegexAssert.assertMatches("d", expr);
		RegexAssert.assertNotMatches("e", expr);
	}

	@Test
	public void testIntersection() {
		CharacterClass expr = Regex.range('a', 'd')
				.intersection(Regex.range('c', 'f'));
		RegexAssert.assertMatches("c", expr);
		RegexAssert.assertMatches("d", expr);
		RegexAssert.assertNotMatches("a", expr);
		RegexAssert.assertNotMatches("b", expr);
		RegexAssert.assertNotMatches("e", expr);
		RegexAssert.assertNotMatches("f", expr);
		RegexAssert.assertNotMatches("g", expr);
		RegexAssert.assertNotMatches("1", expr);
	}

	@Test
	public void testIntersectionChain() {
		CharacterClass expr = Regex.range('a', 'd')
				.intersection(Regex.range('b', 'e'))
				.intersection(Regex.range('c', 'f'))
				.intersection(Regex.range('d', 'g'));
		RegexAssert.assertMatches("d", expr);
		RegexAssert.assertNotMatches("a", expr);
		RegexAssert.assertNotMatches("b", expr);
		RegexAssert.assertNotMatches("c", expr);
		RegexAssert.assertNotMatches("e", expr);
		RegexAssert.assertNotMatches("f", expr);
		RegexAssert.assertNotMatches("g", expr);
	}
}
