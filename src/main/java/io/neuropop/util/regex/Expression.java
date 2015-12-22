package io.neuropop.util.regex;

import java.util.regex.Pattern;

public interface Expression {
	String regex();
	
	default Pattern toPattern(RegexOption... options) {
		int flags = 0;
		if (options != null) {
			for (RegexOption option : options) {
				switch (option) {
					case UNIX_LINES:
						flags |= Pattern.UNIX_LINES;
						break;
					case CASE_INSENSITIVE:
						flags |= Pattern.CASE_INSENSITIVE;
						break;
					case PERMIT_COMMENTS:
						flags |= Pattern.COMMENTS;
						break;
					case MULTILINE:
						flags |= Pattern.MULTILINE;
						break;
					case LITERAL_PARSING:
						flags |= Pattern.LITERAL;
						break;
					case DOT_TERMINATORS:
						flags |= Pattern.DOTALL;
						break;
					case UNICODE_CASE_FOLDING:
						flags |= Pattern.UNICODE_CASE;
						break;
					case CANONICAL_EQUIVALENCE:
						flags |= Pattern.CANON_EQ;
						break;
					case UNICODE_CLASSES:
						flags |= Pattern.UNICODE_CHARACTER_CLASS;
						break;
					default:
						throw new IllegalArgumentException("Option not supported <" + option + ">");
				}
			}
		}
		return Pattern.compile(regex(), flags);
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
