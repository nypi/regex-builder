package io.neuropop.util.regex;

import java.util.Objects;

import io.neuropop.text.Ascii;

public class Capture extends LogicalExpression {
	private final Expression x;
	private final String name;
	
	public Capture(Expression x) {
		Objects.requireNonNull(x);

		this.x = x;
		this.name = null;
	}
	
	public Capture(Expression x, String name) {
		Objects.requireNonNull(x);

		if (name != null && !name.isEmpty()) {
			if (!Ascii.isLetter(name.charAt(0)))
				throw new IllegalArgumentException("Invalid group name <" + name + ">");
			for (int i = 0; i < name.length(); ++i)
				if (!Ascii.isLetterOrDigit(name.charAt(i)))
					throw new IllegalArgumentException("Invalid group name <" + name + ">");
		}
		
		this.x = x;
		this.name = name;
	}

	@Override
	public String regex() {
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		if (name != null && !name.isEmpty())
			builder.append("?<").append(name).append(">");
		builder.append(x.regex());
		builder.append(")");
		return builder.toString();
	}
}
