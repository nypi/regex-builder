package io.neuropop.util.regex;

import io.neuropop.util.Preconditions;

public class QuantifiedExpression extends LogicalExpression {
	private final Expression x;
	private final int min;
	private final int max;
	private final QuantificationMode mode;
	
	public QuantifiedExpression(Expression x, int min, int max, QuantificationMode mode) {
		Preconditions.checkNotNull(x);
		Preconditions.checkArgument(min >= 0);
		Preconditions.checkArgument(max >= 1 && max >= min);
		Preconditions.checkNotNull(mode);
		
		this.x = x;
		this.min = min;
		this.max = max;
		this.mode = mode;
	}
	
	public QuantifiedExpression(Expression x, int min, int max) {
		this(x, min, max, QuantificationMode.GREEDY);
	}
	
	public QuantifiedExpression(Expression x, int min, QuantificationMode mode) {
		this(x, min, Integer.MAX_VALUE, mode);
	}

	public QuantifiedExpression(Expression x, int min) {
		this(x, min, Integer.MAX_VALUE, QuantificationMode.GREEDY);
	}
	
	@Override
	public String regex() {
		StringBuilder builder = new StringBuilder();
		if (x instanceof CharacterClass)
			builder.append(x.regex());
		else
			builder.append("(?:").append(x.regex()).append(")");
		if (min == 0 && max == 1) {
			builder.append("?");
		} else if (min == 0 && max == Integer.MAX_VALUE) {
			builder.append("*");
		} else if (min == 1 && max == Integer.MAX_VALUE) {
			builder.append("+");
		} else {
			builder.append("{");
			builder.append(Integer.toString(min));
			if (max > min) {
				builder.append(",");
				if (max != Integer.MAX_VALUE)
					builder.append(Integer.toString(max));
			}
			builder.append("}");
		}
		switch (mode) {
		case GREEDY:
			break;
		case RELUCTANT:
			builder.append("?");
			break;
		case POSSESSIVE:
			builder.append("+");
			break;
		default:
			break;
		}
		return builder.toString();
	}
}
