package io.neuropop.util.regex;

import java.util.regex.Pattern;

import org.junit.Assert;

public class RegexAssert {
	protected RegexAssert() {
	}

	protected static boolean matches(CharSequence input, Expression expression, RegexOption... options) {
		Assert.assertNotNull(expression);
		Pattern pattern = expression.toPattern(options);
		Assert.assertNotNull(pattern);
		return pattern.matcher(input).matches();
	}

	public static void assertMatches(CharSequence input, Expression expression, RegexOption... options) {
		Assert.assertTrue(matches(input, expression, options));
	}

	public static void assertNotMatches(CharSequence input, Expression expression, RegexOption... options) {
		Assert.assertFalse(matches(input, expression, options));
	}
}
