package io.neuropop.util.regex;

import java.util.regex.Pattern;

public interface Expression {
	String regex();
	
	default Pattern toPattern() {
		return Pattern.compile(regex());
	}
	
	default Pattern toPattern(String flags) {
		int f = 0;
		if (flags != null) {
			for (int i = 0; i < flags.length(); ++i) {
				char c = flags.charAt(i);
				switch (c) {
				case 'i':
					f |= Pattern.CASE_INSENSITIVE;
					break;
				case 'd':
					f |= Pattern.UNIX_LINES;
					break;
				case 'm':
					f |= Pattern.MULTILINE;
					break;
				case 's':
					f |= Pattern.DOTALL;
					break;
				case 'u':
					f |= Pattern.UNICODE_CASE;
					break;
				case 'x':
					f |= Pattern.COMMENTS;
					break;
				case 'U':
					f |= Pattern.UNICODE_CHARACTER_CLASS;
					break;
				default:
					throw new IllegalArgumentException("Invalid flag <" + c + ">");
				}
			}
		}
		return Pattern.compile(regex(), f);
	}
	
	/* logical operators */
	
	default Expression and(Expression y) {
		return new LogicalExpression.And(this, y);
	}
	
	default Expression or(Expression y) {
		return new LogicalExpression.Or(this, y);
	}
	
	/* quantifiers */
	
	default QuantifiedExpression anyTimes() {
		return new QuantifiedExpression(this, 0);
	}

	default QuantifiedExpression atLeastOnce() {
		return new QuantifiedExpression(this, 1);
	}

	default QuantifiedExpression atMostOnce() {
		return new QuantifiedExpression(this, 0, 1);
	}
	
	default QuantifiedExpression atLeast(int n) {
		return new QuantifiedExpression(this, n);
	}	
	
	default QuantifiedExpression atMost(int n) {
		return new QuantifiedExpression(this, 0, n);
	}	
	
	default QuantifiedExpression repeat(int n) {
		return new QuantifiedExpression(this, n, n);
	}	
	
	default QuantifiedExpression repeat(int min, int max) {
		return new QuantifiedExpression(this, min, max);
	}

	/* quantifiers (with mode) */

	default QuantifiedExpression repeatReluctant(int n) {
		return new QuantifiedExpression(this, n, n, 
				QuantificationMode.RELUCTANT);
	}	
	
	default QuantifiedExpression repeatReluctant(int min, int max) {
		return new QuantifiedExpression(this, min, max, 
				QuantificationMode.RELUCTANT);
	}

	default QuantifiedExpression repeatPossessive(int n) {
		return new QuantifiedExpression(this, n, n, 
				QuantificationMode.POSSESSIVE);
	}	
	
	default QuantifiedExpression repeatPossessive(int min, int max) {
		return new QuantifiedExpression(this, min, max, 
				QuantificationMode.POSSESSIVE);
	}
	
	/* captures */
	
	default Capture capture() {
		return new Capture(this);
	}
	
	default Capture capture(String name) {
		return new Capture(this, name);
	}
}
