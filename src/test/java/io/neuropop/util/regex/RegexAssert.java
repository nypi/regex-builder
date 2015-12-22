package io.neuropop.util.regex;

import java.util.regex.Pattern;

import org.junit.Assert;

public class RegexAssert {
	protected RegexAssert() {
	}

	protected static boolean matches(CharSequence input, Expression expression, String flags) {
		Assert.assertNotNull(expression);

		Pattern pattern = flags == null
				? expression.toPattern()
				: expression.toPattern(flags);

		Assert.assertNotNull(pattern);
		return pattern.matcher(input).matches();
	}

	public static void assertMatches(CharSequence input, Expression expression, String flags) {
		Assert.assertTrue(matches(input, expression, flags));
	}

	public static void assertMatches(CharSequence input, Expression expression) {
		Assert.assertTrue(matches(input, expression, null));
	}

	public static void assertNotMatches(CharSequence input, Expression expression, String flags) {
		Assert.assertFalse(matches(input, expression, flags));
	}

	public static void assertNotMatches(CharSequence input, Expression expression) {
		Assert.assertFalse(matches(input, expression, null));
	}
}
